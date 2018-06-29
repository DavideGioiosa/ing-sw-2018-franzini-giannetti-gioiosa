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

    public void defaultMove(){
        if(!gameStatus){
            gameStarter.defaultMove();
        }else if(gameManager == null){
            gameManager = new GameManager(remoteView, gameStarter.getGameBoard());
            gameManager.defaultMove();
        }
    }

    public void receiveFromClient(PlayerMessage playerMessage) {

        if(!gameStatus){
            if(playerMessage.getId().equals(PlayerMessageTypeEnum.CHOICE)){
                gameStatus = gameStarter.newChoice(playerMessage.getPlayerChoice());
            }
        }else if(gameManager == null){
            gameManager = new GameManager(remoteView, gameStarter.getGameBoard());
        }
        if(playerMessage.getId().equals(PlayerMessageTypeEnum.CHECK_MOVE)){
            int result = gameManager.tryMove(playerMessage.getPlayerMove());
            if(result==1){
              remoteView.sendWinner(gameManager.getGameBoard());
            }
        }

    }
}
