package it.polimi.se2018.connection.client;

import com.google.gson.Gson;
import it.polimi.se2018.connection.server.ServerRemoteInterface;
import it.polimi.se2018.model.PlayerMessage;


import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RMITypeClient implements ClientStrategy {

    private boolean quit;
    private ServerRemoteInterface stub;
    private Client client;
    private ClientImplementation clientImplementation;

    public RMITypeClient(Client client){
        this.client = client;
        this.clientImplementation = new ClientImplementation(this);

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
             LocateRegistry.getRegistry();
        } catch (RemoteException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());
        }

        try {

                this.stub = (ServerRemoteInterface) Naming.lookup("//localhost");

        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            Logger.getGlobal().log(Level.SEVERE, e.toString());
        }
    }
    @Override
    public void sendToServer(PlayerMessage playerMessage){

        if(stub != null)
        {
            try {
                stub.receive(playerMessage);
            } catch (RemoteException e) {
                Logger.getGlobal().log(Level.SEVERE, e.toString());
            }
        }
    }

    @Override
    public void receive(PlayerMessage playerMessage) {
       client.receive(playerMessage);
    }


    @Override
    public void close(){
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setClosure();
        try {
            stub.receive(playerMessage);
        } catch (RemoteException e) {
            Logger.getGlobal().log(Level.SEVERE, e.toString());
        }
    }
}
