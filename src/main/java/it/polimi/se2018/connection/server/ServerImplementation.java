package it.polimi.se2018.connection.server;

import it.polimi.se2018.connection.client.ClientRemoteIterface;
import it.polimi.se2018.model.PlayerChoice;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observable;


import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerImplementation implements ServerRemoteInterface {
    private HashMap<String,ClientRemoteIterface> clientList = new HashMap<>();
    private HashMap<User, ClientListener> userClientListenerList = new HashMap<>();
    private List<String> codeList = new ArrayList<>();
    private Observable<PlayerMessage> obs = new Observable<>();

    ServerImplementation(){} //costruttore da rivedere

    public Observable<PlayerMessage> getObs() {
        return obs;
    }

    public void sendToClient(String code,PlayerMessage playerMessage) throws RemoteException {

         ClientRemoteIterface clientRemoteIterface = clientList.get(code);
         clientRemoteIterface.receiveFromServer(playerMessage);
    }


    @Override
    public void addClient(ClientRemoteIterface client){

        User user = new User(TypeOfConnection.RMI);
        String code = user.getUniqueCode();
        while(codeList.contains(code)){
            user = new User(TypeOfConnection.RMI);
            code = user.getUniqueCode();
        }
        codeList.add(code);
        clientList.put(code, client);
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setUser(user);
        try {

            client.receiveFromServer(playerMessage);
        } catch (RemoteException e) {
            Logger.getGlobal().log(Level.SEVERE, e.toString());
        }
    }


    @Override
    public void receive(PlayerMessage playerMessage) {
        obs.notify(playerMessage);
    }

}
