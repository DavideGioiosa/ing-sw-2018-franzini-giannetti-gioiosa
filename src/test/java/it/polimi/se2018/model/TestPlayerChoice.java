package it.polimi.se2018.model;

import it.polimi.se2018.controller.GameLoader;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.player.User;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2018.model.player.TypeOfConnection.*;
import static org.junit.Assert.*;

/**
 * Tests PlayerChoice class
 *
 * @author  Cristian Giannetti
 */
public class TestPlayerChoice {
    private User user;
    private List<ColourEnum> colourList;
    private List<SchemaCard> schemaCardList;
    private SchemaCard schemaCard;
    private SchemaCard schemaSelected;

    /**
     * Sets objects used by PlayerChoice
     */
    @Before
    public void setUp(){
        final String NICKNAME = "nickname";
        user = new User(NICKNAME,SOCKET);

        colourList = new ArrayList<>();
        colourList.add(ColourEnum.BLUE);
        colourList.add(ColourEnum.RED);

        schemaCardList = new ArrayList<>();
        GameLoader gameLoader = new GameLoader();

        for (int i = 0; i < 12; i++) {
            schemaCard = (SchemaCard) gameLoader.getSchemaDeck().extractCard();
            schemaCardList.add(schemaCard);
        }

        schemaSelected = schemaCard;
    }

    /**
     * Test for an empty Creation of a PlayerChoice
     */
    @Test
    public void creationVoidTest(){
        PlayerChoice playerChoice = new PlayerChoice(user);

        assertEquals(user, playerChoice.getUser());
        assertEquals(0, playerChoice.getSchemaCardList().size());
        assertEquals(0, playerChoice.getColourEnumList().size());
        assertEquals(null, playerChoice.getChosenSchema());
        assertEquals(null, playerChoice.getChosenColour());
    }

    /**
     * Test for setting valid ColourEnumList of a PlayerChoice
     */
    @Test
    public void settingValidWindowPatternColourListPlayerChoiceTest(){
        PlayerChoice playerChoice = new PlayerChoice(user);
        playerChoice.setColourEnumList(colourList);

        assertEquals(colourList, playerChoice.getColourEnumList());
    }

    /**
     * Test for setting invalid ColourEnumList of a PlayerChoice
     */
    @Test
    public void settingNullWindowPatternColourListPlayerChoiceTest(){
        PlayerChoice playerChoice = new PlayerChoice(user);
        try {
            playerChoice.setColourEnumList(null);
            fail();
        }catch(NullPointerException e){}
    }

    /**
     * Test for setting valid SchemaCardList of a PlayerChoice
     */
    @Test
    public void settingValidSchemaListInPlayerChoiceTest(){
        PlayerChoice playerChoice = new PlayerChoice(user);
        playerChoice.setSchemaCardList(schemaCardList);

        assertEquals(schemaCardList, playerChoice.getSchemaCardList());
    }

    /**
     * Test for setting invalid SchemaCardList of a PlayerChoice
     */
    @Test
    public void settingInvalidSchemaListInPlayerChoiceTest(){
        PlayerChoice playerChoice = new PlayerChoice(user);
        try {
            playerChoice.setSchemaCardList(null);
            fail();
        }catch(NullPointerException e){}
    }

    /**
     * Test for setting valid Chosen Colour of a PlayerChoice
     */
    @Test
    public void settingValidWindowPatternColourChosenInPlayerChoiceTest(){
        PlayerChoice playerChoice = new PlayerChoice(user);
        playerChoice.setColourEnumList(colourList);
        playerChoice.setChosenColour(ColourEnum.RED);
        assertEquals(ColourEnum.RED, playerChoice.getChosenColour());
    }

    /**
     * Test for setting null Chosen Colour of a PlayerChoice
     */
    @Test
    public void settingNullWindowPatternColourChosenInPlayerChoiceTest(){
        PlayerChoice playerChoice = new PlayerChoice(user);
        try {
            playerChoice.setChosenColour(null);
            fail();
        }catch(NullPointerException e){}
    }

    /**
     * Test for setting valid chosen Schema Card of a PlayerChoice
     */
    @Test
    public void settingValidChosenSchemaInPlayerChoiceTest(){
        PlayerChoice playerChoice = new PlayerChoice(user);
        playerChoice.setChosenSchema(schemaSelected);

        assertEquals(schemaSelected, playerChoice.getChosenSchema());
    }

    /**
     * Test for setting null Schema Card choice of a PlayerChoice
     */
    @Test
    public void settingInvalidChosenSchemaInPlayerChoiceTest(){
        PlayerChoice playerChoice = new PlayerChoice(user);
        try {
            playerChoice.setChosenSchema(null);
            fail();
        }catch(NullPointerException e){}
    }

}
