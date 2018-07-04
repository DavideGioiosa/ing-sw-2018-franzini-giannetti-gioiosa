package it.polimi.se2018.model;

import it.polimi.se2018.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Board containing only information available for Players
 */
public class ClientBoard{

    /**
     * List of players in the match
     */
    private List<Player> playerList;
    /**
     * Dice in the Draft Pool
     */
    private BoardDice boardDice;
    /**
     * Dice placed on the Trackboard
     */
    private TrackBoard trackBoardDice;
    /**
     * Public and Tool cards usable in the match
     */
    private BoardCard cardOnBoard;

    /**
     * Creation of the visible GameBoard of the match
     * @param playerList list of players in the match
     * @param boardDice dice in the Draft Pool
     * @param trackBoardDice dice placed on the trackboard
     * @param boardCard public and tool cards usable in the match
     */
    public ClientBoard(List<Player> playerList, BoardDice boardDice, TrackBoard trackBoardDice, BoardCard boardCard) {

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
     * Copy Constructor
     * @param clientBoard ClientBoard to be cloned
     */
    protected ClientBoard(ClientBoard clientBoard){
        if (clientBoard == null) throw new NullPointerException("ERROR: Tried to create a null ClientBoard");

        this.playerList = new ArrayList<>();
        for(Player player: clientBoard.playerList) this.playerList.add(player.getClone());

        this.boardDice = clientBoard.boardDice.getClone();
        this.trackBoardDice = clientBoard.getTrackBoardDice().getClone();
        this.cardOnBoard = clientBoard.getCardOnBoard().getClone();
    }

    /**
     * Getter method for Draft Pool
     * @return Status of Draft Pool
     */
    public BoardDice getBoardDice() {
        return boardDice;
    }

    /**
     * Getter method for status of Trackboard
     * @return TrackBoard containing dice placed in each round
     */
    public TrackBoard getTrackBoardDice() {
        return trackBoardDice;
    }

    /**
     * Getter method for Public and Tool cards available in the match
     * @return cards available in the match
     */
    public BoardCard getCardOnBoard() {
        return cardOnBoard;
    }

    /**
     * Getter method for list of Player in the match
     * @return list of the player in the match
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Gets a clone of ClientBoard
     * @return Cloned ClientBoard
     */
    public ClientBoard getClone(){
        return new ClientBoard(this);
    }
}
