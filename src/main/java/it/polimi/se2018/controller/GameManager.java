package it.polimi.se2018.controller;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.Gameboard;
import it.polimi.se2018.model.cards.public_card.PublicObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.User;

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

    public GameManager(List<User> userList){
        this.userList=userList;

    }

    public void newGame(){
        gameStarter = new GameStarter();
        gameBoard = gameStarter.startGame(this.userList);
        for(Round round : roundList){
            round.(startRound()){}
            //da completare con elementi relativi a round
        }
    }

    private void calculateGameScore(){
        int score = 0;
        for(Player player: gameBoard.getPlayerList()){
            for(PublicObjCard publicObjCard : gameBoard.getCardOnBoard().getPublicObjCardList()){
                score += publicObjCard.scoreCalculation(player.getSchemaCard());
            }
            for(Cell c : player.getSchemaCard().getCellList()){
                if(c.isEmpty()){
                    score--;
                }
            }
            player.setScore(score);
        }
    }

    private void setGameWinner(){
        int max = 0;
        for (Player player : gameBoard.getPlayerList()){
            if(player.getScore() > max){
                winner = player;
                max = player.getScore();
            }
        }
    }

    public Player getGameWinner(){
        return winner;
    }
}
