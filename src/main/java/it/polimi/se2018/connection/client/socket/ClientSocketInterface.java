package it.polimi.se2018.connection.client.socket;

import it.polimi.se2018.model.PlayerMessage;

/**
 * Socket Interface for client's receive method
 */
public interface ClientSocketInterface {

    /**
     * Method invoked to receive a message from the server
     * @param playerMessage message received
     */
    void receive(PlayerMessage playerMessage);
}
