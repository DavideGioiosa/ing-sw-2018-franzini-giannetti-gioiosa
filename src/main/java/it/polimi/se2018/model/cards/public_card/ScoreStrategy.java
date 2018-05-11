package it.polimi.se2018.model.cards.public_card;

import it.polimi.se2018.model.cards.SchemaCard;

/**
 * interface used to implement the strategy pattern for the Public Objective Cards
 * @author Silvia Franzini
 */
public interface ScoreStrategy {
    /**
     * method that the classes are going to implement
     * @param schemaCard, window pattern card
     */

    int getScore(SchemaCard schemaCard);


}
