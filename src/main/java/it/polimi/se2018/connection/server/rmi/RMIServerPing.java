package it.polimi.se2018.connection.server.rmi;

import it.polimi.se2018.connection.client.rmi.ClientRemoteInterface;

import java.rmi.RemoteException;
import java.util.TimerTask;

/**
 * RMI server's class for checking client's connection status
 * @author Silvia Franzini
 */
public class RMIServerPing extends TimerTask {

    /**
     * Reference to client's remote interfece, used to send and receive messages
     */
    private ClientRemoteInterface clientRemoteInterface;
    /**
     * Personal code of the client saved to handle disconnection
     */
    private String code;
    /**
     * Reference to the server object to handle disconnection
     */
    private ServerImplementation serverImplementation;

    /**
     * Builder method of the class
     * @param serverImplementation reference to the server RMI class
     * @param code personal code of the client in order to recognize it and handle disconnection
     * @param clientRemoteInterface client's remote interface to ping
     */
    public RMIServerPing(ServerImplementation serverImplementation, String code, ClientRemoteInterface clientRemoteInterface){
        this.clientRemoteInterface = clientRemoteInterface;
        this.code = code;
        this.serverImplementation = serverImplementation;
    }

    /**
     * Thread's run method that checks if the client is still connected, otherwise it handles the disconnection
     */
    @Override
    public void run() {
        try {
            System.out.println("ping");
            clientRemoteInterface.receiveLifeline();
        } catch (RemoteException e) {
            System.out.println("utente disconnesso, mancato ping");
            serverImplementation.disconnectionHandler(code);

        }
    }

}
