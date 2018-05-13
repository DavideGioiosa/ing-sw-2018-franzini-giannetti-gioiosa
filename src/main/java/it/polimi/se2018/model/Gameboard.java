package it.polimi.se2018.model;

import it.polimi.se2018.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Public Class Gameboard
 * @author Davide Gioiosa
 */

public class Gameboard {
    private List<Player> playerList;
    private BagDice bagDice;
    private BoardDice boardDice;
    private TrackBoard trackBoardDice;
    private BoardCard cardOnBoard;

    /**
     * Creation of the Gameboard of the match
     */
    public Gameboard (List<Player> playerList, BagDice bagDice, BoardDice boardDice, TrackBoard trackBoardDice, BoardCard boardCard){
        this.playerList = playerList;
        this.bagDice = bagDice;
        this.boardDice = boardDice;
        this.trackBoardDice = trackBoardDice;
        this.cardOnBoard = boardCard;
    }

    /**
     * @return list of the player in the match
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * @return the Bag containing remaining dice
     */
    public BagDice getBagDice() {
        return bagDice;
    }

    /**
     * @return the dice available to use in the action
     */
    public BoardDice getBoardDice() {
        return boardDice;
    }

    /**
     * @return TrackBoard containing dice placed in each round
     */
    public TrackBoard getTrackBoardDice() {
        return trackBoardDice;
    }

    /**
     * @return cards available in the match
     */
    public BoardCard getCardOnBoard() {
        return cardOnBoard;
    }
}