package it.polimi.se2018.connection.client;

import it.polimi.se2018.model.PlayerMessage;

public class SocketTypeClient implements ClientStrategy, ClientSocketInterface{

    private NetworkHandler networkHandler;
    private int networkPort;
    private String host;
    private Client client;

    public SocketTypeClient(Client client, String host, int port){

        this.client = client;
        this.host=host;
        this.networkPort = port;
        this.networkHandler = new NetworkHandler(this.host, this.networkPort, this);
    }


    public void receive(PlayerMessage playerMessage){
        client.receive(playerMessage);
    }


    public void sendToServer(PlayerMessage playerMessage){
        networkHandler.send(playerMessage);
    }


    public void close(){
        networkHandler.setQuit();
    }

}
