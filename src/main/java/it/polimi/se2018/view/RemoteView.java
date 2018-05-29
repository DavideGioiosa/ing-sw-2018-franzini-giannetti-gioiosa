package it.polimi.se2018.view;

import it.polimi.se2018.model.MoveMessage;
import it.polimi.se2018.model.PlayerChoice;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.model.player.User;

import java.util.List;

/**
 * Remote view class (contained in the Server)
 * @author Silvia Franzini
 */
public class RemoteView extends View {

 private List<User> userList;  //necessario per sapere parametri connessione
//private SocketTypeServer socketTypeServer;
//private RMITypeServer rmiTypeServer;
//TODO inserire server con cui far comunicare la view

    /**
     * Builder method of the class
     */
    public RemoteView(List<User> userList){

        this.userList=userList;

    }

    /**
     * Getter method for the list of users
     * @return the list of users
     */
    public List<User> getUserList() {
        return userList;
    }


    public void getServer(PlayerMessage playerMessage, Player player){

        //gestione errore player inesistente?
        for(User user : userList){
            if(user.getPlayer().equals(player))
            {
                /*switch (user.getTypeOfConnection()){
                    case RMI: rmiTypeServer.send(playerMessage);
                    case SOCKET: socketTypeServer.send(playerMessage);
                }*/
            }
        }
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


}
