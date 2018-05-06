package it.polimi.se2018.model.cards.public_card;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Public Objective Class Different Numbers
 * @author Silvia Franzini
 */
public class DiffNumbers implements ScoreStrategy {
    /**
     * Class implementing pattern Strategy for the evaluation of the player's score
     * due to the Public Objective Cards
     * @param schema, player's window pattern card
     * @return an int representing the player's score
     */
    @Override
    public int getScore(SchemaCard schema){
        PublicNumber pub = new PublicNumber();
        ArrayList<Integer> numbers = pub.differentNumbers(schema.getCellList());
        return Collections.min(numbers)*5;
    }
}

