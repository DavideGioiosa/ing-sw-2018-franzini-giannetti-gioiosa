package it.polimi.se2018.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Public Class Trackboard
 * @author Davide Gioiosa
 */

public class TrackBoard {
    /**
     * List of dice in surplus placed in each round
     */
    private List<List<Dice>> diceList;

    /**
     * Builder: create an empty TrackBoard
     */
    public TrackBoard(){
        this.diceList = new ArrayList<>();
    }

    /**
     * Insertion of one or more dice on the Trackboard
     * @param surplusDiceList, it's a List because at the end of the Round you may have more
     * than one dice in surplus
     */
    public void insertDice (List<Dice> surplusDiceList){
        if(surplusDiceList == null){
            throw new NullPointerException("ERROR: Insert surplusDiceList null");
        }
        if (surplusDiceList.isEmpty() == true){
            throw new IllegalArgumentException("ERROR: No dice to put on the Trackboard");
        }
        diceList.add(surplusDiceList);
    }

    /**
     * @return the TrackBoard with the dice placed on it
     */
    public List<List<Dice>> getDiceList() {
        return diceList;
    }

    /**
     * Place the die selected from the Draft Pool into the Trackboard and removes the die selected from the TrackBoard
     * used by Toolcard 5
     * @param indexTb, index of the dice in the Trackboard
     * @param indexTbCell, index of the dice in the ArrayList in Trackboard[indexTb]
     * @param dice, dice of the BoardDice to exchange, added in the Trackboard
     * @return the dice removed in the Trackboard, replaced by dice
     */
    public Dice exchangeDice (int indexTb,int indexTbCell, Dice dice){
        if(indexTb < 0 || indexTb > 9){
            throw new IllegalArgumentException("ERROR: Insert indexTb out of the range permitted");
        }
        if (indexTbCell < 0 || indexTbCell >= diceList.get(indexTb).size()){
            throw new IllegalArgumentException("ERROR: Insert indexTbCell out of the range permitted");
        }
        if(dice == null){
            throw new NullPointerException("ERROR: Insert null die");
        }
        diceList.get(indexTb).add(dice);

        return diceList.get(indexTb).remove(indexTbCell);
    }
}