package it.polimi.se2018.connection.server;

import it.polimi.se2018.connection.client.ClientRemoteInterface;


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
            clientRemoteInterface.receiveLifeline();
        } catch (RemoteException e) {

            serverImplementation.disconnectionHandler(code);

        }
    }
}
