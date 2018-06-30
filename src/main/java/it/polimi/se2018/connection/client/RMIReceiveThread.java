package it.polimi.se2018.connection.client;

import it.polimi.se2018.model.PlayerMessage;

public class RMIReceiveThread implements Runnable {

    private PlayerMessage playerMessage;
    private ClientImplementation clientImplementation;
    RMIReceiveThread(PlayerMessage playerMessage, ClientImplementation clientImplementation){
        this.playerMessage = playerMessage;
        this.clientImplementation = clientImplementation;
    }

    @Override
    public void run() {
        clientImplementation.update(playerMessage);
    }
}
