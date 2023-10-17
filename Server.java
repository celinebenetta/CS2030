package cs2030.simulator;

import java.util.LinkedList;

/**
 * Represents a server.
 */

public class Server {
    protected final Customer cust;
    protected final double serviceTime;
    protected final int id;
    protected final boolean isResting;
    protected static int countServed;
    private LinkedList<Customer> waitingList;
    protected static RandomGenerator rng;
    private static double Q_MAX;
    private static double RESTING_PROBABILITY;

    /**
     * Constructs and initializes a server.
     */

    public Server(int id, double serviceTime) {
        this.id = id;
        this.cust = null;
        this.waitingList = new LinkedList<>();
        this.serviceTime = serviceTime;
        this.isResting = false;
    }

    /**
     * Constructs and initializes a server with the specified customer.
     * @param cust customer being served with this server
     * @param waitingList list containing customer(s) waiting
     * @param serviceTime this particular server's service time
     * @param isResting server's state, true if server is resting 
     *     and false if server is not resting
     */

    public Server(Customer cust, int id, LinkedList<Customer> waitingList, 
            double serviceTime, boolean isResting) {
        this.cust = cust;
        this.id = id;
        this.waitingList = waitingList;
        this.serviceTime = serviceTime;
        this.isResting = isResting;
    }

    public int getID() {
        return this.id;
    }

    public Customer getCust() {
        return this.cust;
    }

    public double getServiceTime() {
        return this.serviceTime;
    }

    public static void setCapacity(int maxQ) {
        Q_MAX = maxQ;
    }

    public static void setRandomizer(RandomGenerator newRng) {
        rng = newRng;
    }

    /**
     * Checks whether this server can still accept waiting customer or not.
     * @return true if this server can still accept waiting customer and false otherwise
     */

    public boolean isFullyBooked() {
        return this.waitingList.size() >= Q_MAX;
    }

    /**
     * Checks whether this server still has waiting customer or not.
     */

    public boolean hasWaitingCustomer() {
        return this.waitingList.size() > 0;
    }

    public int getCountServed() {
        return this.countServed;
    }

    public Customer getWaitingCustomer() {
        return this.waitingList.peek();
    }

    /**
     * Checks current amount of waiting customers.
     * @return amount of waiting customers.
     */

    public int queueLength() {
        return this.waitingList.size();
    }

    /**
     * Sets the resting probability of this server.
     */

    public static void setRestingProbability(double rp) {
        RESTING_PROBABILITY = rp;
    }

    /**
     * Checks whether this server can rest or not.
     * @return true if server can rest and false otherwise.
     */

    public boolean canRest() {
        return rng.genRandomRest() < RESTING_PROBABILITY;
    }

    /**
     * Changes the state of this customer to resting state.
     * @return resting server with this server's details.
     */

    public Server isNowResting() {
        return new Server(this.cust, this.id, this.waitingList, this.serviceTime, true);
    }

    /**
     * Changes the state of this server to non-resting state.
     * @return non-resting server with this server's details.
     */

    public Server isBackOnDuty() {
        return new Server(this.cust, this.id, this.waitingList, this.serviceTime, false);
    }

    public double getRestingPeriod() {
        return rng.genRestPeriod();
    }

    /**
     * Updates waiting list of this server.
     * @param newCust new waiting customer
     * @return updated server with new waiting list.
     */

    public Server updateWaitingList(Customer newCust) {
        LinkedList<Customer> curr = new LinkedList<>(this.waitingList);
        curr.add(newCust);
        return new Server(this.cust, this.id, curr, this.serviceTime, this.isResting);
    }

    /**
     * Determines whether a server can serve or not.
     * @param cust upcoming customer
     * @return true if the server can serve and false otherwise
     */

    public boolean canServe(Customer cust) {
        if (this.isResting) {
            return false;
        } else {
            if (this.cust == null) {
                return true;
            } else {
                return cust.getArrivalTime() >= this.cust.getArrivalTime() + this.serviceTime;
            }
        }

    }

    /**
     * Serves the customer if the server is able to serve it.
     * @param cust the customer this server is going to serve
     * @return the server that is currently serving
     */

    public Server serve(Customer cust) {
        if (canServe(cust)) {
            countServed++;
            return new Server(cust, this.id, this.waitingList, 
                    rng.genServiceTime(), this.isResting);
        } else {
            return this;
        }
    }

    /**
     * Serves a customer in the waiting list.
     * @param time current serving time
     * @return the server that is currently serving
     */

    public Server serveWaitingCustomer(double time) {
        if (this.hasWaitingCustomer()) {
            LinkedList<Customer> newList = new LinkedList<>(this.waitingList);
            Customer curr = newList.pollFirst();
            Customer customer = new Customer(curr.getID(), time, curr.getGreedStatus());
            return new Server(customer, this.id, newList, rng.genServiceTime(), this.isResting);
        } else {
            return this;
        }

    }

    /**
     * Gets the finishing time based on given customer's
     *     arrival time and this service time.
     * @param cust given customer that this server is serving.
     */

    public double getFinishingTime(Customer cust) {
        return cust.getArrivalTime() + this.serviceTime;
    }

    /**
     * Gets the finishing time based on arrival time of
     *     this server's customer customer and service time.
     */

    public double getFinishingTime() {
        return this.cust != null
            ? this.cust.getArrivalTime() + this.serviceTime
            : 0;
    }
    
    /**
     * Returns a string representation of this server
     *  with its id number.
     */

    @Override
    public String toString() {
        return String.format("server %s", this.id);
    }
}
