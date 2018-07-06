package it.polimi.se2018.view.graphic.gui;

import it.polimi.se2018.model.ClientBoard;
import it.polimi.se2018.model.ClientModel;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.view.OutputStrategy;

import java.util.List;

public class GuiOutput implements OutputStrategy {

    private ControllerMatchTable controllerMatchTable;

    public void setControllerMatchTable(ControllerMatchTable controllerMatchTable) {
        this.controllerMatchTable = controllerMatchTable;
    }

    @Override
    public void showGameBoard(ClientModel clientModel) {
        controllerMatchTable.requestShowGameboard(clientModel);
    }

    @Override
    public void showScore(List<Player> playerList) {
        controllerMatchTable.requestShowScore(playerList);
    }
}
