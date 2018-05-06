package it.polimi.se2018.model.cards.public_card;

import java.util.Collections;
import java.util.List;

/**
 * Public Objective Card Column with Different Numbers
 * @author Silvia Franzini
 */
public class DiffNumbersColumn implements ScoreStrategy {
    private final int col = 5;
    private final int offset = 4;
    /**
     * Class implementing pattern Strategy for the evaluation of the player's score
     * due to the Public Objective Cards
     * @param schema, player's window pattern card
     * @return an int representing the player's score
     */
    @Override
    public int getScore(SchemaCard schema){
        int score = 0;
        PublicNumber pub = new PublicNumber();
        for(int i=0; i<col; i++){
            List<Integer> colCell = pub.differentNumbers(schema.getCellList().getCol(i));
            if(Collections.max(colCell)<1){
                score += 1;
            }
        }
        return score*offset;
    }
}

