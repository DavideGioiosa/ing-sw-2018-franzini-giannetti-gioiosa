package it.polimi.se2018.connection.server;



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
