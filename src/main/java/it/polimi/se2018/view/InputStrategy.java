package it.polimi.se2018.view;

import it.polimi.se2018.model.PlayerChoice;

/**
 * Strategy interface for graphics. Used for visualization and input
 */
public interface InputStrategy {

    String getInput(Integer idMessage);
    void show(Integer idMessage);
    void showChoice(PlayerChoice playerChoice);
}
