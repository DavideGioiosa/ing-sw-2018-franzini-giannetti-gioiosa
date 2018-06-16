package it.polimi.se2018.connection.client;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.View;

public class Client implements Observer<PlayerMessage> {

    private User thisUser;
    private ClientStrategy clientStrategy;
    private View view;
    private String nickname;

    public Client(ClientStrategy clientStrategy, View view){
        this.clientStrategy = clientStrategy;
        nickname = null;
        thisUser = null;
        this.view = view;
        clientStrategy.addObserver(this);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    private void setThisUser(User thisUser) {
        this.thisUser = thisUser;
    }


    @Override
    public void update(PlayerMessage playerMessage){

        //if(playerMessage.getUser() == null || playerMessage.getUser().getNickname() == nickname){
        view.receive(playerMessage);
        //}

    }

    public void send(PlayerMessage playerMessage){
        clientStrategy.sendToServer(playerMessage);
    }

    public void close(){
        clientStrategy.close();
    }
}

