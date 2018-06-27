package it.polimi.se2018.controller;

import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.TypeOfChoiceEnum;

import java.util.ArrayList;
import java.util.List;


/**
 * Controller's Class Turn
 * componing the round with all the action of the current player
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
    private List<PickController> turnsActionsList;
    /**
     * informs if the action PICK has already done or not
     * it can be done once a Turn for each player
     */
    private int numberOfPicks;
    /**
     * informs if the action TOOL has already done or not
     * it can be done once a Turn for each player
     */
    private boolean isToolCardUsed;

    private int numberOfPossiblePicks = 1;

    /**
     * Builder of Turn which composes the Round
     * @param gameBoard full table of the game
     */
    public Turn (GameBoard gameBoard){
        if (gameBoard == null){
            throw new NullPointerException("Insertion of null parameter gameBoard");
        }
        this.gameBoard = gameBoard;
        this.numberOfPicks = 0;
        this.isToolCardUsed = false;
        startTurn();

    }

    /**
     * Creates the list of Action belonging the player for each Turn
     */
    private void startTurn() {
        turnsActionsList = new ArrayList<>();
    }

    /**
     * List of the actions componing the Turn
     * @return list of actions
     */
    public List<PickController> getTurnsActionsList() {
        return turnsActionsList;
    }

    /**
     * Start run creating the action and adding to the list of Action if it can be done
     * Check if the action PICK has already done or not, it can be done once for Turn
     * @param playerMove Move that the player wants to make
     * @return boolean to communicate the result of the action
     */
    public boolean runTurn (PlayerMove playerMove){
        if(playerMove == null){
            throw new RuntimeException("Insertion of a PlayerMove null");
        }
        if(playerMove.getTypeOfChoice().equals(TypeOfChoiceEnum.PICK) && !canPick()){
            return false;
        }
        if(playerMove.getTypeOfChoice().equals(TypeOfChoiceEnum.TOOL) && isToolCardUsed){
            return false;
        }

        PickController pickController = new PickController(gameBoard);

        if(pickController.doAction(gameBoard, playerMove,null, null) == 0){
            turnsActionsList.add(pickController);
            if(playerMove.getTypeOfChoice().equals(TypeOfChoiceEnum.PICK)){
                this.numberOfPicks++;
            }
            else if(playerMove.getTypeOfChoice().equals(TypeOfChoiceEnum.TOOL)){
                this.isToolCardUsed = true;
            }
            return true;
        }
        return false;
    }

    /**
     * Communicate if the action received is the last of the player and ends the turn
     * @return boolean to communicate the end of the Turn for the current player
     */
    public boolean endTurn (){
        if (turnsActionsList.isEmpty()) return false;
        return turnsActionsList.get(turnsActionsList.size()-1).getPlayerMove().getTypeOfChoice().equals(TypeOfChoiceEnum.PASS);
    }

    public void incrementPossiblePicks(){
        numberOfPossiblePicks++;
    }

    private boolean canPick(){
        return numberOfPossiblePicks != numberOfPicks;
    }
}
