package it.polimi.se2018.view;

import it.polimi.se2018.model.ClientBoard;
import it.polimi.se2018.model.PlayerMove;

/**
 * Strategy interface for graphics. Used for visualization and input
 */
public interface InputStrategy {

    void yourTurn(ClientBoard clientBoard, PlayerMove playerMove);
    void showMessage(int idMessage);
    void showError(int idError);
    SyntaxController getSyntaxController();
    //void showChoice(PlayerChoice playerChoice);
}
