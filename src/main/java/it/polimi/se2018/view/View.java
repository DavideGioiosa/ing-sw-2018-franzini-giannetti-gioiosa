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

    private PlayerSetupper playerSetupper;

    private ClientBoard clientBoard;

    private CommandLineGraphic commandLineGraphic;

    /**
     * Constructor of the class
     */
    public View() {
        commandLineInput = new CommandLineInput(new SyntaxController());
        playerSetupper = new PlayerSetupper(commandLineInput);
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
                User user = playerMessage.getUser();
                user = playerSetupper.choseNickname(user);
                playerMessage = new PlayerMessage();
                playerMessage.setUser(user);
                notify(playerMessage);
                break;

            case CHOICE:
                PlayerChoice playerChoice = playerSetupper.validCommand(playerMessage.getPlayerChoice());
                playerMessage = new PlayerMessage();
                playerMessage.setChoice(playerChoice);
                notify(playerMessage);
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
     * Method used to update the table status in the local Model
     * @param moveMessage class containing the several elements of the table
     */
    public void updateTable(MoveMessage moveMessage){
        this.moveMessage = moveMessage;
    }

    /**
     *
     * @return
     */
    public PlayerSetupper getPlayerSetupper() {
        return playerSetupper;
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

    @Override
    public void update(ClientBoard clientBoard){
        this.clientBoard = clientBoard;
        commandLineGraphic.showGameBoard(clientBoard);
    }

    public InputStrategy getInputStrategy() {
        return inputStrategy;
    }
}
