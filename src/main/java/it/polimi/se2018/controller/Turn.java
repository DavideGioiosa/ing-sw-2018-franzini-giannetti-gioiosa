package it.polimi.se2018.controller;

import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.TypeOfChoiceEnum;

import java.util.ArrayList;
import java.util.List;


/**
 * Public Class Turn
 * @author Davide Gioiosa
 */

public class Turn {
    /**
     * table containing all the objects belonging to the match
     */
    private GameBoard gameBoard;
    /**
     * list of Actions of the player componing the turn
     */
    private List<Action> turnsActionsList;
    /**
     * informs if the action PICK has already done or not
     * it can be done once a Turn for each player
     */
    private boolean isPick;

    /**
     * Builder of Turn which composes the Round
     * @param gameboard full table of the game
     */
    public Turn (GameBoard gameboard){
        if (gameBoard == null){
            throw new NullPointerException("Insertion of null parameter gameBoard");
        }
        this.gameBoard = gameboard;
        this.isPick = false;
        startTurn();
    }

    /**
     * Creates the list of Action belonging the player for each Turn
     */
    private void startTurn() {
        turnsActionsList = new ArrayList<Action>();
    }

    /**
     * Start run creating the action and adding to the list of Action if it can be done
     * Check if the action PICK has already done or not, it can be done once for Turn
     * @return boolean to communicate the result of the action
     */
    public boolean runTurn (PlayerMove playerMove){
        if(playerMove.getTypeOfChoice().equals(TypeOfChoiceEnum.PICK) && isPick){
            return false;
        }

        Action action = new Action (gameBoard);

        if(action.selectAction(playerMove)){
            turnsActionsList.add(action);
            if(playerMove.getTypeOfChoice().equals(TypeOfChoiceEnum.PICK)){
                this.isPick = true;
            }
            return true;
        }
        return false;
    }

    /**
     * Communicate if the action received is the last of the player
     * @return boolean to communicate the end of the Turn for the current player
     */
    public boolean endTurn (PlayerMove playerMove){
        if(playerMove.getTypeOfChoice().equals(TypeOfChoiceEnum.PASS)){
            return true;
        }
        return false;
    }
}
