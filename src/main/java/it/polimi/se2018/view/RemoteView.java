package it.polimi.se2018.view;

import com.sun.xml.internal.bind.v2.TODO;
import it.polimi.se2018.model.MoveMessage;
import it.polimi.se2018.model.PlayerChoice;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.player.User;

import java.util.List;

/**
 * Remote view class (contained in the Server)
 * @author Silvia Franzini
 */
public class RemoteView extends View {

 private List<User> userList;  //TODO da rivedere se riferimento è necessario
//private SocketTypeServer socketTypeServer;
//private RMITypeServer rmiTypeServer;
//TODO inserire server con cui far comunicare la view

    /**
     * Builder method of the class
     */
    public RemoteView(){

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
