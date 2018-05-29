package it.polimi.se2018.connection.client;

import com.google.gson.Gson;
import it.polimi.se2018.model.PlayerMessage;

import java.io.*;
import java.net.Socket;



public class NetworkHandler extends Thread{

    private Socket socket;
    private BufferedReader bufferedReader;
    private SocketTypeClient socketTypeClient;
    private OutputStreamWriter out;
    private boolean quit;


    public NetworkHandler(String host, int port, SocketTypeClient socketTypeClient){

        quit=false;
        this.socketTypeClient=socketTypeClient;
        try {
            socket = new Socket(host, port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


        } catch (IOException e) {
            e.printStackTrace();
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
                socketTypeClient.receive(message);

            }catch (IOException e){
                e.printStackTrace();
            }
        }

        try{
            this.closeConnection();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public synchronized void send(String playerMessage){
        try {
            out = new OutputStreamWriter(socket.getOutputStream());
            out.write(playerMessage);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void closeConnection() throws IOException{
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setClosure();
        Gson gson = new Gson();
        String jsonInString = gson.toJson(playerMessage);
        try {
            send(jsonInString);
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
