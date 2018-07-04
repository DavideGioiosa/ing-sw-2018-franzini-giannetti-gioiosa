package it.polimi.se2018.connection.client.rmi;

import it.polimi.se2018.connection.client.Client;
import it.polimi.se2018.connection.client.ClientStrategy;
import it.polimi.se2018.connection.client.rmi.ClientImplementation;
import it.polimi.se2018.connection.client.rmi.ClientRemoteInterface;
import it.polimi.se2018.connection.client.rmi.RMIClientPing;
import it.polimi.se2018.connection.server.rmi.ServerRemoteInterface;
import it.polimi.se2018.model.PlayerMessage;
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

public class RMITypeClient implements ClientStrategy, Observer<PlayerMessage> {

    private ServerRemoteInterface stub;
    private Observable<PlayerMessage> obs;
    private Timer timer;
    private ClientImplementation clientImplementation;

    public RMITypeClient(){
        obs = new Observable<>();
        clientImplementation = new ClientImplementation();
        clientImplementation.getObs().addObserver(this);

    }

    @Override
    public void connect(){

        try {


            this.stub = (ServerRemoteInterface) Naming.lookup("//localhost/RMIServer"); //riferimento a server non sarà statico
            //this.stub = (ServerRemoteInterface) Naming.lookup("//192.168.139.101:1099/MyServer");

            ClientRemoteInterface remoteRef = (ClientRemoteInterface) UnicastRemoteObject.exportObject(clientImplementation, 0);

            stub.addClient(remoteRef);

            RMIClientPing rmiClientPing = new RMIClientPing(this, stub);
            timer = new Timer();
            timer.scheduleAtFixedRate(rmiClientPing, new Date(), (long)90*1000);


        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            PlayerMessage playerMessage = new PlayerMessage();
            playerMessage.setError(200);
            update(playerMessage);
        }
        println("connesso a serverRMI");
    }


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

     void disconnectionHandler(){
        PlayerMessage disconnect = new PlayerMessage();
        disconnect.setError(401); //valore da individuare
        obs.notify(disconnect);
    }


    @Override
    public void close(){
        timer.cancel();
        timer.purge();
    }

    @Override
    public void addObserver(Client client) {
        obs.addObserver(client);
    }


    @Override
    public void update(PlayerMessage playerMessage) {

        obs.notify(playerMessage);

    }
}

