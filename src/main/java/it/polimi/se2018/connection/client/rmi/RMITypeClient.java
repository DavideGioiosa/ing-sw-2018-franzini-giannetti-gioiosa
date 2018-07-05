package it.polimi.se2018.connection.client.rmi;

import it.polimi.se2018.connection.client.Client;
import it.polimi.se2018.connection.client.ClientStrategy;
import it.polimi.se2018.connection.server.rmi.ServerRemoteInterface;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

import static  it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.Date;
import java.util.Timer;

/**
 * RMI Connection's strategy class
 * @author Silvia Franzini
 */
public class RMITypeClient implements ClientStrategy, Observer<PlayerMessage> {

    /**
     * Server's remote reference
     */
    private ServerRemoteInterface stub;
    /**
     * List of RMIClient's observers
     */
    private Observable<PlayerMessage> obs;
    /**
     * Timer used to scheule ping at fixed rate
     */
    private Timer timer;
    /**
     * Reference of remote client's object
     */
    private ClientImplementation clientImplementation;

    /**
     * Builder method of the class
     */
    public RMITypeClient(){
        obs = new Observable<>();
        clientImplementation = new ClientImplementation();
        clientImplementation.getObs().addObserver(this);

    }

    /**
     * Method used to set connection to a chosen server
     */
    @Override
    public void connect(){

        try {


            this.stub = (ServerRemoteInterface) Naming.lookup("//localhost/RMIServer"); //riferimento a server non sarà statico
            //this.stub = (ServerRemoteInterface) Naming.lookup("//192.168.139.100:1099/MyServer");

            ClientRemoteInterface remoteRef = (ClientRemoteInterface) UnicastRemoteObject.exportObject(clientImplementation, 0);

            stub.addClient(remoteRef);

            RMIClientPing rmiClientPing = new RMIClientPing(this, stub);
            timer = new Timer();
            timer.scheduleAtFixedRate(rmiClientPing, new Date(), (long)90*1000);


        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            PlayerMessage playerMessage = new PlayerMessage();
            playerMessage.setId(PlayerMessageTypeEnum.DISCONNECTED);
            update(playerMessage);
        }
        println("connesso a serverRMI");
    }

    /**
     * Method used to reconnect to the server
     * @param user this user
     */
    @Override
    public void reconnect(User user){

        if(timer != null){
            timer.cancel();
            timer.purge();
        }
        connect();
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setUser(user);
        sendToServer(playerMessage);

    }

    /**
     * Method invoked to send a message to the server
     * @param playerMessage message that has to be sent
     */
    @Override
    public void sendToServer(PlayerMessage playerMessage){

        if(stub != null)
        {
            try {
                stub.receive(playerMessage);
            } catch (RemoteException e) {
                disconnectionHandler();
            }
        }
    }

    /**
     * Method invoked to handle connection's fall
     */
     void disconnectionHandler(){
        PlayerMessage disconnect = new PlayerMessage();
        disconnect.setError(401); //valore da individuare
        obs.notify(disconnect);
    }

    /**
     * Method used to close client's connection
     */
    @Override
    public void close(){
        timer.cancel();
        timer.purge();
    }

    /**
     * Method used add obsevers to this class
     * @param client the observer type
     */
    @Override
    public void addObserver(Client client) {
        obs.addObserver(client);
    }

    /**
     * Update method used to receive messages and send them to the user
     * @param playerMessage message received
     */
    @Override
    public void update(PlayerMessage playerMessage) {

        if(playerMessage.getId().equals(PlayerMessageTypeEnum.WINNER)){
            close();
        }
        obs.notify(playerMessage);

    }
}

