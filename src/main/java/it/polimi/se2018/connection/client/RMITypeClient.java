package it.polimi.se2018.connection.client;


import it.polimi.se2018.connection.server.ServerRemoteInterface;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import java.util.logging.Level;
import java.util.logging.Logger;


public class RMITypeClient implements ClientStrategy, Observer<PlayerMessage> {

    private ServerRemoteInterface stub;
    private Observable<PlayerMessage> obs;

    public RMITypeClient(){
        obs = new Observable<>();
        ClientImplementation clientImplementation = new ClientImplementation();
        clientImplementation.getObs().addObserver(this);

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
             LocateRegistry.getRegistry();
        } catch (RemoteException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());
        }

        try {
                ClientRemoteIterface remoteRef = (ClientRemoteIterface) UnicastRemoteObject.exportObject(clientImplementation, 0);
                this.stub = (ServerRemoteInterface) Naming.lookup("//localhost"); //riferimento a server non sarà statico
                stub.addClient(remoteRef);


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


     /*void sendUser(PlayerMessage playerMessage){
        String name = client.getThisUser().getNickname();
        playerMessage.getPlayerChoice().getUser().setNickname(name);
        try {
            stub.receive(playerMessage);
        } catch (RemoteException e) {
            Logger.getGlobal().log(Level.SEVERE, e.toString());
        }
    }*/



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

    @Override
    public void addObserver(Client client) {
        obs.addObserver(client);
    }


    @Override
    public void update(PlayerMessage playerMessage) {
        obs.notify(playerMessage);
    }
}
