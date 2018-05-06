package it.polimi.se2018.model.cards.public_card;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used by DiffColourColumn, DiffColourRow, DiffColours to calculate the score
 * @author Silvia Franzini
 */
public class PublicColour {
    private ArrayList<Integer> colours;
    public PublicColour(){
        colours= new ArrayList<>(5);
    }

    /**
     * Creating an array with the number of die of different colours
     * @param cellList cells in the window pattern card
     * @return an array having the number of die of the same colour in every cell
     */
    public ArrayList<Integer> differentColours(List<Cell> cellList){
        for(Cell c : cellList){
            if(!c.isEmpty()){
                switch (c.getDice().getColour()){
                    case BLUE: colours.set(0,colours.get(0)+1); break;
                    case GREEN: colours.set(1,colours.get(1)+1); break;
                    case PURPLE: colours.set(2,colours.get(2)+1); break;
                    case RED: colours.set(3,colours.get(3)+1); break;
                    case YELLOW: colours.set(4,colours.get(4)+1); break;
                    default: break;
                }
            }
        }
        return colours;
    }

}