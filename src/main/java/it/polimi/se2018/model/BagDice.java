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
    private List<Die> dieList;

    /**
     * The Constructor creates an equal number of Die per colour.
     * @param numberOfDice the number of dice to be created
     */
    public BagDice(int numberOfDice) {
        if (numberOfDice<0) throw new IllegalArgumentException("ERROR: Impossible create a negative number of Die");
        int i;
        dieList = new ArrayList<Die>();

        for (i = 0; i < numberOfDice / 5; i++) {
            insertDice(new Die(ColourEnum.BLUE));
            insertDice(new Die(ColourEnum.GREEN));
            insertDice(new Die(ColourEnum.PURPLE));
            insertDice(new Die(ColourEnum.RED));
            insertDice(new Die(ColourEnum.YELLOW));
        }
    }

    /**
     * insertDice inserts a Die in the bag.
     * @param die The Die to be inserted
     */
    public void insertDice(Die die){
        dieList.add(die);
    }

    /**
     * extractDice extracts randomly one of the remaining Die
     * @return The Die extracted randomly
     */
    public Die extractDice(){
        if (dieList.size() < 1) throw new RuntimeException("ERROR: Bag Die is empty");

        int index;
        Die die;

        Random randomGenerator = new Random();
        index =  (int)randomGenerator.nextInt(dieList.size());
        die = dieList.remove(index);

        return die;
    }

    public int numberOfRemainingDice(){
        return dieList.size();
    }

}
