package it.polimi.se2018.connection.server.socket;

import java.util.TimerTask;

/**
 * Socket server's class for checking client's connection status
 * @author Silvia Franzini
 */
public class CheckTimeout extends TimerTask {

    /**
     * Reference to object that handles connection with a client
     */
    private ClientListener clientListener;

    /**
     * Builder method of the class
     * @param clientListener objects that handles connection with the client
     */
    CheckTimeout(ClientListener clientListener){
        this.clientListener = clientListener;
    }

    /**
     * Thread's run method that checks if the client is still connected
     */
    @Override
    public void run() {

        if(clientListener.isPing()){
            clientListener.setPing(false);
            System.out.println("client mi ha risposto");
        }else {
            System.out.println("mancato ping socket");
            clientListener.handleDisconnection();
        }

    }
}
