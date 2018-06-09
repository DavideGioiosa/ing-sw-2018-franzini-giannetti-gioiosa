package it.polimi.se2018.connection.server;

import it.polimi.se2018.connection.client.ClientRemoteIterface;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observable;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerImplementation extends UnicastRemoteObject implements ServerRemoteInterface {
    private HashMap<String,ClientRemoteIterface> clientList = new HashMap<>();
    private List<String> codeList = new ArrayList<>();
    private transient Observable<PlayerMessage> obs = new Observable<>();

    ServerImplementation() throws RemoteException{
        super();

    }

    public Observable<PlayerMessage> getObs() {
        return obs;
    }

    void sendToClient(PlayerMessage playerMessage) throws RemoteException {

        if(playerMessage.getUser().equals(null)){//da rivedere
            for(ClientRemoteIterface clientRemoteIterface : clientList.values()){
                clientRemoteIterface.receiveFromServer(playerMessage);
            }
        }else{
            if(clientList.containsKey(playerMessage.getUser().getUniqueCode())){
                ClientRemoteIterface clientRemoteIterface = clientList.get(playerMessage.getUser().getUniqueCode());
                clientRemoteIterface.receiveFromServer(playerMessage);

            }//else: gestire disconnessione
        }

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
