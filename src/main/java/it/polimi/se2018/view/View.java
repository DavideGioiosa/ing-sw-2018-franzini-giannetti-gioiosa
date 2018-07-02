package it.polimi.se2018.view;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.SchemaCard;
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
    protected ConnectionStrategy connectionStrategy;
    private MoveMessage moveMessage;
    private CommandLineInput commandLineInput;
    //private MessageLoader messageLoader;

    private SyntaxController syntaxController;
    private PlayerSetupper playerSetupper;

    private ClientBoard clientBoard;

    private PlayerMessage lastPlayerMessage;
    private CommandLineGraphic commandLineGraphic;

    /**
     * Constructor of the class
     */
    public View() {
        commandLineInput = new CommandLineInput();
        playerSetupper = new PlayerSetupper();
        syntaxController = new SyntaxController(commandLineInput);
        commandLineGraphic = new CommandLineGraphic();
        /*moveMessage = null;
        try{
            messageLoader = new MessageLoader();

        }catch(Exception e){
            //TODO Gestire eccezione
        }*/
    }

    /**
     * Setter method for the chosen graphic (CLI/GUI)
     * @param inputStrategy the type chosen
     */
    public void setInputStrategy(InputStrategy inputStrategy) {
        this.inputStrategy = inputStrategy;
        //inputStrategy.getInput();
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

        if (playerMessage == null){
            reportError(28031993);
            return;
        }

        switch (playerMessage.getId()){
            case YOUR_TURN:
                PlayerMove playerMove = syntaxController.validCommand("Fai la tua mossa: ", playerMessage.getPlayerMove(), clientBoard);
                playerMessage = new PlayerMessage();
                playerMessage.setCheckMove(playerMove);
                notify(playerMessage);
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

            default:
                reportError(28031993);
                throw new IllegalArgumentException("ERROR: Invalid PlayerMessage");
        }

    }

    /**
     * Method used to update the status of the table when a player haa played and only used a PICK action
     * @param playerMove class containing all the parameters of the move
     */
    public void updateSchema(PlayerMove playerMove){
        SchemaCard schemaCard = playerMove.getPlayer().getSchemaCard();
      //  Die die = moveMessage.getBoardDice().takeDie(playerMove.getDiceBoardIndex());
     //   schemaCard.setDiceIntoCell(playerMove.getDiceSchemaWhereToLeave().get(0), die);
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
        System.out.println("ERROR: " + idError);
    }

    /**
     *
     * @param message
     */
    public void showMessage(int message){
        System.out.println("MESSAGE: " + message);
    }

    public SyntaxController getSyntaxController() {
        return syntaxController;
    }

    public void setSyntaxController(SyntaxController syntaxController) {
        this.syntaxController = syntaxController;
    }

    @Override
    public void update(ClientBoard clientBoard){
        this.clientBoard = clientBoard;
        commandLineGraphic.showGameBoard(clientBoard);
    }
}
