package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.ColourEnum;

/**
 * Public Class Toolcard
 * @author Davide Gioiosa
 */

public class ToolCard extends Card {
    private ColourEnum colour;
    private int token;

    /**
     * Builder: create ToolCard
     * @param id identified of the card
     * @param name name of the card
     * @param description information about the card
     * @param colour colour of the ToolCard, used in SinglePlayer Mode
     */
    public ToolCard(int id, String name, String description, ColourEnum colour) {
        super(id, name, description);
        if(colour == null){
            throw new IllegalArgumentException("ERROR: Insert colour null");
        }
        this.colour = colour;
        this.token = 0;
    }
    
    /**
     * @return colour of the ToolCard, used in SinglePlayer Mode
     */
    public ColourEnum getColour() {
        return colour;
    }

    /**
     * @return number of tokens placed on the ToolCard
     */
    public int getToken() {
        return token;
    }

    /**
     * Check based on the number of tokens placed on a ToolCard, if it's already used or not
     * @return true if the number of tokens is greater than 0
     */
    public boolean isUsed() {
        if (this.token < 0) {
            throw new RuntimeException("ERROR: Negative number of token on a ToolCard");
        }
        if (this.token > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * update the number of tokens placed on a Toolcard
     * @param token is 1, if the ToolCard is not used yet, or 2 if it's already been used
     */
    public void updateToken(int token) {
        if (token < 0 || token > 2) {
            throw new IllegalArgumentException("ERROR: insert negative number of token or more than two tokens");
        }
        this.token += token;
    }

}