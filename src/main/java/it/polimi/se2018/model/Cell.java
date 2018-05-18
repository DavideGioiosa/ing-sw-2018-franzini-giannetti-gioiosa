package it.polimi.se2018.model;

/**
 * Public Class Cell
 * @author Davide Gioiosa
 */

public class Cell {

    /**
     * limitation dice value that a cell accepts
     */
    private int value;
    /**
     * limitation dice colour that a cell accepts
     */
    private ColourEnum colour;
    /**
     * dice placed in the cell
     */
    private Dice dice;

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
     * Check if is there a dice placed on the cell
     * @return true if there is any
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
     * Insertion of a die in the cell
     * @param dice to place in the cell, if the cell it's empty
     */
    public void insertDice(Dice dice) {
        if(dice == null){
            throw new NullPointerException("ERROR: Try to insert a dice null");
        }
        if (!isEmpty()) {
            throw new IllegalArgumentException("ERROR: Try to insert a dice in a not empty cell");
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
     * Remove the dice in the cell
     * used by Toolcard 2,3,4,12
     * @return die removed from the Scheme
     */
    public Dice pickDice(){
        if(this.getDice() == null){
            throw new NullPointerException("ERROR: Tried to pick a dice in an empty cell");
        }
        Dice dicePicked = getDice();
        this.dice = null;

        return dicePicked;
    }
}