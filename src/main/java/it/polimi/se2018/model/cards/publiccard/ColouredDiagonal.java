package it.polimi.se2018.model.cards.publiccard;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.ColourEnum;
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
        List<Cell> cells;
        ColourEnum colour;
        Position pos = new Position(0,0);
        int adj = 0;
        int totalScore = 0;
        //moves through all the cell list of the window pattern card
        while(pos.getIndexArrayPosition() < schema.getCellList().size()){
            if(!schema.getCellList().get(pos.getIndexArrayPosition()).isEmpty()){
                cells = schema.getDiagonalAdjacents(pos);
                //compares the colour in the main cell with the colours of the adjacent cells
                for(Cell c : cells){
                    colour = schema.getCellList().get(pos.getIndexArrayPosition()).getDie().getColour();
                    if(colour.equals(c.getDie().getColour())){
                        adj = 1;
                        // adjacent found sets adj
                    }
                }
                if(adj == 1){
                    totalScore += 1;
                    adj = 0;
                    // increases the total score at every same colour found among the adjacents
                }
           }
            pos.setCol(pos.getCol()+1);
            pos.setRow(pos.getRow()+1);

        }
        return totalScore;
    }
}


