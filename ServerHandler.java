package cs2030.simulator;

import java.util.ArrayList;

/**
 * Handles the server(s) input,
 * check which server is able to serve and update it accordingly.
 */

public class ServerHandler {
    private final Server[] servers;

    /**
     * Constructs a new manager handling several servers.
     * @param servers array of servers to manage
     */

    public ServerHandler(Server[] servers) {
        this.servers = servers;
    }

    /**
     * Checks which server is available based on 
     *     type of customer and server's availability..
     * @param cust arriving customer
     * @return available and appropriate server
     */

    public Server availableServer(Customer cust) {
        //check which server is available and return that server
        for (int i = 0; i < this.servers.length; i++) {
            if (this.servers[i].canServe(cust)) {
                return this.servers[i];
            }
        }

        if (cust.getGreedStatus()) {
            int currQueueLength = Integer.MAX_VALUE;
            Server currFastest = null;

            for (int j = 0; j < this.servers.length; j++) {
                if (!this.servers[j].isFullyBooked()) {
                    if (this.servers[j].queueLength() < currQueueLength) {
                        currFastest = this.servers[j];
                        currQueueLength = currFastest.queueLength();
                    }
                }
            }
            return currFastest;

        } else {

            for (int j = 0; j < this.servers.length; j++) {
                if (!this.servers[j].isFullyBooked()) {
                    return this.servers[j];
                }
            }
            return null;
        }
    }

    /**
     * Updates the status of the specific server.
     * @param newServer new status of the server
     * @return updated ServerHandler
     */

    public ServerHandler updateServer(Server newServer) {
        Server[] updated = new Server[this.servers.length];
        for (int i = 0; i < this.servers.length; i++) {
            if (i == newServer.getID() - 1) {
                updated[i] = newServer;
            } else {
                updated[i] = this.servers[i];
            }
        }
        return new ServerHandler(updated);
    }

    /**
     * Gets server with the specific ID number.
     * @param id ID of the server
     * @return server with specified ID
     */

    public Server getServer(int id) {
        return this.servers[id - 1];
    }
}

