package it.polimi.se2018.connection.server;


import it.polimi.se2018.controller.GameCreator;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.RemoteView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


//link tra connessione e gioco, messaggi server e controller
public class ServerManager implements Observer<PlayerMessage> {
//osserva sia RMITypeServer che SocketTypeServer
    private GameCreator gameCreator;
    private SocketTypeServer serverSocket;
    private RMITypeServer serverRMI;
    private List<User> userList;
    private List<User> disconnectedUserList;
    private Timer userSetupTimer;
    private Timer senderTimer;
    private TimerDelayer timerDelayer;
    private UsersDelayer usersDelayer;


    public ServerManager (SocketTypeServer socketTypeServer, RMITypeServer rmiTypeServer){
        this.serverSocket = socketTypeServer;
        this.serverRMI = rmiTypeServer;
        disconnectedUserList = new ArrayList<>();
        userList = new ArrayList<>();
        userSetupTimer = new Timer();
        senderTimer = new Timer();
        gameCreator = null;
    }

    public void sendMessage(PlayerMessage playerMessage) {

        if(timerDelayer!= null){
            if (timerDelayer.isStarted() && playerMessage.getId().equals(PlayerMessageTypeEnum.ERROR)) {
                serverSocket.send(playerMessage);
                serverRMI.send(playerMessage);
            } else{
                senderTimer.cancel();
                senderTimer.purge();
                serverSocket.send(playerMessage);
                serverRMI.send(playerMessage);

                timerDelayer = new TimerDelayer(playerMessage, serverSocket, serverRMI);
                senderTimer.schedule(timerDelayer, 90*1000);
            }
        }
        else{
            serverSocket.send(playerMessage);
            serverRMI.send(playerMessage);

            timerDelayer = new TimerDelayer(playerMessage, serverSocket, serverRMI);
            senderTimer.schedule(timerDelayer, 90*1000);
        }
    }

    public void createGame(){

        userSetupTimer.cancel();
        userSetupTimer.purge();
        RemoteView remoteView = new RemoteView();
        gameCreator = new GameCreator(userList,remoteView);
    }


    private void addUser(User user){
        userList.add(user);
        if(userList.size()==2){
            usersDelayer = new UsersDelayer(this);
            userSetupTimer.schedule(usersDelayer, 90*1000);
        }else if(userList.size()==4 && usersDelayer.isStarted()){
            createGame();
        }
    }

    public void removeUser(User user){
        disconnectedUserList.add(user);
        userList.remove(user);
        if(userList.size()<2 && usersDelayer.isStarted()){
            userSetupTimer.cancel();
            userSetupTimer.purge();
        }
    }

    @Override
    public void update(PlayerMessage playerMessage) {
        if(playerMessage.getId().equals(PlayerMessageTypeEnum.USER)){
            addUser(playerMessage.getUser());
        }else gameCreator.receiveFromClient(playerMessage);
    }

    //TODO: disconnessione
}
