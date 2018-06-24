package it.polimi.se2018.connection.client;

import it.polimi.se2018.model.PlayerMessage;
import it.polimi.se2018.model.player.User;

public interface ClientStrategy {

    void sendToServer(PlayerMessage playerMessage);
    void close();
    void addObserver(Client client);
    void reconnect(User user);
    void connect();
}
