package it.polimi.se2018.model;

import java.util.ArrayList;

/**
 * BoardDice class contains the dice extracted from the Dice Bag at the beginning of the round
 */

public class BoardDice {

    private ArrayList<Dice> diceList;

    public BoardDice(){
        diceList = null;
    }

    public ArrayList<Dice> getDiceList() {
        return diceList;
    }

    public void insertDice(Dice dice){
        diceList.add(dice);
    }

    public Dice takeDice(int index){
        Dice dice;
        dice = diceList.remove(index);
        return dice;
    }

}
