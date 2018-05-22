package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.ToolCard;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests all ToolCard's methods
 *
 * @author Cristian Giannetti
 */

public class ToolCardTest{

    private final String NAME = "Name";
    private final String DESCRIPTION = "Description";
    private final ColourEnum colour = ColourEnum.BLUE;
    private final int ID = 1;

    /**
     * Creates a new Card with correct parameters
     */
    @Test
    public void toolCardCreationGoodTest() {
        ToolCard toolCard = new ToolCard(ID,NAME, DESCRIPTION, colour);
        assertEquals(ID,toolCard.getId());
        assertFalse(toolCard.isUsed());
        assertEquals(NAME, toolCard.getName());
        assertEquals(DESCRIPTION, toolCard.getDescription());
        assertEquals(colour, toolCard.getColour());
        assertEquals(0, toolCard.getToken());
    }

    /**
     * Creates a new Card with incorrect parameters
     */
    @Test
    public void toolCardCreationBadTest(){
        try {
            ToolCard toolCard = new ToolCard(0, NAME, DESCRIPTION, colour);
            fail();
        }catch(IllegalArgumentException e){}
    }

    /**
     * Creates a new Card with incorrect empty name
     */
    @Test
    public void toolCardCreationBadTestEmptyName(){
        try {
            ToolCard toolCard = new ToolCard(ID, "", DESCRIPTION, colour);
            fail();
        }catch(IllegalArgumentException e){}
    }

    /**
     * Creates a new Card with incorrect null name
     */
    @Test
    public void toolCardCreationBadTestNullName(){
        try {
            ToolCard toolCard = new ToolCard(ID, null, DESCRIPTION, colour);
            fail();
        }catch(IllegalArgumentException e){}
    }

    /**
     * Creates a new Card with incorrect null colour
     */
    @Test
    public void toolCardCreationBadTestNullColour(){
        try {
            ToolCard toolCard = new ToolCard(ID, NAME, DESCRIPTION, null);
            fail();
        }catch(IllegalArgumentException e){}
    }


    /**
     * Updates the number of token used with correct parameters
     */
    @Test
    public void useToolCardGoodTest() {
        final int token = 2;
        ToolCard toolCard = new ToolCard(ID, NAME, DESCRIPTION, colour);
        toolCard.updateToken(token);
        assertEquals(token, toolCard.getToken());
        toolCard.updateToken(token);
        assertEquals(token + token, toolCard.getToken());
        assertTrue(toolCard.isUsed());
    }

    /**
     * Updates the number of token used with incorrect high parameter
     */
    @Test
    public void useToolCardBadTestLowToken() {
        ToolCard toolCard = new ToolCard(ID, NAME, DESCRIPTION, colour);
        try{
            toolCard.updateToken(-1);
            fail();
        }catch(IllegalArgumentException e){}
        assertFalse(toolCard.isUsed());
    }

    /**
     * Updates the number of token used with incorrect negative parameter
     */
    @Test
    public void useToolCardBadHighToken() {
        ToolCard toolCard = new ToolCard(ID, NAME, DESCRIPTION, colour);
        try{
            toolCard.updateToken(3);
            fail();
        }catch(IllegalArgumentException e){}
        assertFalse(toolCard.isUsed());
    }

}

