package it.polimi.se2018.controller;

import it.polimi.se2018.controller.toolcardactions.*;
import it.polimi.se2018.model.DiceContainer;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ToolCards are divided into states and operations. A state is what ToolCard needs between two user interaction divided
 * by Server request. An operation is the minimum ToolCard's action.
 * This ToolCard manager sets operation and manages the interaction between them.
 */
public class ToolController {

    private List<List<ToolOperation>> toolOperationLists;
    private List<List<OperationString>> operationStrings;
    private int state;
    private boolean isFinished;

    public ToolController(List<List<OperationString>> operationStrings, String avoidedRestriction, int minNumberOfDice, int maxNumberOfDice, int indexOfRound){

        isFinished = false;

        this.operationStrings = new ArrayList<>();
        this.operationStrings.addAll(operationStrings);
        createOperationLists(operationStrings, avoidedRestriction, indexOfRound, minNumberOfDice, maxNumberOfDice);

        state = 0;
    }

    public int useTool(GameBoard gameBoard, PlayerMove playerMove, List<Player> roundPlayerOrder, Turn turn){
        if(!isFinished) {
            List<Die> dieList = new ArrayList<>();

            HashMap<String,DiceContainer> diceContainerHashMap = setDiceContainerHashMap(gameBoard, playerMove.getPlayer());

            for (int i = 0; i < toolOperationLists.size(); i++) {
                toolOperationLists.get(state).get(i).start(diceContainerHashMap.get(operationStrings.get(state).get(i).getDiceContainer()),
                        playerMove, dieList, roundPlayerOrder, turn);
            }

            state ++;
            if (state == toolOperationLists.size()) isFinished = true;
            return 0;
        }
        return 1000;
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
                    case "PICK":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationPickDice(minNumberOfDice, maxNumberOfDice));
                        break;
                    case "DOUBLEPICK":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationDoublePick());
                        break;
                    case "CHECKSAMECOLOUR":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationCheckSameColourOfOneInDiceContainer());
                        break;
                    case "INCDECVALUE":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationIncDecValue());
                        break;
                    case "EXCHANGE":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationExchange());
                        break;
                    case "REROLL":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationReRoll(indexOfRound));
                        break;
                    case "LEAVE":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationLeaveDice(avoidedRestriction));
                        break;
                    case "OPPOSITE":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationOppositeValue());
                        break;
                    case "SETDIEVALUE":
                        toolOperationLists.get(toolOperationLists.size() - 1).add(new OperationSetDieValue());
                        break;
                    default: throw new IllegalArgumentException("ERROR: Wrong Operation Name");

                }

            }
        }

    }
}
