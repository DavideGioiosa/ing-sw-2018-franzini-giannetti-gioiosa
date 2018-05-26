package it.polimi.se2018.view;


import it.polimi.se2018.model.MoveMessage;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.utils.Observer;

import java.util.HashMap;
import java.util.Map;

public class View implements Observer<PlayerMessage> {
protected InputStrategy inputStrategy;
protected ConnectionStrategy connectionStrategy;
private MoveMessage moveMessage;
private static final Map<Integer, String> errors= new HashMap<Integer, String>();
    static{
            errors.put(1, "SchemaCardNulla");
            //esempio di inserimento
    }

    public View(){

    }

    public void setInputStrategy(InputStrategy inputStrategy) {
        this.inputStrategy = inputStrategy;
        inputStrategy.getInput();
    }

    public void setConnectionStrategy(ConnectionStrategy connectionStrategy) {
        this.connectionStrategy = connectionStrategy;
    }

    public void update(PlayerMessage playerMessage){
        connectionStrategy.send(playerMessage);
    }


    public void receive(PlayerMessage playerMessage){
        switch (playerMessage.getId()){
            //definire metodi per gestione mosse
            case '1': break;
            case '2': break;
            case '3': updateTable(playerMessage.getMoveMessage()); break;
            default: throw new IllegalArgumentException("ERROR: Message not set");
        }
    }

    public void updateTable(MoveMessage moveMessage){
        //da definire chi chiama richiama il model
        this.moveMessage = moveMessage;
    }

    public void reportError(){//inserire id come parametro
        //necessario definire tipologie errore

    }
}
