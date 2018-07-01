package it.polimi.se2018.view;

import it.polimi.se2018.connection.server.ServerManager;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.player.Player;
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

    /**
    * Builder method of the class
    */
    public RemoteView(ServerManager serverManager){
        this.serverManager = serverManager;
    }

    /**
    * Getter method for the list of users
    * @return the list of users
    */
    public List<User> getUserList() {
      return userList;
    }

    /**
    * Method used to send a choice message to the client specified in the message
    * @param playerChoice message
    */
    public void sendChoice (PlayerChoice playerChoice){
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setUser(playerChoice.getUser());
        playerMessage.setChoice(playerChoice);
        serverManager.sendMessage(playerMessage);
    }

    public void send(PlayerMessage playerMessage){
        serverManager.sendMessage(playerMessage);
    }

    public void sendWinner(GameBoard gameBoard){

        MoveMessage finale = new MoveMessage(gameBoard.getPlayerList(),gameBoard.getBoardDice(), gameBoard.getCardOnBoard(), gameBoard.getTrackBoardDice());
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setMessage(finale);
        playerMessage.setId(PlayerMessageTypeEnum.WINNER);
        playerMessage.setUser(null);
        serverManager.sendMessage(playerMessage);
        serverManager.shutdown();
    }

    public void sendDefaultChoices(List<PlayerChoice> playerChoiceList){
        serverManager.sendDefaultChoices(playerChoiceList);
    }

    /**
    * Method used to send a move message to the client specified in the message
    * @param playerMove message
    */
    public void sendMove(PlayerMove playerMove){
        //send move to remoteView
    }

    /**
    * Method used to send updates of the complete game table to a client specified(need to add the client)
    * @param moveMessage message
    */
    public void sendTable(MoveMessage moveMessage){
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setMessage(moveMessage);
        serverManager.sendMessage(playerMessage);
    }

    public void reportError(int idError, String nickname){
        PlayerMessage playerMessage = new PlayerMessage();
        setRecipient(nickname, playerMessage, serverManager.getUserList());
        playerMessage.setError(idError);
        serverManager.sendMessage(playerMessage);
    }

    public void receiveMessage(PlayerMessage playerMessage){
     //controllo su variabile
    }

    public void isYourTurn(Player player){
        PlayerMessage playerMessage = new PlayerMessage();
        PlayerMove playerMove = new PlayerMove();
        playerMove.setPlayer(player);
        playerMessage.setCheckMove(playerMove);
        playerMessage.setId(PlayerMessageTypeEnum.YOUR_TURN);
        setRecipient(player.getNickname(), playerMessage, serverManager.getUserList());

        serverManager.sendMessage(playerMessage);
    }

    private void setRecipient(String nickname, PlayerMessage playerMessage, List<User> userList){
        playerMessage.setRecipient(null);
        for(User user: userList){
            if (user.getNickname() == nickname) playerMessage.setRecipient(user);
        }

    }
}
