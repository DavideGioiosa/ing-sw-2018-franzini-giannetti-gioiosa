package it.polimi.se2018.model;

import java.io.Serializable;
import java.util.Random;

import static it.polimi.se2018.model.Config.*;

/**
 * Die class represents every single dice of the game
 * @author Cristian Giannetti
 */

public class Die implements Serializable {

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
        if (colour == null) throw new NullPointerException("ERROR: Cannot set a dice with no colour");
        this.colour = colour;
        this.value = 0;
    }

    /**
     * Copy Constructor
     * @param dieToClone Die to be cloned
     */
    Die (Die dieToClone){
        if (dieToClone != null) {
            this.colour = dieToClone.colour;
            this.value = dieToClone.value;
        }else throw new NullPointerException("ERROR: Try to clone a null die");
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
        value = 1 + randomGenerator.nextInt(DIE_MAX_VALUE);
    }

    /**
     * sets a specific value to a die
     * @param val value to set on a die
     */
    public void setValue(int val){
        if (val > DIE_MAX_VALUE || val < DIE_MIN_VALUE) throw new IllegalArgumentException("ERROR: Incorrect value of dice");

        this.value = val;
    }

    /**
     * Increases the value of the die by one unit
     */
    public void increaseValue(){
        if (this.value >= DIE_MAX_VALUE) throw new RuntimeException("ERROR: Cannot increase the value of this dice");
        this.value++;
    }

    /**
     * Decreases the value of the die by one unit
     */
    public void decreaseValue(){
        if (this.value <= DIE_MIN_VALUE) throw new RuntimeException("ERROR: Cannot decrease the value of this dice");
        this.value--;
    }

    /**
     * Possible only if a particular toolCard is active
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
        this.setValue(DIE_MIN_VALUE + DIE_MAX_VALUE - value);
    }

}
