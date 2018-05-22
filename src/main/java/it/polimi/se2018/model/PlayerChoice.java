package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.player.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to send user's choices during match setup
 * @author Silvia Franzini
 */
public class PlayerChoice {
    private User user;
    private List<SchemaCard> schemaCardList;
    private List<ColourEnum> colourEnumList;
    private SchemaCard chosenSchema;
    private ColourEnum chosenColour;

    /**
     * Builder method of PlayerChoice class
     * @param user User making choices
     */
    public PlayerChoice(User user){
        this.user=user;
        schemaCardList = new ArrayList<>();
        colourEnumList = new ArrayList<>();
    }

    /**
     * Setter method for SchemaCard list
     * @param schemaCardList list of Schema Cards
     */
    public void setSchemaCardList(List<SchemaCard> schemaCardList){
        this.schemaCardList= schemaCardList;
    }

    /**
     * Setter method for Colours list
     * @param colourEnumList list of available colours
     */
    public void setColourEnumList(List<ColourEnum> colourEnumList) {
        this.colourEnumList = colourEnumList;
    }

    /**
     * Getter method for SchemaCard list
     * @return the list of available Schema Cards
     */
    public List<SchemaCard> getSchemaCardList() {
        return schemaCardList;
    }

    /**
     * Getter method for ColourEnum list
     * @return the list of available colours
     */
    public List<ColourEnum> getColourEnumList() {
        return colourEnumList;
    }


    public void setChosenColour(ColourEnum chosenColour) {
        this.chosenColour = chosenColour;
    }

    public void setChosenSchema(SchemaCard chosenSchema) {
        this.chosenSchema = chosenSchema;
    }

    public ColourEnum getChosenColour() {
        return chosenColour;
    }

    public SchemaCard getChosenSchema() {
        return chosenSchema;
    }

    public User getUser() {
        return user;
    }
}
