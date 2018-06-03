package it.polimi.se2018.model.cards.publiccard;


import it.polimi.se2018.model.Cell;

import java.util.ArrayList;
import java.util.List;
/**
* Used by DiffNumbersColumn, DiffNumbersRow, DiffNumbers to calculate the score
* @author Silvia Franzini
*/
public class PublicNumber {
    private List<Integer> numbers;

    /**
     * Buider method of PublicNumber class
     */
    public PublicNumber() {
        numbers = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            numbers.add(0);
        }
    }

    /**
     * Creating an array with the number of die of different numbers
     * @param cellList cells in the window pattern card
     * @return an array having the number of die of the same colour in every cell
     */
    public List<Integer> differentNumbers(List<Cell> cellList) {
        if(cellList == null){
            throw new NullPointerException("ERROR: List does not exists");
        }
        for (Cell c : cellList) {
            if (!c.isEmpty()) {

                numbers.set(c.getDie().getValue()-1, numbers.get(c.getDie().getValue()-1)+1);
            }
        }
        return numbers;

    }
}
