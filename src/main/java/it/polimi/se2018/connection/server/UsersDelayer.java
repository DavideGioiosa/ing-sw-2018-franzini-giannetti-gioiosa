package it.polimi.se2018.connection.server;

import java.util.TimerTask;

public class UsersDelayer extends TimerTask {

    private ServerManager serverManager;


     UsersDelayer(ServerManager serverManager){

        this.serverManager = serverManager;
    }

    @Override
    public void run() {

        serverManager.createGame();
    }
}
