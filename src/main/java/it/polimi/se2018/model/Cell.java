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
     * @param value number of the Cell
     * @param colour
     */
    private Cell (int value, ColourEnum colour){
        this.value = value;
        this.colour = colour;
    }


    public boolean isEmpty (){
        if(getDice() == null) {
            return true;
        }
        else {
            return false;
        }
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public Dice getDice() {
        return dice;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setColour(ColourEnum colour){
        this.colour = colour;
    }

    public ColourEnum getColour() {
        return colour;
    }

    /**
     * used by Toolcard 2,3,4,12
     * @return dice removed from the Scheme
     */
    public Dice pickDice(){
        Dice dicePicked = getDice();
        setDice(null);

        return dicePicked;
    }
}