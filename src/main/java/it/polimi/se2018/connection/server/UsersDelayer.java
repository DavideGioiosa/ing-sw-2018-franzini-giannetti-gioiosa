package it.polimi.se2018.connection.server;

import java.util.TimerTask;

public class UsersDelayer extends TimerTask {

    private boolean isStarted;
    private ServerManager serverManager;


    public UsersDelayer(ServerManager serverManager){

        this.isStarted = true;
        this.serverManager = serverManager;
    }

    public boolean isStarted() {
        return isStarted;
    }

    @Override
    public void run() {

        serverManager.createGame();
    }
}
