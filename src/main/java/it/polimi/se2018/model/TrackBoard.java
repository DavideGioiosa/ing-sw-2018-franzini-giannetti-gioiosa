package it.polimi.se2018.model;


import java.util.List;

/**
 * Public Class Trackboard
 * @author Davide Gioiosa
 */

public class TrackBoard {
    private List<List<Dice>> diceList;

    /**
     * Builder: sets TrackBoard empty
     */
    public TrackBoard(){
        this.diceList = null;
    }

    /**
     * Insertion of one or more dice on the Trackboard
     * @param surplusDiceList, it's a List because at the end of the Round you may have more
     * than one dice in surplus
     */
    public void insertDice (List<Dice> surplusDiceList){
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
     * used by Toolcard 5
     * @param indexTb, index of the dice in the Trackboard
     * @param indexTbCell, index of the dice in the ArrayList in Trackboard[indexTb]
     * @param dice, dice of the BoardDice to exchange, added in the Trackboard
     * @return the dice removed in the Trackboard, replaced by dice
     */
    public Dice exchangeDice (int indexTb,int indexTbCell, Dice dice){
        diceList.get(indexTb).add(dice);

        return diceList.remove(indexTb).get(indexTbCell);
    }
}