package it.polimi.se2018.connection.client.rmi;

import it.polimi.se2018.connection.server.rmi.ServerRemoteInterface;

import java.rmi.RemoteException;
import java.util.TimerTask;

/**
 * RMI class for server's ping
 * @author Silvia Franzini
 */
public class RMIClientPing extends TimerTask {

    /**
     * Reference to Server's remote object
     */
    private ServerRemoteInterface serverRemoteInterface;
    /**
     * Reference to this RMI client
     */
    private RMITypeClient rmiTypeClient;

    /**
     * Builder method of the class
     * @param rmiTypeClient this client connection class
     * @param serverRemoteInterface the server's remote reference
     */
     RMIClientPing(RMITypeClient rmiTypeClient, ServerRemoteInterface serverRemoteInterface){
        this.serverRemoteInterface = serverRemoteInterface;
        this.rmiTypeClient = rmiTypeClient;
    }

    /**
     * Thread's run method where client pings the server server checking if is still connected, otherwise it handles disconnection
     */
    @Override
    public void run() {
        try {
            serverRemoteInterface.lifeLine();

        } catch (RemoteException e) {

            rmiTypeClient.disconnectionHandler();
        }
    }
}
