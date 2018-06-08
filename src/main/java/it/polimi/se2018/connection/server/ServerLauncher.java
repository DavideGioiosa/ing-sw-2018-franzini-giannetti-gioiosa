package it.polimi.se2018.connection.server;


import it.polimi.se2018.controller.GameCreator;
import it.polimi.se2018.controller.GameStarter;
import it.polimi.se2018.view.RemoteView;


public class ServerLauncher {

    private ServerManager serverManager;
    private SocketTypeServer socketTypeServer;
    private RMITypeServer rmiTypeServer;
    private RemoteView remoteView;


    public ServerLauncher (){

        remoteView = new RemoteView();
        socketTypeServer = new SocketTypeServer();
        rmiTypeServer = new RMITypeServer();
        serverManager = new ServerManager(remoteView, socketTypeServer,rmiTypeServer);


    }

    //TODO: main

    public static void main(String args[]){


    }


}
