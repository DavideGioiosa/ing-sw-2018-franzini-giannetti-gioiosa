package it.polimi.se2018.connection.server.socket;
import static  it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.model.player.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Socket server's class to gather new clients
 * @author Silvia Franzini
 */
public class ClientGatherer extends Thread{

    /**
     * Socket server's class
     */
    private SocketTypeServer server;
    /**
     * Server's port
     */
    private ServerSocket serverSocket;

    /**
     * Builder method of the class
     * @param server socket server
     * @param port port of the connection
     */
    public ClientGatherer(SocketTypeServer server, int port){

        this.server = server;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            println("impossibile aprire connessione");
        }
    }


    /**
     * Thread's run method to gather new clients
     */
    @Override
    public void run(){

        User user;
        String code;
        Socket clientSocket;
        ClientListener clientListener = null;
         while(!serverSocket.isClosed()){
            try {
                clientSocket = serverSocket.accept();
                clientListener = new ClientListener(clientSocket);
                clientListener.getObs().addObserver(server);
                clientListener.start();

                do{
                     user = new User(TypeOfConnection.SOCKET);
                     code = user.createUniqueCode();

                }while(server.getCodeList().contains(code));

                server.addCode(code);
                server.addClient(code, clientListener);
                clientListener.setCode(code);
                PlayerMessage playerMessage = new PlayerMessage();
                playerMessage.setUser(user);
                clientListener.send(playerMessage);
                println("trovato nuovo client");

            } catch (IOException e) {
                if(clientListener != null){
                    clientListener.handleDisconnection();
                }else println("errrore in creazione clientListener");

            }
        }


    }
}
