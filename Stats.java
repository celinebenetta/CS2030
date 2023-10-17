package cs2030.simulator;

/**
 * Represents a statistics keeping track of current
 *     waiting time and amount of served customer(s).
 */
public class Stats {
    private double waitingTime;
    protected int countServed;

    /**
     * Constructs a new statistics tracker.
     */

    public Stats() {
        this.waitingTime = 0;
        this.countServed = 0;
    }

    /**
     * Constructs a statictics tracker with given
     *     waiting time and served customer(s).
     */

    public Stats(double waitingTime, int countServed) {
        this.waitingTime = waitingTime;
        this.countServed = countServed;
    }

    /**
     * Adds the current waiting time with given amount of time.
     * @param newTime amount of time added to current waiting time
     * @return new statistics tracker with updated waiting time
     */

    public Stats updateWaitingTime(double newTime) {
        return new Stats(this.waitingTime + newTime, this.countServed);
    }

    public double getWaitingTime() {
        return this.waitingTime;
    }

    public int getCountServed() {
        return this.countServed;
    }

    /**
     * Increments the amount of served customer(s).
     * @return statistics tracker with updated served customer(s) amount
     */

    public Stats addServed() {
        return new Stats(this.waitingTime, this.countServed + 1);
    }

    /**
     * Returns a string representation of this statistics tracker
     *     with average waiting time and amount of served customer(s)
     *     and customer(s) that left before being served.
     */

    @Override
    public String toString() {
        double avgWaitingTime = this.countServed == 0 ? 0 : this.waitingTime / this.countServed;
        int left = Customer.getCount() - this.countServed;
        return String.format("[%.3f %s %s]", avgWaitingTime,
                this.countServed, left);
    }
}
