package it.polimi.se2018.connection.client.socket;

import java.util.Timer;
import java.util.TimerTask;

public class SocketClientPing  extends TimerTask {

    private NetworkHandler networkHandler;
    SocketClientPing(NetworkHandler networkHandler){
        this.networkHandler = networkHandler;
    }

    @Override
    public void run() {
        networkHandler.ping();
        Timer timer = new Timer();
        ClientTimeout clientTimeout  = new ClientTimeout(networkHandler);
        timer.schedule(clientTimeout, (long)7*1000);
    }
}
