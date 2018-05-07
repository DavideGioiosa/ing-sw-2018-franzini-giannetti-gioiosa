package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.SchemaCard;

import java.util.ArrayList;

/**
 * Public Class CheckRestriction
 * @author Davide Gioiosa
 */

public class CheckRestriction {

    public CheckRestriction() {
    }

    /**
     * Check adjacents cells (also diagonal ones) of the position requested
     * to get if there are any dice inside
     */
    public boolean adjacentRestriction(SchemaCard schemaCard, Dice dice, Position position) {

        if (schemaCard.isEmpty()) {
            if (position.getRow() == 0 || position.getRow() == 3 || position.getCol() == 0 || position.getCol() == 4) {
                return true;
            } else {
                return false;
            }
        }
        ArrayList<Cell> adjList = schemaCard.getAdjacents(position);
        for (Cell c : adjList) {
            if (c.getDice() != null) {
                return true;
            }
        }
        ArrayList<Cell> adjDiagList = schemaCard.getDiagonalAdjacents(position);
        for (Cell d : adjDiagList) {
             if (d.getDice() != null) {
                 return true;
             }
        }
        return false;
    }

    public boolean cellValueRestriction(SchemaCard schemaCard, Dice dice, Position position){
        if(schemaCard.getCellList().get(position.getIndexArrayPosition()).getValue() != dice.getValue()){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean cellColourRestriction (SchemaCard schemaCard, Dice dice, Position position){
        if(schemaCard.getCellList().get(position.getIndexArrayPosition()).getColour() != dice.getColour()){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean adjacentColourRestriction (SchemaCard schemaCard, Dice dice, Position position){
        ArrayList<Cell> adjList = schemaCard.getAdjacents(position);
        for (Cell c : adjList) {
            if (c.getDice().getColour() == dice.getColour()) {
                return false;
            }
        }
        return true;
    }

    public boolean adjacentValueRestriction (SchemaCard schemaCard, Dice dice, Position position){
        ArrayList<Cell> adjList = schemaCard.getAdjacents(position);
        for (Cell c : adjList) {
            if (c.getDice().getValue()== dice.getValue()) {
                return false;
            }
        }
        return true;
    }
}