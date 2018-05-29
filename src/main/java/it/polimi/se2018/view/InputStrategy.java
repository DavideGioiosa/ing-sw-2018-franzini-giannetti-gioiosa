package it.polimi.se2018.view;

import it.polimi.se2018.model.PlayerChoice;

/**
 * Strategy interface for graphics. Used for visualization and input
 */
public interface InputStrategy {

    void getInput();
    void show();
    void showChoice(PlayerChoice playerChoice);
}
