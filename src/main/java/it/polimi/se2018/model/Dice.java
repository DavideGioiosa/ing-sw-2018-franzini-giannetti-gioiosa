package it.polimi.se2018.model;

import java.util.Random;

public class Dice {

    private final ColourEnum colour;
    private int value;

    public Dice (ColourEnum colour){
        this.colour = colour;
        value = 0;
    }

    public ColourEnum getColour(){
        return colour;
    }

    public int getValue() {
        return value;
    }

    private void roll(){
        Random randomGenerator = new Random();
        value = 1 + (int)randomGenerator.nextInt(6);
    }

    public void setValue(int val){
        if (val<=6 && val>=1) this.value = val;
    }

    public void increaseValue(){
        if (this.value<=5 && this.value>=1) this.value++;
    }

    public void decreaseValue(){
        if (this.value<=6 && this.value>=2) this.value--;
    }

    public void reRoll(){
        this.roll();
    }

    public void firstRoll(){
        if(this.value == 0){
            roll();
        }
    }

    public void oppositeValue(){
        if (this.value > 0) this.setValue(7 - value);
    }

}
