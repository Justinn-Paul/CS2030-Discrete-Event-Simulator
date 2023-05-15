import java.util.function.Supplier;
//import java.util.function.DoubleBinaryOperator;

class Server {
    private final int name;
    private final int qmax;
    private final ImList<Customer> customerQueue;
    private final boolean isServed;
    private final double serverFreeTime;
    private final Supplier<Double> restTime;
    
    Server(int name, int qmax, ImList<Customer> customerQueue, boolean isServed, 
            double serverFreeTime, Supplier<Double> restTime) {
        this.name = name;
        this.qmax = qmax;
        this.customerQueue = customerQueue;
        this.isServed = isServed;
        this.serverFreeTime = serverFreeTime; 
        this.restTime = restTime;
    }

    public Server serveCustomer() {
        return new Server(this.name, this.qmax, this.customerQueue, true, this.serverFreeTime,
                restTime);
    }

    public Server checkRest() {
        return new Server(this.name, this.qmax, this.customerQueue, true,
                this.serverFreeTime + this.restTime.get(), restTime);
    }

    public Server doneServing() {
        return new Server(this.name, this.qmax, this.customerQueue, false, this.serverFreeTime,
                restTime);
    }

    public Server addToQueue(Customer customer) {
        ImList<Customer> newCustomerQueue = this.customerQueue.add(customer);
        return new Server(this.name, this.qmax, newCustomerQueue, this.isServed, 
                this.serverFreeTime, restTime);
    }

    public Server removeFromQueue() {
        //assert this.customerQueue.size() > 0;
        if (this.customerQueue.isEmpty()) {
            return this;
        }
        ImList<Customer> newCustomerQueue = this.customerQueue.remove(0);
        return new Server(this.name, this.qmax, newCustomerQueue, this.isServed, 
                this.serverFreeTime, restTime);
    }

    public double getServerFreeTime() {
        return this.serverFreeTime;
    }

    public int getQueueMax() {
        return this.qmax;
    }

    public ImList<Customer> getCustomerQueue() {
        return this.customerQueue;
    }

    public double getQueueDuration() {
        double qDuration = 0;
        for (int i = 0; i < this.customerQueue.size() - 1; i++) {
            qDuration += this.customerQueue.get(i).getServiceTime();
        }
        return qDuration;
    }

    public Customer getFirstCustomer() {
        //assert this.customerQueue.size() > 0;
        return this.customerQueue.get(0);
    }

    public boolean getIsServed() {
        return this.isServed;
    }

    public boolean isIdle() {
        return !this.isServed;
    }

    public boolean isQueueFull() {
        return this.customerQueue.size() == this.qmax;
    }

    public boolean isQueueEmpty() {
        return this.customerQueue.size() == 0;
    }

    public int getName() {
        return this.name;
    }

    public Supplier<Double> getRestTime() {
        return this.restTime;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %b, %f)", this.name, this.qmax, 
                this.isServed, this.serverFreeTime);
    }
}
