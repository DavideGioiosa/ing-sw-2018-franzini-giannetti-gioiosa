package it.polimi.se2018.model;

import java.io.Serializable;

import static it.polimi.se2018.model.Config.DIE_MAX_VALUE;
import static it.polimi.se2018.model.Config.DIE_MIN_VALUE;

/**
 * Cell class represent a single cell of SchemaCard
 * @author Davide Gioiosa
 */

public class Cell implements Serializable {

    /**
     * Limitation die value that a cell accepts
     */
    private int value;
    /**
     * Limitation die colour that a cell accepts
     */
    private ColourEnum colour;
    /**
     * Die placed in the cell
     */
    private Die die;

    /**
     * Constructor creates a new cell with value and colour restriction
     * @param value value limitation that a cell may have, 0 if there's not limitation
     * @param colour limitation that a cell may have, null if there's not limitation
     */
    public Cell (int value, ColourEnum colour){
        if(value <0 || value >6) {
            throw new IllegalArgumentException("ERROR: Cannot set a value limitation not in the range permitted");
        }
        this.value = value;
        this.colour = colour;
    }

    /**
     * Copy Constructor
     * @param cell Cell to be cloned
     */
    private Cell(Cell cell){
        this.value = cell.value;
        this.colour = cell.colour;
        if(cell.die == null) this.die = null;
        else this.die = cell.die.getClone();
    }

    /**
     * Check if is there a die placed on the cell
     * @return true if there is any
     */
    public boolean isEmpty (){
        return getDie() == null;
    }

    /**
     * Insert a die in the cell
     * @param die to place in the cell, if the cell it's empty
     */
    public void insertDice(Die die) {
        if(die == null){
            throw new NullPointerException("ERROR: Try to insert a die null");
        }
        if (die.getValue() < DIE_MIN_VALUE || die.getValue() > DIE_MAX_VALUE) {
            throw new RuntimeException("ERROR: Try to insert a die with invalid Value");
        }
        if (!isEmpty()) {
            throw new IllegalArgumentException("ERROR: Try to insert a die in a not empty cell");
        }
        this.die = die;
    }

    /**
     * Gets die placed in the cell
     * @return the die placed in the cell
     */
    public Die getDie() {
        return die;
    }

    /**
     * Gets cell's Value limitation
     * @return the value limitation that a cell may have
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets cell's colour limitation
     * @return the colour limitation that a cell may have
     */
    public ColourEnum getColour() {
        return colour;
    }

    /**
     * Remove the die in the cell
     * @return die removed from the Scheme
     */
    public Die pickDice(){
        if(this.getDie() == null){
            throw new NullPointerException("ERROR: Tried to pick a die in an empty cell");
        }
        Die diePicked = getDie();
        this.die = null;

        return diePicked;
    }

    /**
     * Gets a clone of Cell
     * @return Cell cloned
     */
    public Cell getClone(){
        return new Cell(this);
    }
}