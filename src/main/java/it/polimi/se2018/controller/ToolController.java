package it.polimi.se2018.controller;

import it.polimi.se2018.controller.toolcardactions.*;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ToolCards are divided into states and operations. A state is what ToolCard needs between two user interaction divided
 * by Server request. An operation is the minimum ToolCard's action.
 * This ToolCard manager sets operation of the Card used and manages the interaction between the operation.
 */
public class ToolController implements Action{

    private List<List<ToolOperation>> toolOperationLists;
    private List<List<OperationString>> operationStrings;
    private int state;
    private boolean isComplete;
    private List<Die> dieList;
    private PlayerMove playerMoveSaved;
    private GameBoard gameBoardCloned;

    /**
     * Manages the
     * @param toolCard
     */
    public ToolController(ToolCard toolCard){
        dieList = new ArrayList<>();
        isComplete = false;

        this.operationStrings = toolCard.getCommandLists();
        createOperationLists(operationStrings, toolCard.getAvoidedRestriction(), toolCard.getIndexOfTurn(),
                toolCard.getMinQuantity(), toolCard.getMaxQuantity());

        state = 0;
    }

    public int doAction(GameBoard gameBoard, PlayerMove playerMove, List<Player> roundPlayerOrder, Turn turn){


        if(!isComplete) {
            this.gameBoardCloned = gameBoard.getClone();
            this.playerMoveSaved = playerMove.getClone();
            List<Die> dieListCloned = new ArrayList<>();
            for(Die die: dieList)dieListCloned.add(die.getClone());
            Turn turnCloned;
            if(turn != null) turnCloned = turn.getClone();
            else turnCloned = null;
            List<Player> roundPlayerOrderCloned = new ArrayList<>();
            if(roundPlayerOrder != null) for(Player player: roundPlayerOrder) roundPlayerOrderCloned.add(player.getClone());
            else roundPlayerOrderCloned = null;

            int idError = tryMove(gameBoardCloned, toolOperationLists, state, operationStrings, dieListCloned, playerMoveSaved, roundPlayerOrderCloned, turnCloned);
            if(idError != 0) return idError;

            tryMove(gameBoard, toolOperationLists, state, operationStrings, dieList, playerMove, roundPlayerOrder, turn);

            state ++;
            playerMove.setState(state);
            if (state == toolOperationLists.size()) isComplete = true;
            playerMoveSaved = playerMove.getClone();
            return 0;
        }
        return 1000;
    }

    private int tryMove(GameBoard gameBoard, List<List<ToolOperation>> toolOperationLists, int state, List<List<OperationString>> operationStrings, List<Die> dieList, PlayerMove playerMove, List<Player> roundPlayerOrder, Turn turn) {
        HashMap<String,DiceContainer> diceContainerHashMap = setDiceContainerHashMap(gameBoard, playerMove.getPlayer());

        for (int i = 0; i < toolOperationLists.get(state).size(); i++) {
            if(!toolOperationLists.get(state).get(i).start(diceContainerHashMap.get(operationStrings.get(state).get(i).getDiceContainer()),
                    playerMove, dieList, roundPlayerOrder, turn)){
                return 2103;
            }
        }
        return 0;
    }

    private HashMap<String, DiceContainer> setDiceContainerHashMap(GameBoard gameBoard, Player player){
        HashMap<String,DiceContainer> diceContainerHashMap = new HashMap<>();

        diceContainerHashMap.put("dicebag", gameBoard.getBagDice());
        diceContainerHashMap.put("diceboard", gameBoard.getBoardDice());

        diceContainerHashMap.put("trackboard", gameBoard.getTrackBoardDice());
        for(Player playerChecked: gameBoard.getPlayerList()){
            if(playerChecked.getNickname().equals(player.getNickname())) {
                diceContainerHashMap.put("schemacard", playerChecked.getSchemaCard());
            }
        }
        return diceContainerHashMap;
    }

    private void createOperationLists(List<List<OperationString>> operationStringsList, String avoidedRestriction, int indexOfRound, int minNumberOfDice, int maxNumberOfDice){
        toolOperationLists = new ArrayList<>();

        for(List<OperationString> operationList: operationStringsList){
            toolOperationLists.add(new ArrayList<>());

            for(OperationString operationString: operationList){

                switch (operationString.getOperation()){
                    case "pick":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationPickDice(minNumberOfDice, maxNumberOfDice));
                        break;
                    case "doublepick":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationDoublePick());
                        break;
                    case "checksamecolour":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationCheckSameColourOfOneInDiceContainer());
                        break;
                    case "incdecvalue":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationIncDecValue());
                        break;
                    case "exchange":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationExchange());
                        break;
                    case "reroll":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationReRoll(indexOfRound));
                        break;
                    case "leave":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationLeaveDice(avoidedRestriction));
                        break;
                    case "opposite":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationOppositeValue());
                        break;
                    case "setdievalue":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationSetDieValue());
                        break;
                    case "canbeplaced":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationCanBePlaced());
                        break;
                    case "pickcanpass":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationPickCanPass(minNumberOfDice, maxNumberOfDice));
                        break;
                    default: throw new IllegalArgumentException("ERROR: Wrong Operation Name");

                }

            }
        }

    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    public int doDefaultMove(GameBoard gameBoard){
        if(dieList != null) for(Die die: dieList){
            if(die.getValue() == 0) die.firstRoll();
            gameBoard.getBoardDice().insertDie(die);
        }
        return 0;
    }

    public boolean isPass() {
        return false;
    }

    @Override
    public PlayerMove getPlayerMove() {
        return playerMoveSaved;
    }

}
