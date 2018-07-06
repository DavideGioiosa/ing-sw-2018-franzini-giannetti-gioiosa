package it.polimi.se2018.connection.server;
import static it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;


import it.polimi.se2018.connection.server.rmi.RMITypeServer;
import it.polimi.se2018.connection.server.socket.SocketTypeServer;

import java.util.Scanner;

public class ServerLauncher {



    public ServerLauncher (){
        //da rivedere
    }

    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        int portSocket = 0;
        while(portSocket < 1){
            println("Inserisci porta server Socket: ");
            portSocket = input.nextInt();
        }
        SocketTypeServer socketTypeServer = new SocketTypeServer(portSocket);
        int portRmi = 0;
        while(portRmi < 1){
            println("Inserisci porta server RMI: ");
            portRmi = input.nextInt();
        }
        RMITypeServer rmiTypeServer = new RMITypeServer(portRmi);
        ServerManager serverManager = new ServerManager(socketTypeServer,rmiTypeServer);
        serverManager.operate();


    }


}
