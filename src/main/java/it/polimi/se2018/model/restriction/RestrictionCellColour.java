package it.polimi.se2018.model.restriction;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.cards.SchemaCard;

import java.util.HashMap;

import static it.polimi.se2018.model.restriction.ValidateParameters.validateParameters;

public class RestrictionCellColour implements Restriction {

    /**
     * HashMap containing int value of error
     */
    private HashMap<Boolean, Integer> errorMap;

    /**
     * Constructor sets the Error ID in the HashMap
     */
    public RestrictionCellColour(){
        errorMap = new HashMap<>();
        errorMap.put(true, 0);
        errorMap.put(false, 5003);
    }

    /**
     * Checks colour of cell (limitation in the Scheme) in the position requested
     * @param schemaCard of the currentPlayer
     * @param die selected in the Draft Pool
     * @param position where the currentPlayer wants to put the die
     * @return true if the cell colour is the same of the die that the player wants to put into
     */
    @Override
    public int checkRestriction(SchemaCard schemaCard, Die die, Position position) {
        validateParameters(schemaCard, die, position);
        return errorMap.get((schemaCard.getCellList().get(position.getIndexArrayPosition()).getColour() == null ||
                schemaCard.getCellList().get(position.getIndexArrayPosition()).getColour() == die.getColour()));
    }

}
