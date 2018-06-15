package it.polimi.se2018.view;

import it.polimi.se2018.connection.client.Client;
import it.polimi.se2018.model.ClientBoard;
import it.polimi.se2018.model.MoveMessage;
import it.polimi.se2018.model.PlayerMessage;

import it.polimi.se2018.utils.Observer;

/**
 * Local Controller manages the controller classes that help the user to not make wrong choices
 * @author Cristian Giannetti
 */
public class ClientController implements Observer<PlayerMessage> {

    /**
     * Command controller checks Player Moves
     */
    private CommandController commandController;
    /**
     * Checks the choices made by PlayerSetupper
     */
    private ChoiceController choiceController;
    /**
     * Manages the interaction with the user
     */
    private View view;
    /**
     * Sending class
     */
    private Client client;
    /**
     * Local Model
     */
    private ClientModel clientModel;

    /**
     * Constructor
     * @param client
     * @param view
     */
    public ClientController(Client client, View view){
        this.client = client;
        this.view = view;
        this.view.addObserver(this);
        clientModel = new ClientModel();
        clientModel.addObserver(view);
        commandController = new CommandController(client, view);
        choiceController = new ChoiceController(client, null, view);
    }

    /**
     * Method used in Pattern Observer. ClientController receives a new Message from the View
     * @param playerMessage Message containing updates
     */
    @Override
    public void update(PlayerMessage playerMessage){
        boolean validCommand = false;
        int idError = -1;
        switch (playerMessage.getId()){
            case CHOICE:
                idError = choiceController.update(playerMessage.getPlayerChoice());
                break;

            case CHECK_MOVE:
                idError = commandController.checkMoveValidity(playerMessage.getPlayerMove(), clientModel.getClientBoard());
                break;

            case APPLY_MOVE:
                applyMove(playerMessage);
                break;

            case UPDATE:
                updateBoard(playerMessage.getMoveMessage());
                break;

            default:
                break;
        }
        if (idError == 0) client.send(playerMessage);
        else view.reportError(idError);
    }

    /**
     * New move approved by the Server
     * @param playerMessage Move approved by the Server
     */
    private void applyMove(PlayerMessage playerMessage){

    }

    /**
     * New status of Board updated by the Server
     * @param moveMessage Actual status of Board
     */
    private void updateBoard(MoveMessage moveMessage){
        ClientBoard clientBoard = new ClientBoard(moveMessage.getPlayerList(),  moveMessage.getBoardDice(), moveMessage.getTrackboard(), moveMessage.getBoardCard());
        clientModel.setClientBoard(clientBoard);
    }

}
