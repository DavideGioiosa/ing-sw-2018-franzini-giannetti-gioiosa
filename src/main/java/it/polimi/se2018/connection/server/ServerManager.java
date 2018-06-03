package it.polimi.se2018.connection.server;


import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.TypeOfConnection;


//link tra connessione e gioco, messaggi server e controller
public class ServerManager extends it.polimi.se2018.utils.Observable {

    private SocketTypeServer serverSocket;
    private RMITypeServer serverRMI;

    public ServerManager (){
        this.serverSocket = new SocketTypeServer();
        this.serverRMI = new RMITypeServer();
    }

    //nofica messaggi a chi aspetta update, GameStarter e
    //TODO: Come si differenziano i notify diversi se sono entrambi nell'elenco ?
    public void notifyMessage (PlayerMessage playerMessage){
        switch (playerMessage.getId()){
            case 1:
                notify(playerMessage.getPlayerChoice());
                break;

            case 2:
                notify(playerMessage.getPlayerMove());
                break;
        }
    }

    //TODO: metodo get se user a cui devo inviare è RMI o socket
     //private TypeOfConnection getTypeOfConnection (){ }


    //uno per playerChoose e playerMove
    //uno per gli invii delle connessione
    public void sendMessage(PlayerMessage message) {
        //check message
    }

    //TODO: disconnessione
}
