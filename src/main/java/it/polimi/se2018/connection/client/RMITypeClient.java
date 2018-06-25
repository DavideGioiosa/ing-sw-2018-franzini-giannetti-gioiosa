package it.polimi.se2018.connection.client;

import it.polimi.se2018.connection.server.ServerRemoteInterface;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMITypeClient implements ClientStrategy, Observer<PlayerMessage> {

    private ServerRemoteInterface stub;
    private Observable<PlayerMessage> obs;
    private Timer timer;
    private ClientImplementation clientImplementation;
    private ClientRemoteInterface remoteRef;

    public RMITypeClient(){
        obs = new Observable<>();
        clientImplementation = new ClientImplementation();
        clientImplementation.getObs().addObserver(this);

        try {
            LocateRegistry.getRegistry();

        } catch (RemoteException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());
        }

    }

    public ServerRemoteInterface getStub() {
        return stub;
    }

    public ClientRemoteInterface getRemoteRef() {
        return remoteRef;
    }

    @Override
    public void connect(){

        try {

            this.stub = (ServerRemoteInterface) Naming.lookup("RMIServer"); //riferimento a server non sarà statico

            remoteRef = (ClientRemoteInterface) UnicastRemoteObject.exportObject(clientImplementation, 0);

            stub.addClient(remoteRef);

            RMIClientPing rmiClientPing = new RMIClientPing(this, stub);
            timer = new Timer();
            timer.scheduleAtFixedRate(rmiClientPing, new Date(), 90*1000);


        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            PlayerMessage playerMessage = new PlayerMessage();
            playerMessage.setError(200);
            update(playerMessage);
        }
    }


    @Override
    public void reconnect(User user){

        timer.cancel();
        timer.purge();
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
        disconnect.setError(201); //valore da individuare
        obs.notify(disconnect);
    }


    @Override
    public void close(){
        PlayerMessage playerMessage = new PlayerMessage();
        timer.cancel();
        timer.purge();
        playerMessage.setClosure();
        try {
            stub.receive(playerMessage);
        } catch (RemoteException e) {
            disconnectionHandler();

        }
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

