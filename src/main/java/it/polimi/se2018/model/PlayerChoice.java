package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.PrivateObjCard;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.player.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to send user's choices during match setup
 * @author Silvia Franzini
 */
public class PlayerChoice implements Serializable {
    /**
     * User referred to the choices
     */
    private User user;
    /**
     * List of SchemaCard that the User can choice
     */
    private List<SchemaCard> schemaCardList;
    /**
     * List of Window Frame colour that the User can choice
     */
    private List<ColourEnum> colourEnumList;
    /**
     * Chosen Window Frame chosen
     */
    private ColourEnum chosenColour;
    /**
     * Chosen SchemaCard
     */
    private int idChosenSchema;
    /**
     * User's Private Objective Card
     */
    private PrivateObjCard privateObjCard;

    /**
     * Builder method of PlayerChoice class
     * @param user User making choices
     */
    public PlayerChoice(User user){
        if(user == null){
            throw new NullPointerException("Insertion of a null user");
        }

        this.user = user;
        schemaCardList = new ArrayList<>();
        colourEnumList = new ArrayList<>();
        chosenColour = null;
        idChosenSchema = 0;
        privateObjCard = null;
    }

    /**
     * Getter method for the user to whom the choice is referred
     * @return User to whom the choice is referred
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter method for SchemaCard list
     * @param schemaCardList list of Schema Cards
     */
    public void setSchemaCardList(List<SchemaCard> schemaCardList){
        if (schemaCardList == null) throw new NullPointerException("Invalid List of Schema Card");
        this.schemaCardList = schemaCardList;
    }

    /**
     * Getter method for SchemaCard list
     * @return the list of available Schema Cards
     */
    public List<SchemaCard> getSchemaCardList() {
        return schemaCardList;
    }

    /**
     * Setter method for Colours list
     * @param colourEnumList list of available colours
     */
    public void setColourEnumList(List<ColourEnum> colourEnumList) {
        if (colourEnumList == null) throw new NullPointerException("Invalid List of colour");
        this.colourEnumList = colourEnumList;
    }

    /**
     * Getter method for ColourEnum list
     * @return the list of available colours
     */
    public List<ColourEnum> getColourEnumList() {
        return colourEnumList;
    }

    /**
     * Setter method for frame colour's choice
     * @param chosenColour Colour chosen by the User
     */
    public void setChosenColour(ColourEnum chosenColour) {
        if (chosenColour == null) throw new NullPointerException("Invalid colour");
        this.chosenColour = chosenColour;
    }

    /**
     * Getter method for chosen Window frame colour
     * @return Window frame colour chosen by the User
     */
    public ColourEnum getChosenColour() {
        return chosenColour;
    }

    /**
     * Setter method for the window pattern card
     * @param idChosenSchema ID of window pattern chosen by the User
     */
    public void setIdChosenSchema(int idChosenSchema) {
        if (idChosenSchema == 0) throw new NullPointerException("Invalid Schema");
        this.idChosenSchema = idChosenSchema;
    }

    /**
     * Getter method for chosen SchemaCard
     * @return SchemaCard chosen by the User
     */
    public int getIdChosenSchema() {
        return idChosenSchema;
    }

    /**
     * Setter method for Private Objective Card
     * @param privateObjCard Private Objective Card assigned to the User
     */
    public void setPrivateObjCard(PrivateObjCard privateObjCard) {
        this.privateObjCard = privateObjCard;
    }

    /**
     * Getter method for Private Objective Card
     * @return Private Objective Card assigned to the User
     */
    public PrivateObjCard getPrivateObjCard() {
        return privateObjCard;
    }
}
