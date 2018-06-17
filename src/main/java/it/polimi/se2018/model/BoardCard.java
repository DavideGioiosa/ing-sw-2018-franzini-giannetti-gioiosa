package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.publiccard.PublicObjCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains a list of the cards extracted
 * @author Silvia Franzini
 */
public class BoardCard implements Serializable {

    /**
     * List of Public Objective Card on board
     */
    private List<PublicObjCard> publicObjCardList;
    /**
     * List of Tool Card on Board
     */
    private List<ToolCard> toolCardList;

    /**
     * Constructor method of BoardCard class
     * @param publicObjCardList List of Public Objective Cards extracted
     * @param toolCardList List of Tool Cards extracted
     */
    public BoardCard(List<PublicObjCard> publicObjCardList, List<ToolCard> toolCardList){
        if(publicObjCardList == null){
         throw new NullPointerException("ERROR: PublicObj card not existing");
        }
        if(toolCardList == null){
            throw new NullPointerException("ERROR: Tool card not existing");
        }
        this.publicObjCardList = publicObjCardList;
        this.toolCardList = toolCardList;
    }

    /**
     * Copy Constructor
     * @param boardCard BoardCard to be cloned
     */
    public BoardCard(BoardCard boardCard){
        if(boardCard == null) throw new NullPointerException("ERROR: Try to clone a null BoardCard");

        this.publicObjCardList = new ArrayList<>();
        for(PublicObjCard publicObjCard: boardCard.publicObjCardList) this.publicObjCardList.add(new PublicObjCard(publicObjCard));

        this.toolCardList = new ArrayList<>();
        for(ToolCard toolCard: boardCard.toolCardList) this.toolCardList.add(new ToolCard(toolCard));
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
