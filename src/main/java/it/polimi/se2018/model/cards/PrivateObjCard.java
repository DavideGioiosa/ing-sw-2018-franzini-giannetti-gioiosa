package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Card;
import it.polimi.se2018.model.ColourEnum;
/**
 * Private Objective Cards
 * @author Silvia Franzini
 */
public class PrivateObjCard extends Card {
    private ColourEnum colour;

    public PrivateObjCard(int id, String name, String description, ColourEnum colour) {
        super(id, name, description);
        this.colour = colour;
    }

    /**
     * Returns the private objective of the player
      * @return a colour from the ColourEnum
     */
    public ColourEnum getColour() {
        return colour;
    }

    /**
     * Method used to return the score due to the Private Objective Card
     * @param schemaCard, player's window pattern card
     * @return an int representing the score achieved
     */
    public int getScore(SchemaCard schemaCard){
        int sum = 0;
        for(Cell cell : schemaCard.getCellList()) {

            if(cell.getDice().getColour().equals(colour)){
                sum += cell.getDice().getValue();
            }
        }
        return sum;
    }
}
