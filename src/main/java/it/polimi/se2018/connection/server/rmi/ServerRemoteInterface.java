package it.polimi.se2018.connection.server.rmi;

import it.polimi.se2018.connection.client.rmi.ClientRemoteInterface;
import it.polimi.se2018.model.PlayerMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI server's remote interface to make clients communicate with the server
 */
public interface ServerRemoteInterface extends Remote {

    /**
     * Method used to add a client
     * @param client client that has to be added
     * @throws RemoteException if connection has fallen
     */
    void addClient(ClientRemoteInterface client) throws RemoteException;

    /**
     * Method used to receive messages from the client
     * @param playerMessage message received
     * @throws RemoteException if connection has fallen
     */
    void receive(PlayerMessage playerMessage) throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    void lifeLine() throws RemoteException;
}
