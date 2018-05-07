package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * The class contains a list representing at the beginning of the game the entire deck of dice.
 * It has methods for insert a Dice and extract a random Dice.
 * The Constructor creates an equal number of Dices per colour.
 */

public class BagDice {

    private ArrayList<Dice> diceList;

    public BagDice(int numberOfDice){
        int i;
        Dice dice;
        for(i = 0; i < numberOfDice/5; i++){
            insertDice(new Dice(ColourEnum.BLUE));
            insertDice(new Dice(ColourEnum.GREEN));
            insertDice(new Dice(ColourEnum.PURPLE));
            insertDice(new Dice(ColourEnum.RED));
            insertDice(new Dice(ColourEnum.YELLOW));
        }
    }

    public ArrayList<Dice> getDiceList() {
        return diceList;
    }

    public void insertDice(Dice dice){
        diceList.add(dice);
    }

    public Dice extractDice(){
        int index;
        Dice dice;

        Random randomGenerator = new Random();
        index =  (int)randomGenerator.nextInt(diceList.size());
        dice = diceList.remove(index);

        return dice;
    }

}
