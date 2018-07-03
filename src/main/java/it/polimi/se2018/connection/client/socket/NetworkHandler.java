package it.polimi.se2018.connection.client.socket;

import com.google.gson.Gson;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.utils.Observable;

import java.io.*;
import java.net.Socket;

import static  it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;



public class NetworkHandler extends Thread implements ClientSocketInterface {

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
             PlayerMessage playerMessage = new PlayerMessage();
             playerMessage.setError(400);
             receive(playerMessage);
         }
         println("connesso a ServerSocket");
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
                if(playerMessage != null){
                    receive(playerMessage);
                }

            }catch (IOException e){
                setQuit();
                disconnectionHandler();
            }
        }
        try{
            this.closeConnection();
        }catch (IOException e){
            PlayerMessage playerMessage = new PlayerMessage();
            playerMessage.setError(401);
            receive(playerMessage);
        }

    }

    public synchronized void send(PlayerMessage playerMessage){
        try {
            out = new OutputStreamWriter(socket.getOutputStream());
            String jsonInString = gson.toJson(playerMessage) + "\n";
            out.write(jsonInString);
            out.flush();
        } catch (IOException e) {
            disconnectionHandler();
        }

    }

    public void disconnectionHandler(){
        PlayerMessage disconnect = new PlayerMessage();
        disconnect.setError(201); //valore da individuare
        obs.notify(disconnect);
    }

     private synchronized void closeConnection() throws IOException{

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
