package it.polimi.se2018.connection.server.rmi;
import static  it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;

import it.polimi.se2018.connection.client.rmi.ClientRemoteInterface;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;


public class ServerImplementation extends UnicastRemoteObject implements ServerRemoteInterface {
    private HashMap<String,ClientRemoteInterface> clientList = new HashMap<>();
    private HashMap<String, Timer> pingMap = new HashMap<>();
    private List<String> disconnected = new ArrayList<>();
    private List<String> codeList = new ArrayList<>();
    private transient Observable<PlayerMessage> obs = new Observable<>();


    ServerImplementation() throws RemoteException{
        super();
    }
    public void addObserver(Observer<PlayerMessage> observer){
        obs.addObserver(observer);
    }

    public Observable<PlayerMessage> getObs() {
        return obs;
    }

    void sendToClient(PlayerMessage playerMessage) throws RemoteException {

        if(playerMessage.getUser()== null){
            for(ClientRemoteInterface clientRemoteInterface : clientList.values()){
                clientRemoteInterface.receiveFromServer(playerMessage);
            }
        }else{
            if(clientList.containsKey(playerMessage.getUser().getUniqueCode())){
                ClientRemoteInterface clientRemoteInterface = clientList.get(playerMessage.getUser().getUniqueCode());
                clientRemoteInterface.receiveFromServer(playerMessage);
            }

        }
    }

    @Override
    public void addClient(ClientRemoteInterface client) throws RemoteException{

        User user;
        String code;
        do{
            user = new User(TypeOfConnection.RMI);
            code = user.createUniqueCode();
        }while(codeList.contains(code));

        codeList.add(code);
        clientList.put(code, client);
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setUser(user);
        RMIServerPing rmiServerPing = new RMIServerPing(this, code, client);
        Timer lifeLineTimer = new Timer();
        lifeLineTimer.scheduleAtFixedRate(rmiServerPing, new Date(), (long)90*1000);
        pingMap.put(code, lifeLineTimer);
        println("trovato nuovo client");
        try {
            client.receiveFromServer(playerMessage);
        } catch (RemoteException e) {

            String string = clientList.entrySet().stream().filter(entry -> entry.getValue().equals(client)).map(Map.Entry::getKey).findFirst().orElse(null);
            disconnectionHandler(string);
        }
    }


    void disconnectionHandler(String code){
        if(!disconnected.contains(code)){
            disconnected.add(code);
            User user = new User(TypeOfConnection.RMI);
            user.setUniqueCode(code);
            PlayerMessage disconnect = new PlayerMessage();
            disconnect.setUser(user);
            disconnect.setError(100); //valore da individuare
            disconnect.setId(PlayerMessageTypeEnum.DISCONNECTED);
            obs.notify(disconnect);
        }
        Timer timer = pingMap.get(code);
        timer.cancel();
        timer.purge();
        pingMap.remove(code);

    }

    void transmit(PlayerMessage playerMessage){
        obs.notify(playerMessage);
    }

    @Override
    public void receive(PlayerMessage playerMessage) throws RemoteException {

        if(playerMessage.getIdError() == 100 ){
            disconnectionHandler(playerMessage.getUser().getUniqueCode());
        }

        new Thread(new RMIServerThread(playerMessage, this)).start();
    }

    /**
     * Method used to detect client RMI disconnection
     * @throws RemoteException due to disconnection
     */
    @Override
    public void lifeLine() throws RemoteException {
        //detects disconnection by throwing RemoteException
    }


    public void close(){
        for(Timer timer : pingMap.values()){
            timer.cancel();
            timer.purge();
        }
    }
}
