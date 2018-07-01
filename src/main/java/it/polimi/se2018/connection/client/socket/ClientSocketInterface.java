package it.polimi.se2018.connection.client.socket;

import it.polimi.se2018.model.PlayerMessage;

public interface ClientSocketInterface {

    void receive(PlayerMessage playerMessage);
}
