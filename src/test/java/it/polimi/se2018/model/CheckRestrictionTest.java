package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.restriction.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test Restriction Class
 * @author Davide Gioiosa
 */

public class CheckRestrictionTest {
    private static final String NAME = "Name";
    private static final String DESCRIPTION = "Description";
    private static final int DIFFICULTY = 4;
    private static final int ID = 1;
    private List<Cell> cellList;

    private Restriction restriction;

    /**
     * Check exceptions of the class, create a Scheme with 19 cells so that
     * other tests can add the last one with limitations where needed
     */
   @Before
    public void setUp(){
       cellList = new ArrayList<>();
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
    public void adjacentRestriction_BadParametersSent() {
        restriction = new RestrictionAdjacent();
        cellList.add(new Cell(0, null));
        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die = new Die(ColourEnum.BLUE);
        Position position = new Position(4);
        try {
            restriction.checkRestriction(schemaCard, die, null);
            fail();
        }
        catch (NullPointerException e){
        }
        try {
            restriction.checkRestriction(schemaCard, null, position);
            fail();
        }
        catch (NullPointerException e){
        }
        try {
            restriction.checkRestriction(null, die, position);
            fail();
        }
        catch (NullPointerException e){
        }
    }

    /**
     * Check if the die has to be placed in a border cell
     * Method will be called only when the Scheme is empty
     * Check a positive insertion of position
     */
    @Test
    public void isOnTheBorder_shouldReturnTrueWhenThePositionIsOnTheEdge() {
        restriction = new RestrictionFirstDieOnBorder();
        cellList.add(new Cell(0, null));
        SchemaCard schemaCard = new SchemaCard(ID, NAME,DESCRIPTION, DIFFICULTY, cellList);

        Die die = new Die(ColourEnum.BLUE);
        die.firstRoll();
        Position position = new Position(3);

        int result = restriction.checkRestriction(schemaCard, die, position);

        assertEquals(0, result);
    }

    /**
     * Check if the die has to be placed in a border cell
     * Method will be called only when the Scheme is empty
     * Check a negative insertion of position
     */
    @Test
    public void isOnTheBorder_shouldReturnFalseWhenThePositionIsNotOnTheEdge() {
        restriction = new RestrictionFirstDieOnBorder();
        cellList.add(new Cell(0, null));
        SchemaCard schemaCard = new SchemaCard(ID, NAME,DESCRIPTION, DIFFICULTY, cellList);

        Die die = new Die(ColourEnum.BLUE);
        die.firstRoll();
        Position position = new Position(7);

        int result = restriction.checkRestriction (schemaCard, die, position);

        assertEquals(1, result);
    }

    /**
     * Check if the Scheme is not empty the die has to be placed in a cell (also diagonal ones)
     * that has at least one adjacent die inside
     * Expect a positive possibility of placement of the dice,
     * one adjacent in horizontal, one in diagonal
     */
    @Test
    public void adjacentRestriction_shouldReturnTrueWhenThereIsAtLeastADiceAdjacent() {
        restriction = new RestrictionAdjacent();

        cellList.add(new Cell(0, null));
        SchemaCard schemaCard = new SchemaCard(ID, NAME,DESCRIPTION, DIFFICULTY, cellList);

        Die die1 = new Die(ColourEnum.BLUE);
        die1.firstRoll();
        cellList.get(0).insertDie(die1);

        Die die2 = new Die(ColourEnum.RED);
        die2.firstRoll();
        Position position2 = new Position(1);

        Die die3 = new Die(ColourEnum.YELLOW);
        die3.firstRoll();
        Position position3 = new Position(7);

        cellList.get(1).insertDie(die2);

        assertEquals(0, restriction.checkRestriction(schemaCard, die2, position2));
        assertEquals(0, restriction.checkRestriction(schemaCard, die3, position3));
    }

    /**
     * Check if the Scheme is not empty the die has to be placed in a cell (also diagonal ones)
     * that has at least one adjacent die inside
     * Expect a negative possibility of placement of the dice
     */
    @Test
    public void adjacentRestriction_shouldReturnFalseWhenNoAdjacent() {
        restriction = new RestrictionAdjacent();
        cellList.add(new Cell(0, null));
        SchemaCard schemaCard = new SchemaCard(ID, NAME,DESCRIPTION, DIFFICULTY, cellList);

        Die die1 = new Die(ColourEnum.BLUE);
        die1.firstRoll();
        cellList.get(0).insertDie(die1);

        Die die2 = new Die(ColourEnum.RED);
        die2.firstRoll();
        Position position = new Position(4);

        assertEquals(2, restriction.checkRestriction(schemaCard, die2, position));
    }

    /**
     * Check if the die that the player wants to place has the same value
     * of the cell, in the case that it has a value limitation
     * cell 20 with value limitation
     * Expect a positive possibility of placement of the dice
     */
    @Test
    public void cellValueRestriction_shouldReturnFalseIfDiceValueNotEqualsCellValue(){
        restriction = new RestrictionCellValue();
        cellList.add(new Cell(5, null));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die = new Die(ColourEnum.BLUE);
        die.setValue(5);
        Position position = new Position(19);

        assertEquals(0, restriction.checkRestriction(schemaCard, die, position));
    }

    /**
     * Check if the die that the player wants to place has the same value
     * of the cell, in the case that it has a value limitation
     * cell 20 with value limitation
     * Expect a negative possibility of placement of the dice
     */
    @Test
    public void cellValueRestriction_shouldReturnTrueIfDiceValueEqualsCellValue(){
        restriction = new RestrictionCellValue();
        cellList.add(new Cell(5, null));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die = new Die(ColourEnum.BLUE);
        die.setValue(2);
        Position position = new Position(19);

        assertEquals(4, restriction.checkRestriction(schemaCard, die, position));

    }

    /**
     * Check if the die that the player wants to place has the same colour
     * of the cell, in the case that it has a colour limitation
     * cell 20 with colour limitation
     * Expect a positive possibility of placement of the dice
     */
    @Test
    public void cellColourRestriction_shouldReturnTrueIfDiceColourEqualsCellColour() {
        restriction = new RestrictionCellColour();
        cellList.add(new Cell(0, ColourEnum.BLUE));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die = new Die(ColourEnum.BLUE);
        die.firstRoll();
        Position position = new Position(19);

        assertEquals(0, restriction.checkRestriction(schemaCard, die, position));
    }

    /**
     * Check if the die that the player wants to place has the same colour
     * of the cell, in the case that it has a colour limitation
     * cell 20 with colour limitation
     * Expect a negative possibility of placement of the dice
     */
    @Test
    public void cellColourRestriction_shouldReturnFalseIfDiceColourEqualsCellColour() {
        restriction = new RestrictionCellColour();

        cellList.add(new Cell(0, ColourEnum.BLUE));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die = new Die(ColourEnum.RED);
        die.firstRoll();
        Position position = new Position(19);

        assertEquals(3, restriction.checkRestriction(schemaCard, die, position));
    }

    /**
     * Check if the cell position where the player wants to place the die has adjacents dice
     * (no on diagonals) with the same colour, if there is at least one,
     * the player can't place the die
     * Expect a negative possibility of placement of the dice
     */
    @Test
    public void adjacentColourRestriction_shouldReturnFalseIfThereIsAtLeastAnAdjDieOfTheSameColour() {
        restriction = new RestrictionAdjacentColour();

        cellList.add(new Cell(0, null));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die1 = new Die(ColourEnum.RED);
        die1.firstRoll();
        cellList.get(1).insertDie(die1);

        Die die2 = new Die(ColourEnum.RED);
        die2.firstRoll();
        cellList.get(7).insertDie(die2);

        Die die3 = new Die(ColourEnum.GREEN);
        die3.firstRoll();
        cellList.get(11).insertDie(die3);

        Die die4 = new Die(ColourEnum.RED);
        die4.firstRoll();
        Position position = new Position(6);

        assertEquals(5, restriction.checkRestriction(schemaCard, die4, position));
    }

    /**
     * Check if the cell position where the player wants to place the die has adjacents dice
     * (no on diagonals) with the same colour, if there is at least one,
     * the player can't place the die
     * Expect a positive possibility of placement of the dice
     */
    @Test
    public void adjacentColourRestriction_shouldReturnTrueIfThereAreNoAdjDiceOfTheSameColour() {
        restriction = new RestrictionAdjacentColour();


        cellList.add(new Cell(0, null));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die1 = new Die(ColourEnum.RED);
        die1.firstRoll();
        cellList.get(1).insertDie(die1);

        Die die2 = new Die(ColourEnum.RED);
        die2.firstRoll();
        cellList.get(7).insertDie(die2);

        Die die3 = new Die(ColourEnum.GREEN);
        die3.firstRoll();
        cellList.get(11).insertDie(die3);

        Die die4 = new Die(ColourEnum.BLUE);
        die4.firstRoll();
        cellList.get(2).insertDie(die4);

        Die die5 = new Die(ColourEnum.BLUE);
        die5.firstRoll();
        Position position = new Position(6);

        assertEquals(0, restriction.checkRestriction(schemaCard, die5, position));
    }

    /**
     * Check if the cell position where the player wants to place the die has adjacents dice
     * (no on diagonals) with the same value, if there is at least one,
     * the player can't place the die
     * Expect a negative possibility of placement of the dice
     */
    @Test
    public void adjacentValueRestriction_shouldReturnTrueIfThereIsAtLeastAnAdjDieOfTheSameValue() {
        restriction = new RestrictionAdjacentValue();

        cellList.add(new Cell(0, null));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die1 = new Die(ColourEnum.RED);
        die1.setValue(6);
        cellList.get(1).insertDie(die1);

        Die die2 = new Die(ColourEnum.RED);
        die2.setValue(2);
        cellList.get(7).insertDie(die2);

        Die die3 = new Die(ColourEnum.GREEN);
        die3.setValue(6);
        cellList.get(11).insertDie(die3);

        Die die4 = new Die(ColourEnum.BLUE);
        die4.setValue(1);
        cellList.get(2).insertDie(die4);

        Die die5 = new Die(ColourEnum.YELLOW);
        die5.setValue(6);
        Position position = new Position(6);

        Position position2 = new Position(12);

        assertEquals(6, restriction.checkRestriction(schemaCard, die5, position));
        assertEquals(6, restriction.checkRestriction(schemaCard, die5, position2));

    }

    /**
     * Check if the cell position where the player wants to place the die has adjacents dice
     * (no on diagonals) with the same value, if there is at least one,
     * the player can't place the die
     * Expect a positive possibility of placement of the dice
     */
    @Test
    public void adjacentValueRestriction_shouldReturnFalseIfThereAreNoAdjDiceOfTheSameValue() {
        restriction = new RestrictionAdjacentValue();

        cellList.add(new Cell(0, null));

        SchemaCard schemaCard = new SchemaCard(ID, NAME, DESCRIPTION, DIFFICULTY, cellList);
        Die die1 = new Die(ColourEnum.RED);
        die1.setValue(6);
        cellList.get(1).insertDie(die1);

        Die die2 = new Die(ColourEnum.RED);
        die2.setValue(2);
        cellList.get(7).insertDie(die2);

        Die die3 = new Die(ColourEnum.GREEN);
        die3.setValue(6);
        cellList.get(11).insertDie(die3);

        Die die4 = new Die(ColourEnum.BLUE);
        die4.setValue(1);
        cellList.get(2).insertDie(die4);

        Die die5 = new Die(ColourEnum.YELLOW);
        die5.setValue(4);
        Position position = new Position(6);


        Position position2 = new Position(12);

        assertEquals(0, restriction.checkRestriction(schemaCard, die5, position));
        assertEquals(0, restriction.checkRestriction(schemaCard, die5, position2));

    }
}