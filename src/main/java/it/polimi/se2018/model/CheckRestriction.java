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
     * @param die selected in the Draft Pool
     * @param position where the currentPlayer wants to put the die
     * Check adjacents cells (also diagonal ones) of the position requested
     * to get if there are any die inside and
     * @return true if there's at least one adj die
     */
    public boolean adjacentRestriction(SchemaCard schemaCard, Die die, Position position) {
        validateParameters(schemaCard, die, position);

        List<Cell> adjList = schemaCard.getAdjacents(position);
        for (Cell c : adjList) {
            if (c.getDie() != null) {
                return true;
            }
        }

        List<Cell> adjDiagList = schemaCard.getDiagonalAdjacents(position);
        for (Cell d : adjDiagList) {
            if (d.getDie() != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * called if the Scheme is empty, check if the position where
     * the player wants to put the die is on the edge of his Scheme
     * @param position where the currentPlayer wants to put the die
     * @return true if the position is valid
     */
    public boolean isOnTheBorder(Position position) {
        if (position == null || position.getIndexArrayPosition() < 0 || position.getIndexArrayPosition() > 19) {
            throw new IllegalArgumentException("ERROR: Position is null or insert an indexArrayPosition " +
                    "out of the range permitted");
        }
        return position.getRow() == 0 || position.getRow() == 3 || position.getCol() == 0 || position.getCol() == 4;
    }

    /**
     * Check value of cell (limitation in the Scheme) in the position requested
     * @param schemaCard of the currentPlayer
     * @param die selected in the Draft Pool
     * @param position where the currentPlayer wants to put the die
     * @return true if the cell value is the same of the die that the player wants to put into
     */
    public boolean cellValueRestriction(SchemaCard schemaCard, Die die, Position position) {
        validateParameters(schemaCard, die, position);
        return schemaCard.getCellList().get(position.getIndexArrayPosition()).getValue() == die.getValue();
    }

    /**
     * Check colour of cell (limitation in the Scheme) in the position requested
     *
     * @param schemaCard of the currentPlayer
     * @param die selected in the Draft Pool
     * @param position where the currentPlayer wants to put the die
     * @return true if the cell colour is the same of the die that the player wants to put into
     */
    public boolean cellColourRestriction(SchemaCard schemaCard, Die die, Position position) {
        validateParameters(schemaCard, die, position);
        return schemaCard.getCellList().get(position.getIndexArrayPosition()).getColour() == die.getColour();
    }

    /**
     * Check adjacents cells with a die inside, of the position requested,
     * to get if there is a adj die (no on diagonals) of the same colour of the die that the player wants to put
     * into the position of the Scheme
     *
     * @param schemaCard of the currentPlayer
     * @param die selected in the Draft Pool
     * @param position where the currentPlayer wants to put the die
     * @return true if the there aren't any
     */
    public boolean adjacentColourRestriction(SchemaCard schemaCard, Die die, Position position) {
        validateParameters(schemaCard, die, position);

        List<Cell> adjList = schemaCard.getAdjacents(position);
        for (Cell c : adjList) {
            if(!c.isEmpty()) {
                if (c.getDie().getColour() == die.getColour()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check adjacents cells with a die inside, of the position requested,
     * to get if there is a adj die (no on diagonals) of the same value of the die that the player wants to put
     * into the position of the Scheme
     *
     * @param schemaCard of the currentPlayer
     * @param die selected in the Draft Pool
     * @param position where the currentPlayer wants to put the die
     * @return true if there aren't any
     */
    public boolean adjacentValueRestriction(SchemaCard schemaCard, Die die, Position position) {
        validateParameters(schemaCard, die, position);

        List<Cell> adjList = schemaCard.getAdjacents(position);
        for (Cell c : adjList) {
            if(!c.isEmpty()) {
                if (c.getDie().getValue() == die.getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check the validation of the parametres entered
     *
     * @param schemaCard of the currentPlayer
     * @param die selected in the Draft Pool
     * @param position where the currentPlayer wants to put the die
     */
    private void validateParameters(SchemaCard schemaCard, Die die, Position position) {
        if (schemaCard == null) {
            throw new NullPointerException("ERROR: SchemeCard selected is null");
        }
        if (die == null) {
            throw new NullPointerException("ERROR: Die selected is null");
        }
        if (position == null){
            throw new NullPointerException("ERROR: Position selected is null");
        }
        if (position.getIndexArrayPosition() < 0 || position.getIndexArrayPosition() > 19) {
            throw new IllegalArgumentException("Index inserted is out of the range permitted");
        }

    }
}