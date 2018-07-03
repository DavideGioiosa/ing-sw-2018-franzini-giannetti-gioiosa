package it.polimi.se2018.controller.client;

import it.polimi.se2018.connection.client.Client;
import it.polimi.se2018.model.*;

import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.model.ClientModel;
import it.polimi.se2018.view.View;

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
     * Constructor sets this ClientController as view's Observer
     * @param client
     * @param view
     */
    public ClientController(Client client, View view){
        this.client = client;
        this.view = view;
        this.view.addObserver(this);
        clientModel = new ClientModel();
        clientModel.addObserver(view);
        commandController = new CommandController(view);
        choiceController = new ChoiceController();
    }

    /**
     * Method used in Pattern Observer. ClientController receives a new Message from the View
     * @param playerMessage Message containing updates
     */
    @Override
    public void update(PlayerMessage playerMessage){

        int idError;

        switch (playerMessage.getId()){
            case CHOICE:
                idError = choiceController.checkChoice(playerMessage.getPlayerChoice());
                break;

            case CHECK_MOVE:
                idError = commandController.checkMoveValidity(playerMessage.getPlayerMove(), clientModel.getClientBoard());
                if(idError != 0) {
                    view.reportError(idError);
                    playerMessage.setCheckMove(commandController.getResetPlayerMove(playerMessage.getPlayerMove()));
                    playerMessage.setId(PlayerMessageTypeEnum.YOUR_TURN);
                    view.receive(playerMessage);
                    return;
                }
                break;

            case APPLY_MOVE:
                applyMove(playerMessage);
                return;

            case UPDATE:
                updateBoard(playerMessage.getMoveMessage());
                return;

            case USER:
                idError = choiceController.checkNickname(playerMessage.getUser());
                break;

            default:
                break;
        }
        client.send(playerMessage);
        //TODO: Gestione errori nelle varie mosse
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
        ClientBoard clientBoard = new ClientBoard(moveMessage.getPlayerList(),  moveMessage.getBoardDice(), moveMessage.getTrackBoard(), moveMessage.getBoardCard());
        clientModel.setClientBoard(clientBoard);
    }

}
