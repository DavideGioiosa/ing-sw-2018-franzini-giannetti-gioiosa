package it.polimi.se2018.model;

import it.polimi.se2018.controller.Action;
import it.polimi.se2018.model.player.Player;

import java.util.ArrayList;
import java.util.List;


//TODO: It will be observer of PlayerMove to catch the update after client inserts an action

public class Round {
    /**
     * list of turns belongining to each player componing the round
     */
    private ArrayList<ArrayList<Action>> turnsActionsList;
    /**
     * list of players in the match
     */
    private List <Player> playerList;
    /**
     * will receive the playerMove from the server after upload
     */
    private PlayerMove playerMove;
    /**
     * actual state of the gameBoard
     */
    private GameBoard gameBoard;
    /**
     * Trackboard cointaning surplus dice of every round
     */
    TrackBoard trackBoard;

    /**
     * Builder of Round
     * @param playerList list of player in the game
     * @param gameBoard full table of the game
     */
    public Round (List<Player> playerList, GameBoard gameBoard, TrackBoard trackBoard){
        this.playerList = playerList;
        this.gameBoard = gameBoard;
        this.trackBoard = trackBoard;
    }

    /**
     * Start of the round including all the actions of each player
     * @param turnOrder list of player ordered by turn priority
     */
    public void startRound(List<Player> turnOrder) {
        turnsActionsList = new ArrayList<ArrayList<Action>> ();

        for (int i = 0; i <= turnOrder.size(); i++) {
             update(playerMove, turnOrder, i);
        }

        for (int i = turnOrder.size(); i >=0; i--) {
            update(playerMove, turnOrder, i);
        }
        endRound();
    }

    /**
     * Placement of the surplus dice in the Draft Pool on the Trackboard
     */
    private void endRound (){
        trackBoard.insertDice(gameBoard.getBoardDice().getDieList());
    }

    /**
     * Wait the update of the player move of the current Player,
     * create and performs each actions until the 'Pass' one
     * @param playerMove action of the current player
     * @param actionOrder list of player ordered by turn priority
     * @param index of the current player
     */
    public void update (PlayerMove playerMove, List<Player> actionOrder, int index){
        this.playerMove = playerMove;
        if (playerMove.getPlayer() ==  actionOrder.get(index)) {
            while (playerMove.getTypeOfChoice().equals(TypeOfChoiceEnum.PASS)){

                Action action = new Action (gameBoard);

                if(action.selectAction(playerMove)) {
                    turnsActionsList.get(index).add(action);
                }

                //wait for the next action of the player
                update(playerMove, actionOrder, index);
            }
        }
    }

}