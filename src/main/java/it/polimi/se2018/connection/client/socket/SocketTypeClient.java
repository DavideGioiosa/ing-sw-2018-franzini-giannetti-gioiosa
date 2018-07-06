package it.polimi.se2018.connection.client.socket;

import it.polimi.se2018.connection.client.Client;
import it.polimi.se2018.connection.client.ClientStrategy;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

import java.io.IOException;

/**
 * Socket Connection's strategy class
 */
public class SocketTypeClient implements ClientStrategy, Observer<PlayerMessage> {

    /**
     * Reference to class handling connection to the server
     */
    private NetworkHandler networkHandler;
    /**
     * Server's port
     */
    private int networkPort;
    /**
     * Server's ip address
     */
    private String host;
    /**
     * Observable objects for callbacks
     */
    private Observable<PlayerMessage> obs;

    /**
     * Builder method of the class
     * @param host server's ip address
     * @param port server's port
     */
    public SocketTypeClient(String host, int port){
        obs = new Observable<>();
        this.host = host;
        this.networkPort = port;
    }

    /**
     * Mothod invoked to start a connection with the server
     */
    @Override
    public void connect(){

        this.networkHandler = new NetworkHandler(this.host, this.networkPort);
        networkHandler.getObs().addObserver(this);
        networkHandler.start();
    }

    /**
     * Method used to reconnect to the server once the connection has been compromised
     * @param user this user
     */
    @Override
    public void reconnect(User user){
        networkHandler.setQuit();
        try {
            networkHandler.closeConnection();
        } catch (IOException e) {
            PlayerMessage playerMessage = new PlayerMessage();
            playerMessage.setError(450);
            update(playerMessage);
        }
        connect();
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setUser(user);
        networkHandler.send(playerMessage);

    }

    /**
     * Strategy's sender method of client's socket connection
     * @param playerMessage message that has to be send
     */
    @Override
    public void sendToServer(PlayerMessage playerMessage){
        networkHandler.send(playerMessage);
    }

    /**
     * Strategy's method to close connection
     */
    @Override
    public void close(){
        networkHandler.setQuit();
    }

    /**
     * Strategy's method to add observers to this class
     * @param client typer of the observer added
     */
    @Override
    public void addObserver(Client client) {
        obs.addObserver(client);
    }

    /**
     * Method invoked by subclasses to receive a message from the server
     * @param playerMessage
     */
    @Override
    public void update(PlayerMessage playerMessage) {
        obs.notify(playerMessage);
    }

}

