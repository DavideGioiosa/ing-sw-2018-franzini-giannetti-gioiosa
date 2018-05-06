package it.polimi.se2018.model;

import java.util.List;

/**
 * Contains a list of the cards extracted
 * @author Silvia Franzini
 */
public class BoardCard {
    private static BoardCard instance= new BoardCard();
    private List<PublicObjCard> publicObjCardList;
    private List<ToolCard> toolCardList;

    private BoardCard(){}

    /**
     * Used to get the instance of BoardCard as it is implemented with a Singleton pattern
     * @return
     */
    public static BoardCard getInstance(){
        return instance;
    }

    /**
     * Allows the initialization of the Public Objective Card list on board
     * @param publicObjCardList list of Public Objective Cards extracted
     */
    public void setPublicObjCardList(List<PublicObjCard> publicObjCardList) {
        this.publicObjCardList = publicObjCardList;
    }

    /**
     * Allows the initialization of the Tool Card list on board
     * @param toolCardList list of Tool Cards extracted
     */
    public void setToolCardList(List<ToolCard> toolCardList) {
        this.toolCardList = toolCardList;
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
