package it.polimi.se2018.model.cards;

package groupid.model.cards;

import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.ColourEnum;

/**
 * Public Class Toolcard
 * @author Davide Gioiosa
 */

public class ToolCard extends Card {
    private ColourEnum colour;
    private int token;

    public ToolCard (int id, String name, String description, ColourEnum colour){
        super(id, name, description);
        this.colour = colour;
    }

    public boolean isUsed(){
        if (token > 0){
            return true;
        }
        else {
            return false;
        }
    }
}