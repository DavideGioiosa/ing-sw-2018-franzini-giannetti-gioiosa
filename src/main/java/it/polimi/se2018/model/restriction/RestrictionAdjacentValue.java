package it.polimi.se2018.model.restriction;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.cards.SchemaCard;

import java.util.HashMap;
import java.util.List;

import static it.polimi.se2018.model.restriction.ValidateParameters.validateParameters;

public class RestrictionAdjacentValue implements Restriction{

    /**
     * HashMap containing int value of error
     */
    private HashMap<Boolean, Integer> errorMap;

    /**
     * Constructor sets the Error ID in the HashMap
     */
    public RestrictionAdjacentValue(){
        errorMap = new HashMap<>();
        errorMap.put(true, 0);
        errorMap.put(false, 5006);
    }

    /**
     * Checks adjacent cells with a die inside, of the position requested,
     * to get if there is a adj die (no on diagonals) of the same value of the die that the player wants to put
     * into the position of the Scheme
     * @param schemaCard of the currentPlayer
     * @param die selected in the Draft Pool
     * @param position where the currentPlayer wants to put the die
     * @return true if there aren't any
     */
    @Override
    public int checkRestriction(SchemaCard schemaCard, Die die, Position position) {
        validateParameters(schemaCard, die, position);

        List<Cell> adjList = schemaCard.getAdjacents(position);
        for (Cell c : adjList) {
            if(!c.isEmpty() && c.getDie().getValue() == die.getValue()) return errorMap.get(false);
        }
        return errorMap.get(true);
    }

}
