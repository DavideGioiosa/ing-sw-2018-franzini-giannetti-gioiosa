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

public class ClientListener extends Thread implements ClientSocketInterface {

    private Socket clientSocket;
    private boolean quit;
    private boolean disconnection;
    private Gson gson;
    private Observable<PlayerMessage> obs;
    private String code;


    ClientListener(Socket clientSocket){
        this.clientSocket = clientSocket;
        gson = new Gson();
        obs = new Observable<>();
        quit = false;
        disconnection = false;
    }

    Observable<PlayerMessage> getObs() {
        return obs;
    }

    void setQuit(boolean value) {
        this.quit = value;
    }

    @Override
    public void run(){

        while(!disconnection && !quit){
            try {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message = bufferedReader.readLine();
                PlayerMessage playerMessage = gson.fromJson(message, PlayerMessage.class);
                if(playerMessage != null){
                    receive(playerMessage);
                }

            } catch (IOException e) {
                handleDisconnection();
                disconnection = true;
            }
        }

    }

     void handleDisconnection(){

        User user = new User(TypeOfConnection.SOCKET);
        user.setUniqueCode(code);
        PlayerMessage disconnected = new PlayerMessage();
        disconnected.setUser(user);
        disconnected.setError(100);
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
            handleDisconnection();
        }
    }


    public synchronized void receive(PlayerMessage playerMessage) {

        obs.notify(playerMessage);
    }
}
