package it.polimi.se2018.connection.client;

import it.polimi.se2018.model.PlayerMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRemoteInterface extends Remote {

    void receiveFromServer(PlayerMessage playerMessage) throws RemoteException;
}

