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

    public Client(ClientStrategy clientStrategy){
        this.clientStrategy = clientStrategy;
        clientStrategy.addObserver(this);
        //nel costruttore verrà assegnata la view scelta
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private void setThisUser(User thisUser) {
        this.thisUser = thisUser;
    }


    @Override
    public void update(PlayerMessage playerMessage){

       if(playerMessage.getId().equals(PlayerMessageTypeEnum.USER)){
            playerMessage.getUser().setNickname(nickname);
            setThisUser(playerMessage.getUser());
            clientStrategy.sendToServer(playerMessage);
       }else if(playerMessage.getUser().equals(thisUser) || playerMessage.getUser() == null){
           view.receive(playerMessage);
       }


    }

    public void send(PlayerMessage playerMessage){
        clientStrategy.sendToServer(playerMessage);
    }

    public void close(){
        clientStrategy.close();
    }
}

