package it.polimi.se2018.view.graphic.gui;

import it.polimi.se2018.model.ClientBoard;
import it.polimi.se2018.model.PlayerChoice;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.view.InputStrategy;
import it.polimi.se2018.view.PlayerSetupper;
import it.polimi.se2018.view.SyntaxController;

public class GuiInput implements InputStrategy {

    private boolean timeOut;
    private SyntaxController syntaxController;
    private PlayerSetupper playerSetupper;

    private ControllerMatchTable controllerMatchTable;

    public GuiInput(SyntaxController syntaxController, PlayerSetupper playerSetupper){
        this.syntaxController = syntaxController;
        this.playerSetupper = playerSetupper;
    }

    public void setControllerMatchTable(ControllerMatchTable controllerMatchTable) {
        this.controllerMatchTable = controllerMatchTable;
    }

    @Override
    public void yourTurn(ClientBoard clientBoard, PlayerMove playerMove) {
        controllerMatchTable.requestYourTurn(clientBoard, playerMove);
    }

    @Override
    public void showMessage(int idMessage) {
        ControllerMatchTable.inizMsgAreaMessage(idMessage);
    }

    @Override
    public void showError(int idError) {
        ControllerMatchTable.inizMsgAreaError(idError);

    }

    @Override
    public SyntaxController getSyntaxController() {
        return syntaxController;
    }

    @Override
    public PlayerSetupper getPlayerSetupper() {
        return playerSetupper;
    }

    @Override
    public void makeChoice(PlayerChoice playerChoice) {
        playerSetupper.newChoiceReceived(playerChoice);
        
        if(playerChoice.getChosenColour() == null){
            playerSetupper.validCommand(playerChoice.getColourEnumList().get(0).toString());
        }else {
            controllerMatchTable.requestSchemeSelection(playerChoice);
        }

    }

    @Override
    public void choseNickname(User user) {
        controllerMatchTable.requestNickname(user);
    }


    @Override
    public void tryReconnection() {

    }

    @Override
    public void validPath() {

    }


}
