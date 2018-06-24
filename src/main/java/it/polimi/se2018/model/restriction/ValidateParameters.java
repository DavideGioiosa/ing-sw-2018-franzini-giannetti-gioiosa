package it.polimi.se2018.model.restriction;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.cards.SchemaCard;

public class ValidateParameters {

    private ValidateParameters() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Checks the validation of the parameters entered
     * @param schemaCard of the currentPlayer
     * @param die selected in the Draft Pool
     * @param position where the currentPlayer wants to put the die
     */
    public static void validateParameters(SchemaCard schemaCard, Die die, Position position) {
        if (schemaCard == null) {
            throw new NullPointerException("ERROR: SchemeCard selected is null");
        }
        if (die == null) {
            throw new NullPointerException("ERROR: Die selected is null");
        }
        if (position == null){
            throw new NullPointerException("ERROR: Position selected is null");
        }
        if (position.getIndexArrayPosition() < 0 || position.getIndexArrayPosition() > 19) {
            throw new IllegalArgumentException("Index inserted is out of the range permitted");
        }

    }
}
