package it.polimi.se2018.model.cards.publiccard;

import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.cards.SchemaCard;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Public Objective Card Column with Different Colours
 * @author Silvia Franzini
 */
public class DiffColoursColumn implements ScoreStrategy {
    private static final int COL = 5;

    /**
     * Class implementing pattern Strategy for the evaluation of the player's score
     * due to the Public Objective Cards
     * @param schema player's window pattern card
     * @return an int representing the player's score
     */
    @Override
    public int getScore(SchemaCard schema){

        int score = 0;
        PublicColour pub = new PublicColour();
        for(int i=0; i<COL; i++){
            HashMap<ColourEnum,Integer> colCell = pub.differentColours(schema.getCellCol(i));

            if( colCell.values().stream().allMatch(c -> c.equals(1))){
                score += 1;
            }
        }
        return score*COL;
    }
}

