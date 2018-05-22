package it.polimi.se2018.model.player;

/**
 * Public Class User
 * @author Davide Gioiosa
 */

public class User {
    private String nickname;
    private Player player;
    private boolean connection;
    //TODO: PARAMETRI CONNESSIONE

    /**
     * Builder: create a user
     * @param nickname
     */
    public User (String nickname){
        this.nickname = nickname;
        this.connection = true;
    }

    /**
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

    public boolean isConnected() {
        return connection;
    }
}
