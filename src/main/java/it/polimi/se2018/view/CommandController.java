package it.polimi.se2018.view;

import it.polimi.se2018.connection.client.Client;
import it.polimi.se2018.connection.client.ClientStrategy;
import it.polimi.se2018.model.CheckRestriction;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMove;

/**
 * View's Class: Command Controller
 *
 * @author Davide Gioiosa
 */

public class CommandController {

    /**
     * Checks the validity of the Player Move
     */
    private CheckRestriction checkRestriction;
    /**
     * Client for sending of valid move
     */
    private Client client;

    /**
     * Builder of the class, creates a checkRestriction object
     */
    public CommandController (Client client){
        this.client = client;
        checkRestriction = new CheckRestriction();
    }

    /**
     * Checks if the action that the current player wants to do is legal and acceptable for the rules
     * 1 - All the CheckRestriction (0,1,4,5,6,7,8,10,11,12)
     * 2 - All the CheckRestriction except cellColourRestriction (2)
     * 3 - All the CheckRestriction except cellValueRestriction (3)
     * 4 - All the CheckRestriction except adjacentRestriction (9)
     * @param playerMove Contains the complete action of the current player
     * @param gameBoard Board with actual game status
     * @return 0 if the action is correct, else the ID code of the error why the action can't be done
     */
    public int checkMoveValidity (PlayerMove playerMove, GameBoard gameBoard){

        if (playerMove.getPlayer().getSchemaCard().isEmpty() &&
                !checkRestriction.isOnTheBorder(playerMove.getDiceSchemaWhereToLeave().get(0))) {
            return 1;
        }

        if(!playerMove.getPlayer().getSchemaCard().isEmpty() &&
                playerMove.getIdToolCard() != 9 && !checkRestriction.adjacentRestriction(playerMove.getPlayer().getSchemaCard(),
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

        if(!playerMove.getPlayer().getSchemaCard().isEmpty() &&
                !checkRestriction.adjacentColourRestriction(playerMove.getPlayer().getSchemaCard(),
                gameBoard.getBoardDice().getDieList().get(playerMove.getDiceBoardIndex()),
                playerMove.getDiceSchemaWhereToLeave().get(0))){
            return 5;
        }

        if(!playerMove.getPlayer().getSchemaCard().isEmpty() &&
                !checkRestriction.adjacentValueRestriction(playerMove.getPlayer().getSchemaCard(),
                gameBoard.getBoardDice().getDieList().get(playerMove.getDiceBoardIndex()),
                playerMove.getDiceSchemaWhereToLeave().get(0))){
            return 6;
        }

        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setMove(playerMove);
        client.send(playerMessage);
        return 0;
    }
        //chiama ClientStrategy per inviare la playermove
}
