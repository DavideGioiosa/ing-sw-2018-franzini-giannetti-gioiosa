package it.polimi.se2018.connection.client;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.User;

/**
 * Interface for Connection Strategy
 * @author  Silvia Franzini
 */
public interface ClientStrategy {

    /**
     * Method used to send messages to the server
     * @param playerMessage message that has to be send
     */
    void sendToServer(PlayerMessage playerMessage);

    /**
     * Method used to close connection with the server
     */
    void close();

    /**
     * Method used to add client's class as observer of subclasses
     * @param client observer type
     */
    void addObserver(Client client);

    /**
     * Method used to restart the connection among client and server
     * @param user this user
     */
    void reconnect(User user);

    /**
     * Method invoked to start  the connection
     */
    void connect();
}
