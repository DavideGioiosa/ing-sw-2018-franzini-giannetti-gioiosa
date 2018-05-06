package it.polimi.se2018.model;

import java.util.ArrayList;

/**
 * Public Class Gameboard
 * @author Davide Gioiosa
 */

public class Gameboard {
    private ArrayList playerList;
    private BagDice bagDice;
    private BoardDice boardDice;
    private TrackBoard trackBoardDice;
    private BoardCard cardOnBoard;

    public Gameboard (ArrayList playerList, BagDice bagDice, BoardDice boardDice, TrackBoard trackBoardDice, BoardCard boardCard){
        this.playerList = playerList;
        this.bagDice = bagDice;
        this.boardDice = boardDice;
        this.trackBoardDice = trackBoardDice;
        this.cardOnBoard = boardCard;
    }

    public ArrayList getPlayerList() {
        return playerList;
    }

    public BagDice getBagDice() {
        return bagDice;
    }

    public BoardDice getBoardDice() {
        return boardDice;
    }

    public TrackBoard getTrackBoardDice() {
        return trackBoardDice;
    }

    public BoardCard getCardOnBoard() {
        return cardOnBoard;
    }
}