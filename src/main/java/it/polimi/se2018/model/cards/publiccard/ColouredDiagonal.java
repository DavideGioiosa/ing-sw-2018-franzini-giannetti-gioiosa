package it.polimi.se2018.model.cards.publiccard;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.cards.SchemaCard;

import java.util.List;

/**
 * Public Objective Card Colored Diagonals
 * @author Silvia Franzini
 */
public class ColouredDiagonal implements ScoreStrategy {

    /**
     * Class implementing the pattern Strategy for the evaluation of the player's score
     * due to Public Objective Cards
     * @param schema player's window pattern card
     * @return an int representing the player's score
     */
    @Override
    public int getScore(SchemaCard schema){
        Cell cellToCheck;
        List<Cell> cellList;
        int adj = 0;
        int totalScore = 0;
        //moves through all the cell list of the window pattern card
        for (int i = 0; i < schema.getCellList().size(); i++) {

            cellToCheck = schema.getCellList().get(i);

            if (!cellToCheck.isEmpty()) {
                cellList = schema.getDiagonalAdjacents(new Position(i));

                for(Cell adjacent : cellList){
                    if(!adjacent.isEmpty() && cellToCheck.getDie().getColour().equals(adjacent.getDie().getColour())) adj = 1;
                }

                if(adj == 1){
                    // Increases the total score if at least one colour found among the adjacents
                    totalScore += 1;
                    adj = 0;
                }

            }

        }

        return totalScore;
    }
}


