package it.polimi.se2018.model;

import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PrivatePlayer;

import java.util.List;

/**
 * Public Class GameBoard
 * @author Davide Gioiosa
 */

public class GameBoard {
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
     * List of Private Objective Card referred to every Player
     */
    private List<PrivatePlayer> privatePlayerList;


    /**
     * Creation of the GameBoard of the match
     * @param playerList list of players in the match
     * @param bagDice bag containing all the 90 dice
     * @param boardDice dice in the Draft Pool
     * @param trackBoardDice dice placed on the trackboard
     * @param boardCard public and tool cards usable in the match
     * @param privatePlayerList List of Private Objective Card referred to every Player
     */
    public GameBoard(List<Player> playerList, BagDice bagDice, BoardDice boardDice, TrackBoard trackBoardDice, BoardCard boardCard, List<PrivatePlayer> privatePlayerList){
        if(playerList == null){
            throw new NullPointerException("Insertion of a null playerList");
        }
        if(bagDice == null){
            throw new NullPointerException("Insertion of a null bagDice");
        }
        if(boardDice == null){
            throw new NullPointerException("Insertion of a null boardDice");
        }
        if(trackBoardDice == null){
            throw new NullPointerException("Insertion of a null trackBoardDice");
        }
        if(boardCard == null){
            throw new NullPointerException("Insertion of a null boardCard");
        }
        if(privatePlayerList == null){
            throw new NullPointerException("Insertion of a null privatePlayerList");
        }

        this.playerList = playerList;
        this.bagDice = bagDice;
        this.boardDice = boardDice;
        this.trackBoardDice = trackBoardDice;
        this.cardOnBoard = boardCard;
        this.privatePlayerList = privatePlayerList;
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

    public List<PrivatePlayer> getPrivatePlayerList() {
        return privatePlayerList;
    }
}