package it.polimi.se2018.model.cards;

import it.polimi.se2018.controller.OperationString;
import it.polimi.se2018.model.ColourEnum;

import java.io.Serializable;
import java.util.List;

/**
 * ToolCard allows the player to get Die placement benefits using it
 * @author Davide Gioiosa
 */

public class ToolCard extends Card implements Serializable {
    /**
     * Colour of Die to be used for using toolcard in single player mode
     */
    private ColourEnum colour;
    /**
     * Number of Token used in the game
     */
    private int token;

    private int indexOfTurn;

    private int minQuantity;

    private int maxQuantity;

    private String restriction;

    private List<List<OperationString>> commandLists;

    /**
     * Builder: create ToolCard
     * @param id identified of the card
     * @param name name of the card
     * @param description information about the card
     * @param colour colour of the ToolCard, used in SinglePlayer Mode
     */
    public ToolCard(int id, String name, String description, ColourEnum colour, int indexOfTurn, int minQuantity,
                    int maxQuantity, String restriction, List<List<OperationString>> commandLists) {
        super(id, name, description);
        if(colour == null){
            throw new IllegalArgumentException("ERROR: Insert colour null");
        }
        this.colour = colour;
        this.token = 0;
        //TODO: controlli vailidità parametri
        this.indexOfTurn = indexOfTurn;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
        this.restriction = restriction;
        this.commandLists = commandLists;
    }

    /**
     * Copy Constructor
     * @param toolCard ToolCard to be cloned
     */
    private ToolCard(ToolCard toolCard){
        super(toolCard);
        this.colour = toolCard.colour;
        this.token = toolCard.token;
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
        return this.token > 0;
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

    /**
     * Gets a clone of Tool Card
     * @return Cloned ToolCard
     */
    @Override
    public ToolCard getClone(){
        return new ToolCard(this);
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public int getIndexOfTurn() {
        return indexOfTurn;
    }

    public String getRestriction() {
        return restriction;
    }

    public List<List<OperationString>> getCommandLists() {
        return commandLists;
    }
}