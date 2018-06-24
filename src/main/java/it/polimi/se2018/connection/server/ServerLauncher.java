package it.polimi.se2018.connection.server;



public class ServerLauncher {



    public ServerLauncher (){}


    public static void main(String args[]){

        SocketTypeServer socketTypeServer = new SocketTypeServer();
        RMITypeServer rmiTypeServer = new RMITypeServer();
        ServerManager serverManager = new ServerManager(socketTypeServer,rmiTypeServer);
        ServerLauncher serverLauncher = new ServerLauncher();

    }


}
