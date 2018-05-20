package it.polimi.se2018.model;

/**
 * Public Class Cell
 * @author Davide Gioiosa
 */

public class Cell {

    /**
     * limitation die value that a cell accepts
     */
    private int value;
    /**
     * limitation die colour that a cell accepts
     */
    private ColourEnum colour;
    /**
     * die placed in the cell
     */
    private Die die;

    /**
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
     * Check if is there a die placed on the cell
     * @return true if there is any
     */
    public boolean isEmpty (){
        if(getDie() == null) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @param die to place in the cell, if the cell it's empty
     */
    public void insertDice(Die die) {
        if(die == null){
            throw new NullPointerException("ERROR: Try to insert a die null");
        }
        if (!isEmpty()) {
            throw new IllegalArgumentException("ERROR: Try to insert a die in a not empty cell");
        }
        this.die = die;
    }

    /**
     * @return the die placed in the cell
     */
    public Die getDie() {
        return die;
    }

    /**
     * @return the value limitation that a cell may have
     */
    public int getValue() {
        return value;
    }

    /**
     * @return the colour limitation that a cell may have
     */
    public ColourEnum getColour() {
        return colour;
    }

    /**
     * Remove the die in the cell
     * used by Toolcard 2,3,4,12
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
}