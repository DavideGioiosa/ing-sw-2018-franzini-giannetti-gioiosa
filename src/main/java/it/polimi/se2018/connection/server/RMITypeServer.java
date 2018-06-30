package it.polimi.se2018.connection.server;
import static  it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RMITypeServer implements Observer<PlayerMessage> {
    //private static int port=1099 verrà presa da file
    private ServerImplementation serverImplementation;
    private Observable<PlayerMessage> obs;


    RMITypeServer(int port){

        obs = new Observable<>();
        try {
            LocateRegistry.createRegistry(port);

        } catch (RemoteException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());

        }
        try {

            this.serverImplementation = new ServerImplementation();
            serverImplementation.addObserver(this);
            Naming.bind("RMIServer", serverImplementation);

            println("ServerRMI acceso");
        } catch (RemoteException | AlreadyBoundException | MalformedURLException e) {
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
}
