package it.polimi.se2018.model.cards.public_card;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Public Objective Card Middle Shades
 * @author Silvia Franzini
 */
public class MiddleNumbers implements ScoreStrategy {
    /**
     * Class implementing pattern Strategy for the evaluation of the player's score
     * due to the Public Objective Cards
     * @param schema, player's window pattern card
     * @return an int representing the player's score
     */
    @Override
    public int getScore(SchemaCard schema){
        Shades shad = new Shades();
        ArrayList<Integer> numbers = shad.operate(schema.getCellList(), 3, 4);
        return Collections.min(numbers)*2;
    }

}
