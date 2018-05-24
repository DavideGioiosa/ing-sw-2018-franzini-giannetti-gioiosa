package it.polimi.se2018.view;

import it.polimi.se2018.model.CheckRestriction;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.PlayerMove;

/**
 * View's Class: Command Controller
 *
 * @author Davide Gioiosa
 */

public class CommandController {

    private CheckRestriction checkRestriction;

    /**
     * Builder of the class, creates a checkRestriction object
     */
    public CommandController (){
        checkRestriction = new CheckRestriction();
    }

    /**
     * Checks if the action that the current player wants to do is legal and acceptable for the rules
     * 1 - All the CheckRestriction (0,1,4,5,6,7,8,10,11,12)
     * 2 - All the CheckRestriction except cellColourRestriction (2)
     * 3 - All the CheckRestriction except cellValueRestriction (3)
     * 4 - All the CheckRestriction except adjacentRestriction (9)
     * @param playerMove contains the complete action of the current player
     * @return 0 if the action is correct, else the ID code of the error why the action can't be done
     */
    public int checkMoveValidity (PlayerMove playerMove, GameBoard gameBoard){

        if (playerMove.getPlayer().getSchemaCard().isEmpty() &&
                checkRestriction.isOnTheBorder(playerMove.getDiceSchemaWhereToLeave().get(0))) {
            return 0;
        }

        if (playerMove.getPlayer().getSchemaCard().isEmpty() &&
                !checkRestriction.isOnTheBorder(playerMove.getDiceSchemaWhereToLeave().get(0))) {
            return 1;
        }

        if(playerMove.getIdToolCard() != 9 && !checkRestriction.adjacentRestriction(playerMove.getPlayer().getSchemaCard(),
                gameBoard.getBoardDice().getDieList().get(playerMove.getDiceBoardIndex()),
                playerMove.getDiceSchemaWhereToLeave().get(0))){
            return 2;
        }

        if(playerMove.getIdToolCard() != 2 && !checkRestriction.cellColourRestriction(playerMove.getPlayer().getSchemaCard(),
                gameBoard.getBoardDice().getDieList().get(playerMove.getDiceBoardIndex()),
                playerMove.getDiceSchemaWhereToLeave().get(0))){
            return 3;
        }

        if(playerMove.getIdToolCard() != 3 && !checkRestriction.cellValueRestriction(playerMove.getPlayer().getSchemaCard(),
                gameBoard.getBoardDice().getDieList().get(playerMove.getDiceBoardIndex()),
                playerMove.getDiceSchemaWhereToLeave().get(0))){
            return 4;
        }

        if(!checkRestriction.adjacentColourRestriction(playerMove.getPlayer().getSchemaCard(),
                gameBoard.getBoardDice().getDieList().get(playerMove.getDiceBoardIndex()),
                playerMove.getDiceSchemaWhereToLeave().get(0))){
            return 5;
        }

        if(!checkRestriction.adjacentValueRestriction(playerMove.getPlayer().getSchemaCard(),
                gameBoard.getBoardDice().getDieList().get(playerMove.getDiceBoardIndex()),
                playerMove.getDiceSchemaWhereToLeave().get(0))){
            return 6;
        }

        return 0;
    }

}
