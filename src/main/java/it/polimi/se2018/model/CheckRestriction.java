package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.SchemaCard;

import java.util.List;

/**
 * Public Class CheckRestriction
 * @author Davide Gioiosa
 */

public class CheckRestriction {

    public CheckRestriction() {
    }

    /**
     * if Scheme isEmpty is true:
     * 1. Check adjcents cells (also diagonal ones) and
     * @return true if the position where the player wants to put the dice is on the edge
     * if Scheme isEmpty is false:
     * 2. Check adjacents cells (also diagonal ones) of the position requested
     * to get if there are any dice inside and
     * @return true if there's an adj dice
     */
    public boolean adjacentRestriction(SchemaCard schemaCard, Dice dice, Position position) {

        if (schemaCard.isEmpty()) {
            if (position.getRow() == 0 || position.getRow() == 3 || position.getCol() == 0 || position.getCol() == 4) {
                return true;
            } else {
                return false;
            }
        }
        List<Cell> adjList = schemaCard.getAdjacents(position);
        for (Cell c : adjList) {
            if (c.getDice() != null) {
                return true;
            }
        }
        List<Cell> adjDiagList = schemaCard.getDiagonalAdjacents(position);
        for (Cell d : adjDiagList) {
             if (d.getDice() != null) {
                 return true;
             }
        }
        return false;
    }

    /**
     * Check value of cell (limitation in the Scheme) in the position requested
     * @return true if the cell value is the same of the dice that the player wants to put into
     */
    public boolean cellValueRestriction(SchemaCard schemaCard, Dice dice, Position position){
        if(schemaCard.getCellList().get(position.getIndexArrayPosition()).getValue() != dice.getValue()){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Check colour of cell (limitation in the Scheme) in the position requested
     * @return true if the cell colour is the same of the dice that the player wants to put into
     */
    public boolean cellColourRestriction (SchemaCard schemaCard, Dice dice, Position position){
        if(schemaCard.getCellList().get(position.getIndexArrayPosition()).getColour() != dice.getColour()){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Check adjacents cells of the position requested
     * to get if there is a adj dice (no on diagonals) of the same colour of the dice that the player wants to put
     * into the position of the Scheme
     * @return true if the there aren't
     */
    public boolean adjacentColourRestriction (SchemaCard schemaCard, Dice dice, Position position){
        List<Cell> adjList = schemaCard.getAdjacents(position);
        for (Cell c : adjList) {
            if (c.getDice().getColour() == dice.getColour()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check adjacents cells of the position requested
     * to get if there is a adj dice (no on diagonals) of the same value of the dice that the player wants to put
     * into the position of the Scheme
     * @return true if there aren't
     */
    public boolean adjacentValueRestriction (SchemaCard schemaCard, Dice dice, Position position){
        List<Cell> adjList = schemaCard.getAdjacents(position);
        for (Cell c : adjList) {
            if (c.getDice().getValue()== dice.getValue()) {
                return false;
            }
        }
        return true;
    }
}