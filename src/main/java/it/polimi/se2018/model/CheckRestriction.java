package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.SchemaCard;

import java.util.List;

/**
 * Public Class CheckRestriction
 *
 * @author Davide Gioiosa
 */

public class CheckRestriction {

    public CheckRestriction() {
    }

    /**
     * @param schemaCard of the currentPlayer
     * @param dice selected in the Draft Pool
     * @param position, where the currentPlayer wants to put the die
     * if Scheme isEmpty is true:
     * 1. Check adjcents cells (also diagonal ones) and
     * @return true if the position where the player wants to put the die is on the edge of his Scheme
     * if Scheme isEmpty is false:
     * 2. Check adjacents cells (also diagonal ones) of the position requested
     * to get if there are any dice inside and
     * @return true if there's at least one adj die
     */
    public boolean adjacentRestriction(SchemaCard schemaCard, Dice dice, Position position) {
        validateParameters(schemaCard, dice, position);

        if (schemaCard.isEmpty()) {
            return isOnTheBorder(position);
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

    private boolean isOnTheBorder(Position position) {
        return position.getRow() == 0 || position.getRow() == 3 || position.getCol() == 0 || position.getCol() == 4;
    }

    /**
     * Check value of cell (limitation in the Scheme) in the position requested
     * @param schemaCard of the currentPlayer
     * @param dice selected in the Draft Pool
     * @param position,  where the currentPlayer wants to put the die
     * @return true if the cell value is the same of the dice that the player wants to put into
     */
    public boolean cellValueRestriction(SchemaCard schemaCard, Dice dice, Position position) {
        validateParameters(schemaCard, dice, position);

        if (schemaCard.getCellList().get(position.getIndexArrayPosition()).getValue() != dice.getValue()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check colour of cell (limitation in the Scheme) in the position requested
     *
     * @param schemaCard of the currentPlayer
     * @param dice       selected in the Draft Pool
     * @param position,  where the currentPlayer wants to put the die
     * @return true if the cell colour is the same of the dice that the player wants to put into
     */
    public boolean cellColourRestriction(SchemaCard schemaCard, Dice dice, Position position) {
        validateParameters(schemaCard, dice, position);

        if (schemaCard.getCellList().get(position.getIndexArrayPosition()).getColour() != dice.getColour()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check adjacents cells of the position requested
     * to get if there is a adj dice (no on diagonals) of the same colour of the dice that the player wants to put
     * into the position of the Scheme
     *
     * @param schemaCard of the currentPlayer
     * @param dice       selected in the Draft Pool
     * @param position,  where the currentPlayer wants to put the die
     * @return true if the there aren't
     */
    public boolean adjacentColourRestriction(SchemaCard schemaCard, Dice dice, Position position) {
        validateParameters(schemaCard, dice, position);

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
     *
     * @param schemaCard of the currentPlayer
     * @param dice       selected in the Draft Pool
     * @param position,  where the currentPlayer wants to put the die
     * @return true if there aren't
     */
    public boolean adjacentValueRestriction(SchemaCard schemaCard, Dice dice, Position position) {
        validateParameters(schemaCard, dice, position);

        List<Cell> adjList = schemaCard.getAdjacents(position);
        for (Cell c : adjList) {
            if (c.getDice().getValue() == dice.getValue()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check the validation of the parametres entered
     *
     * @param schemaCard of the currentPlayer
     * @param dice selected in the Draft Pool
     * @param position,  where the currentPlayer wants to put the die
     */
    private void validateParameters(SchemaCard schemaCard, Dice dice, Position position) {
        if (schemaCard == null) {
            throw new IllegalArgumentException("ERROR: SchemeCard selected is null");
        }
        if (dice == null) {
            throw new IllegalArgumentException("ERROR: Die selected is null");
        }
        if (position == null || position.getIndexArrayPosition() < 0 || position.getIndexArrayPosition() > 19) {
            throw new IllegalArgumentException("ERROR: Position is null or insert an indexArrayPosition " +
                    "out of the range permitted");
        }
    }
}