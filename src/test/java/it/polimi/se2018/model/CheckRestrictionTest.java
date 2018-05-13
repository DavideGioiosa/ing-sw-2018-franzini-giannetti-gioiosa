package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.SchemaCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test CheckRestriction Class
 * @author Davide Gioiosa
 */

public class CheckRestrictionTest {

    public static final String NAME = "Name";
    public static final String DESCRIPTION = "Description";
    public static final int DIFFICULTY = 4;
    public static final int ID = 1;
    private CheckRestriction checkRestriction;

    /**
     * Check exceptions of the class
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        checkRestriction = new CheckRestriction();
    }

    @Test
    public void adjacentRestriction_shouldReturnTrueWhenThereIsAtLeastADiceAdjacent() {
        List<Cell> cellList = new ArrayList<Cell>();
        for(int i = 0; i<20; i++){
            cellList.add(new Cell(0, null));
        }
        SchemaCard schemaCard = new SchemaCard(NAME,DESCRIPTION, DIFFICULTY, ID, cellList);

        Dice dice1 = new Dice(ColourEnum.BLUE);
        dice1.firstRoll();
        cellList.get(0).insertDice(dice1);

        Dice dice2 = new Dice(ColourEnum.RED);
        dice2.firstRoll();
        Position position = new Position(1);

        boolean result = checkRestriction.adjacentRestriction(schemaCard, dice2, position);

        assertTrue(result);
    }

    @Test
    public void adjacentRestriction_shouldReturnFalseWhenNoAdjacent() {
        List<Cell> cellList = new ArrayList<Cell>();
        for(int i = 0; i<20; i++){
            cellList.add(new Cell(0, null));
        }
        SchemaCard schemaCard = new SchemaCard(NAME,DESCRIPTION, DIFFICULTY, ID, cellList);

        Dice dice1 = new Dice(ColourEnum.BLUE);
        dice1.firstRoll();
        cellList.get(0).insertDice(dice1);

        Dice dice2 = new Dice(ColourEnum.RED);
        dice2.firstRoll();
        Position position = new Position(4);

        boolean result = checkRestriction.adjacentRestriction(schemaCard, dice2, position);

        assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cellValueRestriction() {
        checkRestriction.adjacentRestriction(null, null, null);
    }

    @Test
    public void cellColourRestriction_shouldReturnFalseWhenSameColour() {
    }

    @Test
    public void adjacentColourRestriction() {
    }

    @Test
    public void adjacentValueRestriction() {
    }
}