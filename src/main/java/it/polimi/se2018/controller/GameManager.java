package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.publiccard.PublicObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PrivatePlayer;
import it.polimi.se2018.view.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static it.polimi.se2018.model.Config.NUMBER_OF_ROUND;

/**
 * Controller's Class GameManager
 *
 * @author Silvia Franzini
 */

public class GameManager {

    /**
     * Status of the Board Table
     */
    private GameBoard gameBoard;
    /**
     * List of game Round
     */
    private List<Round> roundList;
    /**
     * Winner of the game
     */
    private Player winner;
    /**
     * View called for show board's updates and errors
     */
    private RemoteView view;

    /**
     * Builder method of GameManager class
     * @param view View's class that talks with Controller
     * @param gameBoard Starting GameBoard
     */
    public GameManager(RemoteView view, GameBoard gameBoard){
        this.view = view;
        this.gameBoard = gameBoard;
        roundList = new ArrayList<>();
        Round round = new Round(gameBoard, 0 , view);
        roundList.add(round);
        winner = null;
        checkStatus();
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Player getWinner() {
        return winner;
    }

    public void checkStatus(){
        int connected = 0;
        Player gameWinner = null;
        for(Player player : gameBoard.getPlayerList()){
            if(player.getConnectionStatus()){
                connected++;
                gameWinner = player;
            }
        }
        if(!(roundList.size() == NUMBER_OF_ROUND && roundList.get(NUMBER_OF_ROUND - 1).isEnded()) &&
                connected <= 1) {
            winner = gameWinner;
            if (winner != null){
                winner.setScore(99);
                view.sendWinner(gameBoard);
            }
        }
    }

    private int blankCells(Player player){

        int score = 0;
        for(Cell c : player.getSchemaCard().getCellList()){
            if(c.isEmpty()){
                score++;
            }
        }
        return score;
    }

    /**
     * Method calculates the game score for each player at the end of the match
     */
    private void calculateGameScore(){

        for(Player player: gameBoard.getPlayerList()){
            int score = 0;
            for(PublicObjCard publicObjCard : gameBoard.getCardOnBoard().getPublicObjCardList()){
                try{
                    score += publicObjCard.scoreCalculation(player.getSchemaCard());
                }catch (NullPointerException e){
                    view.reportError(7002, null);
                }

            }
            score = score - blankCells(player);
            for(PrivatePlayer privatePlayer: gameBoard.getPrivatePlayerList()){
                if(player.equals(privatePlayer.getPlayer())){
                    score += privatePlayer.getPrivateObj().getScore(player.getSchemaCard());
                }
            }

            score += player.getTokens();
            player.setScore(score);
        }
    }

    void defaultMove(){
        roundList.get(roundList.size() - 1).defaultMove();
        if(roundList.get(roundList.size() - 1).isEnded()){
            endRound();
        }
    }

    /**
     * Method finds out the game winner
     */
    private void setGameWinner(){
       winner = Collections.max(gameBoard.getPlayerList(), Comparator.comparingInt(Player :: getScore));
    }

    private void closeGame(){
        calculateGameScore();
        setGameWinner();
        view.sendWinner(gameBoard);
    }

    /**
     * Update method for Observer pattern implementation
     */
    private void endRound(){

        if(roundList.size() == NUMBER_OF_ROUND){
            closeGame();
        }else {
            Round newRound = new Round(gameBoard, roundList.size(), view);
            roundList.add(newRound);
        }
    }

    void tryMove(PlayerMove playerMove){
        roundList.get(roundList.size() - 1).update(playerMove);
        if(roundList.get(roundList.size() - 1).isEnded()){
            endRound();
        }
    }

    public void reconnectUser(User user){
        for(Player player: gameBoard.getPlayerList()){
            if(player.getNickname().equals(user.getNickname())){
                user.setPlayer(player);
                player.setConnectionStatus(true);
            }
        }
    }

}
