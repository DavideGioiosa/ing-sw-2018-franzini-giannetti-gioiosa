package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.model.player.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Test Model's Class: PlayerChoice
 * @author Davide Gioiosa
 */

public class PlayerChoiceTest {
    private PlayerChoice playerChoice;
    private User user;
    private SchemaCard schemaCard;
    private SchemaCard schemaCard_2;

    @Before
    public void setUp() throws Exception {
        user = new User(TypeOfConnection.SOCKET);
        playerChoice = new PlayerChoice(user);
        List<Cell> cellList = new ArrayList<>();
        for(int i = 0; i<20; i++){
            cellList.add(new Cell(0, null));
        }
        final int ID = 1;
        final int DIFFICULTY = 1;
        schemaCard = new SchemaCard(ID,"name","desc", DIFFICULTY, cellList);
        schemaCard_2 = new SchemaCard(ID,"name","desc", DIFFICULTY, cellList);
    }

    /**
     * Check the correct creation of the object
     */
    @Test
    public void creation_checkCorrectConstruction() {
        assertEquals(user, playerChoice.getUser());
    }

    /**
     * Check the correct creation of the object, expecting an exception call for null parameter
     */
    @Test
    public void creation_shouldCallException() {
        try {
            playerChoice = new PlayerChoice(null);
            fail();
        } catch (NullPointerException e){}
    }

    /**
     * Correct setting of the list of Schema Cards in the PlayerChoice
     */
    @Test
    public void setSchemaCardList() {
        List<SchemaCard> schemaCardList = new ArrayList<>();
        schemaCardList.add(schemaCard);
        schemaCardList.add(schemaCard_2);

        playerChoice.setSchemaCardList(schemaCardList);

        assertEquals(schemaCardList, playerChoice.getSchemaCardList());
    }

    /**
     * Incorrect setting of the list of Schema Cards in the PlayerChoice, expecting an exception call for null parameter
     */
    @Test
    public void setSchemaCardList_shouldCallException() {
        try {
            playerChoice.setSchemaCardList(null);
            fail();
        } catch (NullPointerException e){}
    }


    /**
     * Correct setting of the list of Colour Enum available in the PlayerChoice
     */
    @Test
    public void setColourEnumList() {
        List<ColourEnum> colourEnumList = new ArrayList<>();
        colourEnumList.add(ColourEnum.RED);
        colourEnumList.add(ColourEnum.BLUE);

        playerChoice.setColourEnumList(colourEnumList);

        assertEquals(colourEnumList, playerChoice.getColourEnumList());
    }

    /**
     * Incorrect setting of the list of Colour Enum in the PlayerChoice, expecting an exception call for null parameter
     */
    @Test
    public void setColourEnumList_shouldCallException() {
        try {
            playerChoice.setColourEnumList(null);
            fail();
        } catch (NullPointerException e){}
    }

    /**
     * Correct setting of the chosen colour in the PlayerChoice
     */
    @Test
    public void setChosenColour() {
        playerChoice.setChosenColour(ColourEnum.BLUE);

        assertEquals(ColourEnum.BLUE, playerChoice.getChosenColour());
    }

    /**
     * Incorrect setting of chosen colour in the PlayerChoice, expecting an exception call for null parameter
     */
    @Test
    public void setChosenColour_shouldCallException() {
        try {
            playerChoice.setChosenColour(null);
            fail();
        } catch (NullPointerException e){}
    }


    /**
     * Correct setting of the chosen Schema card in the PlayerChoice
     */
    @Test
    public void setChosenSchema() {
        playerChoice.setIdChosenSchema(schemaCard.getId());

        assertEquals(schemaCard.getId(), playerChoice.getIdChosenSchema());
    }

    /**
     * Incorrect setting of the list of Colour Enum in the PlayerChoice, expecting an exception call for null parameter
     */
    @Test
    public void setChosenSchema_shouldCallException() {
        try {
            playerChoice.setIdChosenSchema(0);
            fail();
        } catch (NullPointerException e){}
    }
}