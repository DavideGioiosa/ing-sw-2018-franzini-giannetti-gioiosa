package it.polimi.se2018.connection.server;

import it.polimi.se2018.controller.GameManager;
import it.polimi.se2018.view.RemoteView;

public class GameCreator {
    private GameManager gameManager;
    private RemoteView remoteView;

    public GameCreator (){
       // this.gameManager = new GameManager(remoteView, gameBoard);
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
