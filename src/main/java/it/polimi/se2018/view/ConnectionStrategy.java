package it.polimi.se2018.view;

import it.polimi.se2018.model.PlayerMessage;

public interface ConnectionStrategy {

     void send(PlayerMessage playerMessage);
}
