package it.polimi.se2018.connection.client;


import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

import java.rmi.RemoteException;


public class ClientImplementation implements Observer<PlayerMessage>,ClientRemoteInterface {

    private Observable<PlayerMessage> obs;

     ClientImplementation(){
        obs = new Observable<>();
    }

     Observable<PlayerMessage> getObs() {
        return obs;
    }


    @Override
    public void receiveFromServer(PlayerMessage playerMessage) throws RemoteException {

        new Thread(new RMIReceiveThread(playerMessage, this)).start();
    }

    @Override
    public void update(PlayerMessage playerMessage) {
        obs.notify(playerMessage);
    }

    @Override
    public void receiveLifeline() throws RemoteException {
        //crea thread di risposta
    }
}

