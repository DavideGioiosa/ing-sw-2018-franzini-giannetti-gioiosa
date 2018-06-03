package it.polimi.se2018.model.cards.publiccard;

import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.cards.SchemaCard;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Public Objective Card Row with Different Colours
 * @author Silvia Franzini
 */
public class DiffColoursRow implements ScoreStrategy {
    private static final int ROW = 4;
    private static final int OFFSET = 6;
    /**
     * Class implementing pattern Strategy for the evaluation of the player's score
     * due to the Public Objective Cards
     * @param schema player's window pattern card
     * @return an int representing the player's score
     */
    @Override
    public int getScore(SchemaCard schema){

        int score = 0;

        for(int i=0; i<ROW; i++){
            PublicColour pub = new PublicColour();
            HashMap<ColourEnum,Integer> rowCell = pub.differentColours(schema.getCellRow(i));

            if(rowCell.values().stream().allMatch(c -> c.equals(1))){
                score += 1;
            }
        }
        return score*OFFSET;
    }
}
