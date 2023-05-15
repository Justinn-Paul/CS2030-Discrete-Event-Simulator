//import java.util.Optional;

class EventRest implements Event {
    private final Customer customer;
    private final int serverId;
    private final Double time;

    EventRest(Customer customer, int serverId, Double time) {
        this.customer = customer;
        this.serverId = serverId;
        this.time = time;
    }

    @Override
    public Pair<Pair<Event, Integer>, ImList<Server>> process(ImList<Server> servers) {
        Server server = servers.get(this.serverId);
        Server newServer = server.doneServing();
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
        return "";
    }

    @Override
    public Double getWaitTime() {
        return -1.0;
    }
}

