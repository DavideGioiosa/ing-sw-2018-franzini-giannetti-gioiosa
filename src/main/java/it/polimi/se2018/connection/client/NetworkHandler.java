package it.polimi.se2018.connection.client;

import com.google.gson.Gson;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.utils.Observable;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NetworkHandler extends Thread implements ClientSocketInterface{

    private Socket socket;
    private BufferedReader bufferedReader;
    private OutputStreamWriter out;
    private boolean quit;
    private Gson gson;
    private Observable<PlayerMessage> obs;


     NetworkHandler(String host, int port){
         obs = new Observable<>();
         gson = new Gson();
         quit = false;
         try {
             socket = new Socket(host, port);
             bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         } catch (IOException e) {
             //TODO: gestire assenza di server a cui connettersi
             PlayerMessage playerMessage = new PlayerMessage();
             playerMessage.setError(1000);
             receive(playerMessage);
         }

    }

    Observable<PlayerMessage> getObs() {
        return obs;
    }

    synchronized void setQuit() {// viene invocato da SocketTypeClient
        this.quit = true;
    }

    @Override
    public void receive(PlayerMessage playerMessage) {
        obs.notify(playerMessage);
    }

    @Override
    public void run(){

        while(!socket.isClosed() && !quit){
            try{

                String message = bufferedReader.readLine();
                PlayerMessage playerMessage = gson.fromJson(message, PlayerMessage.class);
                receive(playerMessage);

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
            String jsonInString = gson.toJson(playerMessage) + "\n";
            out.write(jsonInString);
            out.flush();
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, e.toString());
        }

    }

     private synchronized void closeConnection() throws IOException{
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

