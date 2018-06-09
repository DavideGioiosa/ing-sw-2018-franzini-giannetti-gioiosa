package it.polimi.se2018.connection.server;


import it.polimi.se2018.controller.GameCreator;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.RemoteView;


//link tra connessione e gioco, messaggi server e controller
public class ServerManager implements Observer<PlayerMessage> {
//osserva sia RMITypeServer che SocketTypeServer
    private GameCreator gameCreator;
    private SocketTypeServer serverSocket;
    private RMITypeServer serverRMI;


    public ServerManager (GameCreator gameCreator, SocketTypeServer socketTypeServer, RMITypeServer rmiTypeServer){
        this.serverSocket = socketTypeServer;
        this.serverRMI = rmiTypeServer;
        this.gameCreator = gameCreator;
    }

    public void sendMessage(PlayerMessage playerMessage) {
        serverSocket.send(playerMessage);
        serverRMI.send(playerMessage);
    }

    @Override
    public void update(PlayerMessage playerMessage) {
        gameCreator.update(playerMessage);
    }

    //TODO: disconnessione
}
