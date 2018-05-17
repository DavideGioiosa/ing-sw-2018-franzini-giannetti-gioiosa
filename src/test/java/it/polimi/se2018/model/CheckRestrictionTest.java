package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.SchemaCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
    private List<Cell> cellList;

    
    /**
     * Check exceptions of the class, create a Scheme with 19 cells so that
     * other tests can add the last one with limitations where needed
     */
   @Before
    public void setUp(){
       checkRestriction = new CheckRestriction();
       cellList = new ArrayList<Cell>();
       for(int i = 0; i<19; i++){
           cellList.add(new Cell(0, null));
       }
    }

    /**
     * Check the method validateParametres that is called before the methods of
     * the CheckRestrictions Class, throws exceptions if one of the parametres
     * is null
     */
    @Test
    public void adjacentRestriction_BadParametresSent() {
        cellList.add(new Cell(0, null));
        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die = new Die(ColourEnum.BLUE);
        Position position = new Position(4);
        try {
            checkRestriction.adjacentRestriction(schemaCard, die, null);
            fail();
        }
        catch (Exception e){
        }
        try {
            checkRestriction.adjacentRestriction(schemaCard, null, position);
            fail();
        }
        catch (Exception e){
        }
        try {
            checkRestriction.adjacentRestriction(null, die, position);
            fail();
        }
        catch (Exception e){
        }
    }

    /**
     * If the Scheme is empty the die has to be placed in a border cell
     * Check a positive insertion of position
     */
    @Test
    public void adjacentRestriction_shouldReturnTrueWhenThePositionIsOnTheEdge() {
        cellList.add(new Cell(0, null));
        SchemaCard schemaCard = new SchemaCard(ID, NAME,DESCRIPTION, DIFFICULTY, cellList);

        Die die = new Die(ColourEnum.BLUE);
        die.firstRoll();
        Position position = new Position(3);

        boolean result = checkRestriction.adjacentRestriction(schemaCard, die, position);

        assertTrue(result);
    }

    /**
     * Check if the Scheme is empty the die has to be placed in a border cell
     * Check a negative insertion of position
     */
    @Test
    public void adjacentRestriction_shouldReturnFalseWhenThePositionIsNotOnTheEdge() {
        cellList.add(new Cell(0, null));
        SchemaCard schemaCard = new SchemaCard(ID, NAME,DESCRIPTION, DIFFICULTY, cellList);

        Die die = new Die(ColourEnum.BLUE);
        die.firstRoll();
        Position position = new Position(7);

        boolean result = checkRestriction.adjacentRestriction(schemaCard, die, position);

        assertFalse(result);
    }

    /**
     * Check if the Scheme is not empty the die has to be placed in a cell (also diagonal ones)
     * that has at least one adjacent die inside
     * Expect a positive possibility of placement of the dice,
     * one adjacent in horizontal, one in diagonal
     */
    @Test
    public void adjacentRestriction_shouldReturnTrueWhenThereIsAtLeastADiceAdjacent() {
        cellList.add(new Cell(0, null));
        SchemaCard schemaCard = new SchemaCard(ID, NAME,DESCRIPTION, DIFFICULTY, cellList);

        Die die1 = new Die(ColourEnum.BLUE);
        die1.firstRoll();
        cellList.get(0).insertDice(die1);

        Die die2 = new Die(ColourEnum.RED);
        die2.firstRoll();
        Position position2 = new Position(1);

        Die die3 = new Die(ColourEnum.YELLOW);
        die3.firstRoll();
        Position position3 = new Position(7);

        boolean resultHoriz = checkRestriction.adjacentRestriction(schemaCard, die2, position2);
        cellList.get(1).insertDice(die2);
        boolean resultDiag = checkRestriction.adjacentRestriction(schemaCard, die3, position3);

        assertTrue(resultHoriz);
        assertTrue(resultDiag);
    }

    /**
     * Check if the Scheme is not empty the die has to be placed in a cell (also diagonal ones)
     * that has at least one adjacent die inside
     * Expect a negative possibility of placement of the dice
     */
    @Test
    public void adjacentRestriction_shouldReturnFalseWhenNoAdjacent() {
        cellList.add(new Cell(0, null));
        SchemaCard schemaCard = new SchemaCard(ID, NAME,DESCRIPTION, DIFFICULTY, cellList);

        Die die1 = new Die(ColourEnum.BLUE);
        die1.firstRoll();
        cellList.get(0).insertDice(die1);

        Die die2 = new Die(ColourEnum.RED);
        die2.firstRoll();
        Position position = new Position(4);

        boolean result = checkRestriction.adjacentRestriction(schemaCard, die2, position);

        assertFalse(result);
    }

    /**
     * Check if the die that the player wants to place has the same value
     * of the cell, in the case that it has a value limitation
     * cell 20 with value limitation
     * Expect a positive possibility of placement of the dice
     */
    @Test
    public void cellValueRestriction_shouldReturnFalseIfDiceValueNotEqualsCellValue(){
        cellList.add(new Cell(5, null));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die = new Die(ColourEnum.BLUE);
        die.setValue(5);
        Position position = new Position(19);

        boolean result = checkRestriction.cellValueRestriction(schemaCard, die, position);

        assertTrue(result);
    }

    /**
     * Check if the die that the player wants to place has the same value
     * of the cell, in the case that it has a value limitation
     * cell 20 with value limitation
     * Expect a negative possibility of placement of the dice
     */
    @Test
    public void cellValueRestriction_shouldReturnTrueIfDiceValueEqualsCellValue(){
        cellList.add(new Cell(5, null));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die = new Die(ColourEnum.BLUE);
        die.setValue(2);
        Position position = new Position(19);

        boolean result = checkRestriction.cellValueRestriction(schemaCard, die, position);

        assertFalse(result);
    }

    /**
     * Check if the die that the player wants to place has the same colour
     * of the cell, in the case that it has a colour limitation
     * cell 20 with colour limitation
     * Expect a positive possibility of placement of the dice
     */
    @Test
    public void cellColourRestriction_shouldReturnTrueIfDiceColourEqualsCellColour() {
        cellList.add(new Cell(0, ColourEnum.BLUE));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die = new Die(ColourEnum.BLUE);
        die.firstRoll();
        Position position = new Position(19);

        boolean result = checkRestriction.cellColourRestriction(schemaCard, die, position);

        assertTrue(result);
    }

    /**
     * Check if the die that the player wants to place has the same colour
     * of the cell, in the case that it has a colour limitation
     * cell 20 with colour limitation
     * Expect a negative possibility of placement of the dice
     */
    @Test
    public void cellColourRestriction_shouldReturnFalseIfDiceColourEqualsCellColour() {
        cellList.add(new Cell(0, ColourEnum.BLUE));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die = new Die(ColourEnum.RED);
        die.firstRoll();
        Position position = new Position(19);

        boolean result = checkRestriction.cellColourRestriction(schemaCard, die, position);

        assertFalse(result);
    }

    /**
     * Check if the cell position where the player wants to place the die has adjacents dice
     * (no on diagonals) with the same colour, if there is at least one,
     * the player can't place the die
     * Expect a negative possibility of placement of the dice
     */
    @Test
    public void adjacentColourRestriction_shouldReturnFalseIfThereIsAtLeastAnAdjDieOfTheSameColour() {
        cellList.add(new Cell(0, null));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die1 = new Die(ColourEnum.RED);
        die1.firstRoll();
        cellList.get(1).insertDice(die1);

        Die die2 = new Die(ColourEnum.RED);
        die2.firstRoll();
        cellList.get(7).insertDice(die2);

        Die die3 = new Die(ColourEnum.GREEN);
        die3.firstRoll();
        cellList.get(11).insertDice(die3);

        Die die4 = new Die(ColourEnum.RED);
        die4.firstRoll();
        Position position = new Position(6);

        boolean result = checkRestriction.adjacentColourRestriction(schemaCard, die4, position);

        assertFalse(result);

    }

    /**
     * Check if the cell position where the player wants to place the die has adjacents dice
     * (no on diagonals) with the same colour, if there is at least one,
     * the player can't place the die
     * Expect a positive possibility of placement of the dice
     */
    @Test
    public void adjacentColourRestriction_shouldReturnTrueIfThereAreNoAdjDiceOfTheSameColour() {
        cellList.add(new Cell(0, null));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die1 = new Die(ColourEnum.RED);
        die1.firstRoll();
        cellList.get(1).insertDice(die1);

        Die die2 = new Die(ColourEnum.RED);
        die2.firstRoll();
        cellList.get(7).insertDice(die2);

        Die die3 = new Die(ColourEnum.GREEN);
        die3.firstRoll();
        cellList.get(11).insertDice(die3);

        Die die4 = new Die(ColourEnum.BLUE);
        die4.firstRoll();
        cellList.get(2).insertDice(die4);

        Die die5 = new Die(ColourEnum.BLUE);
        die5.firstRoll();
        Position position = new Position(6);

        boolean result = checkRestriction.adjacentColourRestriction(schemaCard, die5, position);

        assertTrue(result);
    }

    /**
     * Check if the cell position where the player wants to place the die has adjacents dice
     * (no on diagonals) with the same value, if there is at least one,
     * the player can't place the die
     * Expect a negative possibility of placement of the dice
     */
    @Test
    public void adjacentValueRestriction_shouldReturnTrueIfThereIsAtLeastAnAdjDieOfTheSameValue() {
        cellList.add(new Cell(0, null));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die1 = new Die(ColourEnum.RED);
        die1.setValue(6);
        cellList.get(1).insertDice(die1);

        Die die2 = new Die(ColourEnum.RED);
        die2.setValue(2);
        cellList.get(7).insertDice(die2);

        Die die3 = new Die(ColourEnum.GREEN);
        die3.setValue(6);
        cellList.get(11).insertDice(die3);

        Die die4 = new Die(ColourEnum.BLUE);
        die4.setValue(1);
        cellList.get(2).insertDice(die4);

        Die die5 = new Die(ColourEnum.YELLOW);
        die5.setValue(6);
        Position position = new Position(6);

        boolean result = checkRestriction.adjacentValueRestriction(schemaCard, die5, position);
        Position position2 = new Position(12);
        boolean result2 = checkRestriction.adjacentValueRestriction(schemaCard, die5, position2);

        assertFalse(result);
        assertFalse(result2);
    }

    /**
     * Check if the cell position where the player wants to place the die has adjacents dice
     * (no on diagonals) with the same value, if there is at least one,
     * the player can't place the die
     * Expect a positive possibility of placement of the dice
     */
    @Test
    public void adjacentValueRestriction_shouldReturnFalseIfThereAreNoAdjDiceOfTheSameValue() {
        cellList.add(new Cell(0, null));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die1 = new Die(ColourEnum.RED);
        die1.setValue(6);
        cellList.get(1).insertDice(die1);

        Die die2 = new Die(ColourEnum.RED);
        die2.setValue(2);
        cellList.get(7).insertDice(die2);

        Die die3 = new Die(ColourEnum.GREEN);
        die3.setValue(6);
        cellList.get(11).insertDice(die3);

        Die die4 = new Die(ColourEnum.BLUE);
        die4.setValue(1);
        cellList.get(2).insertDice(die4);

        Die die5 = new Die(ColourEnum.YELLOW);
        die5.setValue(4);
        Position position = new Position(6);

        boolean result = checkRestriction.adjacentValueRestriction(schemaCard, die5, position);
        Position position2 = new Position(12);
        boolean result2 = checkRestriction.adjacentValueRestriction(schemaCard, die5, position2);

        assertTrue(result);
        assertTrue(result2);
    }
}