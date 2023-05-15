//import java.util.Optional;

class EventLeaves implements Event {
    private final Customer customer;
    private final Double time;

    EventLeaves(Customer customer, Double time) { 
        this.customer = customer;
        this.time = time;
    }

    @Override
    public Pair<Pair<Event, Integer>, ImList<Server>> process(ImList<Server> servers) {
        //Optional<Event> optional = Optional.empty();
        return new Pair<Pair<Event, Integer>, ImList<Server>>(new Pair<Event, 
                Integer>(this, 0), servers);
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
        return false;
    }

    @Override
    public String toString() {
        return String.format("%.3f %d leaves", 
                this.time, this.customer.getNumber());
    }

    @Override
    public boolean isLeave() {
        return true;
    }

    @Override
    public Double getWaitTime() {
        return -1.0;
    }
}
