package it.polimi.se2018.view;

import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.PlayerChoice;
import it.polimi.se2018.model.cards.SchemaCard;

/**
 * Manages the setup of the player at the beginning of the game
 * @author Cristian Giannetti
 */
public class PlayerSetupper {

    /**
     * Contains the first choices made by the player at the beginning of the game
     */
    PlayerChoice playerChoice;

    /**
     * Constructor sets the PlayerChoice to check
     * @param playerChoice Contains the value to check
     */
    public PlayerSetupper(PlayerChoice playerChoice){
        if (playerChoice == null) throw new NullPointerException("Impossible to set a null PlayerChoice");
        this.playerChoice = playerChoice;
    }

    /**
     * Gets the PlayerChoice to be checked
     * @return PlayerChoice to be checked
     */
    public PlayerChoice getPlayerChoice() {
        return playerChoice;
    }

    /**
     * Checks the Colour Frame choice made by the player
     * @param message String containing the choice to be validated
     * @return Error or ok message
     */
    public String validCommand(String message){
        message = message.toUpperCase();
        ColourEnum colourToCheck;

        try{
            colourToCheck = ColourEnum.valueOf(message);
        }catch (IllegalArgumentException e){
            return "Il colore scelto non è valido";
        }

        for (ColourEnum colour : playerChoice.getColourEnumList()) {
            if (colourToCheck == colour) playerChoice.setChosenColour(colour);
        }

        if (playerChoice.getChosenColour() == null) return "Impossibile impostare il colore scelto";
        return "Window Frame inserito correttamente";
    }

    /**
     * Checks the Schema Card choice made by the player
     * @param idSchema ID of the Schema chosen by the player
     * @return Error or ok message
     */
    public String validCommand(int idSchema){
        for(SchemaCard schema: playerChoice.getSchemaCardList()){
            if (schema.getId() == idSchema) playerChoice.setChosenSchema(schema);
        }
        if (playerChoice.getChosenSchema() == null) return "Impossibile impostare lo schema scelto";
        return "Schema inserito correttamente";
    }


}
