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
 * This ToolCard manager sets operation and manages the interaction between them.
 */
public class ToolController implements Action{

    private List<List<ToolOperation>> toolOperationLists;
    private List<List<OperationString>> operationStrings;
    private int state;
    private boolean isComplete;
    private List<Die> dieList;


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

            HashMap<String,DiceContainer> diceContainerHashMap = setDiceContainerHashMap(gameBoard, playerMove.getPlayer());

            for (int i = 0; i < toolOperationLists.get(state).size(); i++) {
                if(!toolOperationLists.get(state).get(i).start(diceContainerHashMap.get(operationStrings.get(state).get(i).getDiceContainer()),
                        playerMove, dieList, roundPlayerOrder, turn)) throw new RuntimeException("ERRORE DA GESTIRE");
            }

            state ++;
            if (state == toolOperationLists.size()) isComplete = true;
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
                    default: throw new IllegalArgumentException("ERROR: Wrong Operation Name");

                }

            }
        }

    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    public int doDefaultMove(){
        return 0;
    }

    public boolean isPass() {
        return false;
    }

}
