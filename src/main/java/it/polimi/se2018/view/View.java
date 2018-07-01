package it.polimi.se2018.view;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.*;
import it.polimi.se2018.view.graphic.cli.CommandLineGraphic;
import it.polimi.se2018.view.graphic.cli.CommandLineInput;

/**
 * Used for interaction with the player
 * @author Silvia Franzini
 */
public class View extends Observable implements Observer<ClientBoard> {
    protected InputStrategy inputStrategy;
    private MoveMessage moveMessage;
    private CommandLineInput commandLineInput;

    private ClientBoard clientBoard;

    private CommandLineGraphic commandLineGraphic;

    /**
     * Constructor of the class
     */
    public View() {
        commandLineInput = new CommandLineInput(new SyntaxController(), new PlayerSetupper());
        commandLineGraphic = new CommandLineGraphic();
        this.inputStrategy = commandLineInput;
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
                for(Player player: playerMessage.getMoveMessage().getPlayerList()) System.out.println( player.getNickname() + " " + player.getScore());
                break;

            default:
                reportError(1001);
        }

    }

    /**
     * Method used to update the status of the table when a player haa played and only used a PICK action
     * @param playerMove class containing all the parameters of the move
     */
    public void updateSchema(PlayerMove playerMove){
        SchemaCard schemaCard = playerMove.getPlayer().getSchemaCard();
        Die die = moveMessage.getBoardDice().takeDie(playerMove.getDiceBoardIndex());
        schemaCard.setDiceIntoCell(playerMove.getDiceSchemaWhereToLeave().get(0), die);
    }

    /**
     * Method used to understand the error notify received
     */
    public void reportError(int idError){
        inputStrategy.showError(idError);
    }

    /**
     *
     * @param message
     */
    public void showMessage(int message){
        inputStrategy.showMessage(message);
    }

    /**
     * Method used to update the table status in the local Model
     * @param clientBoard class containing the several elements of the table
     */
    @Override
    public void update(ClientBoard clientBoard){
        this.clientBoard = clientBoard;
        commandLineGraphic.showGameBoard(clientBoard);
    }

    public InputStrategy getInputStrategy() {
        return inputStrategy;
    }
}
