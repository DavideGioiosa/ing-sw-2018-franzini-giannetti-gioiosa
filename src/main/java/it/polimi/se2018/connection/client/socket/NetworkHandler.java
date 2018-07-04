package it.polimi.se2018.connection.client.socket;

import com.google.gson.Gson;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.utils.Observable;
import jdk.nashorn.internal.runtime.regexp.joni.constants.TargetInfo;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Timer;

import static  it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;



public class NetworkHandler extends Thread implements ClientSocketInterface {

    private Socket socket;
    private BufferedReader bufferedReader;
    private OutputStreamWriter out;
    private boolean quit;
    private Gson gson;
    private Observable<PlayerMessage> obs;
    private String host;
    private int port;
    private boolean connected;
    private boolean ping;
    private Timer timerPing;


    NetworkHandler(String host, int port){
        obs = new Observable<>();
        gson = new Gson();
        quit = false;
        this.host = host;
        this.port = port;
        connected = false;
        ping = false;
        connect();
    }

    private void connect(){

        while(!connected){
            try {
                println("tento connessione");
                InetAddress addr = InetAddress.getByName(host);
                socket = new Socket(addr, port);
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                connected = true;
                timerPing = new Timer();
                timerPing.scheduleAtFixedRate(new SocketClientPing(this), (long)5*1000, (long)5*1000);

            } catch (IOException e) {
                println("connessione non riuscita");
                PlayerMessage playerMessage = new PlayerMessage();
                playerMessage.setError(400);
                receive(playerMessage);
            }

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

        while(!quit){
            try{

                String message = bufferedReader.readLine();
                PlayerMessage playerMessage = gson.fromJson(message, PlayerMessage.class);
                if(playerMessage != null){
                    if(playerMessage.getId().equals(PlayerMessageTypeEnum.PING)){
                        ping = true;
                    }else receive(playerMessage);
                }

            }catch (IOException e){
                connected = false;
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
            connected = false;
            disconnectionHandler();
        }

    }

    void ping(){

        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setId(PlayerMessageTypeEnum.PING);
        send(playerMessage);

    }


    public void disconnectionHandler(){

        System.out.println("server disconnesso");
        timerPing.cancel();
        timerPing.purge();

        PlayerMessage disconnect = new PlayerMessage();
        disconnect.setId(PlayerMessageTypeEnum.DISCONNECTED);
        obs.notify(disconnect);
    }

    private synchronized void closeConnection() throws IOException{

        if(timerPing != null){
            timerPing.cancel();
            timerPing.purge();
        }

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

