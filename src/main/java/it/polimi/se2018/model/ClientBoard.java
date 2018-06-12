package it.polimi.se2018.model;

import it.polimi.se2018.model.player.Player;

import java.util.List;

/**
 * Board with information available for Players
 */
public class ClientBoard {

    /**
     * list of players in the match
     */
    private List<Player> playerList;
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
     * Creation of the visible GameBoard of the match
     * @param playerList list of players in the match
     * @param boardDice dice in the Draft Pool
     * @param trackBoardDice dice placed on the trackboard
     * @param boardCard public and tool cards usable in the match
     */
    public ClientBoard(List<Player> playerList, BoardDice boardDice, TrackBoard trackBoardDice, BoardCard boardCard){

        if(playerList == null){
            throw new NullPointerException("Insertion of a null playerList");
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

        this.playerList = playerList;
        this.boardDice = boardDice;
        this.trackBoardDice = trackBoardDice;
        this.cardOnBoard = boardCard;

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

    /**
     * @return list of the player in the match
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

}
