package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.RemoteView;

import java.util.ArrayList;
import java.util.List;

/**
 * Round is composed by a List of Turn. It starts with dice extraction and ends putting discarded dice in the TrackBoard
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

    private Player nextRoundFirstPlayer;

    private RemoteView view;

    /**
     * Builder of Round which composes the match
     * @param gameBoard full table of the game
     * @param indexRound index of the current Round created
     */
    public Round (GameBoard gameBoard, int indexRound, RemoteView view){
        if (indexRound < 0 || indexRound > 9){
            throw new IllegalArgumentException("Error creation of a Round " +
                    "with index out of the range permitted");
        }
        this.playerList = gameBoard.getPlayerList();
        this.gameBoard = gameBoard;
        this.isDraftPoolSet = false;
        initializeRound(indexRound);
        this.view = view;
        if(getCurrPlayer().getConnectionStatus()) view.isYourTurn(getCurrPlayer(), new PlayerMove());
        else defaultMove();
    }

    /**
     * Start of the round sets the list of players ordered by priority for the current Round
     * and creates the list of Turns belonging to each player
     * @param indexRound Index of actual round
     * */
    private void initializeRound(int indexRound) {
        turnsList = new ArrayList<>();
        setRoundPlayerOrder(indexRound);
        nextRoundFirstPlayer = roundPlayerOrder.get(1);
        turn = new Turn(gameBoard, getCurrPlayer());
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


    void defaultMove(){
        System.out.println("Default move invocata per: " + roundPlayerOrder.get(0).getNickname());
        if(!isDraftPoolSet) {
            PlayerMove playerMove = new PlayerMove();
            playerMove.setPlayer(roundPlayerOrder.get(0));
            playerMove.setTypeOfChoice(TypeOfChoiceEnum.EXTRACT);
            update(playerMove);
        }else{
            if(!turn.isFinished()) turn.defaultMove();
            if(terminateTurn()) return;
        }
        Player player = getCurrPlayer();
        if(player != null && player.getConnectionStatus())
            view.isYourTurn(getCurrPlayer(), getStartedPlayerMove());
        else defaultMove();
    }

    /**
     * Get of the current player from the ordered list of turns in the Round
     * @return current player
     */
    private Player getCurrPlayer() {
        if(roundPlayerOrder.isEmpty()) return null;
        return roundPlayerOrder.get(0);
    }

    boolean isEnded(){
        return roundPlayerOrder.isEmpty();
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
        List<Die> dieList = new ArrayList<>();
        while(!gameBoard.getBoardDice().getDieList().isEmpty()){
            dieList.add(gameBoard.getBoardDice().takeDie(0));
        }
        gameBoard.getTrackBoardDice().insertDice(dieList);
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
                Die die = gameBoard.getBagDice().extractDice();
                die.firstRoll();
                gameBoard.getBoardDice().insertDie(die);
                this.isDraftPoolSet = true;
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
        int idError;
        if(playerMove.getPlayer() != null && playerMove.getPlayer().getNickname().equals(getCurrPlayer().getNickname())) {

            if (!isDraftPoolSet) {
                boolean rightMove = setDraftPoolDice(playerMove);
                view.sendTable(new MoveMessage(gameBoard.getPlayerList(), gameBoard.getBoardDice(), gameBoard.getCardOnBoard(), gameBoard.getTrackBoardDice()));
                if(!rightMove) view.reportError(2104, getCurrPlayer().getNickname());

            } else {
                idError = turn.runTurn(playerMove, roundPlayerOrder);
                view.sendTable(new MoveMessage(gameBoard.getPlayerList(), gameBoard.getBoardDice(), gameBoard.getCardOnBoard(), gameBoard.getTrackBoardDice()));
                if (idError != 0) view.reportError(idError, getCurrPlayer().getNickname());

                if (turn.isFinished()) {
                    if(terminateTurn()) return;
                }
            }
            if(getCurrPlayer().getConnectionStatus()) view.isYourTurn(getCurrPlayer(), getStartedPlayerMove());
            else defaultMove();
        }

    }

    /**
     * Gets last Turn created
     * @return Last Turn created
     */
    private Turn lastTurn(){
        return turnsList.get(turnsList.size() - 1);
    }

    /**
     * PlayerMove to be sent to the next User
     * @return New PlayerMove or PlayerMove not finished
     */
    private PlayerMove getStartedPlayerMove(){
        if(lastTurn().lastActionFinished()) return new PlayerMove();
        return lastTurn().getStartedPlayerMove();
    }

    /**
     * Close turn
     * @return Boolean indicating if the round is terminated or not
     */
    private boolean terminateTurn(){
        removeCurrPlayer();
        if (roundPlayerOrder.isEmpty()) {
            endRound();
            view.sendTable(new MoveMessage(gameBoard.getPlayerList(), gameBoard.getBoardDice(), gameBoard.getCardOnBoard(), gameBoard.getTrackBoardDice()));
            return true;
        } else {
            turn = new Turn(gameBoard, getCurrPlayer());
            turnsList.add(turn);
            return false;
        }
    }

}