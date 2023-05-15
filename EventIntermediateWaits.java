//import java.util.Optional;

class EventIntermediateWaits implements Event {
    private final Customer customer;
    private final int serverId;
    private final Double time;

    EventIntermediateWaits(Customer customer, int serverId, Double time) {
        this.customer = customer;
        this.serverId = serverId;
        this.time = time;
    }

    @Override
    public Pair<Pair<Event, Integer>, ImList<Server>> process(ImList<Server> servers) {
        Server server = servers.get(this.serverId);
        if (!server.getIsServed() && server.getCustomerQueue().get(0) == this.customer) {
            Server newServer = server.serveCustomer().removeFromQueue();//not yet edit the time 
            /*Server newServer = new Server(server.getName(), server.getQueueMax(), 
              server.getCustomerQueue(), server.getIsServed(), 
              server.getServerFreeTime() + customer.getServiceTime());*/
            ImList<Server> newServerList = servers.set(this.serverId, newServer);
            EventServed newEvent = new EventServed(this.customer, this.serverId, 
                    server.getServerFreeTime());
            //Optional<Event> optional = Optional.empty();
            return new Pair<Pair<Event, Integer>, ImList<Server>>(new Pair<Event, 
                    Integer>(newEvent, 1), newServerList);
        } else {
            EventIntermediateWaits newEvent = new EventIntermediateWaits(this.customer,
                    this.serverId, server.getServerFreeTime());
            return new Pair<Pair<Event, Integer>, ImList<Server>>(new Pair<Event,
                    Integer>(newEvent, 1), servers);
        }
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
        return "";
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
