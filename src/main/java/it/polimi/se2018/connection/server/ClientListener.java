package it.polimi.se2018.connection.server;

import com.google.gson.Gson;
import it.polimi.se2018.connection.client.ClientSocketInterface;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.utils.Observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientListener extends Thread implements ClientSocketInterface {

    private Socket clientSocket;
    private Gson gson;
    private Observable<PlayerMessage> obs;

    ClientListener(Socket clientSocket){
        this.clientSocket = clientSocket;
        gson = new Gson();
        obs = new Observable<>();
    }

    Observable<PlayerMessage> getObs() {
        return obs;
    }

    @Override
    public void run(){
        try {
            //TODO mettere controllo su keep alive client
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message = bufferedReader.readLine();
            PlayerMessage playerMessage = gson.fromJson(message, PlayerMessage.class);
            receive(playerMessage);

        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE,e.toString());
        }

    }

    public synchronized void send(PlayerMessage playerMessage){
        try {
            OutputStreamWriter output = new OutputStreamWriter(clientSocket.getOutputStream());
            String jsonInString = gson.toJson(playerMessage);
            output.write(jsonInString);
            output.flush();
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, e.toString());
        }
    }


    public synchronized void receive(PlayerMessage playerMessage) {

        obs.notify(playerMessage);
    }
}
