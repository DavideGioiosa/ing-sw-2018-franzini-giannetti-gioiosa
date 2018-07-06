package it.polimi.se2018.view;

import it.polimi.se2018.model.ClientModel;
import it.polimi.se2018.model.player.Player;

import java.util.List;

public interface OutputStrategy {

    void showGameBoard (ClientModel clientModel);

    void showScore (List<Player> playerList);
}
