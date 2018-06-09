package it.polimi.se2018.connection.server;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.model.player.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientGatherer extends Thread{

    private SocketTypeServer server;
    private ServerSocket serverSocket;


    public ClientGatherer(SocketTypeServer server, int port){


        this.server = server;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());
        }
    }

    @Override
    public void run(){

        Socket clientSocket = null;
        while(!serverSocket.isClosed()){
            try {
                clientSocket = serverSocket.accept();
                ClientListener clientListener = new ClientListener(clientSocket);
                clientListener.getObs().addObserver(server);
                clientListener.start();
                User user = new User(TypeOfConnection.SOCKET);
                String code = user.getUniqueCode();
                while(server.getCodeList().contains(code)){
                    user = new User(TypeOfConnection.SOCKET);
                    code = user.getUniqueCode();
                }
                server.addCode(code);
                server.addClient(code, clientListener);
                PlayerMessage playerMessage = new PlayerMessage();
                playerMessage.setUser(user);
                clientListener.send(playerMessage);



            } catch (IOException e) {
                Logger.getGlobal().log(Level.SEVERE,e.toString());
            }
        }


    }
}
