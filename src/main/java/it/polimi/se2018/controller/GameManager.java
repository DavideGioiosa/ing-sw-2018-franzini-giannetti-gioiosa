package it.polimi.se2018.controller;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.Gameboard;
import it.polimi.se2018.model.cards.public_card.PublicObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.User;

import java.util.ArrayList;
import java.util.List;


/**
 * Controller's Class GameManager
 * @author Silvia Franzini
 */

public class GameManager {

    private Gameboard gameBoard;
    private List<User> userList;
    private List<Round> roundList;
    private Player winner;
    private GameStarter gameStarter;
    private View view;

    /**
     * Builder method of GameManager class
     * @param userList the list of connected users for that match
     */
    public GameManager(List<User> userList){
        this.userList=userList;
    }

    /**
     * Method coordinates all game's operation, by creating the main elements of the game managing their interaction
     */
    public void newGame(){
        gameStarter = new GameStarter();
        gameBoard = gameStarter.startGame(this.userList);
        for(Round round : roundList){
            //da completare con elementi relativi a round

            calculateGameScore();
            setGameWinner();
            //ritornare vincitore alla view per visualizzazione
        }
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
                    //da gestire
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
        int max = 0;
        for (Player player : gameBoard.getPlayerList()){
            if(player.getScore() > max){
                winner = player;
                max = player.getScore();
            }
        }
    }

    /**
     * @return the winner of the match
     */
    public Player getGameWinner(){
        return winner;
    }
}
