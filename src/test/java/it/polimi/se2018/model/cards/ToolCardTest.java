package it.polimi.se2018.model.cards;

import it.polimi.se2018.controller.OperationString;
import it.polimi.se2018.model.ColourEnum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    private int indexOfTurn = 1;
    private int maxNumberOfDice = 1;
    private int minNumberOfDice = 1;
    private String avoidedRestriction = "";
    private List<List<OperationString>> operationStrings = new ArrayList<>();

    /**
     * Creates a new Card with correct parameters
     */
    @Test
    public void toolCardCreationGoodTest() {
        operationStrings.add(new ArrayList<>());
        operationStrings.get(0).add(new OperationString("pick", "diceboard"));
        operationStrings.get(0).add(new OperationString("leave", "diceboard"));

        ToolCard toolCard = new ToolCard(ID,NAME, DESCRIPTION, colour, indexOfTurn, minNumberOfDice, maxNumberOfDice, avoidedRestriction, operationStrings);
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
            ToolCard toolCard = new ToolCard(0, NAME, DESCRIPTION, colour, indexOfTurn, minNumberOfDice, maxNumberOfDice, avoidedRestriction, operationStrings);
            fail();
        }catch(IllegalArgumentException e){}
    }

    /**
     * Creates a new Card with incorrect empty name
     */
    @Test
    public void toolCardCreationBadTestEmptyName(){
        try {
            ToolCard toolCard = new ToolCard(ID, "", DESCRIPTION, colour, indexOfTurn, minNumberOfDice, maxNumberOfDice, avoidedRestriction, operationStrings);
            fail();
        }catch(IllegalArgumentException e){}
    }

    /**
     * Creates a new Card with incorrect null name
     */
    @Test
    public void toolCardCreationBadTestNullName(){
        try {
            ToolCard toolCard = new ToolCard(ID, null, DESCRIPTION, colour, indexOfTurn, minNumberOfDice, maxNumberOfDice, avoidedRestriction, operationStrings);
            fail();
        }catch(NullPointerException e){}
    }

    /**
     * Creates a new Card with incorrect null colour
     */
    @Test
    public void toolCardCreationBadTestNullColour(){
        try {
            ToolCard toolCard = new ToolCard(ID, NAME, DESCRIPTION, null, indexOfTurn, minNumberOfDice, maxNumberOfDice, avoidedRestriction, operationStrings);
            fail();
        }catch(IllegalArgumentException e){}
    }


    /**
     * Updates the number of token used with correct parameters
     */
    @Test
    public void useToolCardGoodTest() {
        final int token = 2;
        ToolCard toolCard = new ToolCard(ID, NAME, DESCRIPTION, colour, indexOfTurn, minNumberOfDice, maxNumberOfDice, avoidedRestriction, operationStrings);
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
        ToolCard toolCard = new ToolCard(ID, NAME, DESCRIPTION, colour, indexOfTurn, minNumberOfDice, maxNumberOfDice, avoidedRestriction, operationStrings);
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
        ToolCard toolCard = new ToolCard(ID, NAME, DESCRIPTION, colour, indexOfTurn, minNumberOfDice, maxNumberOfDice, avoidedRestriction, operationStrings);
        try{
            toolCard.updateToken(3);
            fail();
        }catch(IllegalArgumentException e){}
        assertFalse(toolCard.isUsed());
    }

}

