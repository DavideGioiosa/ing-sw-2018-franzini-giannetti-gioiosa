package it.polimi.se2018.model.cards.publiccard;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.ColourEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class used by DiffColourColumn, DiffColourRow, DiffColours to calculate the score
 * @author Silvia Franzini
 */
public class PublicColour {
    private HashMap<ColourEnum,Integer> hashMapColours;
    /**
     * Builder method of PublicColour class
     */
    public PublicColour(){

        hashMapColours = new HashMap();
        hashMapColours.put(ColourEnum.BLUE, 0);
        hashMapColours.put(ColourEnum.GREEN, 0);
        hashMapColours.put(ColourEnum.PURPLE, 0);
        hashMapColours.put(ColourEnum.RED, 0);
        hashMapColours.put(ColourEnum.YELLOW, 0);


    }

    /**
     * Creating an array with the number of die of different colours
     * @param cellList cells in the window pattern card
     * @return an array having the number of die of the same colour in every cell
     */
    public HashMap<ColourEnum,Integer> differentColours(List<Cell> cellList){
        if(cellList == null){
            throw new NullPointerException("ERROR: List does not exists");
        }
        for(Cell c : cellList){
            if(!c.isEmpty()){

                hashMapColours.put(c.getDie().getColour(), hashMapColours.get(c.getDie().getColour())+1);

            }
        }
        return hashMapColours;
    }

}
