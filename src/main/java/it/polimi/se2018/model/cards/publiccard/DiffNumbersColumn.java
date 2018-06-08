package it.polimi.se2018.model.cards.publiccard;

import it.polimi.se2018.model.cards.SchemaCard;

import java.util.List;

import static it.polimi.se2018.model.Config.NUMBER_OF_SCHEMA_COL;

/**
 * Public Objective Card Column with Different Numbers
 * @author Silvia Franzini
 */
public class DiffNumbersColumn implements ScoreStrategy {
    /**
     * Class implementing pattern Strategy for the evaluation of the player's score
     * due to the Public Objective Cards
     * @param schema player's window pattern card
     * @return an int representing the player's score
     */
    @Override
    public int getScore(SchemaCard schema){

        int score = 0;

        for(int i=0; i< NUMBER_OF_SCHEMA_COL; i++){
            PublicNumber pub = new PublicNumber();
            List<Integer> colCell = pub.differentNumbers(schema.getCellCol(i));
            colCell.removeIf(c -> c.equals(0));

            if(colCell.size()== 4 && colCell.stream().allMatch(c -> c.equals(1))){
                score += 1;
            }
        }
        return score;
    }
}

