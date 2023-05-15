import java.util.Optional;

interface Event {
    String toString();

    double getTime();

    int getCustomerNumber();

    Pair<Pair<Event, Integer>, ImList<Server>> process(ImList<Server> sl);

    boolean isServe();

    Double getWaitTime();

    boolean isLeave();
}
