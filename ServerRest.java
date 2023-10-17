package cs2030.simulator;

/**
 * Represents an event with server resting.
 */

class ServerRest extends Event {

    private final int serverID;

    /**
     * Constructs an event with state "server resting" with current customer,
     *  time, and serving server's ID number.
     */

    public ServerRest(Customer customer, double time, int serverID) {
        super(customer, time);
        this.serverID = serverID;
    }

    /**
     * Determines schedule of next event.
     * @return event with state "server back on duty" with this event details.
     */

    @Override
    public Event determineNext() {
        Server server = sh.getServer(this.serverID);
        return new ServerBack(this.getCust(), this.getTime() 
                + server.getRestingPeriod(), this.serverID);        
    }

    /**
     * Returns a string representation of this event.
     * @return nothing
     */

    @Override
    public String toString() {
        return null;
    }
}
