package it.polimi.se2018.connection.client.socket;


import java.util.TimerTask;

public class ClientTimeout extends TimerTask {

    private NetworkHandler networkHandler;
    ClientTimeout(NetworkHandler networkHandler){
        this.networkHandler = networkHandler;
    }
    @Override
    public void run() {

        if(networkHandler.isPing()){
            networkHandler.setPing(false);
        }else networkHandler.disconnectionHandler();

    }
}
