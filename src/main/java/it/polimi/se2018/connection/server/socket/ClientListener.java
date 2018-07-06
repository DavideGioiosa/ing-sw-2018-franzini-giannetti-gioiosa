package it.polimi.se2018.connection.server.socket;

import com.google.gson.Gson;
import it.polimi.se2018.connection.client.socket.ClientSocketInterface;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Timer;

/**
 * Socket's server class to handle connetion with clients
 */
public class ClientListener extends Thread implements ClientSocketInterface {

    /**
     * Client's socket
     */
    private Socket clientSocket;
    /**
     * Boolean used to close connection
     */
    private boolean quit;
    /**
     * Boolean used to notify disconnection
     */
    private boolean disconnection;
    /**
     * Gson object for sends
     */
    private Gson gson;
    /**
     * Observable object to implement callback's
     */
    private Observable<PlayerMessage> obs;
    /**
     * User's unique code
     */
    private String code;
    /**
     *  Buffer reader for inputs
     */
    private BufferedReader bufferedReader;
    /**
     * Boolean to check connection
     */
    boolean connected;
    /**
     * Boolean to check pings
     */
    boolean ping;
    /**
     * Client's timer
     */
    private Timer timer;

    /**
     * Builder method of the class
     * @param clientSocket client's socket
     */
    ClientListener(Socket clientSocket){
        this.clientSocket = clientSocket;
        gson = new Gson();
        obs = new Observable<>();
        quit = false;
        disconnection = false;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            connected = true;
        } catch (IOException e) {
            handleDisconnection();
            disconnection = true;
        }
        timer = new Timer();
        CheckTimeout checkTimeout = new CheckTimeout(this);
        timer.scheduleAtFixedRate(checkTimeout, (long)30*1000, (long)15*1000);

    }

    /**
     * Getter method of observable
     * @return
     */
    Observable<PlayerMessage> getObs() {
        return obs;
    }

    /**
     * Method to sto listening to client
     */
    void setQuit() {
        this.quit = false;
        if(timer != null){
            timer.cancel();
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("connessione già chiusa");
        }
    }

    /**
     * Method to handle client's disconnection
     */
    void handleDisconnection(){
        if(timer != null){
            timer.cancel();
        }

        User user = new User(TypeOfConnection.SOCKET);
        user.setUniqueCode(code);
        PlayerMessage disconnected = new PlayerMessage();
        disconnected.setUser(user);
        disconnected.setId(PlayerMessageTypeEnum.DISCONNECTED);
        obs.notify(disconnected);
    }

    /**
     * Setter method for unique code
     * @param code user's unique code
     */
    public void setCode(String code){
        this.code = code;
    }

    /**
     * Getter method for unique code
     * @return user's unique code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sender method
     * @param playerMessage message that has to be send
     */
    public synchronized void send(PlayerMessage playerMessage){
        try {

            OutputStreamWriter output = new OutputStreamWriter(clientSocket.getOutputStream());
            String jsonInString = gson.toJson(playerMessage) + "\n";
            output.write(jsonInString);
            output.flush();

        } catch (IOException e) {
            handleDisconnection();
        }
    }

    /**
     * Receive method from client
     * @param playerMessage message received
     */
    public synchronized void receive(PlayerMessage playerMessage) {

        obs.notify(playerMessage);
    }

    /**
     * Setter method for ping parameter
     * @param ping boolean to check client's connected status
     */
    void setPing(boolean ping) {
        this.ping = ping;
    }

    /**
     * Method to check client's connected status
     * @return status of the client
     */
    boolean isPing() {
        return ping;
    }

    /**
     * Thread's run method for listening
     */
    @Override
    public void run(){

        while(!clientSocket.isClosed() && !quit && !disconnection){
            try {

                bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message = bufferedReader.readLine();
                PlayerMessage playerMessage = gson.fromJson(message, PlayerMessage.class);
                if(playerMessage != null ){
                    if(playerMessage.getId().equals(PlayerMessageTypeEnum.PING)){
                        System.out.println("ricevo ping");
                        ping = true;
                    }else receive(playerMessage);
                }

            } catch (IOException e) {
                handleDisconnection();
                disconnection = true;
            }
        }

    }
}
