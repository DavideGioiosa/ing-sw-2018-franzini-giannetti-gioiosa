package it.polimi.se2018.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests Cell class
 *
 * @author Cristian Giannetti
 */
public class CellTest {

    private Die die;
    private final int value = 5;
    private final ColourEnum colourEnum = ColourEnum.RED;

    /**
     * Initialization for CellTest
     */
    @Before
    public void init(){
        die = new Die(colourEnum);
        die.setValue(value);
    }

    /**
     * Creation test with valid parameters
     */
    @Test
    public void creationCellWithValidParametersTest(){
        Cell cell = new Cell(value, colourEnum);
        assertEquals(null, cell.getDie());
        assertEquals(value, cell.getValue());
        assertEquals(colourEnum, cell.getColour());
    }

    /**
     * Creation test with too high value
     */
    @Test
    public void creationCellWithTooHighValueTest(){
        try {
            Cell cell = new Cell(7, null);
            fail();
        }catch(IllegalArgumentException e){}
    }

    /**
     * Creation test with too low value
     */
    @Test
    public void creationCellWithTooLowValueTest(){
        try {
            Cell cell = new Cell(-1, null);
            fail();
        }catch(IllegalArgumentException e){}
    }

    /**
     * Tests the method setDice that places a dice in that specific cell
     */
    @Test
    public void insertValidDieIntoCellTest() {
        Cell cell = new Cell(0, ColourEnum.RED);
        cell.insertDice(die);
        assertEquals(die, cell.getDie());
    }

    /**
     * Tries to insert a null Die in a cell
     */
    @Test
    public void insertNullDieIntoCellTest() {
        Cell cell = new Cell(0, ColourEnum.RED);
        try{
            cell.insertDice(null);
            fail();
        }catch(NullPointerException e){}
    }

    /**
     * Tries to insert a Die without value in a cell
     */
    @Test
    public void insertDieNotRolledIntoCellTest() {
        Cell cell = new Cell(0, ColourEnum.RED);
        try{
            cell.insertDice(new Die(ColourEnum.RED));
            fail();
        }catch(RuntimeException e){}
    }

    /**
     * Tests method pickDiee that returns the dice in that specific cell
     */
    @Test
    public void pickDieFromFullCellTest() {
        Cell cell = new Cell(value, ColourEnum.RED);
        cell.insertDice(die);

        assertEquals(die, cell.getDie());
        Die pickedDie = cell.pickDice();

        assertTrue(cell.isEmpty());
        assertEquals(die, pickedDie);
    }

    /**
     * Tries to pick die from an empty cell
     */
    @Test
    public void pickDieFromEmptyCellTest() {
        Cell cell = new Cell(value, ColourEnum.RED);
        assertTrue(cell.isEmpty());
        try {
            Die pickedDie = cell.pickDice();
            fail();
        }catch(NullPointerException e){}
    }

    /**
     * Tries to insert die in not empty Cell
     */
    @Test
    public void insertDieInFullCell(){
        Cell cell = new Cell(value, null);
        cell.insertDice(die);
        Die die1 = new Die(ColourEnum.BLUE);
        die1.firstRoll();
        try {
            cell.insertDice(die1);
            fail();
        }catch(IllegalArgumentException e){}
    }

    /**
     * Inserts die in a cell and verifies that its clone is still empty
     */
    @Test
    public void insertDieInClonedCellTest(){
        Cell cell = new Cell(value, ColourEnum.BLUE);
        Cell clonedCell = cell.getClone();

        assertTrue(cell.isEmpty());
        assertTrue(clonedCell.isEmpty());

        cell.insertDice(die);

        assertFalse(cell.isEmpty());
        assertTrue(clonedCell.isEmpty());
    }

    /**
     * Picks die in a cell and verifies that its clone is still full
     */
    @Test
    public void pickDieInClonedCellTest(){
        Cell cell = new Cell(value, ColourEnum.BLUE);
        cell.insertDice(die);
        Cell clonedCell = cell.getClone();

        assertFalse(clonedCell.isEmpty());
        assertFalse(cell.isEmpty());

        Die die = cell.pickDice();

        assertTrue(cell.isEmpty());
        assertFalse(clonedCell.isEmpty());
    }

}