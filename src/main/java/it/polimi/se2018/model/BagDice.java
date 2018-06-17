package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static it.polimi.se2018.model.Config.*;

/**
 * BagDice represents the bag from which the dice are extracted.
 * @author Cristian Giannetti
 */

public class BagDice {

    /**
     * List of dices contained in the bag
     */
    private List<Die> dieList;

    /**
     * The Constructor creates an equal number of Dice per colour.
     */
    public BagDice() {
        int i;
        dieList = new ArrayList<>();

        for (i = 0; i < NUMBER_OF_DICE_PER_COLOUR; i++) {
            for (ColourEnum colourEnum : ColourEnum.values())
            insertDice(new Die(colourEnum));
        }
    }

    /**
     * Inserts a Die in the bag.
     * @param die Die to be inserted
     */
    public void insertDice(Die die){
        if(die == null) throw new NullPointerException("ERROR: Cannot insert a null Die");
        dieList.add(die);
    }

    /**
     * Extracts randomly one of the remaining Die
     * @return Die extracted randomly
     */
    public Die extractDice(){
        if (dieList.isEmpty()) throw new RuntimeException("ERROR: Bag Die is empty");

        int index;
        Die die;

        Random randomGenerator = new Random();
        index = randomGenerator.nextInt(dieList.size());
        die = dieList.remove(index);

        return die;
    }

    public int numberOfRemainingDice(){
        return dieList.size();
    }

}
