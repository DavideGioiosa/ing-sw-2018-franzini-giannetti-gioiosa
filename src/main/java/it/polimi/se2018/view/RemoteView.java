package it.polimi.se2018.view;

import it.polimi.se2018.connection.server.RMITypeServer;
import it.polimi.se2018.connection.server.ServerManager;
import it.polimi.se2018.connection.server.SocketTypeServer;
import it.polimi.se2018.model.MoveMessage;
import it.polimi.se2018.model.PlayerChoice;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observable;

import java.util.List;

/**
 * Remote view class (contained in the Server)
 * @author Silvia Franzini
 */
public class RemoteView extends Observable<PlayerMessage> {

 private List<User> userList;  //necessario per sapere parametri connessione
 private ServerManager serverManager;
//TODO inserire server con cui far comunicare la view



    private class MessageUpdater extends Observable<String> {



    }

    /**
     * Builder method of the class
     */
    public RemoteView(){

    }

    /**
     * Getter method for the list of users
     * @return the list of users
     */
    public List<User> getUserList() {
        return userList;
    }

    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    /**
     * Method used to send a choice message to the client specified in the message
     * @param playerChoice message
     */
    public void sendChoice (PlayerChoice playerChoice){

    }

    /**
     * Method used to send a move message to the client specified in the message
     * @param playerMove message
     */
    public void sendMove(PlayerMove playerMove){

    }

    /**
     * Method used to send updates of the complete game table to a client specified(need to add the client)
     * @param moveMessage message
     */
    public void sendTable(MoveMessage moveMessage){

    }

    public void reportError(){

    }
    public void receiveMessage(PlayerMessage playerMessage){
        //controllo su variabile
    }

}
