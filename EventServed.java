package cs2030.simulator;

/**
 * Represents an event with state "served".
 */

public class EventServed extends Event {
    private final int serverID;
   
    /**
     * Constructs an event with state "served" with its customer's information.
     * @param cust current served customer
     */

    public EventServed(Customer cust, int serverID) {
        super(cust);
        this.serverID = serverID;
        stats = stats.addServed();
    }

    /**
     * Constructs an event with state "served" with its
     * customer's information and specified time.
     * @param cust current served customer
     * @param time specified time
     */

    public EventServed(Customer cust, double time, int serverID) {
        super(cust, time);
        this.serverID = serverID;
        stats = stats.addServed();
    }

    /**
     * Determines what event will occur next.
     * @return EventDone after the customers has finished being served
     */

    @Override
    public Event determineNext() {
        Server server = sh.getServer(this.serverID);
        Customer customer = this.getCust();

        if (customer == server.getWaitingCustomer()) {
            sh = sh.updateServer(server.serveWaitingCustomer(this.getTime()));
        } else {
            sh = sh.updateServer(server.serve(customer));
        }

        customer = new Customer(customer.getID(), this.getTime(), customer.getGreedStatus());
        server = sh.getServer(this.serverID);

        return new EventDone(customer, server.getFinishingTime(customer), this.serverID);
    }

    /**
     * Returns a string representation of event with state "served"
     * with its customer's ID and current event's time.
     */

    @Override
    public String toString() {
        Server server = sh.getServer(this.serverID);
        return String.format("%.3f %s served by %s", this.getTime(), cust, server);
    }
}
