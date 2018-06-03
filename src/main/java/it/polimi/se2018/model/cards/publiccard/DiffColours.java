package it.polimi.se2018.model.cards.publiccard;

import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.cards.SchemaCard;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Public Class Different Colours
 * @author Silvia Franzini
 */
public class DiffColours implements ScoreStrategy {
    /**
     * Class implementing pattern Strategy for the evaluation of the player's score
     * due to the Public Objective Cards
     * @param schema player's window pattern card
     * @return an int representing the player's score
     */
    @Override
    public int getScore(SchemaCard schema){

        PublicColour pub = new PublicColour();
        HashMap<ColourEnum,Integer> colours = pub.differentColours(schema.getCellList());
        Integer i = Collections.min(colours.entrySet(), Map.Entry.comparingByValue()).getValue();

        return 4*i;
    }
}