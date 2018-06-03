package it.polimi.se2018.model.cards.publiccard;

import it.polimi.se2018.model.cards.SchemaCard;

import java.util.Collections;
import java.util.List;

/**
 * Public Objective Card Row with Different Numbers
 * @author Silvia Franzini
 */
public class DiffNumbersRow implements ScoreStrategy {
    private static final int ROW = 4;
    private static final int OFFSET = 5;
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
            PublicNumber pub = new PublicNumber();
            List<Integer> rowCell = pub.differentNumbers(schema.getCellRow(i));
            rowCell.removeIf(c -> c.equals(0));

            if(rowCell.size()== 5 && rowCell.stream().allMatch(c -> c.equals(1))){
                score += 1;
            }
        }
        return score*OFFSET;
    }
}
