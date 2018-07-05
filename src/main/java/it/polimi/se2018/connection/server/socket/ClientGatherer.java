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

public class ClientGatherer extends Thread{

    private SocketTypeServer server;
    private ServerSocket serverSocket;
    private HashMap<String, Timer> timerHashMap;


    public ClientGatherer(SocketTypeServer server, int port){

        timerHashMap = new HashMap<>();
        this.server = server;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());
        }
    }

    public void close(){

        Iterator<Map.Entry<String, Timer>> iterator = timerHashMap.entrySet().iterator();
        while(iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());
        }
    }


     HashMap<String, Timer> getTimerHashMap() {
        return timerHashMap;
    }

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

                //timerHashMap.put(code, timer);
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
                }else Logger.getGlobal().log(Level.SEVERE,e.toString());

            }
        }


    }
}
