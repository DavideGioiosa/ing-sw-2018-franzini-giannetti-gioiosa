package it.polimi.se2018.model.player;

import java.util.Random;

/**
 * Public Class User
 * @author Davide Gioiosa
 */

public class User {
    private String uniqueCode;
    private String nickname;
    private Player player;
    private boolean connection;
    private TypeOfConnection typeOfConnection;

    /**
     * Builder: create a user with the id and the connection
     * @param typeOfConnection the chosen type of connection
     */
    public User (TypeOfConnection typeOfConnection){
        this.connection = true;
        this.typeOfConnection = typeOfConnection;
    }


    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    /**
     * Method used to create an identifier for the user to setup a connection
     * @return the identifier
     */
    public void createCode(){
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
        }

        this.uniqueCode = sb.toString();
    }
}
