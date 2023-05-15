import java.util.List;

class Customer {
    private final int number;
    private final double arrivalTime;
    private final Lazy<Double> serviceTime;


    Customer(int number, double arrivalTime, Lazy<Double> serviceTime) {
        this.number = number;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    /*Customer() {
        this.number = -1;
        this.arrivalTime = -1;
        this.serviceTime = -1;
    }*/

    public int getNumber() {
        return this.number;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public double getServiceTime() {
        return this.serviceTime.get();
    }

    @Override
    public String toString() {
        return String.format("Customer %d", this.number);
    }
}
