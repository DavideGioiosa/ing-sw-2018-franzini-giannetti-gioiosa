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
        if(view == null){
            throw new NullPointerException("ERROR: view not initialized");
        }else this.view = view;
        if(gameBoard == null){
            this.view.reportError(1000, null);
        }else this.gameBoard = gameBoard;
        roundList = new ArrayList<>();
        Round round = new Round(gameBoard, 0 , view);
        roundList.add(round);
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
                    view.reportError(1000, null);
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
        if(roundList.get(roundList.size() - 1).isEnded()){
            endRound();
        }else roundList.get(roundList.size() - 1).defaultMove();
    }


    /**
     * Method finds out the game winner
     */
    private void setGameWinner(){
       winner = Collections.max(gameBoard.getPlayerList(), Comparator.comparingInt(Player :: getScore));
    }

    /**
     * Gets game winner of the match
     * @return the winner of the match
     */
    public Player getGameWinner(){
        return winner;
    }

    /**
     * Update method for Observer pattern implementation
     */
    private void endRound(){
        //TODO: da adattare a nuovo UML
        if(roundList.size() == 4){
            calculateGameScore();
            setGameWinner();

        }else {
            Round newRound = new Round(gameBoard, roundList.size(), view);
            roundList.add(newRound);
        }
    }

    void tryMove(PlayerMove playerMove){
        if(roundList.get(roundList.size() - 1).isEnded()){
            endRound();
        }else roundList.get(roundList.size() - 1).update(playerMove);
    }

}
