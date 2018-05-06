package it.polimi.se2018.model.cards.public_card;

import java.util.Collections;
import java.util.List;

/**
 * Public Objective Card Row with Different Colours
 * @author Silvia Franzini
 */
public class DiffColoursRow implements ScoreStrategy {
    private final int row = 4;
    private final int add = 1;
    private final int offset = 6;
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
        for(int i=0; i<row; i++){
            List<Integer> rowCell = pub.differentColours(schema.getCellList().getRow(i));
            if(Collections.max(rowCell)<1){
                score += 1;
            }
        }
        return score*offset;
    }
}