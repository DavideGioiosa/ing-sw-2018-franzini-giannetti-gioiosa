package it.polimi.se2018.model.restriction;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.cards.SchemaCard;

import java.util.HashMap;

public class RestrictionFirstDieOnBorder implements Restriction{

    /**
     * HashMap containing int value of error
     */
    private HashMap<Boolean, Integer> errorMap;

    /**
     * Constructor sets the Error ID in the HashMap
     */
    public RestrictionFirstDieOnBorder(){
        errorMap = new HashMap<>();
        errorMap.put(true, 0);
        errorMap.put(false, 5001);
    }

    /**
     * If the Schema is empty, checks that the position where the player wants to put the die is on the edge
     * @param position where the currentPlayer wants to put the die
     * @return true if the position is valid
     */
    @Override
    public int checkRestriction(SchemaCard schemaCard, Die die, Position position) {
        if (position == null || position.getIndexArrayPosition() < 0 || position.getIndexArrayPosition() > 19) {
            throw new IllegalArgumentException("ERROR: Position is null or insert an indexArrayPosition " +
                    "out of the range permitted");
        }
        return errorMap.get(!schemaCard.isEmpty() || position.getRow() == 0 || position.getRow() == 3 || position.getCol() == 0 || position.getCol() == 4);
    }

}
