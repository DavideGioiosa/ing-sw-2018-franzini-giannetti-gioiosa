package it.polimi.se2018.connection.server;


import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RMITypeServer {
    private static int PORT = 1099;


    public RMITypeServer(){

        ServerImplementation serverImplementation = new ServerImplementation();
        try {
            LocateRegistry.createRegistry(PORT);

        } catch (RemoteException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());

        }
        try {

            ServerRemoteInterface stub = (ServerRemoteInterface) UnicastRemoteObject.exportObject(serverImplementation, 0);
            Naming.bind("//localhost", stub);

        } catch (RemoteException | AlreadyBoundException | MalformedURLException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());
        }
    }

}
