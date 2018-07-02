package it.polimi.se2018.connection.server;


import it.polimi.se2018.connection.server.rmi.RMITypeServer;
import it.polimi.se2018.connection.server.socket.SocketTypeServer;
import it.polimi.se2018.controller.GameCreator;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.RemoteView;
import static  it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;
import java.util.ArrayList;
import java.util.Iterator;
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
    private UsersDelayer usersDelayer;
    private boolean setUp;



    public ServerManager (SocketTypeServer socketTypeServer, RMITypeServer rmiTypeServer){

        this.serverSocket = socketTypeServer;
        this.serverRMI = rmiTypeServer;
    }

    void operate(){
        setUp = true;
        disconnectedUserList = new ArrayList<>();
        userList = new ArrayList<>();
        gameCreator = null;

        serverSocket.addObserver(this);
        serverRMI.addObserver(this);
    }

    void defaultMove(){
        gameCreator.defaultMove();
    }

    public void sendMessage(PlayerMessage playerMessage) {

        if(senderTimer!= null){
            if (playerMessage.getId().equals(PlayerMessageTypeEnum.ERROR)) {
                serverSocket.send(playerMessage);
                serverRMI.send(playerMessage);
            } else{
                senderTimer.cancel();
                serverSocket.send(playerMessage);
                serverRMI.send(playerMessage);

                TimerDelayer timerDelayer = new TimerDelayer(playerMessage, this);
                senderTimer = new Timer();

                senderTimer.schedule(timerDelayer, (long)90*1000);
            }
        }
        else{
            serverSocket.send(playerMessage);
            serverRMI.send(playerMessage);

            senderTimer = new Timer();
            TimerDelayer timerDelayer = new TimerDelayer(playerMessage, this);
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

        boolean alreadyUsed = false;
        for(User addedUser: userList){
            if(addedUser.getNickname().equals(user.getNickname())){
                alreadyUsed = true;
            }
        }
        if(alreadyUsed){
            PlayerMessage playerMessage = new PlayerMessage();
            playerMessage.setUser(user);
            playerMessage.setError(450); //nickname già presente
            sendMessage(playerMessage);
        }else {
            userList.add(user);
            println("aggiunto user: "+ user.getNickname());
            if(gameCreator == null){
                if(userList.size()== 2){
                    userSetupTimer = new Timer();
                    usersDelayer = new UsersDelayer(this);
                    userSetupTimer.schedule(usersDelayer, (long)90*1000);
                }else if(userList.size() == 4 && userSetupTimer != null ){
                    createGame();
                }
            }
        }
    }

     private void removeUser(User user){

         Iterator<User> userIterator = userList.iterator();
         while(userIterator.hasNext()){
             User us = userIterator.next();
             if(us.getUniqueCode().equals(user.getUniqueCode())){
                 userIterator.remove();
                 if(gameCreator == null){
                     if(setUp && userList.size() < 2 && userSetupTimer!=null){
                         userSetupTimer.cancel();
                         userSetupTimer.purge();
                         userSetupTimer = null;
                     }
                 }else disconnectedUserList.add(user);
                 println("disconnesso user: "+ us.getNickname());
             }
         }
    }

    private void reconnection(PlayerMessage playerMessage){

        boolean found = false;
        for(Iterator<User> userIterator = disconnectedUserList.iterator(); userIterator.hasNext();){
            User user = userIterator.next();
            if(user.getUniqueCode().equals(playerMessage.getUser().getUniqueCode())){
                disconnectedUserList.remove(user);
                addUser(playerMessage.getUser());
                found = true;
            }
        }
        if(!found){
            PlayerMessage playerMessage1 = new PlayerMessage();
            playerMessage1.setError(102);
            sendMessage(playerMessage1);
        }

    }


    @Override
    public synchronized void update(PlayerMessage playerMessage) {

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

    public synchronized List<User> getUserList(){
        return userList;
    }

    public void sendDefaultChoices(List<PlayerChoice> playerChoiceList){
        for(User user : userList){
            for(PlayerChoice player : playerChoiceList){
                if(user.getNickname().equals(player.getUser().getNickname())){
                    PlayerMessage playerMessage = new PlayerMessage();
                    playerMessage.setChoice(player);
                    playerMessage.setId(PlayerMessageTypeEnum.DEFAULTCHOICE);
                    serverRMI.send(playerMessage);
                    serverSocket.send(playerMessage);
                }
            }
        }
    }


    public void shutdown(){
        serverRMI.shutdown();
        serverSocket.shutdown();
    }
}
