package it.polimi.se2018.connection.client;

import com.google.gson.Gson;
import it.polimi.se2018.connection.client.Client;
import it.polimi.se2018.connection.client.NetworkHandler;
import it.polimi.se2018.model.PlayerMessage;

public class SocketTypeClient {

    private NetworkHandler networkHandler;
    private int networkPort;
    private String host;
    private Gson gson;
    private Client client;

    public SocketTypeClient(Client client){
        gson = new Gson();
        this.client = client;
    }

    public void receive(String string){
        PlayerMessage playerMessage = gson.fromJson(string, PlayerMessage.class);
        client.receive(playerMessage);
    }


    public void sendToServer(PlayerMessage playerMessage){
        String jsonInString = gson.toJson(playerMessage);
        networkHandler.send(jsonInString);
    }

    public void close(){
        networkHandler.setQuit();
    }

}
