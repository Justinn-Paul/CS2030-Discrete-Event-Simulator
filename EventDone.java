//import java.util.Optional;

class EventDone implements Event {
    private final Customer customer;
    private final int serverId;
    private final Double time;

    EventDone(Customer customer, int serverId, Double time) {
        this.customer = customer;
        this.serverId = serverId;
        this.time = time;
    }

    @Override
    public Pair<Pair<Event, Integer>, ImList<Server>> process(ImList<Server> servers) {
        Server server = servers.get(this.serverId);
        double prevTime = server.getServerFreeTime();
        //System.out.println("prevTime");
        //System.out.println(prevTime);
        Server newServer = server.checkRest();
        double nextTime = newServer.getServerFreeTime();
        //System.out.println(nextTime);
        if (prevTime < nextTime) {
            ImList<Server> newServerList = servers.set(this.serverId, newServer);
            EventRest newEvent = new EventRest(customer, this.serverId, 
                newServer.getServerFreeTime());
            return new Pair<Pair<Event, Integer>, ImList<Server>>(new Pair<Event,
                    Integer>(newEvent, 1), newServerList);
        }
        newServer = server.doneServing();
        //Optional<Event> optional = Optional.empty();
        ImList<Server> newServerList = servers.set(this.serverId, newServer);
        //optional = Optional.of(newEvent);
        return new Pair<Pair<Event, Integer>, ImList<Server>>(new Pair<Event, 
                Integer>(this, 0), newServerList);
    }

    @Override
    public int getCustomerNumber() {
        return customer.getNumber();
    }

    @Override
    public double getTime() {
        return this.time;
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
    public String toString() {
        return String.format("%.3f %d done serving by %s", this.time,
                customer.getNumber(), this.serverId + 1);
    }

    @Override
    public Double getWaitTime() {
        return -1.0;
    }
}

