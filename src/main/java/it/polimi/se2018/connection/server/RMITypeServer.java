package it.polimi.se2018.connection.server;


import it.polimi.se2018.model.PlayerMessage;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RMITypeServer {
    private static int port=1099; // verrà presa da file
    private ServerImplementation serverImplementation;

     RMITypeServer(){

        try {
            LocateRegistry.createRegistry(port);

        } catch (RemoteException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());

        }
        try {

            this.serverImplementation = new ServerImplementation();
            Naming.bind("RMIServer", serverImplementation); //nome del server verrà passato

        } catch (RemoteException | AlreadyBoundException | MalformedURLException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());
        }
    }

    public void send(PlayerMessage playerMessage){
        try {
            serverImplementation.sendToClient(playerMessage);
        } catch (RemoteException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());
        }
    }

}
