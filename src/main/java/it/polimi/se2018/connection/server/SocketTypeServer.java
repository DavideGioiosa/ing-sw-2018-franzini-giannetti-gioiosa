package it.polimi.se2018.connection.server;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SocketTypeServer implements Observer<PlayerMessage> {

    private List<String> codeList;
    private HashMap<String, ClientListener> clientListenerList;
    private Observable<PlayerMessage> obs;
    private List<String> disconnectedCodes;
    //private static int port = 1111; verrà preso da file config

    public SocketTypeServer(int port){
        disconnectedCodes = new ArrayList<>();
        codeList = new ArrayList<>();
        clientListenerList = new HashMap<>();
        obs = new Observable<>();
        ClientGatherer clientGatherer = new ClientGatherer(this, port);
        clientGatherer.start();
    }

    public void addObserver(Observer<PlayerMessage> observer){
        obs.addObserver(observer);
    }

    public Observable<PlayerMessage> getObs() {
        return obs;
    }

    List<String> getCodeList() {
        return codeList;
    }

    void addCode(String code){
        this.codeList.add(code);
    }

    void addClient(String code, ClientListener clientListener){
        this.clientListenerList.put(code,clientListener);
    }

    public void removeClient(String code){
        ClientListener clientListener = clientListenerList.get(code);
        clientListener.setQuit();
        this.clientListenerList.remove(code);
    }


    public void send (PlayerMessage playerMessage){

        if(playerMessage.getUser() == null){
            for(ClientListener clientListener : clientListenerList.values()){
                clientListener.send(playerMessage);
            }
        }else {
            if(clientListenerList.containsKey(playerMessage.getUser().getUniqueCode()))
            {
                ClientListener clientListener = clientListenerList.get(playerMessage.getUser().getUniqueCode());
                clientListener.send(playerMessage);
            }
        }
    }

    @Override
    public void update(PlayerMessage playerMessage) {

        if(playerMessage.getId().equals(PlayerMessageTypeEnum.DISCONNECTED) && !disconnectedCodes.contains(playerMessage.getUser().getUniqueCode())){
            disconnectedCodes.add(playerMessage.getUser().getUniqueCode());
            clientListenerList.get(playerMessage.getUser().getUniqueCode()).setQuit();
        }
        obs.notify(playerMessage);
    }
}
