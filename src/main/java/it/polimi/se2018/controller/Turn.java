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
     * current playerMove belonging to the current Turn player
     */
    private PlayerMove playerMove;
    /**
     * list of Actions of the player componing the turn
     */
    private List<Action> turnsActionsList;

    /**
     * Builder of Turn which composes the Round
     * @param gameboard full table of the game
     * @param playerMove contains the action belonging to the current player
     */
    public Turn (GameBoard gameboard, PlayerMove playerMove){
        if (gameBoard == null){
            throw new NullPointerException("Insertion of null parameter gameBoard");
        }
        if (playerMove == null){
            throw new NullPointerException("Insertion of null parameter playerMove");
        }
        this.gameBoard = gameboard;
        this.playerMove = playerMove;
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
     * @return boolean to communicate the result of the action
     */
    public boolean runTurn (){
        Action action = new Action (gameBoard);

        if(action.selectAction(playerMove)){
            turnsActionsList.add(action);
            return true;
        }
        return false;
    }

    /**
     * Communicate if the action received is the last of the player
     * @return boolean to communicate the end of the Turn for the current player
     */
    public boolean endTurn (){
        if(playerMove.getTypeOfChoice().equals(TypeOfChoiceEnum.PASS)){
            return true;
        }
        return false;
    }
}
