package it.polimi.se2018.model;

import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PrivatePlayer;

import java.util.List;

/**
 * Public Class GameBoard
 * @author Davide Gioiosa
 */

public class GameBoard extends ClientBoard{

    /**
     * Bag containing all the 90 dice
     */
    private BagDice bagDice;
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

        super(playerList, boardDice, trackBoardDice, boardCard);

        if(bagDice == null){
            throw new NullPointerException("Insertion of a null bagDice");
        }
        if(privatePlayerList == null){
            throw new NullPointerException("Insertion of a null privatePlayerList");
        }

        this.bagDice = bagDice;
        this.privatePlayerList = privatePlayerList;
    }

    /**
     * Copy Constructor
     * @param gameBoard GameBoard to be cloned
     */
    private GameBoard(GameBoard gameBoard){
        super(gameBoard);
        this.bagDice = new BagDice();
        for(Die die: gameBoard.getBagDice().getClonedDieList()) this.bagDice.insertDice(die);
        this.privatePlayerList = null;
    }

    /**
     * Getter method for Dice Bag
     * @return Bag containing remaining dice
     */
    public BagDice getBagDice() {
        return bagDice;
    }

    /**
     * Gets method for List of Private Objective card assigned to players
     * @return List of PrivatePlayer
     */
    public List<PrivatePlayer> getPrivatePlayerList() {
        return privatePlayerList;
    }

    @Override
    public GameBoard getClone(){
        return new GameBoard(this);
    }
}