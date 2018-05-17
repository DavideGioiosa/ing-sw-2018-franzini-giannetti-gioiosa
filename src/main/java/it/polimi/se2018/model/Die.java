package it.polimi.se2018.model;

import java.util.Random;

import static it.polimi.se2018.model.Config.*;

/**
 * Die class represents every single dice of the game
 *
 * @author Cristian Giannetti
 */

public class Die {

    /**
     * Indicates the colour of the dice
     */
    private final ColourEnum colour;

    /**
     * Indicates the value of the upper face of the die rolled. Value 0 indicates a die not rolled
     */
    private int value;

    /**
     * Constructor creates a dice with a specific colour and a value equal to zero
     * @param colour indicates the colour of the die
     */
    public Die(ColourEnum colour){
        if (colour == null) throw new IllegalArgumentException("ERROR: Cannot set a dice with no colour");
        this.colour = colour;
        this.value = 0;
    }

    /**
     * returns the colour of the die
     * @return colour of the die
     */
    public ColourEnum getColour(){
        return colour;
    }

    /**
     * returns the value of the die
     * @return the value of the dice front face on board
     */
    public int getValue() {
        return value;
    }

    /**
     * method assigns a value between 1 and 6 to a die
     */
    private void roll(){
        Random randomGenerator = new Random();
        value = 1 + (int)randomGenerator.nextInt(DIEMAXVALUE);
    }

    /**
     * sets a specific value to a die
     * @param val value to set on a die
     */
    public void setValue(int val){
        if (val > DIEMAXVALUE || val < DIEMINVALUE) throw new IllegalArgumentException("ERROR: Incorrect value of dice");

        this.value = val;
    }

    /**
     * increases the value of the die by one unit
     */
    public void increaseValue(){
        if (this.value >= DIEMAXVALUE) throw new RuntimeException("ERROR: Cannot increase the value of this dice");
        this.value++;
    }

    /**
     * decreases the value of the die by one unit
     */
    public void decreaseValue(){
        if (this.value <= DIEMINVALUE) throw new RuntimeException("ERROR: Cannot decrease the value of this dice");
        this.value--;
    }

    /**
     * is possible only if a particular toolCard is active
     */
    public void reRoll(){
        //TODO check if the toolcard is active
        this.roll();
    }

    /**
     * rolls the die for the first time
     */
    public void firstRoll(){
        if(this.value != 0) throw new RuntimeException("ERROR: This dice is already rolled");
        roll();
    }

    /**
     * Sets the value of the opposite face to the die
     */
    public void oppositeValue(){
        if(this.value == 0) throw new RuntimeException("ERROR: This dice is not rolled");
        this.setValue(DIEMINVALUE + DIEMAXVALUE - value);
    }

}
