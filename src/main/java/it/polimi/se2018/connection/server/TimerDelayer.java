package it.polimi.se2018.connection.server;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.TypeOfChoiceEnum;

import java.util.TimerTask;

public class TimerDelayer extends TimerTask {

    private boolean thisHasStarted = false;
    private PlayerMessage playerMessage;
    private ServerManager serverManager;

    TimerDelayer(PlayerMessage playerMessage, ServerManager serverManager){
        this.playerMessage = playerMessage;
        this.serverManager = serverManager;
        thisHasStarted = true;
    }

    boolean isStarted(){
        return thisHasStarted;
    }

    @Override
    public void run() {
        if(playerMessage.getId().equals(PlayerMessageTypeEnum.CHECK_MOVE) || playerMessage.getId().equals(PlayerMessageTypeEnum.CHOICE)){
            PlayerMessage pass = new PlayerMessage();
            PlayerMove playerMove = new PlayerMove();
            playerMove.setTypeOfChoice(TypeOfChoiceEnum.PASS);
            pass.setUser(playerMessage.getUser());
            pass.setCheckMove(playerMove);
            serverManager.update(pass);
        }
    }
}
