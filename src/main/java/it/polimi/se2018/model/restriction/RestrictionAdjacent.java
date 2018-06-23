package it.polimi.se2018.model.restriction;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.cards.SchemaCard;

import java.util.HashMap;
import java.util.List;

import static it.polimi.se2018.model.restriction.ValidateParameters.validateParameters;

public class RestrictionAdjacent implements Restriction {

    /**
     * HashMap containing int value of error
     */
    private HashMap<Boolean, Integer> errorMap;

    /**
     * Constructor sets the Error ID in the HashMap
     */
    public RestrictionAdjacent(){
        errorMap = new HashMap<>();
        errorMap.put(true, 0);
        errorMap.put(false, 2);
    }

    /**
     * Checks adjacent cells (also diagonal ones) of the position requested to get if there are any die inside
     * @param schemaCard of the currentPlayer
     * @param die selected in the Draft Pool
     * @param position where the currentPlayer wants to put the die
     * @return true if there's at least one adjacent die
     */
    @Override
    public int checkRestriction(SchemaCard schemaCard, Die die, Position position){

        validateParameters(schemaCard, die, position);

        if(schemaCard.isEmpty()) return errorMap.get(true);

        List<Cell> adjList = schemaCard.getAdjacents(position);
        for (Cell c : adjList) {
            if (c.getDie() != null) {
                return errorMap.get(true);
            }
        }

        List<Cell> adjDiagList = schemaCard.getDiagonalAdjacents(position);
        for (Cell d : adjDiagList) {
            if (d.getDie() != null) {
                return errorMap.get(true);
            }
        }
        return errorMap.get(false);
    }
}
