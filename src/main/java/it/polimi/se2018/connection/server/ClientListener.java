package it.polimi.se2018.connection.server;

import com.google.gson.Gson;
import it.polimi.se2018.connection.client.ClientSocketInterface;
import it.polimi.se2018.model.PlayerMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientListener extends Thread implements ClientSocketInterface {

    private Socket clientSocket;
    private SocketTypeServer socketTypeServer;
    private Gson gson;

    public ClientListener(SocketTypeServer socketTypeServer, Socket clientSocket){
        this.clientSocket = clientSocket;
        this.socketTypeServer = socketTypeServer;
        gson = new Gson();
    }


    @Override
    public void run(){
        try {
            //TODO mettere controllo su keep alive client
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message = bufferedReader.readLine();
            PlayerMessage playerMessage = gson.fromJson(message, PlayerMessage.class);
            receive(playerMessage);

        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());
        }

    }

    @Override
    public synchronized void receive(PlayerMessage playerMessage) {
        socketTypeServer.receive(playerMessage);
    }
}
