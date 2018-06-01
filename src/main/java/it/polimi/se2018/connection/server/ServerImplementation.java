package it.polimi.se2018.connection.server;

import it.polimi.se2018.connection.client.ClientRemoteIterface;
import it.polimi.se2018.model.PlayerMessage;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServerImplementation implements ServerRemoteInterface {
    private List<ClientRemoteIterface> clientList= new ArrayList<>();

    public ServerImplementation(){ //da completare
    }

    @Override
    public void sendToClient(PlayerMessage playerMessage) throws RemoteException {
        Iterator<ClientRemoteIterface> clientRemoteIterfaceIterator = clientList.iterator();
        while(clientRemoteIterfaceIterator.hasNext()){
            try{
                 clientRemoteIterfaceIterator.next().receiveFromServer(playerMessage);
            }catch(ConnectException e ){
                //gestire rimozione
            }
        }
    }

    public List<ClientRemoteIterface> getClientList() {
        return clientList;
    }

    @Override
    public void addClient(ClientRemoteIterface client){
        this.clientList.add(client);
    }

    @Override
    public void receive(PlayerMessage playerMessage) {
        //passare a classe che invii a controller


    }
}
