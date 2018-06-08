package it.polimi.se2018.connection.server;


import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.RemoteView;


//link tra connessione e gioco, messaggi server e controller
public class ServerManager implements Observer<PlayerMessage> {
//osserverà sia RMITypeServer che SocketTypeServer
    private SocketTypeServer serverSocket;
    private RMITypeServer serverRMI;

    //gestirà salvataggio utenti?

    public ServerManager (RemoteView remoteView, SocketTypeServer socketTypeServer, RMITypeServer rmiTypeServer){
        this.serverSocket = socketTypeServer;
        this.serverRMI = rmiTypeServer;
    }

    //nofica messaggi a chi aspetta update, GameStarter e
    //TODO: Come si differenziano i notify diversi se sono entrambi nell'elenco ?


    //TODO: metodo get se user a cui devo inviare è RMI o socket
     //private TypeOfConnection getTypeOfConnection (){ }


    //uno per playerChoose e playerMove
    //uno per gli invii delle connessione
    public void sendMessage(PlayerMessage message) {
        //check message
    }

    @Override
    public void update(PlayerMessage message) {



    }

    //TODO: disconnessione
}
