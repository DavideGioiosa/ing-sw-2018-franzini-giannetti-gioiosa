package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.ColourEnum;

import java.io.Serializable;

/**
 * Private Objective Cards
 * @author Silvia Franzini
 */
public class PrivateObjCard extends Card implements Serializable {

    /**
     * Colour of the Dice to be collected for bonus points
     */
    private ColourEnum colour;

    /**
     * Builder Method of Private Objective Card class
     * @param id identifier of the card
     * @param name name of the card
     * @param description description of the card
     * @param colour aim colour
     */
    public PrivateObjCard(int id, String name, String description, ColourEnum colour) {
        super(id, name, description);
        this.colour = colour;
    }

    /**
     * Copy Constructor
     * @param privateObjCard Private Objective Card to be cloned
     */
    private PrivateObjCard(PrivateObjCard privateObjCard){
        super(privateObjCard);
        this.colour = privateObjCard.colour;
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
     * @param schemaCard player's window pattern card
     * @return an int representing the score achieved
     */
    public int getScore(SchemaCard schemaCard){
        if(schemaCard == null){
            throw new IllegalArgumentException("ERROR: Cannot use that Schema Card");
        }
        int sum = 0;
        for(Cell cell : schemaCard.getCellList()) {

            if(cell.getDie()!= null && cell.getDie().getColour().equals(colour)){
                sum += cell.getDie().getValue();
            }
        }
        return sum;
    }

    /**
     * Gets a clone of Private Objective Card
     * @return Cloned PrivateObjCard
     */
    @Override
    public PrivateObjCard getClone(){
        return new PrivateObjCard(this);
    }
}
