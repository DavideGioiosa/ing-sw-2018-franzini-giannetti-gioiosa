package it.polimi.se2018.connection.client.socket;

import com.google.gson.Gson;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.utils.Observable;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;

import static  it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;


/**
 * Class that handles client's connection
 * @author Silvia Franzini
 */
public class NetworkHandler extends Thread implements ClientSocketInterface {

    /**
     * Reference to the server's socket
     */
    private Socket socket;
    /**
     * Buffer for receiving messages
     */
    private BufferedReader bufferedReader;
    /**
     * Stream used to send messages
     */
    private OutputStreamWriter out;
    /**
     * Boolean to set connection's closure
     */
    private boolean quit;
    /**
     * Gson reference used to modify objects for send
     */
    private Gson gson;
    /**
     * Observable object used for callbacks
     */
    private Observable<PlayerMessage> obs;
    /**
     * Host's address
     */
    private String host;
    /**
     * Host's port
     */
    private int port;
    /**
     * Client's status
     */
    private boolean connected;
    /**
     * Timer used to scheule ping at fixed rate
     */
    private Timer timerPing;

    /**
     * Builder method of the class
     * @param host Host's address
     * @param port Host's port
     */
    NetworkHandler(String host, int port){
        obs = new Observable<>();
        gson = new Gson();
        quit = false;
        this.host = host;
        this.port = port;
        connected = false;
        connect();
    }

    /**
     * Method used to start a connection with the server
     */
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

    /**
     * Getter method for observable
     * @return the observable object
     */
    Observable<PlayerMessage> getObs() {
        return obs;
    }

    /**
     * Method used to stop connection
     */
    synchronized void setQuit() {// viene invocato da SocketTypeClient
        this.quit = true;
    }

    /**
     * Receive method used to send a server's message to the client
     * @param playerMessage message received
     */
    @Override
    public void receive(PlayerMessage playerMessage) {

        obs.notify(playerMessage);
    }

    /**
     * Thread's run method used to listen to the server's socket
     */
    @Override
    public void run(){

        while(!quit){
            try{

                String message = bufferedReader.readLine();
                PlayerMessage playerMessage = gson.fromJson(message, PlayerMessage.class);
                if(playerMessage != null){
                    if(playerMessage.getId().equals(PlayerMessageTypeEnum.WINNER)){
                        closeConnection();
                    }
                    receive(playerMessage);
                }
            }catch (IOException e){
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

    /**
     * Send method used to send a client's message to the server
     * @param playerMessage message that has to be send
     */
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

    /**
     * Client's keepAlive method
     */
    void ping(){

        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setId(PlayerMessageTypeEnum.PING);
        send(playerMessage);

    }

    /**
     * Method that handles server's disconnection
     */
    private void disconnectionHandler(){
        if(connected){
            connected = false;
            System.out.println("server disconnesso");
            timerPing.cancel();
            timerPing.purge();

            PlayerMessage disconnect = new PlayerMessage();
            disconnect.setId(PlayerMessageTypeEnum.DISCONNECTED);
            obs.notify(disconnect);
        }

    }

    /**
     * Method used to close client's socket and connection's buffers
     * @throws IOException if the operation can't be executed
     */
     synchronized void closeConnection() throws IOException{

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

