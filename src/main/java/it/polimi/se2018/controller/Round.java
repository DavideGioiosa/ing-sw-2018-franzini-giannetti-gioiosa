package it.polimi.se2018.controller;

import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.TypeOfChoiceEnum;
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
     * informs if the action EXTRACT has already done or not
     * it can be done once a Round and only by the first player
     */
    private boolean isDraftPoolSet;

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
        this.isDraftPoolSet = false;
        initializeRound(indexRound);
    }

    /**
     * Start of the round sets the list of players ordered by priority for the current Round
     * and creates the list of Turns belonging to each player
     * @param indexRound Index of actual round
     * */
    private void initializeRound(int indexRound) {
        turnsList = new ArrayList<> ();
        setRoundPlayerOrder(indexRound);
        turn = new Turn(gameBoard);
        turnsList.add(turn);
    }

    /**
     * Creates and sets the list of players ordered by priority for the current Round
     * starting from the list of player in the match and the current Round index
     * @param indexRound Index of actual round
     */
    private void setRoundPlayerOrder (int indexRound){
        roundPlayerOrder = new ArrayList<>();

        for (int i = indexRound; i < indexRound + playerList.size(); i++){
            roundPlayerOrder.add(playerList.get(i % playerList.size()));
        }
        for (int i = indexRound + playerList.size() - 1 ; i >= indexRound; i--){
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
     * List of the turns componing the Round
     * @return list of turns
     */
    public List<Turn> getTurnsList() {
        return turnsList;
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
        //notify(); //TODO: CHECK CORRECT
    }

    /**
     * Extraction of the dice from the BagDice into the Draft Pool
     * @param playerMove extraction of the first player of the Turn
     * @return the result of the operation
     */
    private boolean setDraftPoolDice (PlayerMove playerMove) {
        if (playerMove.getTypeOfChoice().equals(TypeOfChoiceEnum.EXTRACT)){
            for (int i = 0; i < playerList.size() * 2 + 1; i++) {
                gameBoard.getBoardDice().insertDice(gameBoard.getBagDice().extractDice());
            }
            return true;
        }
        return false;
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
            if (!isDraftPoolSet) {
                if (setDraftPoolDice(playerMove)) {
                    this.isDraftPoolSet = true;
                }
                else {
                    //set errore da restituire
                    //System.out.println("La prima mossa da fare per il primo player del Round è l'extract");
                }
            }

            else {
                if (!turn.runTurn(playerMove)) {
                    //Notifica al server che la playerMove non è corretta
                }

                if (turn.endTurn()) {
                    removeCurrPlayer();

                    //works but it be may exists a better check
                    if (turnsList.size() < roundPlayerOrder.size() + turnsList.size()) {
                        turn = new Turn(gameBoard);
                        turnsList.add(turn);
                    }

                    if (roundPlayerOrder.isEmpty()) {
                        endRound();
                    }
                }
            }
        }
    }

}