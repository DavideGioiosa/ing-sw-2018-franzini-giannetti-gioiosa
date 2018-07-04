package it.polimi.se2018.view;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.*;
import it.polimi.se2018.view.graphic.cli.CommandLineGraphic;
import it.polimi.se2018.view.graphic.cli.CommandLineInput;
import it.polimi.se2018.view.graphic.gui.GuiInput;
import it.polimi.se2018.view.graphic.gui.GuiOutput;

import static it.polimi.se2018.view.graphic.cli.CommandLinePrint.println;

/**
 * Used for interaction with the player
 * @author Silvia Franzini
 */
public class View extends Observable implements Observer<ClientModel> {

    protected InputStrategy inputStrategy;
    protected OutputStrategy outputStrategy;

    private ClientBoard clientBoard;

    /**
     * Constructor of the class
     */
    public View() {

  //      this.inputStrategy = new CommandLineInput(new SyntaxController(), new PlayerSetupper());
//        this.outputStrategy = new CommandLineGraphic();

        this.inputStrategy = new GuiInput(new SyntaxController(), new PlayerSetupper());
        this.outputStrategy = new GuiOutput();

    }

    /**
     * Method used to identify the type of message received by the Server
     * @param playerMessage message received
     */
    public void receive(PlayerMessage playerMessage){

        if (playerMessage == null){
            reportError(1001);
            return;
        }

        switch (playerMessage.getId()){
            case YOUR_TURN:
                inputStrategy.yourTurn(clientBoard, playerMessage.getPlayerMove());
                break;

            case USER:
                inputStrategy.choseNickname(playerMessage.getUser());

                break;

            case CHOICE:
                inputStrategy.makeChoice(playerMessage.getPlayerChoice());
                break;

            case ERROR:
                reportError(playerMessage.getIdError());
                break;

            case MESSAGE:
                showMessage(playerMessage.getIdError());
                break;

            case UPDATE:
                notify(playerMessage);
                break;

            case WINNER:
                for(Player player: playerMessage.getMoveMessage().getPlayerList()) println( player.getNickname() + " " + player.getScore());
                break;

            case DISCONNECTED:
                reconnection();
                break;

            default:
                reportError(1001);
        }

    }

    /**
     * Method used to understand the error notify received
     */
    public void reportError(int idError){
        inputStrategy.showError(idError);
    }

    /**
     * Displays a message on vide
     * @param message id of the message
     */
    public void showMessage(int message){
        inputStrategy.showMessage(message);
    }

    /**
     * Method used to update the table status in the local Model
     * @param clientModel class containing the several elements of the table
     */
    @Override
    public void update(ClientModel clientModel){
        this.clientBoard = clientModel.getClientBoard();
        outputStrategy.showGameBoard(clientBoard);
    }

    /**
     * Gets the object that manages input
     * @return Input manager
     */
    public InputStrategy getInputStrategy() {
        return inputStrategy;
    }

    /**
     * Gets the object that manages output
     * @return Output manager
     */
    public OutputStrategy getOutputStrategy() {
        return outputStrategy;
    }

    /**
     *
     */
    private void reconnection(){
        inputStrategy.tryReconnection();
    }

    public void setConnection(){
        inputStrategy.validPath();

    }

}
