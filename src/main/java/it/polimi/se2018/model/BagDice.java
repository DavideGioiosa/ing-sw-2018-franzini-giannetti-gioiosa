package it.polimi.se2018.model;

import it.polimi.se2018.model.restriction.Restriction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static it.polimi.se2018.model.Config.*;

/**
 * BagDice represents the bag from which the dice are extracted.
 * @author Cristian Giannetti
 */

public class BagDice implements DiceContainer {

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
        dieList.add(new Die(die.getColour()));
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


    @Override
    public boolean pickDice(PlayerMove playerMove, List<Die> dieList, int min, int max){
        try {
            dieList.add(extractDice());
        }catch (RuntimeException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean exchangeDice(PlayerMove playerMove, List<Die> dieList){
        try {
            int size = dieList.size();
            for (int i = size; i > 0; i--) {
                insertDice(dieList.remove(0));
            }

            for (int i = 0; i < size; i++) {
                dieList.add(extractDice());
            }
        }catch(NullPointerException | IndexOutOfBoundsException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean leaveDice(PlayerMove playerMove, List<Die> dieList, List<Restriction> restrictionList){
        for(Die die: dieList) {
            insertDice(die);
        }
        return true;
    }

    public List<Die> getClonedDieList(){
        return new ArrayList<>();
    }
}
