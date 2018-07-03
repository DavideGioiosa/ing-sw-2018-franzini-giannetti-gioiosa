package it.polimi.se2018.controller;

import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.TypeOfChoiceEnum;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.player.Player;

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
    private List<Action> turnsActionsList;
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

    private Player actualPlayer;

    /**
     * Builder of Turn which composes the Round
     * @param gameBoard full table of the game
     */
    public Turn (GameBoard gameBoard, Player actualPlayer){
        if (gameBoard == null){
            throw new NullPointerException("Insertion of null parameter gameBoard");
        }
        if (actualPlayer == null) throw new NullPointerException("Insertion of null player in turn");
        this.gameBoard = gameBoard;
        this.numberOfPicks = 0;
        this.isToolCardUsed = false;
        startTurn();
        this.actualPlayer = actualPlayer;
    }

    void defaultMove(){
        if(turnsActionsList.isEmpty() || turnsActionsList.get(turnsActionsList.size()-1).isComplete()){
            PlayerMove playerMove = new PlayerMove();
            playerMove.setPlayer(actualPlayer);
            playerMove.setTypeOfChoice(TypeOfChoiceEnum.PASS);

            PickController action = new PickController();
            action.doAction(gameBoard, playerMove, null,null);
            turnsActionsList.add(action);
        }else turnsActionsList.get(turnsActionsList.size()-1).doDefaultMove();
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
    public List<Action> getTurnsActionsList() {
        return turnsActionsList;
    }

    /**
     * Start run creating the action and adding to the list of Action if it can be done
     * Check if the action PICK has already done or not, it can be done once for Turn
     * @param playerMove Move that the player wants to make
     * @return boolean to communicate the result of the action
     */
    int runTurn (PlayerMove playerMove, List<Player> roundPlayerOrder){
        int errorId = 0;
        if(playerMove == null){
            throw new NullPointerException("Insertion of a PlayerMove null");
        }
        if(turnsActionsList.isEmpty() || turnsActionsList.get(turnsActionsList.size() - 1).isComplete()) {
            if(playerMove.getTypeOfChoice().equals(TypeOfChoiceEnum.PASS)){
                PickController pickController = new PickController();
                errorId = pickController.doAction(gameBoard, playerMove, null, null);
                turnsActionsList.add(pickController);
                return errorId;
            }

            if (playerMove.getTypeOfChoice().equals(TypeOfChoiceEnum.PICK)){
                if (canPick()) {
                    PickController pickController = new PickController();
                    errorId = pickController.doAction(gameBoard, playerMove, null, null);
                    if (errorId == 0) {
                        turnsActionsList.add(pickController);
                        this.numberOfPicks++;
                        if (isToolCardUsed && numberOfPicks == numberOfPossiblePicks){
                            PickController passMove = new PickController();
                            passMove.doDefaultMove();
                            turnsActionsList.add(passMove);
                        }
                    }
                    return errorId;
                }
                errorId = 2100;
                return errorId;
            }

            if (playerMove.getTypeOfChoice().equals(TypeOfChoiceEnum.TOOL)) {
                if (!isToolCardUsed) {
                    for (ToolCard toolCard : gameBoard.getCardOnBoard().getToolCardList()) {
                        if (toolCard.getId() == playerMove.getIdToolCard()) {
                            ToolController toolController = new ToolController(toolCard);
                            errorId = toolController.doAction(gameBoard, playerMove, roundPlayerOrder, this);
                            if (errorId == 0) {
                                turnsActionsList.add(toolController);
                                this.isToolCardUsed = true;
                                if (toolController.isComplete() && numberOfPicks == numberOfPossiblePicks){
                                    PickController passMove = new PickController();
                                    passMove.doDefaultMove();
                                    turnsActionsList.add(passMove);
                                }
                            }
                            return errorId;

                        }
                    }
                    errorId = 2007;
                } else errorId = 2101;
            }
            return errorId;
        }else{
            errorId = turnsActionsList.get(turnsActionsList.size() -1).doAction(gameBoard, playerMove, null, this);
        }
        return errorId;
    }

    /**
     * Communicate if the action received is the last of the player and ends the turn
     * @return boolean to communicate the end of the Turn for the current player
     */
    boolean isFinished (){
        if (turnsActionsList.isEmpty()) return false;
        return turnsActionsList.get(turnsActionsList.size()-1).isPass();
    }

    public void incrementPossiblePicks(){
        numberOfPossiblePicks++;
    }

    private boolean canPick(){
        return numberOfPossiblePicks != numberOfPicks;
    }

    private Action lastAction(){
        if(turnsActionsList.isEmpty()) return null;
        return turnsActionsList.get(turnsActionsList.size() - 1);
    }

    public boolean lastActionFinished(){
        return lastAction() == null || lastAction().isComplete();
    }

    public PlayerMove getStartedPlayerMove(){
        if(lastActionFinished()) return new PlayerMove();
        return lastAction().getPlayerMove();
    }
}