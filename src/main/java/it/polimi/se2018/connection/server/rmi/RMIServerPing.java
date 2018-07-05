package it.polimi.se2018.connection.server.rmi;

import it.polimi.se2018.connection.client.rmi.ClientRemoteInterface;

import java.rmi.RemoteException;
import java.util.TimerTask;

public class RMIServerPing extends TimerTask {

    private ClientRemoteInterface clientRemoteInterface;
    private String code;
    private ServerImplementation serverImplementation;
    public RMIServerPing(ServerImplementation serverImplementation, String code, ClientRemoteInterface clientRemoteInterface){
        this.clientRemoteInterface = clientRemoteInterface;
        this.code = code;
        this.serverImplementation = serverImplementation;
    }

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
