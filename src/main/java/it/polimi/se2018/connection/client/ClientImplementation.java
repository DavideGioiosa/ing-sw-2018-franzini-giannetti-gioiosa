package it.polimi.se2018.connection.client;

import it.polimi.se2018.model.PlayerMessage;

import java.rmi.RemoteException;


public class ClientImplementation implements ClientRemoteIterface{

    private RMITypeClient rmiTypeClient;
    public ClientImplementation(RMITypeClient rmiTypeClient){
        this.rmiTypeClient = rmiTypeClient;
    }


    @Override
    public void receiveFromServer(PlayerMessage playerMessage) throws RemoteException {
        rmiTypeClient.receive(playerMessage);
    }
}
