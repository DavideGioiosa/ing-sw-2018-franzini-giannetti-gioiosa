package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.SchemaCard;
import jdk.nashorn.internal.parser.Token;
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
        SchemaCard schemaCard = new SchemaCard(NAME, DESCRIPTION, ID, DIFFICULTY, cellList);
        Dice dice = new Dice(ColourEnum.BLUE);
        Position position = new Position(4);
        try {
            checkRestriction.adjacentRestriction(schemaCard, dice, null);
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
            checkRestriction.adjacentRestriction(null, dice, position);
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
        SchemaCard schemaCard = new SchemaCard(NAME,DESCRIPTION, DIFFICULTY, ID, cellList);

        Dice dice = new Dice(ColourEnum.BLUE);
        dice.firstRoll();
        Position position = new Position(3);

        boolean result = checkRestriction.adjacentRestriction(schemaCard, dice, position);

        assertTrue(result);
    }

    /**
     * Check if the Scheme is empty the die has to be placed in a border cell
     * Check a negative insertion of position
     */
    @Test
    public void adjacentRestriction_shouldReturnFalseWhenThePositionIsNotOnTheEdge() {
        cellList.add(new Cell(0, null));
        SchemaCard schemaCard = new SchemaCard(NAME,DESCRIPTION, DIFFICULTY, ID, cellList);

        Dice dice = new Dice(ColourEnum.BLUE);
        dice.firstRoll();
        Position position = new Position(7);

        boolean result = checkRestriction.adjacentRestriction(schemaCard, dice, position);

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
        SchemaCard schemaCard = new SchemaCard(NAME,DESCRIPTION, DIFFICULTY, ID, cellList);

        Dice dice1 = new Dice(ColourEnum.BLUE);
        dice1.firstRoll();
        cellList.get(0).insertDice(dice1);

        Dice dice2 = new Dice(ColourEnum.RED);
        dice2.firstRoll();
        Position position2 = new Position(1);

        Dice dice3 = new Dice(ColourEnum.YELLOW);
        dice3.firstRoll();
        Position position3 = new Position(7);

        boolean resultHoriz = checkRestriction.adjacentRestriction(schemaCard, dice2, position2);
        cellList.get(1).insertDice(dice2);
        boolean resultDiag = checkRestriction.adjacentRestriction(schemaCard, dice3, position3);

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

    /**
     * Check if the die that the player wants to place has the same value
     * of the cell, in the case that it has a value limitation
     * cell 20 with value limitation
     * Expect a positive possibility of placement of the dice
     */
    @Test
    public void cellValueRestriction_shouldReturnFalseIfDiceValueNotEqualsCellValue(){
        cellList.add(new Cell(5, null));

        SchemaCard schemaCard = new SchemaCard(NAME, DESCRIPTION, ID, DIFFICULTY, cellList);
        Dice dice = new Dice(ColourEnum.BLUE);
        dice.setValue(5);
        Position position = new Position(19);

        boolean result = checkRestriction.cellValueRestriction(schemaCard, dice, position);

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

        SchemaCard schemaCard = new SchemaCard(NAME, DESCRIPTION, ID, DIFFICULTY, cellList);
        Dice dice = new Dice(ColourEnum.BLUE);
        dice.setValue(2);
        Position position = new Position(19);

        boolean result = checkRestriction.cellValueRestriction(schemaCard, dice, position);

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

        SchemaCard schemaCard = new SchemaCard(NAME, DESCRIPTION, ID, DIFFICULTY, cellList);
        Dice dice = new Dice(ColourEnum.BLUE);
        dice.firstRoll();
        Position position = new Position(19);

        boolean result = checkRestriction.cellColourRestriction(schemaCard, dice, position);

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

        SchemaCard schemaCard = new SchemaCard(NAME, DESCRIPTION, ID, DIFFICULTY, cellList);
        Dice dice = new Dice(ColourEnum.RED);
        dice.firstRoll();
        Position position = new Position(19);

        boolean result = checkRestriction.cellColourRestriction(schemaCard, dice, position);

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

        SchemaCard schemaCard = new SchemaCard(NAME, DESCRIPTION, ID, DIFFICULTY, cellList);
        Dice dice1 = new Dice(ColourEnum.RED);
        dice1.firstRoll();
        cellList.get(1).insertDice(dice1);

        Dice dice2 = new Dice(ColourEnum.RED);
        dice2.firstRoll();
        cellList.get(7).insertDice(dice2);

        Dice dice3 = new Dice(ColourEnum.GREEN);
        dice3.firstRoll();
        cellList.get(11).insertDice(dice3);

        Dice dice4 = new Dice(ColourEnum.RED);
        dice4.firstRoll();
        Position position = new Position(6);

        boolean result = checkRestriction.adjacentColourRestriction(schemaCard, dice4, position);

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

        SchemaCard schemaCard = new SchemaCard(NAME, DESCRIPTION, ID, DIFFICULTY, cellList);
        Dice dice1 = new Dice(ColourEnum.RED);
        dice1.firstRoll();
        cellList.get(1).insertDice(dice1);

        Dice dice2 = new Dice(ColourEnum.RED);
        dice2.firstRoll();
        cellList.get(7).insertDice(dice2);

        Dice dice3 = new Dice(ColourEnum.GREEN);
        dice3.firstRoll();
        cellList.get(11).insertDice(dice3);

        Dice dice4 = new Dice(ColourEnum.BLUE);
        dice4.firstRoll();
        cellList.get(2).insertDice(dice4);

        Dice dice5 = new Dice(ColourEnum.BLUE);
        dice5.firstRoll();
        Position position = new Position(6);

        boolean result = checkRestriction.adjacentColourRestriction(schemaCard, dice5, position);

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

        SchemaCard schemaCard = new SchemaCard(NAME, DESCRIPTION, ID, DIFFICULTY, cellList);
        Dice dice1 = new Dice(ColourEnum.RED);
        dice1.setValue(6);
        cellList.get(1).insertDice(dice1);

        Dice dice2 = new Dice(ColourEnum.RED);
        dice2.setValue(2);
        cellList.get(7).insertDice(dice2);

        Dice dice3 = new Dice(ColourEnum.GREEN);
        dice3.setValue(6);
        cellList.get(11).insertDice(dice3);

        Dice dice4 = new Dice(ColourEnum.BLUE);
        dice4.setValue(1);
        cellList.get(2).insertDice(dice4);

        Dice dice5 = new Dice(ColourEnum.YELLOW);
        dice5.setValue(6);
        Position position = new Position(6);

        boolean result = checkRestriction.adjacentValueRestriction(schemaCard, dice5, position);
        Position position2 = new Position(12);
        boolean result2 = checkRestriction.adjacentValueRestriction(schemaCard, dice5, position2);

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

        SchemaCard schemaCard = new SchemaCard(NAME, DESCRIPTION, ID, DIFFICULTY, cellList);
        Dice dice1 = new Dice(ColourEnum.RED);
        dice1.setValue(6);
        cellList.get(1).insertDice(dice1);

        Dice dice2 = new Dice(ColourEnum.RED);
        dice2.setValue(2);
        cellList.get(7).insertDice(dice2);

        Dice dice3 = new Dice(ColourEnum.GREEN);
        dice3.setValue(6);
        cellList.get(11).insertDice(dice3);

        Dice dice4 = new Dice(ColourEnum.BLUE);
        dice4.setValue(1);
        cellList.get(2).insertDice(dice4);

        Dice dice5 = new Dice(ColourEnum.YELLOW);
        dice5.setValue(4);
        Position position = new Position(6);

        boolean result = checkRestriction.adjacentValueRestriction(schemaCard, dice5, position);
        Position position2 = new Position(12);
        boolean result2 = checkRestriction.adjacentValueRestriction(schemaCard, dice5, position2);

        assertTrue(result);
        assertTrue(result2);
    }
}