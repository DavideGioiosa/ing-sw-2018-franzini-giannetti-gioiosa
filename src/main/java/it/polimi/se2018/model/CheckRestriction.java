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

    public boolean adjacentRestriction(SchemaCard schemaCard, Dice dice, Position position){

        if(schemaCard.isEmpty()){
            if(position.getRow() == 0 || position.getRow() == 3 || position.getCol() == 0 || position.getCol() == 4){
                return true;
            }
            else {
                return false;
            }
        }

        if(adjacentColourRestriction(schemaCard, dice, position) && adjacentValueRestriction(schemaCard, dice, position)) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean cellValueRestriction(SchemaCard schemaCard, Dice dice, Position position){
        if(schemaCard.getCellList().get(position.getIndexArrayPosition()).getValue() != dice.getValue()){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean cellColourRestriction (SchemaCard schemaCard, Dice dice, Position position){
        if(schemaCard.getCellList().get(position.getIndexArrayPosition()).getColour() != dice.getColour()){
            return true;
        }
        else {
            return false;
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