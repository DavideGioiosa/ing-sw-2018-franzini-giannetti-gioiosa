package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.ColourEnum;
/**
 * Private Objective Cards
 * @author Silvia Franzini
 */
public class PrivateObjCard extends Card {
    private ColourEnum colour;

    /**
     * Builder Method of Private Objective Card class
     * @param id identifier of the card
     * @param name name of the card
     * @param description descriprion of the card
     * @param colour aim colour
     */
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
        if(schemaCard == null){
            throw new IllegalArgumentException("ERROR: Cannot use that Schema Card");
        }
        int sum = 0;
        for(Cell cell : schemaCard.getCellList()) {

            if(cell.getDice().getColour().equals(colour)){
                sum += cell.getDice().getValue();
            }
        }
        return sum;
    }
}
