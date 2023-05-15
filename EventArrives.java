//import java.util.Optional;

class EventArrives implements Event {
    private final Customer customer;
    private final Double time;

    EventArrives(Customer customer) {
        this.customer = customer;
        this.time = customer.getArrivalTime();
    }

    @Override
    public Pair<Pair<Event, Integer>, ImList<Server>> process(ImList<Server> servers) {
        for (int i = 0; i < servers.size(); i++) {
            if (servers.get(i).isIdle()) {
                Server server = servers.get(i);
                Server newServer = new Server(server.getName(), server.getQueueMax(), 
                        server.getCustomerQueue(), true, this.time, server.getRestTime());
                ImList<Server> newServers = servers.set(i, newServer); 
                EventServed newEvent = new EventServed(this.customer, i, this.time);
                //Optional<Event> optional = Optional.of(newEvent);
                return new Pair<Pair<Event, Integer>, ImList<Server>>(new Pair<Event,
                        Integer>(newEvent, 1), newServers);
            }
        }
        for (int i = 0; i < servers.size(); i++) {
            if (!servers.get(i).isQueueFull()) {
                Server server = servers.get(i);
                Server newServer = server.addToQueue(this.customer);
                ImList<Server> newServers = servers.set(i, newServer);
                EventWaits newEvent = new EventWaits(customer, i, this.time);
                //Optional<Event> optional = Optional.of(newEvent);
                return new Pair<Pair<Event, Integer>, ImList<Server>>(new Pair<Event, 
                        Integer>(newEvent, 1), newServers);
            }
        }
        EventLeaves newEvent = new EventLeaves(customer, this.time);
        //Optional<Event> optional = Optional.of(newEvent);
        return new Pair<Pair<Event, Integer>, ImList<Server>>(new Pair<Event, 
                Integer>(newEvent, 1), servers);
    }

    @Override
    public int getCustomerNumber() {
        return this.customer.getNumber();
    }

    @Override
    public double getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        return String.format("%.3f %d arrives", 
                this.time, this.customer.getNumber());
    }

    @Override
    public boolean isServe() {
        return false;
    }

    @Override
    public boolean isLeave() {
        return false;
    }

    @Override
    public Double getWaitTime() {
        return -1.0;
    }
}
