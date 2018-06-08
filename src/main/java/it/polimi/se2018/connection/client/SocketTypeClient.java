package it.polimi.se2018.connection.client;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

public class SocketTypeClient implements ClientStrategy, Observer<PlayerMessage> {

    private NetworkHandler networkHandler;
    private int networkPort;
    private String host;
    private Client client;
    private Observable<PlayerMessage> obs;

    public SocketTypeClient(String host, int port){
        obs = new Observable<>();
        this.host=host;
        this.networkPort = port;
        this.networkHandler = new NetworkHandler(this.host, this.networkPort);
        networkHandler.getObs().addObserver(this);
    }


    @Override
    public void sendToServer(PlayerMessage playerMessage){
        networkHandler.send(playerMessage);
    }


    public void close(){
        networkHandler.setQuit();
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

