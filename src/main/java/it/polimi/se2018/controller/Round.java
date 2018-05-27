package it.polimi.se2018.controller;

import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.utils.Observer;

import java.util.ArrayList;
import java.util.List;


/**
 * Controller's Class Round
 * componing the the match
 * @author Davide Gioiosa
 */

public class Round implements Observer<PlayerMove>{
    /**
     * list of Turns of each player componing the round
     */
    private List<Turn> turnsList;
    /**
     * list of players in the match
     */
    private List <Player> playerList;
    /**
     * table containing all the objects belonging to the match
     */
    private GameBoard gameBoard;
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
     * @param gameBoard full table of the game
     * @param indexRound index of the current Round created
     */
    public Round (GameBoard gameBoard, int indexRound){
        if (gameBoard == null){
            throw new NullPointerException("Insertion of null parameter gameBoard");
        }
        if (indexRound < 0 || indexRound > 9){
            throw new IllegalArgumentException("Error creation of a Round " +
                    "with index out of the range permitted");
        }
        this.playerList = gameBoard.getPlayerList();
        this.gameBoard = gameBoard;
        initializeRound(indexRound);
    }

    /**
     * Start of the round sets the list of players ordered by priority for the current Round
     * and creates the list of Turns belonging to each player
     */
    private void initializeRound(int indexRound) {
        turnsList = new ArrayList<Turn> ();
        setRoundPlayerOrder(indexRound);
        turn = new Turn(gameBoard);
        turnsList.add(turn);
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
        for (int i = indexRound + playerList.size() - 1 ; i > indexRound; i--){
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
    private void removeCurrPlayer () {
        roundPlayerOrder.remove(0);
    }

    /**
     * Placement of the surplus dice in the Draft Pool on the Trackboard
     */
    private void endRound (){
        gameBoard.getTrackBoardDice().insertDice(gameBoard.getBoardDice().getDieList());
        notify(); //TODO: CHECK CORRECT
    }

    /**
     * Update of the playerMove of the current Player during the Round,
     * perform Turns of each player composed by actions
     * @param playerMove action of the current player
     */
    public void update (PlayerMove playerMove){
        if(playerMove == null){
            throw new RuntimeException("Empty playerMove action to execute");
        }

        if (playerMove.getPlayer().equals(getCurrPlayer())) {

            if(!turn.runTurn(playerMove)){
                //Notifica al server che la playerMove non è corretta
            }

            if(turn.endTurn()){
                removeCurrPlayer();

                //works but it be may exists a better check
                if(turnsList.size() < roundPlayerOrder.size() + turnsList.size()) {
                    turn = new Turn(gameBoard);
                    turnsList.add(turn);
                }

                if(roundPlayerOrder.isEmpty()){
                    endRound();
                }
            }
        }
    }

}