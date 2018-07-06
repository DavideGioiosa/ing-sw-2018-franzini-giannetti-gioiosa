package it.polimi.se2018.connection.client;
import static it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;
import it.polimi.se2018.connection.client.rmi.RMITypeClient;
import it.polimi.se2018.connection.client.socket.SocketTypeClient;
import it.polimi.se2018.controller.client.ClientController;
import it.polimi.se2018.view.View;

import java.util.Scanner;


public class ClientLauncher {

    private static void createRMI(String host, int port){
        //host "//192.168.139.101:1099/MyServer"
        View viewRMI = new View();
        Client clientRMI = new Client(new RMITypeClient(host, port), viewRMI);

        ClientController clientControllerRMI = new ClientController(clientRMI, viewRMI);

        viewRMI.getInputStrategy().getPlayerSetupper().addObserver(clientControllerRMI);
        viewRMI.getInputStrategy().getSyntaxController().addObserver(clientControllerRMI);
        clientRMI.connect();

    }

    private static void createSocket(String host, int port){

        View viewSocket = new View();
        Client clientSocket = new Client(new SocketTypeClient(host, port), viewSocket);

        ClientController clientControllerSocket = new ClientController(clientSocket, viewSocket);

        viewSocket.getInputStrategy().getPlayerSetupper().addObserver(clientControllerSocket);
        viewSocket.getInputStrategy().getSyntaxController().addObserver(clientControllerSocket);
        clientSocket.connect();
    }

    public static void main(String[] args){


        String host;
        int port;
        println("Scegli il tipo di connessione:[RMI/Socket]");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        switch (input){
            case "rmi":
                println("Inserire indirizzo host: ");
                host = scanner.nextLine();

                println("Inserire porta host: ");
                port = scanner.nextInt();

                createRMI(host, port);

                break;
            case "socket":
                println("Inserire indirizzo host: ");
                host = scanner.nextLine();

                println("Inserire porta host: ");
                port = scanner.nextInt();

                createSocket(host, port);
                break;
            default:
                View viewSocket = new View();
                Client clientSocket = new Client(new SocketTypeClient("localhost", 1111), viewSocket);

                ClientController clientControllerSocket = new ClientController(clientSocket, viewSocket);

                viewSocket.getInputStrategy().getPlayerSetupper().addObserver(clientControllerSocket);
                viewSocket.getInputStrategy().getSyntaxController().addObserver(clientControllerSocket);
                clientSocket.connect();
                break;
        }

    }

}
