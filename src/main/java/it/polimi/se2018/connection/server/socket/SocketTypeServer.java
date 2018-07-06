package it.polimi.se2018.connection.server.socket;
import static  it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMessageTypeEnum;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

import java.util.*;

/**
 * Socket sever's class
 * @author Silvia Franzini
 */
public class SocketTypeServer implements Observer<PlayerMessage> {

    /**
     * List of client's unique codes
     */
    private List<String> codeList;
    /**
     * Reference to client gatherer
     */
    private ClientGatherer clientGatherer;
    /**
     * Map of connected clients
     */
    private HashMap<String, ClientListener> clientListenerList;
    /**
     * Observable object for callbacks
     */
    private Observable<PlayerMessage> obs;
    /**
     * List of disconnected users
     */
    private List<String> disconnectedCodes;
    //private static int port = 1111; verrà preso da file config

    /**
     * Builder method of the class
     * @param port host's port
     */
    public SocketTypeServer(int port){
        disconnectedCodes = new ArrayList<>();
        codeList = new ArrayList<>();
        clientListenerList = new HashMap<>();
        obs = new Observable<>();
        clientGatherer = new ClientGatherer(this, port);
        clientGatherer.start();
        println("ServerSocket acceso");
    }

    /**
     * Method to add observer to implement callbacks
     * @param observer observe class
     */
    public void addObserver(Observer<PlayerMessage> observer){
        obs.addObserver(observer);
    }

    /**
     * Getter method for observable
     * @return observable
     */
    public Observable<PlayerMessage> getObs() {
        return obs;
    }

    /**
     * Getter method for code list
     * @return list of unique codes
     */
    List<String> getCodeList() {
        return codeList;
    }

    /**
     * Method used to add codes to the list
     * @param code new code
     */
    void addCode(String code){
        this.codeList.add(code);
    }

    /**
     * Method used to add a new client
     * @param code user's code
     * @param clientListener user's listener
     */
    void addClient(String code, ClientListener clientListener){
        this.clientListenerList.put(code,clientListener);
    }

    /**
     * Closure method
     */
    public void shutdown(){

        if(clientListenerList != null){
            Iterator<Map.Entry<String, ClientListener>> iterator = clientListenerList.entrySet().iterator();

            for(Map.Entry<String, ClientListener> code: clientListenerList.entrySet()){
                code.getValue().setQuit();
            }

            while(iterator.hasNext()){
                iterator.next();
                iterator.remove();
            }

        }

    }

    /**
     * Send method to communicate with clients
     * @param playerMessage message that has to be send
     */
    public void send (PlayerMessage playerMessage){
        if(!clientListenerList.isEmpty()){
            if(playerMessage.getUser() == null){
                for(ClientListener clientListener : clientListenerList.values()){
                    clientListener.send(playerMessage);
                }
            }else {
                if(clientListenerList.containsKey(playerMessage.getUser().getUniqueCode()))
                {
                    ClientListener clientListener = clientListenerList.get(playerMessage.getUser().getUniqueCode());
                    clientListener.send(playerMessage);
                }
            }
        }

    }

    /**
     * Update method used for callbacks, to receive a message
     * @param playerMessage message received
     */
    @Override
    public synchronized void update(PlayerMessage playerMessage) {

        if(playerMessage.getId().equals(PlayerMessageTypeEnum.DISCONNECTED) && !disconnectedCodes.contains(playerMessage.getUser().getUniqueCode())){
            disconnectedCodes.add(playerMessage.getUser().getUniqueCode());
            clientListenerList.get(playerMessage.getUser().getUniqueCode()).setQuit();
            clientListenerList.remove(playerMessage.getUser().getUniqueCode());
        }
        obs.notify(playerMessage);
    }
}
