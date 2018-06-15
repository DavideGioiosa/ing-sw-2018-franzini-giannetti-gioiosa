package it.polimi.se2018.view;

import it.polimi.se2018.connection.client.Client;
import it.polimi.se2018.model.PlayerChoice;
import it.polimi.se2018.model.PlayerMessage;

public class ChoiceController {

    private Client client;

    private PlayerSetupper playerSetupper;

    private View view;

    public ChoiceController (Client client, PlayerSetupper playerSetupper, View view){
        this.client = client;
        this.playerSetupper = playerSetupper;
    }

    public int update(PlayerChoice message) {
        //TODO Check validità choice
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setChoice(message);


        return 0;
    }
}
