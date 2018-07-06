package it.polimi.se2018.connection.server.rmi;
import static  it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


/**
 * RMI server's class
 * @author Silvia Franzini
 */
public class RMITypeServer implements Observer<PlayerMessage> {
    //private static int port=1099 verrà presa da file
    /**
     * Reference to object that implements RMI server's remote methods
     */
    private ServerImplementation serverImplementation;
    /**
     * Observable object to implement callbacks in connection's operations
     */
    private Observable<PlayerMessage> obs;

    /**
     * Builder method of the class
     * @param port chosen port to start the server
     */
    public RMITypeServer(int port){

        obs = new Observable<>();

        try {
            //System.setProperty("java.rmi.server.hostname", "192.168.139.100");

            LocateRegistry.createRegistry(port);
            this.serverImplementation = new ServerImplementation(port);
            serverImplementation.addObserver(this);
            Naming.rebind("//localhost/RMIServer", serverImplementation);

            //Naming.rebind("//192.168.139.101:1099/MyServer", serverImplementation);

            println("ServerRMI acceso");
        } catch (RemoteException | MalformedURLException e) {
            println("Non riesco ad aprire il server");
        }
    }

    /**
     * Sender method of RMI server
     * @param playerMessage message that has to be send
     */
    public void send(PlayerMessage playerMessage){

        serverImplementation.sendToClient(playerMessage);
    }

    /**
     * Method invoked to add observer's to the class
     * @param observer observer that has to be added
     */
    public void addObserver(Observer<PlayerMessage> observer){
        obs.addObserver(observer);
    }

    /**
     * Getter method for observable object
     * @return
     */
    public Observable<PlayerMessage> getObs() {
        return obs;
    }

    /**
     * Method used to send message received to upper classes
     * @param message message received
     */
    @Override
    public void update(PlayerMessage message) {
        obs.notify(message);
    }

    /**
     * Shutdown method to close connection
     */
    public void shutdown(){
        serverImplementation.close();
    }
}
