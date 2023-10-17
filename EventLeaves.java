package cs2030.simulator;

/**
 * Represent an event with customer leaving.
 */

public class EventLeaves extends Event {
    
    /**
     * Constructs an event with state "leaves" with customer's information.
     */

    public EventLeaves(Customer cust) {
        super(cust);
    }
    
    /**
     * Determine what event will occur next.
     * @return null because no event will occur
     *     after the customer leaves.
     */

    @Override
    public Event determineNext() {
        return null;
    }
    
    /**
     * Returns a string representation of event 
     * "leaves" with customer's information and current event's time.
     */

    @Override
    public String toString() {
        return String.format("%.3f %s leaves", this.getTime(), cust);
    }
}
