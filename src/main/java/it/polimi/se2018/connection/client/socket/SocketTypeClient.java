package it.polimi.se2018.connection.client.socket;

import it.polimi.se2018.connection.client.Client;
import it.polimi.se2018.connection.client.ClientStrategy;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

public class SocketTypeClient implements ClientStrategy, Observer<PlayerMessage> {

    private NetworkHandler networkHandler;
    private int networkPort;
    private String host;
    private Observable<PlayerMessage> obs;

    public SocketTypeClient(String host, int port){
        obs = new Observable<>();
        this.host = host;
        this.networkPort = port;
    }

    @Override
    public void connect(){

        this.networkHandler = new NetworkHandler(this.host, this.networkPort);
        networkHandler.getObs().addObserver(this);
        networkHandler.start();
    }

    @Override
    public void reconnect(User user){
        networkHandler.setQuit();
        connect();
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setUser(user);
        networkHandler.send(playerMessage);

    }

    @Override
    public void sendToServer(PlayerMessage playerMessage){
        networkHandler.send(playerMessage);
    }

    @Override
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

