import java.util.Comparator;

class EventComparator implements Comparator<Event> {

    public int compare(Event a, Event b) {
        if (a.getTime() == b.getTime()) {
            return a.getCustomerNumber() - b.getCustomerNumber();
        } 
        double diff = a.getTime() - b.getTime();
        if (diff < 0) {
            return -1;
        } else {
            return 1;
        }
    }

}
