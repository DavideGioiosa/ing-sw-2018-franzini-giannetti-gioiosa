package it.polimi.se2018.controller;


import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.view.RemoteView;

import java.util.List;

public class GameCreator {
    private GameManager gameManager;
    private GameStarter gameStarter;
    private boolean gameStatus;
    private RemoteView remoteView;



    public GameCreator (List<User> userList, RemoteView remoteView){
        this.remoteView = remoteView;
        gameStatus = false;
        this.gameManager = null;
        this.gameStarter = new GameStarter(userList, remoteView);

    }

    public void modifyStatus(){
        if(gameManager != null){
            gameManager.checkStatus();
        }
    }

    public void defaultMove(){
        /*if(!gameStatus) {
            gameStatus = gameStarter.defaultMove();
            if(gameStatus) gameManager = new GameManager(remoteView, gameStarter.getGameBoard());
            return;
        }*/
        if(gameStatus) gameManager.defaultMove();
    }

    public void receiveFromClient(PlayerMessage playerMessage) {

        if(!gameStatus && playerMessage.getId().equals(PlayerMessageTypeEnum.CHOICE)){
            gameStatus = gameStarter.newChoice(playerMessage.getPlayerChoice());
            if(gameStatus) gameManager = new GameManager(remoteView, gameStarter.getGameBoard());
        }
        /*else if(gameManager == null){
            gameManager = new GameManager(remoteView, gameStarter.getGameBoard());
        }*/
        if(playerMessage.getId().equals(PlayerMessageTypeEnum.CHECK_MOVE)){
            gameManager.tryMove(playerMessage.getPlayerMove());
        }

    }

    public void reconnectUser(User user){
        if(user!=null){
            gameManager.reconnectUser(user);
        }
    }
}
