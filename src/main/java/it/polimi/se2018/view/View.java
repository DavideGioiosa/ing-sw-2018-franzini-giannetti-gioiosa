package it.polimi.se2018.view;


import it.polimi.se2018.model.MoveMessage;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.utils.Observer;

import java.util.HashMap;
import java.util.Map;

/**
 * View class, used for interaction with the player
 * @author Silvia Franzini
 */
public class View implements Observer<PlayerMessage> {
protected InputStrategy inputStrategy;
protected ConnectionStrategy connectionStrategy;
private MoveMessage moveMessage;
private static final Map<Integer, String> errors= new HashMap<Integer, String>();
    static{
            errors.put(1, "SchemaCardNulla");
            //esempio di inserimento
        //TODO gestire inserimento da file
    }

    /**
     * Builder method of the class
     */
    public View(){}

    /**
     * Setter method for the chosen graphic (CLI/GUI)
     * @param inputStrategy the type chosen
     */
    public void setInputStrategy(InputStrategy inputStrategy) {
        this.inputStrategy = inputStrategy;
        inputStrategy.getInput();
    }

    /**
     * Setter method for the chosen connection (RMI/Socket)
     * @param connectionStrategy the type chosen
     */
    public void setConnectionStrategy(ConnectionStrategy connectionStrategy) {
        this.connectionStrategy = connectionStrategy;
    }

    /**
     * Update method for the implementation of the Observer pattern
     * @param playerMessage type of message received
     */
    public void update(PlayerMessage playerMessage){
        connectionStrategy.send(playerMessage);
    }

    /**
     * Method used to identify the type of message received by the Server
     * @param playerMessage message received
     */
    public void receive(PlayerMessage playerMessage){
        switch (playerMessage.getId()){
            //TODO definire metodi per gestione mosse
            case '1': break;
            case '2': break;
            case '3': updateTable(playerMessage.getMoveMessage()); break;
            default: throw new IllegalArgumentException("ERROR: Message not set");
        }
    }

    /**
     * Method used to update the table status in the local Model
     * @param moveMessage class containing the several elements of the table
     */
    public void updateTable(MoveMessage moveMessage){
        //TODO da definire chi chiama richiama il model
        this.moveMessage = moveMessage;
    }

    /**
     * Method used to understand the error notify received
     */
    public void reportError(){//TODO inserire id come parametro
        //TODO necessario definire tipologie errore

    }
}
