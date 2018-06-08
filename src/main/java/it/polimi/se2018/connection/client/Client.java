package it.polimi.se2018.connection.client;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.View;



public class Client implements Observer<PlayerMessage> {

    private User thisUser;
    private ClientStrategy clientStrategy;
    private View view;
    private String nickname;

    public Client(ClientStrategy clientStrategy, View view, String nickname){
        this.clientStrategy = clientStrategy;
        clientStrategy.addObserver(this);
        this.view = view;
        this.nickname = nickname;
    }

    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
    }


    @Override
    public void update(PlayerMessage playerMessage){ //da classi Client

        //controllo che messaggio sia destinato a me
       if(playerMessage.getId().equals(PlayerMessageTypeEnum.USER)){
            playerMessage.getUser().setNickname(nickname);
            setThisUser(playerMessage.getUser());
            clientStrategy.sendToServer(playerMessage);
       }else view.receive(playerMessage);

    }


    public void close(){
        clientStrategy.close();
    }
}

