package it.polimi.se2018.model.cards.public_card;

import it.polimi.se2018.model.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used by BigNumbers, MiddleNumbers, SmallNumbers to calculate the score
 */
public class Shades {
    private List<Integer> numbers;

    public Shades(){
        numbers= new ArrayList<>(2);
    }

    /**
     * Creates an array having the number of die of the numbers chosen
     * @param cellList cells in the window pattern card
     * @param num1 lowest number of the shade
     * @param num2 highest number of the shade
     * @return an array having the number of die of the same colour in every cell
     */
    public List<Integer> operate(List<Cell> cellList, int num1, int num2){
        if(cellList == null){
            throw new IllegalArgumentException("ERROR: List does not exists");
        }
        for(Cell c: cellList){
            if(!c.isEmpty()){
                if(c.getDice().getValue()== num1){
                    numbers.set(0,numbers.get(0)+1);
                }
                else if(c.getDice().getValue()== num2){
                    numbers.set(1,numbers.get(1)+1);
                }
            }
        }
        return numbers;
    }
}
