package it.polimi.se2018.connection.server.rmi;
import static  it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RMITypeServer implements Observer<PlayerMessage> {
    //private static int port=1099 verrà presa da file
    private ServerImplementation serverImplementation;
    private Observable<PlayerMessage> obs;


    public RMITypeServer(int port){

        obs = new Observable<>();

        try {
            //System.setProperty("java.rmi.server.hostname", "192.168.139.100");

            Registry registry =  LocateRegistry.createRegistry(port);
            this.serverImplementation = new ServerImplementation(port);
            serverImplementation.addObserver(this);
            Naming.rebind("//localhost/RMIServer", serverImplementation);

            //Naming.rebind("//192.168.139.100:1099/MyServer", serverImplementation);

            println("ServerRMI acceso");
        } catch (RemoteException | MalformedURLException e) {

            Logger.getGlobal().log(Level.SEVERE,e.toString());
        }
    }


    public void send(PlayerMessage playerMessage){
        try {
            serverImplementation.sendToClient(playerMessage);
        } catch (RemoteException e) {
            serverImplementation.disconnectionHandler(playerMessage.getUser().getUniqueCode());
        }
    }

    public void addObserver(Observer<PlayerMessage> observer){
        obs.addObserver(observer);
    }

    public Observable<PlayerMessage> getObs() {
        return obs;
    }

    @Override
    public void update(PlayerMessage message) {
        obs.notify(message);
    }

    public void shutdown(){
        serverImplementation.close();
    }
}
