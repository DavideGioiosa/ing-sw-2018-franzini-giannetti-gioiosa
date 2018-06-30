package it.polimi.se2018.model;

import it.polimi.se2018.model.restriction.Restriction;

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
    private List<List<Die>> diceLists;

    /**
     * Builder: create an empty TrackBoard
     */
    public TrackBoard(){
        this.diceLists = new ArrayList<>();
    }

    /**
     * Copy Constructor
     * @param trackBoard TrackBoard to be cloned
     */
    private TrackBoard(TrackBoard trackBoard) {
        if (trackBoard == null) throw new NullPointerException("ERROR: Tried to create a null TrackBoard");

        this.diceLists = new ArrayList<>();

        for (List<Die> dieList : trackBoard.diceLists){
            List<Die> tempDieList = new ArrayList<>();

            for (Die die : dieList){
                tempDieList.add(die.getClone());
            }

            this.diceLists.add(tempDieList);
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
        diceLists.add(surplusDiceList);
    }

    /**
     * @return the TrackBoard with the dice placed on it
     */
    public List<List<Die>> getDiceList() {
        return diceLists;
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
        if (indexTbCell < 0 || indexTbCell >= diceLists.get(indexTb).size()){
            throw new IllegalArgumentException("ERROR: Insert indexTbCell out of the range permitted");
        }
        if(die == null){
            throw new IllegalArgumentException("ERROR: Insert null die");
        }
        diceLists.get(indexTb).add(die);

        return diceLists.get(indexTb).remove(indexTbCell);
    }

    /**
     * Gets a clone of TrackBoard
     * @return Cloned TrackBoard
     */
    public TrackBoard getClone(){
        return new TrackBoard(this);
    }

    @Override
    public void pickDice(PlayerMove playerMove, List<Die> dieList){
        dieList.add(this.diceLists.get(playerMove.getTrackBoardIndex()[0]).remove(playerMove.getTrackBoardIndex()[1]));
    }

    @Override
    public void exchangeDice(PlayerMove playerMove, List<Die> dieList){
        dieList.add(this.diceLists.get(playerMove.getTrackBoardIndex()[0]).remove(playerMove.getTrackBoardIndex()[1]));
        this.diceLists.get(playerMove.getTrackBoardIndex()[0]).add(dieList.get(0));
    }

    @Override
    public void leaveDice(PlayerMove playerMove, List<Die> dieList, List<Restriction> restrictionList){
        for (Die die: dieList) this.diceLists.get(playerMove.getTrackBoardIndex()[0]).add(die);
    }

    @Override
    public List<Die> getClonedDieList(){
        List<Die> dieClonedList = new ArrayList<>();
        for(List<Die> dieList: this.diceLists) dieClonedList.addAll(dieList);
        return dieClonedList;
    }
}