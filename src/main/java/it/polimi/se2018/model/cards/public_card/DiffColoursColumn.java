package it.polimi.se2018.model.cards.public_card;

import java.util.Collections;
import java.util.List;

/**
 * Public Objective Card Column with Different Colours
 * @author Silvia Franzini
 */
public class DiffColoursColumn implements ScoreStrategy {
    private final int col = 5;
    private final int add = 1;

    /**
     * Class implementing pattern Strategy for the evaluation of the player's score
     * due to the Public Objective Cards
     * @param schema, player's window pattern card
     * @return an int representing the player's score
     */
    @Override
    public int getScore(SchemaCard schema){
        int score = 0;
        PublicColour pub = new PublicColour();
        for(int i=0; i<col; i++){
            List<Integer> colCell = pub.differentColours(schema.getCellList().getCol(i));
            if(Collections.max(colCell)<1){
                score += 1;
            }
        }
        return score*col;
    }
}
