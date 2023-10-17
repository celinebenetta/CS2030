package cs2030.simulator;

import java.util.PriorityQueue;
import java.util.Scanner;
/**
 * Represents an event.
 */

public abstract class Event implements Comparable<Event> {

    protected final Customer cust;
    private final double time;
    private final int id;
    protected static ServerHandler sh;
    protected static Stats stats;

    //Scanner sc = new Scanner(System.in);
    /**
     * Constructs an event from the customer's information.
     * @param cust current customer.
     */

    public Event(Customer cust) {
        this.cust = cust;
        this.time = cust.getArrivalTime();
        this.id = cust.getID();
    }

    /**
     * Constructs an event from the customers's information and specified time.
     * @param cust current customer.
     * @param time specified time.
     */

    public Event(Customer cust, double time) {
        this.cust = cust;
        this.time = time;
        this.id = cust.getID();
    }
    
    /**
     * Starts a new statistic.
     */

    public static void initiateStats() {
        stats = new Stats();
    }

    public Customer getCust() {
        return this.cust;
    }

    public double getTime() {
        return this.time;
    }

    public static Stats getStats() {
        return stats;
    }

    /**
     * Sets a server handler for this event.
     * @param newSH server handler that will be used
     */

    public static void setServerHandler(ServerHandler newSH) {
        sh = newSH;
    }

    /**
     * Determines next event based
     * on current event and condition.
     */

    public abstract Event determineNext();

    /**
     * Compares this Event with other Event by their time,
     * or with their customer's ID if the time does not differ..
     * @param other the other event
     * @return -1 if other event occurs after this event 
     *     1 if other occurs before this event, and 0 if they occurs at the same time.
     *     If they occur at the same time, then it will return -1 if other customer's ID
     *     is bigger, 1 if it is smaller, and finally 0 if they are the same.
     */

    public int compareTo(Event other) {
        if (other.time > this.time) {
            return -1;
        } else if (other.time < this.time) {
            return 1;
        } else {
            if (other.id > this.id) {
                return -1;
            } else if (other.id < this.id) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
