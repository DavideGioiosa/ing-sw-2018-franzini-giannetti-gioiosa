package it.polimi.se2018.connection.server;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.TypeOfChoiceEnum;

import java.util.TimerTask;

public class TimerDelayer extends TimerTask {

    private boolean thisHasStarted = false;
    private SocketTypeServer socketTypeServer;
    private PlayerMessage playerMessage;
    private RMITypeServer rmiTypeServer;

     TimerDelayer(PlayerMessage playerMessage, SocketTypeServer socketTypeServer, RMITypeServer rmiTypeServer){
        this.playerMessage = playerMessage;
        this.socketTypeServer = socketTypeServer;
        this.rmiTypeServer = rmiTypeServer;
        thisHasStarted = true;
    }

    boolean isStarted(){
        return thisHasStarted;
    }

    @Override
    public void run() {
        if(playerMessage.getId().equals(PlayerMessageTypeEnum.MOVE) || playerMessage.getId().equals(PlayerMessageTypeEnum.CHOICE)){
            PlayerMessage pass = new PlayerMessage();
            PlayerMove playerMove = new PlayerMove();
            playerMove.setTypeOfChoice(TypeOfChoiceEnum.PASS);
            pass.setUser(playerMessage.getUser());
            pass.setMove(playerMove);
            socketTypeServer.update(playerMessage);
            rmiTypeServer.receive(playerMessage);
        }
    }
}
