package it.polimi.se2018.connection.server.socket;

import java.util.TimerTask;

public class CheckTimeout extends TimerTask {

    private ClientListener clientListener;
    CheckTimeout(ClientListener clientListener){
        this.clientListener = clientListener;
    }
    @Override
    public void run() {

        if(clientListener.isPing()){
            clientListener.setPing(false);
        }else clientListener.handleDisconnection();

    }
}
