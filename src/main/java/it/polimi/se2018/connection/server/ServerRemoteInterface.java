package it.polimi.se2018.connection.server;

import it.polimi.se2018.connection.client.ClientRemoteInterface;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRemoteInterface extends Remote {

    void addClient(ClientRemoteInterface client) throws RemoteException;
    void receive(PlayerMessage playerMessage) throws RemoteException;
    void lifeLine() throws RemoteException;
}
