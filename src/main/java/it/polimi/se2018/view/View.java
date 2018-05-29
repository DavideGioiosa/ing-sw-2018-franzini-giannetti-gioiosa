package it.polimi.se2018.view;


import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.MoveMessage;
import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.cards.SchemaCard;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * View class, used for interaction with the player
 * @author Silvia Franzini
 */
public class View  {
protected InputStrategy inputStrategy;
protected ConnectionStrategy connectionStrategy;
private MoveMessage moveMessage;
private MessageLoader messageLoader;


    /**
     * Builder method of the class
     */
    public View() {
        moveMessage = null;
        try{
            messageLoader = new MessageLoader();

        }catch(Exception e){
            //TODO Gestire eccezione
        }
    }

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
     * Method used to identify the type of message received by the Server
     * @param playerMessage message received
     */
    public void receive(PlayerMessage playerMessage){
        switch (playerMessage.getId()){
            case '1': updateSchema(playerMessage.getPlayerMove());break;
            case '2': inputStrategy.showChoice(playerMessage.getPlayerChoice()); break;
            case '3': updateTable(playerMessage.getMoveMessage()); break;
            default: throw new IllegalArgumentException("ERROR: Message not set");
        }
    }

    /**
     * Method used to update the status of the table when a player haa played and only used a PICK action
     * @param playerMove class containing all the parameters of the move
     */
    public void updateSchema(PlayerMove playerMove){
        SchemaCard schemaCard = playerMove.getPlayer().getSchemaCard();
        Die die = moveMessage.getBoardDice().takeDice(playerMove.getDiceBoardIndex());
        schemaCard.setCell(playerMove.getDiceSchemaWhereToLeave().get(0), die);
    }

    /**
     * Method used to update the table status in the local Model
     * @param moveMessage class containing the several elements of the table
     */
    public void updateTable(MoveMessage moveMessage){

        this.moveMessage = moveMessage;
    }

    /**
     * Method used to understand the error notify received
     */
    public void reportError(){//TODO inserire id come parametro
        //TODO necessario definire tipologie errore

    }

}
