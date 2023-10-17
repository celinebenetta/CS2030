package cs2030.simulator;

/**
 * Represents a customer.
 */

public class Customer {

    private static int counter = 0;
    private final int id;
    private final double timeArr;
    private final boolean isGreedy;

    /**
     * Constructs and initializes a customer from its id number, time of arrival, and type.
     * @param timeArr arrival time of the customer
     * @param isGreedy type of customer, can either be greedy or normal.
     */

    public Customer(double timeArr, boolean isGreedy) {
        this.id = counter + 1;
        this.timeArr = timeArr;
        this.isGreedy = isGreedy;
        counter++;
    }

    /**
     * Constructs and initializes a customer from its id number, time of arrival, and type.
     * @param id customer's ID number
     * @param timeArr arrival time of the customer
     * @param isGreedy type of customer, can either be greedy or normal.
     */

    public Customer(int id, double timeArr, boolean isGreedy) {
        this.id = id;
        this.timeArr = timeArr;
        this.isGreedy = isGreedy;
    }

    protected boolean getGreedStatus() {
        return this.isGreedy;
    }

    protected int getID() {
        return this.id;
    }

    protected double getArrivalTime() {
        return this.timeArr;
    } 

    protected static int getCount() {
        return counter;
    }

    /**
     * String representation of a customer with its ID and type.
     */

    @Override
    public String toString() {
        if (this.isGreedy) {
            return String.format("%s(greedy)", this.id);
        } else {
            return String.format("%s", this.id);
        }
    }
}
