package cs2030.simulator;

/**
 * Represents an event with "non-resting server" state.
 */

class ServerBack extends Event {

    private final int serverID;

    /**
     * Constructs an event with "non-resting server" state 
     *  with customer this server is serving, current time, and this server's ID.
     */

    ServerBack(Customer customer, double time, int serverID) {
        super(customer, time);
        this.serverID = serverID;
    }

    /**
     * Determines next event.
     * @return appropriate event based on current condition
     */
    
    @Override
    public Event determineNext() {
        Server server = sh.getServer(this.serverID);
        sh = sh.updateServer(server.isBackOnDuty());
        server = sh.getServer(this.serverID);

        if (server.hasWaitingCustomer()) {
            Customer customer = server.getWaitingCustomer();
            Event.stats = Event.stats.updateWaitingTime(this.getTime() - customer.getArrivalTime());

            return new Event(customer, this.getTime()) {

                @Override
                public Event determineNext() {
                    Server server = sh.getServer(serverID);
                    Customer customer = this.getCust();

                    if (customer == server.getWaitingCustomer()) {
                        sh = sh.updateServer(server.serveWaitingCustomer(this.getTime()));
                    } else {
                        sh = sh.updateServer(server.serve(customer));
                    }

                    customer = new Customer(customer.getID(), 
                            this.getTime(), customer.getGreedStatus());
                    server = sh.getServer(serverID);

                    Event.stats = Event.stats.addServed();
                    return new EventDone(customer, 
                            server.getFinishingTime(customer), ServerBack.this.serverID);
                }

                @Override
                public String toString() {
                    Server server = sh.getServer(serverID);
                    return String.format("%.3f %s served by %s", this.getTime(), cust, server);
                }
            };        
        }
        return null;
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
