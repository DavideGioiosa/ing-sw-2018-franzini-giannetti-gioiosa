package it.polimi.se2018.connection.server.socket;
import static  it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;

import it.polimi.se2018.connection.server.socket.ClientGatherer;
import it.polimi.se2018.connection.server.socket.ClientListener;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

import java.util.*;


public class SocketTypeServer implements Observer<PlayerMessage> {

    private List<String> codeList;
    private ClientGatherer clientGatherer;
    private HashMap<String, ClientListener> clientListenerList;
    private Observable<PlayerMessage> obs;
    private List<String> disconnectedCodes;
    //private static int port = 1111; verrà preso da file config

    public SocketTypeServer(int port){
        disconnectedCodes = new ArrayList<>();
        codeList = new ArrayList<>();
        clientListenerList = new HashMap<>();
        obs = new Observable<>();
        clientGatherer = new ClientGatherer(this, port);
        clientGatherer.start();
        println("ServerSocket acceso");
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

    public void shutdown(){

        Iterator<Map.Entry<String, ClientListener>> iterator = clientListenerList.entrySet().iterator();

        for(int i = codeList.size() - 1; i >= 0; i--){
            clientListenerList.get(codeList.get(i)).setQuit();
        }
        while(iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }

        clientGatherer.close();
    }

    public void send (PlayerMessage playerMessage){
        if(!clientListenerList.isEmpty()){
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

    }

    @Override
    public synchronized void update(PlayerMessage playerMessage) {

        if(playerMessage.getId().equals(PlayerMessageTypeEnum.DISCONNECTED) && !disconnectedCodes.isEmpty() && !disconnectedCodes.contains(playerMessage.getUser().getUniqueCode())){
            disconnectedCodes.add(playerMessage.getUser().getUniqueCode());
            clientListenerList.get(playerMessage.getUser().getUniqueCode()).setQuit();
            clientGatherer.getTimerHashMap().get(playerMessage.getUser().getUniqueCode()).cancel();
        }
        obs.notify(playerMessage);
    }
}
