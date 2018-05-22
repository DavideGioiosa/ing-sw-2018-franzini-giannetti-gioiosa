package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.List;

/**
 * BoardDice represents the Draft Pool. It contains the dice extracted from the Die Bag at the beginning of the round
 *
 * @author Cristian Giannetti
 */

public class BoardDice {

    /**
     * List of Die in the Draft Pool
     */
    private List<Die> dieList;

    /**
     * The Constructor creates an empty Draft Pool
     */
    public BoardDice(){
        dieList = new ArrayList<Die>();
    }

    /**
     * returns the list of the dice in the Draft Pool
     * @return the list of Die contained in the Draft Pool
     */
    public List<Die> getDieList() {
        return dieList;
    }

    /**
     * inserts a die in the Draft Pool
     * @param die to be inserted in the Draft Pool
     */
    public void insertDice(Die die){
        dieList.add(die);
    }

    /**
     * takes a Die from the Draft Pool
     * @param index Position of dice to be taken from the Draft Pool
     * @return Die removed from the Draft Pool
     */
    public Die takeDice(int index){
        if (dieList.size() == 0) throw new RuntimeException("ERROR: Draft Pool is empty");
        if (index < 0 || index >= dieList.size()) throw new RuntimeException("ERROR: A die with this index does not exist");

        Die die;
        die = dieList.remove(index);
        return die;
    }

}
