package it.polimi.se2018.connection.client.socket;

import java.util.TimerTask;

/**
 * Socket's class for client's ping
 */
public class SocketClientPing  extends TimerTask {

    /**
     * Reference to network's handler class
     */
    private NetworkHandler networkHandler;

    /**
     * Builder method for the class
     * @param networkHandler reference to connection's class NetworkHandler
     */
    SocketClientPing(NetworkHandler networkHandler){
        this.networkHandler = networkHandler;
    }

    /**
     * Thread's run method for ping the server to notify that client is still connected
     */
    @Override
    public void run() {

        networkHandler.ping();

    }
}

