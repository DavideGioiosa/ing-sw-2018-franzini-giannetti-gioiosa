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
    private boolean setUp;


    public ServerManager (SocketTypeServer socketTypeServer, RMITypeServer rmiTypeServer){
        setUp = true;
        this.serverSocket = socketTypeServer;
        this.serverRMI = rmiTypeServer;
        disconnectedUserList = new ArrayList<>();
        userList = new ArrayList<>();


        serverSocket.addObserver(this);
        serverRMI.addObserver(this);

        gameCreator = null;
    }

    public void sendMessage(PlayerMessage playerMessage) {

        if(timerDelayer!= null){
            if (timerDelayer.isStarted() && playerMessage.getId().equals(PlayerMessageTypeEnum.ERROR)) {
                serverSocket.send(playerMessage);
                serverRMI.send(playerMessage);
            } else{
                senderTimer.cancel();
                serverSocket.send(playerMessage);
                serverRMI.send(playerMessage);

                timerDelayer = new TimerDelayer(playerMessage, this);
                senderTimer = new Timer();

                senderTimer.schedule(timerDelayer, (long)90*1000);
            }
        }
        else{
            serverSocket.send(playerMessage);
            serverRMI.send(playerMessage);

            senderTimer = new Timer();
            timerDelayer = new TimerDelayer(playerMessage, this);
            senderTimer.schedule(timerDelayer, (long)90*1000);
        }
    }

     void createGame(){

         setUp = false;
         userSetupTimer.cancel();
         userSetupTimer.purge();
         userSetupTimer = null;
         usersDelayer.cancel();
         RemoteView remoteView = new RemoteView(this);
         gameCreator = new GameCreator(userList,remoteView);
    }


    private void addUser(User user){

        userList.add(user);
        if(gameCreator == null){
            if(userList.size()== 2){
                userSetupTimer = new Timer();
                usersDelayer = new UsersDelayer(this);
                userSetupTimer.schedule(usersDelayer, (long)90*1000);
            }else if(userList.size() == 4 && userSetupTimer != null){
                createGame();
            }
        }

    }

     private void removeUser(User user){

         userList.remove(user);
         if(gameCreator == null){
             if(setUp && userList.size() < 2 && userSetupTimer!=null){
                 userSetupTimer.cancel();
                 userSetupTimer.purge();
                 userSetupTimer = null;
             }
         }else disconnectedUserList.add(user);

    }

    private void reconnection(PlayerMessage playerMessage){

        for(User user : disconnectedUserList){
            if(user.getNickname().equals(playerMessage.getUser().getNickname()) && user.getUniqueCode().equals(playerMessage.getUser().getUniqueCode())){
                disconnectedUserList.remove(user);
                addUser(playerMessage.getUser());

            }else {
                PlayerMessage playerMessage1 = new PlayerMessage();
                playerMessage1.setError(102);
                sendMessage(playerMessage1);
            }
        }
    }


    @Override
    public void update(PlayerMessage playerMessage) {

        if(gameCreator == null){
            if(playerMessage.getId().equals(PlayerMessageTypeEnum.USER)){
                addUser(playerMessage.getUser());
            }else if(playerMessage.getId().equals(PlayerMessageTypeEnum.DISCONNECTED)){
                removeUser(playerMessage.getUser());
            }
        }else{
            if(playerMessage.getId().equals(PlayerMessageTypeEnum.USER)){
                reconnection(playerMessage);
            } else if(playerMessage.getId().equals(PlayerMessageTypeEnum.DISCONNECTED)){
                removeUser(playerMessage.getUser());
            } else gameCreator.receiveFromClient(playerMessage);
        }

    }

    public List<User> getUserList(){
        return userList;
    }

}
