package it.polimi.se2018.connection.client.rmi;


import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

import java.rmi.RemoteException;


/**
 * Implementation of RMI Remote Interface
 * @author Silvia Franzini
 */
public class ClientImplementation implements Observer<PlayerMessage>,ClientRemoteInterface {

    /**
     * Observable element used to send the received message to the upper classes
     */
    private Observable<PlayerMessage> obs;

    /**
     * Builder method of the class
     */
    ClientImplementation(){
        obs = new Observable<>();
    }

    /**
     * Getter method of the observable, mainly used to attach observers
     * @return the observable element
     */
    Observable<PlayerMessage> getObs() {
        return obs;
    }

    /**
     * Receiver method, gets messages from the server
     * @param playerMessage message received
     * @throws RemoteException exception due to communication fall
     */
    @Override
    public void receiveFromServer(PlayerMessage playerMessage) throws RemoteException {
        System.out.println("ricevo dal server");
        if(playerMessage != null){
            new Thread(new RMIReceiveThread(playerMessage, this)).start();
        }

    }

    /**
     * Observer pattern's update method used to send the view a received message from the server
     * @param playerMessage message received from the server
     */
    @Override
    public void update(PlayerMessage playerMessage) {
        obs.notify(playerMessage);
    }

    /**
     * Method used by the server to verify if the client is still connected
     * @throws RemoteException exception due to communication fall
     */
    @Override
    public void receiveLifeline() throws RemoteException {
        //crea thread di risposta
    }
}

