package it.polimi.se2018.connection.client.rmi;

import it.polimi.se2018.model.PlayerMessage;

/**
 * RMI thread for receiving messages from server
 * @author Silvia Franzini
 */
public class RMIReceiveThread implements Runnable {

    /**
     * Message received from server
     */
    private PlayerMessage playerMessage;
    /**
     * Client's communication class that will handle the message
     */
    private ClientImplementation clientImplementation;

    /**
     * Builder method of the class
     * @param playerMessage message received from the server
     * @param clientImplementation communication class that will receive the message
     */
    RMIReceiveThread(PlayerMessage playerMessage, ClientImplementation clientImplementation){
        this.playerMessage = playerMessage;
        this.clientImplementation = clientImplementation;
    }

    /**
     * Thread's run method for message receiving
     */
    @Override
    public void run(){
        clientImplementation.update(playerMessage);
    }
}
