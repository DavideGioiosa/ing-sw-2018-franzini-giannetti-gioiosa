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

    public ToolCard(int id, String name, String description, ColourEnum colour) {
        super(id, name, description);
        if (colour == null) throw new IllegalArgumentException("ERROR: Cannnot set null colour in Tool Card");
        this.colour = colour;
        this.token = 0;
    }

    public int getToken() {
        return token;
    }

    public ColourEnum getColour() {
        return colour;
    }

    /**
     * Check based on the number of tokens placed on a toolcard, if it's already used or not
     * @return true if the number of tokens is > 0
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