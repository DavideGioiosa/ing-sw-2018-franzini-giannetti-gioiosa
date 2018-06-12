package it.polimi.se2018.model;

import it.polimi.se2018.model.player.Player;

import java.io.Serializable;
import java.util.List;

/**
 * Observed by the view, this class contains elements that could change during the match and that the player
 * may need to know
 * @author Silvia Franzini
 */
public class MoveMessage implements Serializable {
    /**
     * Player of the game
     */
    private List<Player> playerList;
    /**
     * Actual Draft Pool
     */
    private BoardDice boardDice;
    /**
     * Public and tool cards on board
     */
    private BoardCard boardCard;
    /**
     * Actual status of trackboard
     */
    private TrackBoard trackboard;

    /**
     * Constructor assigns the public objects
     * @param playerList List of Player of the game
     * @param boardDice Status of Draft Pool
     * @param boardCard Cards on board
     * @param trackboard Status of TrackBoard
     */
    public MoveMessage(List<Player> playerList, BoardDice boardDice, BoardCard boardCard, TrackBoard trackboard){
        this.boardCard = boardCard;
        this.boardDice = boardDice;
        this.playerList = playerList;
        this.trackboard = trackboard;
    }

    /**
     * Allows the view to see the player's Window Pattern Card
     * @return a list of players
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Allows the view to see die extracted
     * @return the die on board
     */
    public BoardDice getBoardDice() {
        return boardDice;
    }

    /**
     * Allows the view to see the cards extracted
     * @return the cards on board
     */
    public BoardCard getBoardCard() {
        return boardCard;
    }

    /**
     * Allows the view to see the die on the TrackBoard
     * @return the TrackBoard
     */
    public TrackBoard getTrackboard() {
        return trackboard;
    }
}
