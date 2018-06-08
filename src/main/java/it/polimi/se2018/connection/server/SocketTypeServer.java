package it.polimi.se2018.connection.server;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SocketTypeServer implements Observer<PlayerMessage> {

    private List<String> codeList;
    private HashMap<String, ClientListener> clientListenerList;
    private HashMap<User, ClientListener> userClientListenerList;
    private Observable<PlayerMessage> obs;

    public SocketTypeServer(){
        codeList = new ArrayList<>();
        clientListenerList = new HashMap<>();
        userClientListenerList = new HashMap<>();
        obs = new Observable<>();
    }

    public Observable<PlayerMessage> getObs() {
        return obs;
    }

    public List<String> getCodeList() {
        return codeList;
    }

    public void addCode(String code){
        this.codeList.add(code);
    }

    public void addClient(String code, ClientListener clientListener){
        this.clientListenerList.put(code,clientListener);
    }

    public void removeClient(String code){
        this.clientListenerList.remove(code);
    }


    public void send (String code, PlayerMessage playerMessage){

       ClientListener clientListener = clientListenerList.get(code);
        clientListener.send(playerMessage);
    }

    @Override
    public void update(PlayerMessage playerMessage) {

        obs.notify(playerMessage);

    }
}
