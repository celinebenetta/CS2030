package cs2030.simulator;

import java.util.LinkedList;

/**
 * Represents a self-checkout counter.
 */

public class SelfCheckout extends Server {

    private static LinkedList<Customer> selfQueue;

    /**
     * Constructs a self-checkout counter with an id and 
     *  specific service time.
     */
    public SelfCheckout(int id, double serviceTime) {
        super(id, serviceTime);
    }

    /**
     * Constructs a self-checkout counter with a customer,
     *  an id, and specific service time.
     */

    public SelfCheckout(Customer cust, int id, double serviceTime) {
        super(cust, id, selfQueue, serviceTime, false);
    }
    
    /**
     * Updates waiting list of self-checkout counters.
     * @param newCust customer that is going to be added to the queue.
     * @return self-checkout with updated waiting list.
     */

    @Override
    public SelfCheckout updateWaitingList(Customer newCust) {
        selfQueue.add(newCust);
        return this;
    }

    /**
     * Serves the customer.
     * @param cust customer that this self-checkout counter is going to serve.
     * @return self-checkout counter serving this customer
     */

    @Override
    public SelfCheckout serve(Customer cust) {
        if (canServe(cust)) {
            countServed++;
            return new SelfCheckout(cust, this.id, rng.genServiceTime());
        } else {
            return this;
        }
    }

    /**
     * Servers a customer in the waiting list.
     * @param time current time
     * @return self-checkout serving the waiting customer.
     */

    @Override
    public SelfCheckout serveWaitingCustomer(double time) {
        if (this.hasWaitingCustomer()) {
            Customer curr = selfQueue.pollFirst();
            Customer customer = new Customer(curr.getID(), time, curr.getGreedStatus());
            return new SelfCheckout(customer, this.id, rng.genServiceTime());
        } else {
            return this;
        }

    }

    /**
     * Return this self-checkout because it never rests.
     * @return current self-checkout counter.
     */

    @Override
    public SelfCheckout isBackOnDuty() {
        return this;
    }
    
    /**
     * Determines whether this self-checkout counter can rest or not.
     * @return false because self-checkout counters never rest.
     */

    @Override
    public boolean canRest() {
        return false;
    }

    /**
     * Opens the queue of self-checkout counters.
     */

    public static void startQueue() {
        selfQueue = new LinkedList<>();
    }

    /**
     * Returns a string representation of this self-checkout counter.
     */

    @Override
    public String toString() {
        return String.format("self-check %s", this.id);
    }
}
