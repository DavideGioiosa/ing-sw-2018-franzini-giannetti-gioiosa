package it.polimi.se2018.connection.server;

import it.polimi.se2018.model.PlayerMessage;

public class RMIServerThread implements Runnable {

    private PlayerMessage playerMessage;
    private ServerImplementation serverImplementation;
    RMIServerThread(PlayerMessage playerMessage, ServerImplementation serverImplementation){
        this.playerMessage = playerMessage;
        this.serverImplementation = serverImplementation;
    }

    @Override
    public void run() {

        serverImplementation.transmit(playerMessage);

    }
}
