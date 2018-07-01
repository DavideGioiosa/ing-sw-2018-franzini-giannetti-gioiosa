package it.polimi.se2018.view;

import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.PlayerChoice;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.view.graphic.cli.CommandLineInput;

/**
 * Manages the setup of the player at the beginning of the game
 * @author Cristian Giannetti
 */
public class PlayerSetupper extends Observable<PlayerChoice>{

    /**
     * Class that manages the interaction with user
     */
    private CommandLineInput commandLineInput;

    /**
     * Constructor sets input class
     */
    public PlayerSetupper(CommandLineInput commandLineInput){
        this.commandLineInput = commandLineInput;
    }

    /**
     * Checks the Colour Frame choice made by the player
     * @return Error or Ok message
     */
    public PlayerChoice validCommand(PlayerChoice playerChoice){

        if(playerChoice.getColourEnumList()!= null && !playerChoice.getColourEnumList().isEmpty() && playerChoice.getChosenColour() == null) {
            chosePatternColour(playerChoice);
        }

        if(playerChoice.getSchemaCardList()!= null && !playerChoice.getSchemaCardList().isEmpty() && playerChoice.getIdChosenSchema() == 0) {
            choseSchema(playerChoice);
        }
        return playerChoice;
    }

    /**
     * Sets nickname to User received
     * @param user User initialized
     * @return User with nickname set
     */
    public User choseNickname(User user){
        user.setNickname(commandLineInput.getInput("Inserisci un nickname:\n"));
        return user;
    }

    /**
     * Manages the choice of Pattern colour
     * @param playerChoice PlayerChoice containing available colours
     */
    private void chosePatternColour(PlayerChoice playerChoice){
        String message = commandLineInput.getInput("Inserisci colore: " + playerChoice.getColourEnumList().toString() + "\n");
        message = message.toUpperCase();
        ColourEnum colourToCheck;

        try{
            colourToCheck = ColourEnum.valueOf(message);
        }catch (IllegalArgumentException e){
            return;
        }

        for (ColourEnum colour : playerChoice.getColourEnumList()) {
            if (colourToCheck == colour) playerChoice.setChosenColour(colour);
        }
    }

    /**
     * Checks the Schema Card choice made by the player
     */
    public void choseSchema(PlayerChoice playerChoice){

        String availableSchema = "";
        for(SchemaCard schemaCard: playerChoice.getSchemaCardList()){
            availableSchema = availableSchema.concat(schemaCard.getId() + " ");
        }

        String message = commandLineInput.getInput("Schemi disponibili: " + availableSchema + "\nInserisci id schema: \n");
        int idSchema = Integer.parseInt(message);

        for(SchemaCard schema: playerChoice.getSchemaCardList()){
            if (schema.getId() == idSchema) playerChoice.setIdChosenSchema(idSchema);
        }

    }

}
