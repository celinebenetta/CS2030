package cs2030.simulator;

import java.util.PriorityQueue;

/**
 * Represents the process of serving all arriving customers. 
 */

public class Simulation {
    public RandomGenerator rng;
    private PriorityQueue<Event> events; 

    /**
     * Constructs a new simulation with given informations.
     * @param seed base seed for the random generator
     * @param arrRate customers' arrival rate
     * @param serviceRate servers' service rate
     * @param restingRate servers' resting rate
     * @param numOfCust number of customers to simulate
     * @param restingProbability probability of server going to rest
     * @param greedProbability probability of customer being greedy
     */

    public Simulation(int seed, double arrRate, double serviceRate, 
            double restingRate, int numOfCust, 
            double restingProbability, double greedProbability) {
        this.rng = new RandomGenerator(seed, arrRate, serviceRate, restingRate);
        this.events = addArrivals(numOfCust, restingProbability, greedProbability);

    }

    /**
     * Adds an array of servers and sets new server handler for Event class.
     * @param numOfServers number of servers.
     * @param numOfSelfCounter number of self-checkout counters.
     * @param qMax capacity of servers' waiting list.
     * @return array of servers containing all 
     *     the servers and self-checkout counters.
     */

    public Server[] addServers(int numOfServers, int numOfSelfCounter, int qMax) {
        int totalServers = numOfServers + numOfSelfCounter;
        Server[] servers = new Server[totalServers];
        Server.setRandomizer(this.rng);
        Server.setCapacity(qMax);

        for (int i = 0; i < numOfServers; i++) {
            servers[i] = new Server(i + 1, 0);
        }
        
        SelfCheckout.startQueue(); 
        for (int i = numOfServers; i < totalServers; i++) {
            servers[i] = new SelfCheckout(i + 1, 0);
        }

        Event.setServerHandler(new ServerHandler(servers));
        return servers;
    }

    /**
     * Adds all the arriving customer to the queue.
     * @param numOfCust number of customers arriving
     * @param restingProbability probability of server going to rest
     * @param greedProbability probability of a customer being greedy
     * @return queue containing all the arriving events.
     */

    public PriorityQueue<Event> addArrivals(int numOfCust, 
            double restingProbability, double greedProbability) {
        Customer customer;
        Event event;
        double arrTime = 0.0;
        PriorityQueue<Event> events = new PriorityQueue<>();

        for (int i = 0; i < numOfCust; i++) {
            if (this.rng.genCustomerType() < greedProbability) {
                customer = new Customer(arrTime, true);
            } else {
                customer = new Customer(arrTime, false);
            }

            event = new EventArrives(customer);
            events.add(event);
            arrTime += this.rng.genInterArrivalTime();
        }

        Server.setRestingProbability(restingProbability);
        Event.initiateStats();
        return events;
    }

    /**
     * Processes all the EventArrives in the queue and determines 
     * the next event for each.
     */

    //do determineNext method to all events in the events queue
    public void process() {
        while (!this.events.isEmpty()) {
            Event currEvent = this.events.poll();
            
            if (currEvent.toString() != null) {
                System.out.println(currEvent);
            }

            Event next = currEvent.determineNext();
            if (next != null) {
                this.events.add(next);
            }
        }

        System.out.println(Event.getStats());
    }
}
