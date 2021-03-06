package it.polimi.se2018.model.cards.publiccard;

import it.polimi.se2018.model.cards.SchemaCard;

import java.util.Collections;
import java.util.List;

/**
 * Public Objective Class Different Numbers
 * @author Silvia Franzini
 */
public class DiffNumbers implements ScoreStrategy {
    /**
     * Class implementing pattern Strategy for the evaluation of the player's score
     * due to the Public Objective Cards
     * @param schema player's window pattern card
     * @return an int representing the player's score
     */
    @Override
    public int getScore(SchemaCard schema){

        PublicNumber pub = new PublicNumber();
        List<Integer> numbers = pub.differentNumbers(schema.getCellList());
        return Collections.min(numbers);
    }
}

