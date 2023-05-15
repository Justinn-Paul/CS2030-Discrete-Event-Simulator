//import java.util.Optional;

class EventServed implements Event {
    private final Customer customer;
    private final int serverId;
    private final Double time;

    EventServed(Customer customer, int serverId, Double time) {
        this.customer = customer;
        this.serverId = serverId;
        this.time = time;
    }

    @Override
    public Pair<Pair<Event, Integer>, ImList<Server>> process(ImList<Server> servers) {
        Server server = servers.get(this.serverId);
        Server newServer = new Server(server.getName(), server.getQueueMax(), 
                server.getCustomerQueue(), true, server.getServerFreeTime() 
                + customer.getServiceTime(), server.getRestTime());
        ImList<Server> newServerList = servers.set(this.serverId, newServer);
        EventDone newEvent = new EventDone(customer, this.serverId, 
                newServer.getServerFreeTime()); 
        //Optional<Event> optional = Optional.of(newEvent);
        return new Pair<Pair<Event, Integer>, ImList<Server>>(new Pair<Event, 
                Integer>(newEvent, 1), newServerList);
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
    public boolean isServe() {
        return true;
    }

    @Override
    public boolean isLeave() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("%.3f %d serves by %s", this.time,
                this.customer.getNumber(), this.serverId + 1);
    }

    @Override
    public Double getWaitTime() {
        return this.time - this.customer.getArrivalTime();
    }
}

