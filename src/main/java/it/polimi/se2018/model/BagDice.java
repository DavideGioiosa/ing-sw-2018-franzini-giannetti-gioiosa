package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * BagDice represents the bag from which the dice are extracted.
 *
 * @author Cristian Giannetti
 */

public class BagDice {

    /**
     * The list of dices contained in the bag
     */
    private List<Dice> diceList;

    /**
     * The Constructor creates an equal number of Dice per colour.
     * @param numberOfDice the number of dice to be created
     */
    public BagDice(int numberOfDice) {
        int i;
        diceList = new ArrayList<Dice>();

        for (i = 0; i < numberOfDice / 5; i++) {
            insertDice(new Dice(ColourEnum.BLUE));
            insertDice(new Dice(ColourEnum.GREEN));
            insertDice(new Dice(ColourEnum.PURPLE));
            insertDice(new Dice(ColourEnum.RED));
            insertDice(new Dice(ColourEnum.YELLOW));
        }
    }

    /**
     * insertDice inserts a Dice in the bag.
     * @param dice The Dice to be inserted
     */
    public void insertDice(Dice dice){
        diceList.add(dice);
    }

    /**
     * extractDice extracts randomly one of the remaining Dice
     * @return The Dice extracted randomly
     */
    public Dice extractDice(){
        if (diceList.size() < 1) throw new RuntimeException("ERROR: Bag Dice is empty");

        int index;
        Dice dice;

        Random randomGenerator = new Random();
        index =  (int)randomGenerator.nextInt(diceList.size());
        dice = diceList.remove(index);

        return dice;
    }

}
