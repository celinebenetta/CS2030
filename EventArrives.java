package cs2030.simulator;

/**
 * Represents an event with customer arriving.
 */

public class EventArrives extends Event {

    /**
     * Construct an event with state "arrives" with customer's information.
     * @param cust customers arriving.
     */

    public EventArrives(Customer cust) {
        super(cust);
    }

    /**
     * Determine what to do to arriving customers based on several conditions.
     * @return next event
     */

    @Override
    public Event determineNext() { 
        Customer customer = this.getCust();
        Server server = sh.availableServer(customer);
        if (server == null) {
            return new EventLeaves(customer);
        } else if (server.canServe(customer)) {
            //current customer can be served
            sh = sh.updateServer(server.serve(customer));
            Event.stats = Event.stats.updateWaitingTime(this.getTime() - customer.getArrivalTime());
            return new EventServed(customer, server.getID());

        } else {
            sh = sh.updateServer(server.updateWaitingList(customer));
            return new EventWaits(customer, server.getID());
        }
    }

    /**
     * Returns a string representation of event with state 
     * "arrives" with its customer's ID and current event's time.
     */

    @Override
    public String toString() {
        return String.format("%.3f %s arrives", this.getTime(), cust);
    }
}
