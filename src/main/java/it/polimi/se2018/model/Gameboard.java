package it.polimi.se2018.model;

import it.polimi.se2018.model.player.Player;

import java.util.List;

/**
 * Public Class Gameboard
 * @author Davide Gioiosa
 */

public class Gameboard {
    /**
     * list of players in the match
     */
    private List<Player> playerList;
    /**
     * bag containing all the 90 dice
     */
    private BagDice bagDice;
    /**
     * dice in the Draft Pool
     */
    private BoardDice boardDice;
    /**
     * dice placed on the trackboard
     */
    private TrackBoard trackBoardDice;
    /**
     * public and tool cards usable in the match
     */
    private BoardCard cardOnBoard;

    /**
     * Creation of the Gameboard of the match
     * @param playerList, list of players in the match
     * @param bagDice, bag containing all the 90 dice
     * @param boardDice, dice in the Draft Pool
     * @param trackBoardDice, dice placed on the trackboard
     * @param boardCard, public and tool cards usable in the match
     */
    public Gameboard (List<Player> playerList, BagDice bagDice, BoardDice boardDice, TrackBoard trackBoardDice, BoardCard boardCard){
        if(playerList == null){
            throw new IllegalArgumentException("Insert a playerList null");
        }
        if(bagDice == null){
            throw new IllegalArgumentException("Insert a bagDice null");
        }
        if(boardDice == null){
            throw new IllegalArgumentException("Insert a boardDice null");
        }
        if(trackBoardDice == null){
            throw new IllegalArgumentException("Insert a trackBoardDice null");
        }
        if(boardCard == null){
            throw new IllegalArgumentException("Insert a boardCard null");
        }

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