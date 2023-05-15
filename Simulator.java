import java.util.Optional;
import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int qmax;
    private final ImList<Pair<Double,Supplier<Double>>> inputTimes;
    private final Supplier<Double> restTimes;

    Simulator(int numOfServers, int qmax, ImList<Pair<Double,Supplier<Double>>> inputTimes,
            Supplier<Double> restTimes) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;
        this.inputTimes = inputTimes;
        this.restTimes = restTimes;
    }

    private PQ<Event> initEventQueue(ImList<Pair<Double,Supplier<Double>>> inputTimes) { 
        int currCustomerNum = 1; //initialise PQ with ArrivalEvents
        PQ<Event> pq = new PQ<Event>(new EventComparator());

        for (int c = 0; c < inputTimes.size(); c++) {
            Customer customer = new Customer(currCustomerNum, inputTimes.get(c).first(),
                    Lazy.<Double>of(inputTimes.get(c).second()));
            EventArrives ea = new EventArrives(customer);
            pq = pq.add(ea);
            currCustomerNum++;
        }
        return pq;
    }

    private ImList<Server> initServers(int numOfServers, Supplier<Double> restTimes) { 
        // initialises a list of servers
        ImList<Server> servers = new ImList<Server>();
        for (int i = 0; i < numOfServers; i++) {
            ImList<Customer> customerList = new ImList<Customer>();
            Server server = new Server(i + 1, qmax, customerList, false, 0, 
                    restTimes);//Lazy.<Double>of(restTimes));
            servers = servers.add(server);
        }
        return servers;
    }

    public String simulate() {
        double totalWaitTime = 0;
        int numServed = 0;
        int numLeft = 0;
        ImList<Event> output = new ImList<Event>();
        PQ<Event> events = this.initEventQueue(this.inputTimes);
        ImList<Server> servers = this.initServers(this.numOfServers, this.restTimes);

        while (!events.isEmpty()) { 
            Pair<Event, PQ<Event>> pr = events.poll();
            Event currEvent = pr.first();
            if (currEvent.isLeave()) {
                numLeft++;
            }
            if (currEvent.isServe()) {
                numServed++;
                totalWaitTime += currEvent.getWaitTime();
            }

            events = pr.second();
            if (currEvent.toString().length() > 0) {
                output = output.add(currEvent);
            }
            // System.out.println(currEvent);
            Pair<Pair<Event, Integer>, ImList<Server>> pair = currEvent.process(servers);
            servers = pair.second();
            //System.out.println(servers);
            if (pair.first().second() == 1) {
                events = events.add(pair.first().first());
            }
        }
        
        for (int s = 0; s < output.size(); s++) {
            System.out.println(output.get(s));
        }

        return String.format("[%.3f %d %d]", totalWaitTime / numServed, numServed, numLeft);
    }
    
}
