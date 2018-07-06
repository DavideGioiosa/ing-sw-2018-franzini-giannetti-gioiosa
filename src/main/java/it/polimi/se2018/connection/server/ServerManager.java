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
    private static final int MAXUSER = 3;



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

                senderTimer.schedule(timerDelayer, (long)30*1000);
            }
        }
        else{
            serverSocket.send(playerMessage);
            serverRMI.send(playerMessage);

            senderTimer = new Timer();
            TimerDelayer timerDelayer = new TimerDelayer(playerMessage, this);
            senderTimer.schedule(timerDelayer, (long)30*1000);
        }
    }

     void createGame(){
         System.out.println("creo gioco");
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
                if(userList.size()== 1){
                    userSetupTimer = new Timer();
                    usersDelayer = new UsersDelayer(this);
                    userSetupTimer.schedule(usersDelayer, (long)90*1000);
                }else if(userList.size() == MAXUSER && userSetupTimer != null ){
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
                 }else {
                     println("disconnesso user: "+ us.getNickname());
                     disconnectedUserList.add(us);
                     us.getPlayer().setConnectionStatus(false);
                     gameCreator.modifyStatus();
                 }


             }
         }
    }

    private void reconnection(PlayerMessage playerMessage){

        boolean found = false;
        println("Riconnessione");
        println("ricevuto: " + playerMessage.getUser().getNickname());

        Iterator<User> userIterator = disconnectedUserList.iterator();

        while(userIterator.hasNext()){
            User user = userIterator.next();
            println(user.getNickname());
            if(user.getNickname().equals(playerMessage.getUser().getNickname())){
                userIterator.remove();
                gameCreator.modifyStatus();
                addUser(playerMessage.getUser());
                found = true;
                println("trovato");
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
            if(playerMessage.getId().equals(PlayerMessageTypeEnum.USER) && userList.size()< MAXUSER){
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
