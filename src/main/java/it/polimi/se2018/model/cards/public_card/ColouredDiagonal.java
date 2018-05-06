package it.polimi.se2018.model.cards.public_card;

import it.polimi.se2018.model.ColourEnum;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Public Objective Card Colored Diagonals
 * @author Silvia Franzini
 */
public class ColouredDiagonal implements ScoreStrategy {
    private final int row = 4;
    private final int col = 5;

    /**
     * Class implementing the pattern Strategy for the evaluation of the player's score
     * due to Public Objective Cards
     * @param schema player's window pattern card
     * @return an int representing the player's score
     */
    @Override
    public int getScore(SchemaCard schema){
        int totalScore = 0;
        ColourEnum colours;
        ArrayList<Boolean> bools= new ArrayList<>(schema.getCellList().size()+1);
        Collections.fill(bools,false);
        Position pos=new Position(0,0);
        recursive(schema,schema.getCellList().get(0).getDice().getColour(), pos);
        return totalScore;

    }

    /**
     * Recursive function used to calculate the numbers of adjacent die of the same colour
     * @param schema player's window pattern card
     * @param colour the colour of the previous dice
     * @param pos position of the next dice following the diagonal
     * @return the total number of adjacent die of all colours
     */
    private int recursive(SchemaCard schema, ColourEnum colour, Position pos){
        ArrayList<Boolean> bools= new ArrayList<>(schema.getCellList().size()+1);
        int score = 0;

        for(int i=0; i<schema.getCellList().size(); i++){
            bools.set(i, true);
            if(pos.getCol()>=0 && pos.getRow()>= 0){
                if(!schema.getCellList().get(i).isEmpty() && !bools.get(pos.getIndexArrayPosition()) && schema.getCellList().get(i).getDice().getColour().equals(colour)){
                    score ++;
                    score += recursive(schema, schema.getCellList().get(i).getDice().getColour(), northEast(pos));
                    score += recursive(schema, schema.getCellList().get(i).getDice().getColour(), northWest(pos));
                    score += recursive(schema, schema.getCellList().get(i).getDice().getColour(), southEast(pos));
                    score += recursive(schema, schema.getCellList().get(i).getDice().getColour(), southWest(pos));
                }
            }
        }
        return score;
    }

    /**
     * Method used to calculate the position of the adjacent dice in the nortWest diagonal
     * @param pos Position of the previous dice
     * @return position of the adjacent dice, if existing
     */
    private Position northWest(Position pos){
        if(pos.getRow()-1>0 && pos.getCol()-1>0){
            pos.setRow(pos.getRow()-1);
            pos.setCol(pos.getCol()-1);
        }else{
            pos.setRow(-1);
            pos.setCol(-1);
        }
        return pos;
    }

    /**
     * Method used to calculate the position of the adjacent dice in the nortEast diagonal
     * @param pos Position of the previous dice
     * @return position of the adjacent dice, if existing
     */
    private Position northEast(Position pos){
        if(pos.getRow()-1>0 && pos.getCol()+1<col){
            pos.setRow(pos.getRow()-1);
            pos.setCol(pos.getCol()+1);
        }else{
            pos.setRow(-1);
            pos.setCol(-1);
        }
        return pos;
    }
    /**
     * Method used to calculate the position of the adjacent dice in the southWest diagonal
     * @param pos Position of the previous dice
     * @return position of the adjacent dice, if existing
     */
    private Position southWest(Position pos){
        if(pos.getRow()+1<row && pos.getCol()-1>0){
            pos.setRow(pos.getRow()+1);
            pos.setCol(pos.getCol()-1);
        }else{
            pos.setRow(-1);
            pos.setCol(-1);
        }
        return pos;
    }
    /**
     * Method used to calculate the position of the adjacent dice in the southEast diagonal
     * @param pos Position of the previous dice
     * @return position of the adjacent dice, if existing
     */
    private Position southEast(Position pos){
        if(pos.getRow()+1<row && pos.getCol()+1<col){
            pos.setRow(pos.getRow()+1);
            pos.setCol(pos.getCol()+1);
        }else{
            pos.setRow(-1);
            pos.setCol(-1);
        }
        return pos;
    }
}

}
