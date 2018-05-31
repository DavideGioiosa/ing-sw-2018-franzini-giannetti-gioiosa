package it.polimi.se2018.connection.client;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.View;



public class Client implements Observer<PlayerMessage> {

    private User thisUser;
    private ClientStrategy clientStrategy;
    private View view;

    public Client(ClientStrategy clientStrategy, View view, User user){
        this.clientStrategy = clientStrategy;
        this.view = view;
        this.thisUser = user;
    }


    public void update(PlayerMessage playerMessage){ //da CommandController per notifica scelta mossa
        clientStrategy.sendToServer(playerMessage);
    }


    public void receive(PlayerMessage playerMessage){
        if(!(playerMessage.getId() == 2 && !playerMessage.getPlayerChoice().getUser().equals(thisUser)))
        {
            view.receive(playerMessage);
        }

    }
}
