package it.polimi.se2018.model.cards.public_card;

import it.polimi.se2018.model.Cell;

import java.util.ArrayList;
import java.util.List;
/**
* Used by DiffNumbersColumn, DiffNumbersRow, DiffNumbers to calculate the score
* @author Silvia Franzini
*/
public class PublicNumber {
    private List<Integer> numbers;

    public PublicNumber() {
        numbers = new ArrayList<>(6);
    }

    /**
     * Creating an array with the number of die of different numbers
     * @param cellList cells in the window pattern card
     * @return an array having the number of die of the same colour in every cell
     */
    public List<Integer> differentNumbers(List<Cell> cellList) {
        if(cellList == null){
            throw new IllegalArgumentException("ERROR: List does not exists");
        }
        for (Cell c : cellList) {
            if (!c.isEmpty()) {
                switch(c.getDice().getValue()){
                    case 1: numbers.set(0,numbers.get(0)+1); break;
                    case 2: numbers.set(1,numbers.get(1)+1); break;
                    case 3: numbers.set(2,numbers.get(2)+1); break;
                    case 4: numbers.set(3,numbers.get(3)+1); break;
                    case 5: numbers.set(4,numbers.get(4)+1); break;
                    case 6: numbers.set(5,numbers.get(5)+1); break;
                    default: break;
                }
            }
        }
        return numbers;

    }
}
