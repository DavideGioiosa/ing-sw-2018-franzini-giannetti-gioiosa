package it.polimi.se2018.connection.server.rmi;
import static  it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;

import it.polimi.se2018.connection.client.rmi.ClientRemoteInterface;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Server's class for implementation of remote interface's methods
 * @author Silvia Franzini
 */
public class ServerImplementation extends UnicastRemoteObject implements ServerRemoteInterface {

    /**
     * Map of all connected RMI's clients, searched by user's unique code
     */
    private HashMap<String,ClientRemoteInterface> clientList = new HashMap<>();
    /**
     * Map of all client's ping, searched by user's unique code
     */
    private HashMap<String, Timer> pingMap = new HashMap<>();
    /**
     * Server's disconnected client's list
     */
    private List<String> disconnected = new ArrayList<>();
    /**
     * RMI user's unique codes list
     */
    private List<String> codeList = new ArrayList<>();
    /**
     * Observable object to implements connection's callbacks
     */
    private transient Observable<PlayerMessage> obs = new Observable<>();

    /**
     * Builder method of the class
     * @param port server's port
     * @throws RemoteException if cannot open server on that specific port
     */
    ServerImplementation(int port) throws RemoteException{
        super(port);
    }

    /**
     * Method used to add observers to the class, to implement callbacks
     * @param observer obeserver of the class
     */
    public void addObserver(Observer<PlayerMessage> observer){
        obs.addObserver(observer);
    }

    /**
     * Getter method for observable object
     * @return the observable object
     */
    public Observable<PlayerMessage> getObs() {
        return obs;
    }

    /**
     * Method used to send messages to the client
     * @param playerMessage message that has to be send
     */
    void sendToClient(PlayerMessage playerMessage) {

        if(!clientList.isEmpty()){
            if(playerMessage.getUser()== null){
                for(ClientRemoteInterface clientRemoteInterface : clientList.values()){
                    try {
                        clientRemoteInterface.receiveFromServer(playerMessage);
                    } catch (RemoteException e) {
                        String remove = null;
                        for(String code : clientList.keySet()){
                            if(clientList.get(code).equals(clientRemoteInterface)){ //override equals
                                remove= code;
                            }
                        }
                        if(remove != null){disconnectionHandler(remove);}
                    }
                }

            }else{
                if(clientList.containsKey(playerMessage.getUser().getUniqueCode())){
                    ClientRemoteInterface clientRemoteInterface = clientList.get(playerMessage.getUser().getUniqueCode());
                    try {
                        clientRemoteInterface.receiveFromServer(playerMessage);
                    } catch (RemoteException e) {
                        disconnectionHandler(playerMessage.getUser().getUniqueCode());
                    }
                }

            }
        }

    }

    /**
     * Method invoked to add RMI clients to the client's list
     * @param client RMI client that has to be add
     * @throws RemoteException
     */
    @Override
    public void addClient(ClientRemoteInterface client) throws RemoteException{

        User user;
        String code;
        do{
            user = new User(TypeOfConnection.RMI);
            code = user.createUniqueCode();
        }while(codeList.contains(code));

        codeList.add(code);
        clientList.put(code, client);
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setUser(user);
        RMIServerPing rmiServerPing = new RMIServerPing(this, code, client);
        Timer lifeLineTimer = new Timer();
        lifeLineTimer.scheduleAtFixedRate(rmiServerPing, (long) 10*1000, (long)10*1000);
        pingMap.put(code, lifeLineTimer);
        println("trovato nuovo client");
        try {
            client.receiveFromServer(playerMessage);
        } catch (RemoteException e) {
            String string = null;
            for(Map.Entry<String, ClientRemoteInterface> entry : clientList.entrySet()){
                if(entry.getValue().equals(client)){
                     string = entry.getKey();
                }
            }
            if(string!= null){disconnectionHandler(string);}
        }
    }

    /**
     * Method used to handle a client's disconnection
     * @param code unique code of that specific user
     */
    void disconnectionHandler(String code){
        if(!disconnected.contains(code)){
            disconnected.add(code);
            clientList.remove(code);
            User user = new User(TypeOfConnection.RMI);
            user.setUniqueCode(code);
            PlayerMessage disconnect = new PlayerMessage();
            disconnect.setUser(user);
            disconnect.setId(PlayerMessageTypeEnum.DISCONNECTED);
            obs.notify(disconnect);
        }
        Timer timer = pingMap.get(code);
        if(timer != null){
            timer.cancel();
            timer.purge();
            pingMap.remove(code);
        }
    }

    /**
     * Method used to transmit received messages to upper-classes
     * @param playerMessage message received
     */
    public void transmit(PlayerMessage playerMessage){

        obs.notify(playerMessage);

    }

    /**
     * Method invoked by the client to send a message, starts a thread to handle the request
     * @param playerMessage message received
     * @throws RemoteException if connection has fallen
     */
    @Override
    public void receive(PlayerMessage playerMessage) throws RemoteException {

        if(playerMessage != null){
            new Thread(new RMIServerThread(playerMessage, this)).start();
        }

    }

    /**
     * Method used to detect client RMI disconnection
     * @throws RemoteException due to disconnection
     */
    @Override
    public void lifeLine() throws RemoteException {
        //detects disconnection by throwing RemoteException
    }

    /**
     * Closure method to stop execution
     */
    public void close(){
        for(Timer timer : pingMap.values()){
            timer.cancel();
            timer.purge();
        }
    }
}
