package it.polimi.se2018.connection.server;

import it.polimi.se2018.connection.client.ClientRemoteIterface;
import it.polimi.se2018.connection.client.RMITypeClient;
import it.polimi.se2018.model.PlayerMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRemoteInterface extends Remote {

    void sendToClient(PlayerMessage playerMessage)throws RemoteException;
    void addClient(ClientRemoteIterface client) throws RemoteException;
    void receive(PlayerMessage playerMessage) throws RemoteException;
}
