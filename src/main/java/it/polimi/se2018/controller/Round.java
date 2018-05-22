package it.polimi.se2018.controller;

import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.TrackBoard;
import it.polimi.se2018.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;


/**
 * Public Class Round
 * @author Davide Gioiosa
 */

public class Round implements Observer <PlayerMove> {
    /**
     * list of Turns of each player componing the round
     */
    private List<Turn> turnsList;
    /**
     * list of players in the match
     */
    private List <Player> playerList;
    /**
     * current playerMove belonging to the current Turn player
     */
    private PlayerMove playerMove;
    /**
     * table containing all the objects belonging to the match
     */
    private GameBoard gameBoard;
    /**
     * trackboard cointaning surplus dice of every Round
     */
    private TrackBoard trackBoard;
    /**
     * list of all the turns belonging to each player in the Round
     */
    private List<Player> roundPlayerOrder;
    /**
     * Turn componing the Round
     */
    private Turn turn;


    /**
     * Builder of Round which composes the match
     * @param playerList list of player in the game
     * @param gameBoard full table of the game
     * @param trackBoard list of dice in surplus placed in each round
     * @param indexRound index of the current Round created
     */
    public Round (List<Player> playerList, GameBoard gameBoard, TrackBoard trackBoard, int indexRound){
        if(playerList == null){
            throw new NullPointerException("Insertion of null parameter playerList");
        }
        if (gameBoard == null){
            throw new NullPointerException("Insertion of null parameter gameBoard");
        }
        if (trackBoard == null){
            throw new NullPointerException("Insertion of null parameter trackBoard");
        }
        if (indexRound < 0 || indexRound > 9){
            throw new IllegalArgumentException("Error creation of a Round " +
                    "with index out of the range permitted");
        }
        this.playerList = playerList;
        this.gameBoard = gameBoard;
        this.trackBoard = trackBoard;
        startRound(indexRound);
        turn = new Turn(gameBoard, playerMove);
    }

    /**
     * Start of the round sets the list of players ordered by priority for the current Round
     * and creates the list of Turns belonging to each player
     */
    private void startRound(int indexRound) {
        turnsList = new ArrayList<Turn> ();
        setRoundPlayerOrder(indexRound);
    }

    /**
     * Creates and sets the list of players ordered by priority for the current Round
     * starting from the list of player in the match and the current Round index
     */
    private void setRoundPlayerOrder (int indexRound){
        roundPlayerOrder = new ArrayList<Player>();

        for (int i = indexRound; i < indexRound + playerList.size(); i++){
            roundPlayerOrder.add(playerList.get(i % playerList.size()));
        }
        for (int i = indexRound + playerList.size(); i > indexRound; i--){
            roundPlayerOrder.add(playerList.get(i % playerList.size()));
        }
    }

    /**
     * Get of the current player from the ordered list of turns in the Round
     * @return current player
     */
    private Player getCurrPlayer() {
        return roundPlayerOrder.get(0);
    }

    /**
     * Exctraction of the current player from the ordered list of turns in the Round
     */
    public void removeCurrPlayer () {
        roundPlayerOrder.remove(0);
    }

    /**
     * Placement of the surplus dice in the Draft Pool on the Trackboard
     */
    private void endRound (){
        trackBoard.insertDice(gameBoard.getBoardDice().getDieList());
    }

    /**
     * Update of the playerMove of the current Player during the Round,
     * perform Turns of each player composed by actions
     * @param playerMove action of the current player
     */
    private void update (PlayerMove playerMove){
        if(playerMove == null){
            throw new RuntimeException("Empty playerMove action to execute");
        }

        if (playerMove.getPlayer().equals(getCurrPlayer())) {
            this.playerMove = playerMove;

            if(turn.runTurn()) {
                turnsList.add(turn);
            }

            if(turn.endTurn()){
                removeCurrPlayer();
                if(turnsList.size() < playerList.size() * 2) {
                    turn = new Turn(gameBoard, playerMove);
                }
            }
        }
    }
}