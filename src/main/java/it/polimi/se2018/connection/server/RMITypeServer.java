package it.polimi.se2018.connection.server;


import it.polimi.se2018.view.RemoteView;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RMITypeServer {
    private static int PORT;

     RMITypeServer(){

        try {
            LocateRegistry.createRegistry(PORT);

        } catch (RemoteException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());

        }
        try {

            ServerImplementation serverImplementation = new ServerImplementation();
            ServerRemoteInterface stub = (ServerRemoteInterface) UnicastRemoteObject.exportObject(serverImplementation, 0);
            Naming.bind("//localhost", stub); //nome del server verrà passato

        } catch (RemoteException | AlreadyBoundException | MalformedURLException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());
        }
    }

}
