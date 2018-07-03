package it.polimi.se2018.connection.client.rmi;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface used in RMI connection to expose client's methods for communication
 * @author Silvia Franzini
 */
public interface ClientRemoteInterface extends Remote {

    /**
     * Receiver method, used by the server to send to the client
//     * @param playerMessage message that has been sent
     * @throws RemoteException exception due to communication fall
     */
    void receiveFromServer(PlayerMessage playerMessage) throws RemoteException;

    /**
     * Method used to
     * @throws RemoteException
     */
    void receiveLifeline() throws  RemoteException;
}

