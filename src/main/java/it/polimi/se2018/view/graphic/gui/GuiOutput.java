package it.polimi.se2018.view.graphic.gui;

import it.polimi.se2018.model.ClientBoard;
import it.polimi.se2018.view.OutputStrategy;

public class GuiOutput implements OutputStrategy {

    private ControllerMatchTable controllerMatchTable;

    public void setControllerMatchTable(ControllerMatchTable controllerMatchTable) {
        this.controllerMatchTable = controllerMatchTable;
    }

    @Override
    public void showGameboard(ClientBoard clientBoard) {
        controllerMatchTable.requestShowGameboard(clientBoard);
    }
}
