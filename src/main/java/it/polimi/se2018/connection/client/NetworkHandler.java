package it.polimi.se2018.connection.client;

import com.google.gson.Gson;
import it.polimi.se2018.model.PlayerMessage;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NetworkHandler extends Thread{

    private Socket socket;
    private BufferedReader bufferedReader;
    private SocketTypeClient socketTypeClient;
    private OutputStreamWriter out;
    private boolean quit;
    private Gson gson;


    public NetworkHandler(String host, int port, SocketTypeClient socketTypeClient){
        gson = new Gson();
        quit=false;
        this.socketTypeClient=socketTypeClient;
        try {
            socket = new Socket(host, port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, e.toString());
        }
    }

    public synchronized void setQuit() {// viene invocato da SocketTypeClient
        this.quit = true;
    }

    @Override
    public void run(){

        while(!socket.isClosed() && !quit){
            try{
                String message = bufferedReader.readLine();
                PlayerMessage playerMessage = gson.fromJson(message, PlayerMessage.class);
                socketTypeClient.receive(playerMessage);

            }catch (IOException e){
                Logger.getGlobal().log(Level.SEVERE, e.toString());
            }
        }

        try{
            this.closeConnection();
        }catch (IOException e){
            Logger.getGlobal().log(Level.SEVERE, e.toString());
        }

    }

    public synchronized void send(PlayerMessage playerMessage){
        try {
            out = new OutputStreamWriter(socket.getOutputStream());
            String jsonInString = gson.toJson(playerMessage);
            out.write(jsonInString);
            out.flush();
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, e.toString());
        }

    }

    public synchronized void closeConnection() throws IOException{
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setClosure();

        try {
            send(playerMessage);
        }
        finally {
            try {
                out.close();
            }

            finally {
                try {
                    bufferedReader.close();
                }

                finally {
                    socket.close();
                }
            }
        }
    }

  }
