package it.polimi.se2018.controller;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.Round;
import it.polimi.se2018.model.cards.public_card.PublicObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.view.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Controller's Class GameManager
 * @author Silvia Franzini
 */

public class GameManager {

    private GameBoard gameBoard;
    private List<User> userList;
    private List<Round> roundList;
    private Player winner;
    private GameStarter gameStarter;
    private RemoteView view;

    /**
     * Builder method of GameManager class
     * @param userList the list of connected users for that match
     */
    public GameManager(List<User> userList){
        this.userList=userList;
        roundList = new ArrayList<>();
    }

    /**
     * Method coordinates all game's operation, by creating the main elements of the game managing their interaction
     */
    public void newGame(){

        gameStarter = new GameStarter();
        gameBoard = gameStarter.startGame(this.userList);
        for(int index=0; index<10; index++){
            Round round = new Round(gameBoard.getPlayerList(), gameBoard, gameBoard.getTrackBoardDice(),index);
            roundList.add(round);
        }
        // completare gestione gioco con classi mancanti

        calculateGameScore();
        setGameWinner();
        //ritornare vincitore alla view per visualizzazione

    }

    /**
     * Produces the ordered list of the players for next round
     * @return ordered List of Players
     */
    private List<Player> setActionOrder(){
        List<Player> playerList = gameStarter.getPlayerList();
        Player player = playerList.remove(0);
        playerList.add(player);

        return playerList;
    }

    /**
     * Method calculates the game score for each player at the end of the match
     */
    private void calculateGameScore(){
        int score = 0;
        for(Player player: gameBoard.getPlayerList()){
            for(PublicObjCard publicObjCard : gameBoard.getCardOnBoard().getPublicObjCardList()){
                try{
                    score += publicObjCard.scoreCalculation(player.getSchemaCard());
                }catch (NullPointerException e){
                    //da gestire con invio errore al client
                }

            }
            for(Cell c : player.getSchemaCard().getCellList()){
                if(c.isEmpty()){
                    score--;
                }
            }
            player.setScore(score);
        }
    }

    /**
     * Method finds out the game winner
     */
    private void setGameWinner(){

       winner = Collections.max(gameBoard.getPlayerList(), Comparator.comparingInt(Player :: getScore));

    }

    /**
     * @return the winner of the match
     */
    public Player getGameWinner(){
        return winner;
    }
}
