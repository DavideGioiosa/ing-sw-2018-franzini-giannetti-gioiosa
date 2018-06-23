package it.polimi.se2018.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * BoardDice represents the Draft Pool. It contains the dice extracted from the Die Bag at the beginning of the round
 * @author Cristian Giannetti
 */

public class BoardDice implements Serializable, DiceContainer {

    /**
     * List of Die in the Draft Pool
     */
    private List<Die> dieList;

    /**
     * Constructor creates an empty Draft Pool
     */
    public BoardDice(){
        dieList = new ArrayList<>();
    }

    /**
     * Copy Constructor
     * @param boardDice BoardDice to be cloned
     */
    private BoardDice(BoardDice boardDice){
        if (boardDice == null) throw new NullPointerException("ERROR: Try to clone a null BoardDice");
        this.dieList = new ArrayList<>();
        for(Die die: boardDice.dieList) this.dieList.add(die.getClone());
    }

    /**
     * Returns the list of the dice in the Draft Pool
     * @return the list of Die contained in the Draft Pool
     */
    public List<Die> getDieList() {
        return dieList;
    }

    /**
     * Inserts a die in the Draft Pool
     * @param die to be inserted in the Draft Pool
     */
    public void insertDie(Die die){
        if (die == null) throw new NullPointerException("ERROR: Impossible to insert a null Die");
        dieList.add(die);
    }

    /**
     * Takes a Die from the Draft Pool
     * @param index Position of dice to be taken from the Draft Pool
     * @return Die removed from the Draft Pool
     */
    public Die takeDie(int index){
        if (dieList.isEmpty()) throw new RuntimeException("ERROR: Draft Pool is empty");
        if (index < 0 || index >= dieList.size()) throw new IllegalArgumentException("ERROR: A die with this index does not exist");

        return dieList.remove(index);
    }

    /**
     * Gets a clone of Draft Pool
     * @return Cloned BoardDice
     */
    public BoardDice getClone(){
        return new BoardDice(this);
    }

    /**
     *
     * @param playerMove
     * @return
     */
    @Override
    public List<Die> pickDice(PlayerMove playerMove){

        List<Die> diePickedList = new ArrayList<>();
        if (playerMove.getDiceBoardIndex() == -1){
            int numberOfDice = this.dieList.size();
            for(int i = 0; i < numberOfDice; i++) diePickedList.add(takeDie(0));
        }else diePickedList.add(takeDie(playerMove.getDiceBoardIndex()));

        return diePickedList;
    }

    @Override
    public List<Die> exchangeDice(PlayerMove playerMove, List<Die> dieList){

        List<Die> diePickedList = new ArrayList<>();
        diePickedList.add(takeDie(playerMove.getDiceBoardIndex()));
        this.dieList.addAll(dieList);

        return diePickedList;
    }

    @Override
    public void leaveDice(PlayerMove playerMove, List<Die> dieList){
        this.dieList.addAll(dieList);
    }
}
