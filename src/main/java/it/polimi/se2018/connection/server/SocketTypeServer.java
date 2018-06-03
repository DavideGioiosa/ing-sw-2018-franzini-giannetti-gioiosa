package it.polimi.se2018.connection.server;

import it.polimi.se2018.model.PlayerMessage;

import java.util.ArrayList;
import java.util.List;

public class SocketTypeServer {
    private int networkPort;
    private List<ClientListener> clientListenerList;
   // private ControllerSocket controllerSocket;

    public SocketTypeServer(){

        clientListenerList = new ArrayList<>();
    }

    public void addClient(ClientListener clientListener){
        this.clientListenerList.add(clientListener);
    }

    public void removeClient(ClientListener clientListener){
        this.clientListenerList.remove(clientListener);
    }

    public void receive (PlayerMessage playerMessage){
        //richiama il Global Controller per notificare l'invio
    }

    public void send (PlayerMessage playerMessage){
        //invio
    }
}
