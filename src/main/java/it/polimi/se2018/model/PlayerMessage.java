package it.polimi.se2018.model;


public class PlayerMessage {
    private int id;
    private PlayerMove playerMove;
    private PlayerChoice playerChoice;
    private MoveMessage moveMessage;
    private boolean closure;

    public PlayerMessage(){
        playerMove=null;
        playerChoice=null;
        moveMessage=null;
        id=0;
        closure = false;
    }

    public void setMove(PlayerMove playerMove) {
        this.playerMove = playerMove;
        id=1;
    }

    public void setChoice(PlayerChoice playerChoice) {
        this.playerChoice = playerChoice;
        id=2;
    }

    public void setMessage(MoveMessage moveMessage) {
        this.moveMessage = moveMessage;
        id=3;
    }

    public void setClosure() {
        this.closure = true;
    }

    public boolean isClosed() {
        return closure;
    }

    public int getId() {
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
