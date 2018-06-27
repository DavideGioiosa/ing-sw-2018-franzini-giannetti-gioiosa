package it.polimi.se2018.connection.server;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;

import java.util.TimerTask;

public class TimerDelayer extends TimerTask {


    private PlayerMessage playerMessage;
    private ServerManager serverManager;

    TimerDelayer(PlayerMessage playerMessage, ServerManager serverManager){
        this.playerMessage = playerMessage;
        this.serverManager = serverManager;
    }


    @Override
    public void run() {
        if(playerMessage.getId().equals(PlayerMessageTypeEnum.YOUR_TURN) || playerMessage.getId().equals(PlayerMessageTypeEnum.CHOICE)){
            serverManager.defaultMove();
        }
    }
}
