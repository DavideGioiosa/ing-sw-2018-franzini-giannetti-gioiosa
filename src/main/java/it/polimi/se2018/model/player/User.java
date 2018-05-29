package it.polimi.se2018.model.player;

/**
 * Public Class User
 * @author Davide Gioiosa
 */

public class User {
    private String nickname;
    private Player player;
    private boolean connection;
    private TypeOfConnection typeOfConnection;

    /**
     * Builder: create a user with the id and the connection
     * @param nickname chosen by the user
     */
    public User (String nickname, TypeOfConnection typeOfConnection){
        this.nickname = nickname;
        this.connection = true;
        this.typeOfConnection = typeOfConnection;
    }

    /**
     * Sets the player in the game related to the User
     * @param player in the game, related to the user
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return nickname of the player that is related to the user
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return player related to the user
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Informs if the player is online or not
     * @return the state of the connection
     */
    public boolean isConnected() {
        return connection;
    }

    public TypeOfConnection getTypeOfConnection() {
        return typeOfConnection;
    }
}
