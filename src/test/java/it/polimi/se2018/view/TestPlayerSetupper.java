package it.polimi.se2018.view;

import it.polimi.se2018.controller.GameLoader;
import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.PlayerChoice;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.model.player.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests PlayerSetupper Class
 * @author Cristian Giannetti
 */
public class TestPlayerSetupper {

    private User user;
    private PlayerChoice playerChoice;
    private List<SchemaCard> schemaList;

    @Before
    public void setup(){
        schemaList = new ArrayList<>();
        List<ColourEnum> colourList = new ArrayList<>();
        colourList.add(ColourEnum.BLUE);
        colourList.add(ColourEnum.RED);
        user = new User(TypeOfConnection.RMI);
        playerChoice = new PlayerChoice(user);
        playerChoice.setColourEnumList(colourList);
        GameLoader gameLoader = new GameLoader();
        for (int i = 0; i < 12; i++){
            schemaList.add((SchemaCard) gameLoader.getSchemaDeck().extractCard());
        }
    }

    /**
     * Tests creation of a new PlayerSetupper
     */
    /*@Test
    public void creationTest(){
        PlayerSetupper playerSetupper = new PlayerSetupper(playerChoice);
        assertEquals(playerChoice, playerSetupper.getPlayerChoice());
    }*/

    /**
     * Tests Creation of a new PlayerSetupper with null PlayerChoice
     */
   /* @Test
    public void creationTestWithNullPlayerChoice(){
        try{
            PlayerSetupper playerSetupper = new PlayerSetupper(null);
            fail();
        }catch (NullPointerException e){}
    }*/

    /**
     * Tests setting of a valid Pattern colour
     */
    /*@Test
    public void validCommandWithValidColourTest(){
        PlayerSetupper playerSetupper = new PlayerSetupper(playerChoice);
        String message = playerSetupper.validCommand("red");
        assertEquals(ColourEnum.RED, playerChoice.getChosenColour());
        assertEquals("Window Frame inserito correttamente", message);
    }*/

    /**
     * Tests setting of a null Pattern colour
     */
    /*@Test
    public void validCommandWithNullColourTest(){
        PlayerSetupper playerSetupper = new PlayerSetupper(playerChoice);
        try{
            String message = playerSetupper.validCommand(null);
            fail();
        }catch (NullPointerException e){}
    }*/

    /**
     * Tests setting of an invalid Pattern colour
     */
    /*@Test
    public void validCommandWithInvalidColourTest(){
        PlayerSetupper playerSetupper = new PlayerSetupper(playerChoice);
        String message = playerSetupper.validCommand("yellow");
        assertEquals(null, playerChoice.getChosenColour());
        assertEquals( "Impossibile impostare il colore scelto", message);
    }*/

    /**
     * Tests setting of an empty Pattern colour
     */
    /*@Test
    public void validCommandWithEmptyColourTest(){
        PlayerSetupper playerSetupper = new PlayerSetupper(playerChoice);
        String message = playerSetupper.validCommand("");
        assertEquals(null, playerChoice.getChosenColour());
        assertEquals("Il colore scelto non è valido", message);
    }*/

    /**
     * Tests setting of a valid Schema ID
     */
   /* @Test
    public void validCommandWithValidSchemaTest(){
        playerChoice.setSchemaCardList(schemaList);
        PlayerSetupper playerSetupper = new PlayerSetupper(playerChoice);
        String message = playerSetupper.validCommand(400);
        assertEquals(400, playerChoice.getChosenSchema().getId());
        assertEquals("Schema inserito correttamente", message);
    }*/

    /**
     * Tests setting of an invalid Schema ID
     */
    /*@Test
    public void validCommandWithInvalidSchemaIdTest(){
        playerChoice.setSchemaCardList(schemaList);
        PlayerSetupper playerSetupper = new PlayerSetupper(playerChoice);
        String message = playerSetupper.validCommand(399);
        assertEquals(null, playerChoice.getChosenSchema());
        assertEquals( "Impossibile impostare lo schema scelto", message);
    }*/

    /**
     * Tests setting of a Schema ID without any schema available
     */
   /* @Test
    public void validCommandWithSchemaCardListNotSettedTest(){
        PlayerSetupper playerSetupper = new PlayerSetupper(playerChoice);
        String message = playerSetupper.validCommand(400);
        assertEquals(null, playerChoice.getChosenSchema());
        assertEquals("Impossibile impostare lo schema scelto", message);
    }*/

}
