package it.polimi.se2018.connection.server;

import it.polimi.se2018.connection.client.Client;
import it.polimi.se2018.connection.client.RMITypeClient;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

public class RMITypeServer {
    private static int PORT = 1099;
    private List<Client> clientList;

    public RMITypeServer(){
        clientList = new ArrayList<>();
        try {
            LocateRegistry.createRegistry(PORT);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void addClient(RMITypeClient client){}
}
