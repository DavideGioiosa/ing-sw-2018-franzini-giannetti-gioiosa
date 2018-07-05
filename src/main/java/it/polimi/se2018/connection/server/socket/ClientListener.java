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
import java.util.Date;
import java.util.Timer;

public class ClientListener extends Thread implements ClientSocketInterface {

    private Socket clientSocket;
    private boolean quit;
    private boolean disconnection;
    private Gson gson;
    private Observable<PlayerMessage> obs;
    private String code;
    private BufferedReader bufferedReader;
    boolean connected;
    boolean ping;
    private Timer timer;

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
            System.out.println("disconnesso user");
            handleDisconnection();
            disconnection = true;
        }
        timer = new Timer();
        CheckTimeout checkTimeout = new CheckTimeout(this);
        timer.scheduleAtFixedRate(checkTimeout, (long)30*1000, (long)15*1000);

    }

    Observable<PlayerMessage> getObs() {
        return obs;
    }

    void setQuit() {
        this.quit = false;
        if(timer != null){
            timer.cancel();
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("già chiusa");
        }
    }

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

    public void setCode(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public synchronized void send(PlayerMessage playerMessage){
        try {

            OutputStreamWriter output = new OutputStreamWriter(clientSocket.getOutputStream());
            String jsonInString = gson.toJson(playerMessage) + "\n";
            output.write(jsonInString);
            output.flush();

        } catch (IOException e) {
            System.out.println("eccezione send");
            handleDisconnection();
        }
    }


    public synchronized void receive(PlayerMessage playerMessage) {

        obs.notify(playerMessage);
    }

    void setPing(boolean ping) {
        this.ping = ping;
    }

    boolean isPing() {
        return ping;
    }

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
                System.out.println("eccezione run");
                handleDisconnection();
                disconnection = true;
            }
        }

    }
}
