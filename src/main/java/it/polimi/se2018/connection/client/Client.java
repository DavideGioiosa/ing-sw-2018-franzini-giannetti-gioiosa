package it.polimi.se2018.connection.client;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.View;

public class Client implements Observer<PlayerMessage> {

    private ClientStrategy clientStrategy;
    private View view;

    public Client(ClientStrategy clientStrategy, View view){
        this.clientStrategy = clientStrategy;
        this.view = view;
    }


    public void update(PlayerMessage playerMessage){ //da CommandController per notifica scelta mossa

        clientStrategy.sendToServer(playerMessage);
    }


    public void receive(PlayerMessage playerMessage){
            view.receive(playerMessage);
    }
}
