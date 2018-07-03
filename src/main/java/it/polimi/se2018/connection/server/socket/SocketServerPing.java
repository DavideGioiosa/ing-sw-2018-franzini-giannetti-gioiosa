package it.polimi.se2018.connection.server.socket;

import java.util.Timer;
import java.util.TimerTask;

public class SocketServerPing  extends TimerTask {

    private ClientListener clientListener;
    SocketServerPing(ClientListener clientListener){
        this.clientListener = clientListener;
    }

    @Override
    public void run() {
        clientListener.ping();
        Timer timer = new Timer();
        CheckTimeout checkTimeout = new CheckTimeout(clientListener);
        timer.schedule(checkTimeout, (long)5*1000);
    }
}
