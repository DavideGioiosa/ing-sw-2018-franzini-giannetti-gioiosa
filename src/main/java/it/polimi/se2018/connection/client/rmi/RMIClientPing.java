package it.polimi.se2018.connection.client.rmi;

import it.polimi.se2018.connection.server.rmi.ServerRemoteInterface;

import java.rmi.RemoteException;
import java.util.TimerTask;

public class RMIClientPing extends TimerTask {

    private ServerRemoteInterface serverRemoteInterface;
    private RMITypeClient rmiTypeClient;
     RMIClientPing(RMITypeClient rmiTypeClient, ServerRemoteInterface serverRemoteInterface){
        this.serverRemoteInterface = serverRemoteInterface;
        this.rmiTypeClient = rmiTypeClient;
    }

    @Override
    public void run() {
        try {
            serverRemoteInterface.lifeLine();
        } catch (RemoteException e) {

            rmiTypeClient.disconnectionHandler();
        }
    }
}
