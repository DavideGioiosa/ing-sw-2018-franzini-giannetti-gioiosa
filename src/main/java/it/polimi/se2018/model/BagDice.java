package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.Random;

public class BagDice {

    private ArrayList<Dice> diceList;

    public BagDice(int numberOfDice){
        int i;
        Dice dice;
        for(i = 0; i < numberOfDice/5; i++){
            dice = new Dice(ColourEnum.BLUE);
            insertDice(dice);
            dice = new Dice(ColourEnum.GREEN);
            insertDice(dice);
            dice = new Dice(ColourEnum.PURPLE);
            insertDice(dice);
            dice = new Dice(ColourEnum.RED);
            insertDice(dice);
            dice = new Dice(ColourEnum.YELLOW);
            insertDice(dice);
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
        dice = diceList.get(index);
        diceList.remove(index);

        return dice;
    }

}
