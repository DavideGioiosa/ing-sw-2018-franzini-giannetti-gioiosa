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

    public final static String NAME = "Name";
    public final static String DESCRIPTION = "Description";
    final ColourEnum colour = ColourEnum.BLUE;
    final int ID = 1;

    /**
     * Creates a new Card with correct parameters
     */
    @Test
    public void toolCardCreationGoodTest() {
        ToolCard toolCard = new ToolCard(ID,NAME, DESCRIPTION, colour);
        assertEquals(toolCard.getId(),ID);
        assertFalse(toolCard.isUsed());
        assertEquals(toolCard.getName(), NAME);
        assertEquals(toolCard.getDescription(),DESCRIPTION);
        assertEquals(toolCard.getColour(),colour);
        assertEquals(toolCard.getToken(), 0);
    }

    /**
     * Creates a new Card with incorrect parameters
     */
    @Test
    public void toolCardCreationBadTest(){
        try {
            ToolCard toolCard = new ToolCard(0, NAME, DESCRIPTION, colour);
            fail();
        }catch(IllegalArgumentException e){

        }

        try {
            ToolCard toolCard = new ToolCard(ID, "", DESCRIPTION, colour);
            fail();
        }catch(IllegalArgumentException e){
        }

        try {
            ToolCard toolCard = new ToolCard(ID, null, DESCRIPTION, colour);
            fail();
        }catch(IllegalArgumentException e){
        }

        try {
            ToolCard toolCard = new ToolCard(ID, NAME, DESCRIPTION, null);
            fail();
        }catch(IllegalArgumentException e){
        }

    }

    /**
     * Updates the number of token used with correct parameters
     */
    @Test
    public void useToolCardGoodTest() {
        final int token = 2;
        ToolCard toolCard = new ToolCard(ID, NAME, DESCRIPTION, colour);
        toolCard.updateToken(token);
        assertEquals(toolCard.getToken(), token);
        toolCard.updateToken(token);
        assertEquals(toolCard.getToken(),token + token);
        assertTrue(toolCard.isUsed());
    }

    /**
     * Updates the number of token used with incorrect parameters
     */
    @Test
    public void useToolCardBadTest() {
        final int token = -1;
        ToolCard toolCard = new ToolCard(ID, NAME, DESCRIPTION, colour);
        try{
            toolCard.updateToken(token);
            fail();
        }catch(IllegalArgumentException e){
        }
        assertFalse(toolCard.isUsed());

        try{
            toolCard.updateToken(3);
            fail();
        }catch(IllegalArgumentException e){
        }
        assertFalse(toolCard.isUsed());
    }

}

