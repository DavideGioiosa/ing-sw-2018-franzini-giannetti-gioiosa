package it.polimi.se2018.model;

import java.util.List;

/**
 * Observed by the View, this class contains elements that could change during the match and that the player
 * may need to know
 * @author Silvia Franzini
 */
public class MoveMessage  {
    private List<Player> playerList;
    private BoardDice boardDice;
    private BoardCard boardCard;
    private TrackBoard trackboard;

    public MoveMessage(List<Player> playerList, BoardDice boardDice, BoardCard boardCard, TrackBoard trackboard){
        this.boardCard=boardCard;
        this.boardDice=boardDice;
        this.playerList=playerList;
        this.trackboard=trackboard;
    }

    /**
     * Allows the View to see the player's Window Pattern Card
     * @return a list of players
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Allows the View to see die extracted
     * @return the die on board
     */
    public BoardDice getBoardDice() {
        return boardDice;
    }

    /**
     * Allows the View to see the cards extracted
     * @return the cards on board
     */
    public BoardCard getBoardCard() {
        return boardCard;
    }

    /**
     * Allows the View to see the die on the TrackBoard
     * @return the TrackBoard
     */
    public TrackBoard getTrackboard() {
        return trackboard;
    }
}
