package it.polimi.se2018.view;

import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.PlayerChoice;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.view.graphic.cli.CommandLineInput;

import static java.lang.Integer.valueOf;

/**
 * Manages the setup of the player at the beginning of the game
 * @author Cristian Giannetti
 */
public class PlayerSetupper extends Observable<PlayerChoice>{

    /**
     *
     */
    private CommandLineInput commandLineInput;

    /**
     *
     */
    public PlayerSetupper(){
        commandLineInput = new CommandLineInput();
    }

    /**
     * Checks the Colour Frame choice made by the player
     * @return Error or Ok message
     */
    public PlayerChoice validCommand(PlayerChoice playerChoice){

        try {
            if (playerChoice.getUser().getNickname() == null) {
                choseNickname(playerChoice);
            }
        }catch (NullPointerException e){
        }

        if(playerChoice.getColourEnumList()!= null && !playerChoice.getColourEnumList().isEmpty() && playerChoice.getChosenColour() == null) {
            chosePatternColour(playerChoice);
        }

        if(playerChoice.getSchemaCardList()!= null && !playerChoice.getSchemaCardList().isEmpty() && playerChoice.getChosenSchema() == null) {
            choseSchema(playerChoice);
        }
        return playerChoice;
    }

    /**
     *
     * @param playerChoice
     */
    public void choseNickname(PlayerChoice playerChoice){
        playerChoice.getUser().setNickname(commandLineInput.getInput("Inserisci un nickname\n"));
    }

    /**
     *
     * @param playerChoice
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
     * @return Error or ok message
     */
    public void choseSchema(PlayerChoice playerChoice){

        String availableSchema = "";
        for(SchemaCard schemaCard: playerChoice.getSchemaCardList()){
            availableSchema = availableSchema.concat(schemaCard.getId() + " " + schemaCard.getBackSchema().getId() + " ");
        }

        String message = commandLineInput.getInput("Schemi disponibili: " + availableSchema + "Inserisci id schema: \n");
        int idSchema = Integer.parseInt(message);

        for(SchemaCard schema: playerChoice.getSchemaCardList()){
            if (schema.getId() == idSchema) playerChoice.setChosenSchema(schema);
        }

    }

}
