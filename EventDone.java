package cs2030.simulator;

/**
 * Represents an event with state "done".
 */

public class EventDone extends Event {
    private final int serverID;
    /**
     * Constructs an event with state "done" 
     * based on its customer's information and specified time.
     * @param cust customer done being served.
     * @param time specified time which is 
     *     current event time added with server's service time.
     */

    public EventDone(Customer cust, double time, int serverID) {
        super(cust, time);
        this.serverID = serverID;
    }

    /**
     * Determines what event will occur next based on current condition.
     */

    @Override
    public Event determineNext() {
        Server server = sh.getServer(this.serverID);

        if (server.canRest()) {
            sh = sh.updateServer(server.isNowResting());
            return new ServerRest(server.getCust(), this.getTime(), this.serverID);
        } else {
            return new ServerBack(server.getCust(), this.getTime(), this.serverID);
        }
    }

    /**
     * Returns a string representation of event "done" with
     * its customer's ID and current event's time.
     */

    @Override
    public String toString() {
        Server server = sh.getServer(this.serverID);
        return String.format("%.3f %s done serving by %s", this.getTime(), cust, server);
    }
}

