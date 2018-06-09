package it.polimi.se2018.model;


import it.polimi.se2018.model.player.User;

import java.io.Serializable;

public class PlayerMessage implements Serializable {
    private PlayerMessageTypeEnum id;
    private PlayerMove playerMove;
    private PlayerChoice playerChoice;
    private MoveMessage moveMessage;
    private boolean closure;
    private User user;

    public PlayerMessage(){
        playerMove = null;
        playerChoice = null;
        moveMessage = null;
        closure = false;
        user = null;
    }

    public void setMove(PlayerMove playerMove) {
        if(playerMove == null){
            throw new NullPointerException("Sent a null playerMove");
        }
        this.playerMove = playerMove;
        id = PlayerMessageTypeEnum.MOVE;
    }

    public void setChoice(PlayerChoice playerChoice) {
        if(playerChoice == null){
            throw new NullPointerException("Sent a null playerChoice");
        }
        this.playerChoice = playerChoice;
        id = PlayerMessageTypeEnum.CHOICE;
    }

    public void setMessage(MoveMessage moveMessage) {
        if(moveMessage == null){
            throw new NullPointerException("Sent a null moveMessage");
        }
        this.moveMessage = moveMessage;
        id = PlayerMessageTypeEnum.UPDATE;
    }

     public void setUser(User user){
        if(user == null){
            throw new NullPointerException("Insertion of null user parameter");
        }
        this.user = user;
         id = PlayerMessageTypeEnum.USER;
     }

    public User getUser() {
        return user;
    }

    public void setClosure() {
        this.closure = true;
    }

    public boolean isClosed() {
        return closure;
    }

    public PlayerMessageTypeEnum getId(){
        return id;
    }

    public PlayerMove getPlayerMove() {
        return playerMove;
    }

    public PlayerChoice getPlayerChoice() {
        return playerChoice;
    }

    public MoveMessage getMoveMessage() {
        return moveMessage;
    }
}
