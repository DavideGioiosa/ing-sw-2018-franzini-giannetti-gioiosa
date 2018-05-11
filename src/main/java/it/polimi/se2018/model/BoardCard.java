package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.public_card.PublicObjCard;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains a list of the cards extracted
 * @author Silvia Franzini
 */
public class BoardCard {
    private List<PublicObjCard> publicObjCardList;
    private List<ToolCard> toolCardList;

    private BoardCard(List<PublicObjCard> publicObjCardArrayList, List<ToolCard> toolCardList){
        this.publicObjCardList=publicObjCardArrayList;
        this.toolCardList=toolCardList;
    }

    /**
     * Returns the list of Public Cards extracted
     * @return a list of Public Cards
     */
    public List<PublicObjCard> getPublicObjCardList() {
        return publicObjCardList;
    }

    /**
     * Returns the list of Tool Cards extracted
     * @return a list of Tool Cards
     */
    public List<ToolCard> getToolCardList() {
        return toolCardList;
    }
}
