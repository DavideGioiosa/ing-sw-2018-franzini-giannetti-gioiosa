package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static it.polimi.se2018.model.Config.*;

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
     * The Constructor creates an equal number of Dice per colour.
     */
    public BagDice() {
        int i;
        dieList = new ArrayList<>();

        for (i = 0; i < NUMBEROFDICEPERCOLOUR; i++) {
            for (ColourEnum colourEnum : ColourEnum.values())
            insertDice(new Die(colourEnum));
        }
    }

    /**
     * insertDice inserts a Die in the bag.
     * @param die The Die to be inserted
     */
    public void insertDice(Die die){
        if(die == null) throw new NullPointerException("ERROR: Cannot insert a null Die");
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
