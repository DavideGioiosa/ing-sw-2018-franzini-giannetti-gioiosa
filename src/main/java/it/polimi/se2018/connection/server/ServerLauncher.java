package it.polimi.se2018.connection.server;


import it.polimi.se2018.connection.server.rmi.RMITypeServer;
import it.polimi.se2018.connection.server.socket.SocketTypeServer;

public class ServerLauncher {



    public ServerLauncher (){
        //da rivedere
    }

    public static void main(String[] args){

        SocketTypeServer socketTypeServer = new SocketTypeServer(1111);
        RMITypeServer rmiTypeServer = new RMITypeServer(1099);
        ServerManager serverManager = new ServerManager(socketTypeServer,rmiTypeServer);
        serverManager.operate();

    }


}
