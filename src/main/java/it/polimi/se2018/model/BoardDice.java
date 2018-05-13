package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.List;

/**
 * BoardDice represents the Draft Pool. It contains the dice extracted from the Dice Bag at the beginning of the round
 *
 * @author Cristian Giannetti
 */

public class BoardDice {

    /**
     * List of Dice in the Draft Pool
     */
    private List<Dice> diceList;

    /**
     * The Constructor creates an empty Draft Pool
     */
    public BoardDice(){
        diceList = new ArrayList<Dice>();
    }

    /**
     * returns the list of the dice in the Draft Pool
     * @return the list of Dice contained in the Draft Pool
     */
    public List<Dice> getDiceList() {
        return diceList;
    }

    /**
     * inserts a dice in the Draft Pool
     * @param dice to be inserted in the Draft Pool
     */
    public void insertDice(Dice dice){
        diceList.add(dice);
    }

    /**
     * takes a Dice from the Draft Pool
     * @param index The position of the dice to be taken from the Draft Pool
     * @return Dice removed from the Draft Pool
     */
    public Dice takeDice(int index){
        if (diceList.size() == 0) throw new RuntimeException("ERROR: Draft Pool is empty");
        if (index < 0 || index > diceList.size() -1) throw new RuntimeException("ERROR: A dice with this index does not exist");

        Dice dice;
        dice = diceList.remove(index);
        return dice;
    }

}
