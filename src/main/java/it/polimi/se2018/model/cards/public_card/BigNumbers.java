package it.polimi.se2018.model.cards.public_card;

import it.polimi.se2018.model.cards.SchemaCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Public Objective Card Dark Shades
 * @author Silvia Franzini
 */
public class BigNumbers implements ScoreStrategy {
    /**
     * Class implementing pattern Strategy for the evaluation of the player's score
     * due to the Public Objective Cards
     * @param schema, player's window pattern card
     * @return an int representing the player's score
     */
    @Override
    public int getScore(SchemaCard schema){
        Shades shad= new Shades();
        List<Integer> numbers = shad.operate(schema.getCellList(),5,6);
        return Collections.min(numbers)*2;
    }
}

