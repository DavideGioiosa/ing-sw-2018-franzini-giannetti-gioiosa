package it.polimi.se2018.model.cards.publiccard;

import it.polimi.se2018.model.cards.SchemaCard;

import java.io.Serializable;

/**
 * interface used to implement the strategy pattern for the Public Objective Cards
 * @author Silvia Franzini
 */
public interface ScoreStrategy extends Serializable{

    /**
     * method that the classes are going to implement
     * @param schemaCard, window pattern card
     * @return returns the score achieved by the usage of a certain card
     */
    int getScore(SchemaCard schemaCard);

}
