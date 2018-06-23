package it.polimi.se2018.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * TrackBoard class contains the discarded dice from every round
 * @author Davide Gioiosa
 */

public class TrackBoard implements Serializable, DiceContainer {
    /**
     * List of dice in surplus placed in each round
     */
    private List<List<Die>> diceList;

    /**
     * Builder: create an empty TrackBoard
     */
    public TrackBoard(){
        this.diceList = new ArrayList<>();
    }

    /**
     * Copy Constructor
     * @param trackBoard TrackBoard to be cloned
     */
    private TrackBoard(TrackBoard trackBoard) {
        if (trackBoard == null) throw new NullPointerException("ERROR: Tried to create a null TrackBoard");

        this.diceList = new ArrayList<>();

        for (List<Die> dieList : trackBoard.diceList){
            List<Die> tempDieList = new ArrayList<>();

            for (Die die : dieList){
                tempDieList.add(die.getClone());
            }

            this.diceList.add(tempDieList);
        }
    }

    /**
     * Insertion of one or more dice on the Trackboard
     * @param surplusDiceList, it's a List because at the end of the Round you may have more
     * than one dice in surplus
     */
    public void insertDice (List<Die> surplusDiceList){
        if(surplusDiceList == null){
            throw new NullPointerException("ERROR: Insert surplusDiceList null");
        }
        if (surplusDiceList.isEmpty()){
            throw new IllegalArgumentException("ERROR: No dice to put on the Trackboard");
        }
        diceList.add(surplusDiceList);
    }

    /**
     * @return the TrackBoard with the dice placed on it
     */
    public List<List<Die>> getDiceList() {
        return diceList;
    }

    /**
     * Place the die selected from the Draft Pool into the Trackboard and removes the die selected from the TrackBoard
     * used by Toolcard 5
     * @param indexTb index of the die in the Trackboard
     * @param indexTbCell index of the die in the ArrayList in Trackboard[indexTb]
     * @param die die of the BoardDice to exchange, added in the Trackboard
     * @return the die removed in the Trackboard, replaced by die
     */
    public Die exchangeDice (int indexTb, int indexTbCell, Die die){
        if(indexTb < 0 || indexTb > 9){
            throw new IllegalArgumentException("ERROR: Insert indexTb out of the range permitted");
        }
        if (indexTbCell < 0 || indexTbCell >= diceList.get(indexTb).size()){
            throw new IllegalArgumentException("ERROR: Insert indexTbCell out of the range permitted");
        }
        if(die == null){
            throw new IllegalArgumentException("ERROR: Insert null die");
        }
        diceList.get(indexTb).add(die);

        return diceList.get(indexTb).remove(indexTbCell);
    }

    /**
     * Gets a clone of TrackBoard
     * @return Cloned TrackBoard
     */
    public TrackBoard getClone(){
        return new TrackBoard(this);
    }

    /**
     *
     * @param playerMove
     * @return
     */
    @Override
    public List<Die> pickDice(PlayerMove playerMove){

        List<Die> dieList = new ArrayList<>();
        dieList.add(this.diceList.get(playerMove.getTrackBoardIndex()[0]).remove(playerMove.getTrackBoardIndex()[1]));

        return dieList;
    }

    @Override
    public List<Die> exchangeDice(PlayerMove playerMove, List<Die> dieList){
        List<Die> dieLeavedList = new ArrayList<>();

        dieLeavedList.add(this.diceList.get(playerMove.getTrackBoardIndex()[0]).remove(playerMove.getTrackBoardIndex()[1]));
        diceList.get(playerMove.getTrackBoardIndex()[0]).add(dieList.get(0));

        return dieLeavedList;
    }

    @Override
    public void leaveDice(PlayerMove playerMove, List<Die> dieList){
        for (Die die: dieList) this.diceList.get(playerMove.getTrackBoardIndex()[0]).add(die);
    }
}