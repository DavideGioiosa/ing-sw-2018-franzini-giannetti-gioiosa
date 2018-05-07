package it.polimi.se2018.model;


import java.util.ArrayList;

/**
 * Public Class Trackboard
 * @author Davide Gioiosa
 */

public class TrackBoard {
    private ArrayList<ArrayList<Dice>> diceList;

    public TrackBoard(){
        this.diceList = null;
    }

    /**
     * Insertion of one or more dice on the Trackboard
     * @param surplusDiceList, it's a List because at the end of the Round you may have more
     * than one dice in surplus
     */
    public void insertDice (ArrayList<Dice> surplusDiceList){
        diceList.add(surplusDiceList);
    }

    public ArrayList<ArrayList<Dice>> getDiceList() {
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