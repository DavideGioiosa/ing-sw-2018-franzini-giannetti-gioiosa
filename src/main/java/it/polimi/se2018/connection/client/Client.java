package it.polimi.se2018.connection.client;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.View;

/**
 * Client's connection class
 * @author Silvia Franzini
 */

public class Client implements Observer<PlayerMessage> {

    /**
     * User attached to this client
     */
    private User thisUser;

    /**
     * Strategy used to connect the client on RMI or Socket
     */
    private ClientStrategy clientStrategy;

    /**
     * MVC's view to communicate with the user
     */
    private View view;

    /**
     * Nickname chosen by the client
     */
    private String nickname;

    /**
     * Builder method of the class
     * @param clientStrategy connection's strategy used to chose between RMI or Socket connections
     * @param view MVC's view used to comunicate with the user
     */
    public Client(ClientStrategy clientStrategy, View view){
        this.clientStrategy = clientStrategy;
        nickname = null;
        thisUser = null;
        this.view = view;
        this.clientStrategy.addObserver(this);
    }

    /**
     *Setter method for nickname
     * @param nickname name chosen by the user
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Getter method for nickname
     * @return the nickname chosen
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Setter method for the user
     * @param thisUser user created by the server and received
     */
    private void setThisUser(User thisUser) {
        this.thisUser = thisUser;
    }

    /**
     * Method used to create a connection with the server
     */
    void connect(){
        clientStrategy.connect();
    }

    /**
     * Observer pattern's update method used to send the view a received message from the server
     * @param playerMessage message received from the server
     */
    @Override
    public void update(PlayerMessage playerMessage){

         view.receive(playerMessage);

    }

    /**
     * Method used for reconnection to a server
     */
    public void reconnect(){
        clientStrategy.reconnect(thisUser);
    }

    /**
     * Sender method to communicate with the server
     * @param playerMessage message that has to be send to the server
     */
    public void send(PlayerMessage playerMessage){

        if(playerMessage.getId().equals(PlayerMessageTypeEnum.USER) && thisUser == null){
            setThisUser(playerMessage.getUser());
        }
        clientStrategy.sendToServer(playerMessage);
    }

    /**
     * Method used to close communication
     */
    public void close(){
        clientStrategy.close();
    }
}

