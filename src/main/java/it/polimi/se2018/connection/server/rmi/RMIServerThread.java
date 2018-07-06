package it.polimi.se2018.connection.server.rmi;

import it.polimi.se2018.model.PlayerMessage;

/**
 * RMI thread for receiving messages from the client
 */
public class RMIServerThread implements Runnable {

    /**
     * Message received from the client
     */
    private PlayerMessage playerMessage;
    /**
     * Reference to RMI server's class apt at handling connection with client's
     */
    private ServerImplementation serverImplementation;

    /**
     * Builder method of the class
     * @param playerMessage message received from the client
     * @param serverImplementation reference to class used to receive messages
     */
    RMIServerThread(PlayerMessage playerMessage, ServerImplementation serverImplementation){
        this.playerMessage = playerMessage;
        this.serverImplementation = serverImplementation;
    }

    /**
     * Thread's run method that transmits the messages to upper-classes
     */
    @Override
    public void run() {

        serverImplementation.transmit(playerMessage);

    }
}
