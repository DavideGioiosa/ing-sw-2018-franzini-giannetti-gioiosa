package it.polimi.se2018.model;

/**
 * Public Class Cell
 * @author Davide Gioiosa
 */

public class Cell {
    private int value;
    private ColourEnum colour;
    private Dice dice;

    /**
     * @param value value limitation that a cell may have
     * @param colour limitation that a cell may have
     */
    public Cell (int value, ColourEnum colour){
        if(value <1 || value >6) {
            throw new IllegalArgumentException("ERROR: Cannot set a value limitation not in the range permitted");
        }
        this.value = value;
        this.colour = colour;
    }

    /**
     * Check if there is a dice placed on the cell
     * @return true if there is
     */
    public boolean isEmpty (){
        if(getDice() == null) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @param dice is placed in the cell, if it's empty
     */
    public void insertDice(Dice dice) {
        if(dice == null){
            throw new IllegalArgumentException("ERROR: Try to insert a dice null");
        }
        if (this.getDice() == null) {

        }
        this.dice = dice;
    }

    /**
     * @return the dice placed in the cell
     */
    public Dice getDice() {
        return dice;
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
     * used by Toolcard 2,3,4,12
     * @return dice removed from the Scheme
     */
    public Dice pickDice(){
        if(this.getDice() == null){
            throw new IllegalArgumentException("ERROR: Tried to pick a dice in an empty cell");
        }
        Dice dicePicked = getDice();
        insertDice(null);

        return dicePicked;
    }
}