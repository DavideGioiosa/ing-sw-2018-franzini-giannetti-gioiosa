package it.polimi.se2018.model.player;

/**
 * Public Class User
 * @author Davide Gioiosa
 */

public class User {
    private String nickname;
    private Player player;
    //TODO: PARAMETRI CONNESSIONE

    public User (String nickname){
        this.nickname = nickname;
    }

    /**
     * @param player in the game, related to the user
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getNickname() {
        return nickname;
    }

    public Player getPlayer() {
        return player;
    }

}