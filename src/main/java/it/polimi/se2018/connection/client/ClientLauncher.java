package it.polimi.se2018.connection.client;

import it.polimi.se2018.controller.client.ClientController;
import it.polimi.se2018.view.View;



public class ClientLauncher {


    public static void main(String[] args){

        View viewSocket = new View();
        Client clientSocket = new Client(new SocketTypeClient("localhost", 1111), viewSocket);

        ClientController clientControllerSocket = new ClientController(clientSocket, viewSocket);
        viewSocket.addObserver(clientControllerSocket);
        clientSocket.connect();


        View viewRMI = new View();
        ClientStrategy clientStrategy = new RMITypeClient();
        Client clientRMI = new Client(clientStrategy, viewRMI);

        ClientController clientControllerRMI = new ClientController(clientRMI, viewRMI);
        viewRMI.addObserver(clientControllerRMI);
        clientRMI.connect();


    }

}
