package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.public_card.PublicObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PrivatePlayer;
import it.polimi.se2018.view.*;
import it.polimi.se2018.utils.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



/**
 * Controller's Class GameManager
 * @author Silvia Franzini
 */

public class GameManager implements Observer<Round>{

    private GameBoard gameBoard;
    private List<Round> roundList;
    private Player winner;
    private RemoteView view;

    /**
     * Builder method of GameManager class
     */
    public GameManager(RemoteView view, GameBoard gameBoard){
        if(view == null){
            throw new NullPointerException("ERROR: view not initialized");
        }else this.view = view;
        if(gameBoard == null){
            this.view.reportError();
        }else this.gameBoard = gameBoard;
        roundList = new ArrayList<>();
        Round round = new Round(gameBoard, 0 );
        roundList.add(round);
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
                    view.reportError();
                }

            }
            for(Cell c : player.getSchemaCard().getCellList()){
                if(c.isEmpty()){
                    score--;
                }
            }
            for(PrivatePlayer privatePlayer: gameBoard.getPrivatePlayerList()){
                if(player.equals(privatePlayer.getPlayer())){
                    score += privatePlayer.getPrivateObj().getScore(player.getSchemaCard());
                }
            }

            score += player.getTokens();
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

    /**
     * Update method for Observer pattern implemntation
     * @param round round just completed
     */
    public void update(Round round){
        if(roundList.size()==10){
            calculateGameScore();
            setGameWinner();

        }else {
            Round newRound = new Round(gameBoard, roundList.size() );
            roundList.add(newRound);
        }
    }

}
