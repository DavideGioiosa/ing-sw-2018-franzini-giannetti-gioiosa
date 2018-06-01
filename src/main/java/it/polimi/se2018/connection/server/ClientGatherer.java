package it.polimi.se2018.connection.server;

import it.polimi.se2018.connection.client.ClientRemoteIterface;
import it.polimi.se2018.connection.client.ClientSocketInterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientGatherer extends Thread{

    private SocketTypeServer server;
    private ClientListener clientListener;
    private int networkPort;
    private ServerSocket serverSocket;


    public ClientGatherer(SocketTypeServer server, int port){

        this.server = server;
        this.networkPort = port;
        try {
            this.serverSocket = new ServerSocket();
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());
        }

    }

    @Override
    public void run(){

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();

            clientListener = new ClientListener(server, clientSocket);
            server.addClient(clientListener);

        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());
        }

    }
}
