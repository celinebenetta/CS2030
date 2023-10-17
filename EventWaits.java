package cs2030.simulator;

/**
 * Represents an event with customer waiting.
 */

public class EventWaits extends Event {
    private final int serverID;
    /**
     * Constructs an event with state "waits" based on customer's information.
     */

    public EventWaits(Customer cust, int serverID) {
        super(cust);
        this.serverID = serverID;
    }

    /**
     * Determine what event will occur next based on current condition.
     * @return event served when the server is available and
     *     able to serve the waiting customer.
     */

    @Override
    public Event determineNext() {
        return null;
    }

    /**
     * Returns a string representation of event "waits" with
     * its customer's ID and current event's time.
     */

    @Override
    public String toString() {
        Server server = sh.getServer(this.serverID);
        return String.format("%.3f %s waits to be served by %s", 
                this.getTime(), cust, server);
    }
}
