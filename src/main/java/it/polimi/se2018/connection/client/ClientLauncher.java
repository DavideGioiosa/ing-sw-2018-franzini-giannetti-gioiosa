package it.polimi.se2018.connection.client;

import it.polimi.se2018.connection.client.rmi.RMITypeClient;
import it.polimi.se2018.connection.client.socket.SocketTypeClient;
import it.polimi.se2018.controller.client.ClientController;
import it.polimi.se2018.view.View;



public class ClientLauncher {

    public static void main(String[] args){

        View viewSocket = new View();
        Client clientSocket = new Client(new SocketTypeClient("192.168.139.101", 1111), viewSocket);

        ClientController clientControllerSocket = new ClientController(clientSocket, viewSocket);

        viewSocket.getInputStrategy().getPlayerSetupper().addObserver(clientControllerSocket);
        viewSocket.getInputStrategy().getSyntaxController().addObserver(clientControllerSocket);
        clientSocket.connect();


        /*View viewRMI = new View();
        Client clientRMI = new Client(new RMITypeClient(), viewRMI);

        ClientController clientControllerRMI = new ClientController(clientRMI, viewRMI);

        viewRMI.getInputStrategy().getPlayerSetupper().addObserver(clientControllerRMI);
        viewRMI.getInputStrategy().getSyntaxController().addObserver(clientControllerRMI);
        clientRMI.connect();*/


        /*View viewSocket = new View();
        Client clientSocket = new Client(new SocketTypeClient("localhost", 1111), viewSocket);

        ClientController clientControllerSocket = new ClientController(clientSocket, viewSocket);

        viewSocket.getInputStrategy().getPlayerSetupper().addObserver(clientControllerSocket);
        viewSocket.getInputStrategy().getSyntaxController().addObserver(clientControllerSocket);
        clientSocket.connect();*/

    }

}
