package it.polimi.se2018.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests BoardDice class
 *
 * @author Silvia Franzini
 */
public class BoardDiceTest {

    private Die die;
    private Die die1;

    /**
     * Initialization of BoardDiceTest
     */
    @Before
    public void setUp(){
        die = new Die(ColourEnum.RED);
        die.firstRoll();
        die1 = new Die(ColourEnum.GREEN);
        die1.firstRoll();
    }


    /**
     * Constructor test
     */
    @Test
    public void boardDiceCreationTest(){
        BoardDice boardDice = new BoardDice();
        assertTrue(boardDice.getDieList().isEmpty());
    }

    /**
     * Tests insertDice method
     */
    @Test
    public void insertDiceTest() {
        BoardDice boardDice = new BoardDice();

        boardDice.insertDie(die);
        assertEquals(die, boardDice.getDieList().get(boardDice.getDieList().size()-1));
    }

    /**
     * Tries to insert a null Die in BoardDice
     */
    @Test
    public void insertNullDieTest(){
        BoardDice boardDice = new BoardDice();
        try{
            boardDice.insertDie(null);
            fail();
        }catch (NullPointerException e){}
    }

    /**
     * Tests takeDice method with valid parameter
     */
    @Test
    public void takeDiceWithValidIndexTest() {
        BoardDice boardDice = new BoardDice();
        boardDice.insertDie(die1);
        assertEquals(1, boardDice.getDieList().size());

        assertEquals(die1, boardDice.takeDie(boardDice.getDieList().size()-1));
    }

    /**
     * Tests takeDice method with invalid high index
     */
    @Test
    public void takeDiceWithInvalidHighIndexTest() {
        BoardDice boardDice = new BoardDice();
        boardDice.insertDie(die);
        boardDice.insertDie(die1);
        assertEquals(2, boardDice.getDieList().size());

        try{
            boardDice.takeDie(3);
            fail();
        }catch(IllegalArgumentException e){}
    }

    /**
     * Tests takeDice method with invalid low index
     */
    @Test
    public void takeDiceWithInvalidLowIndexTest() {
        BoardDice boardDice = new BoardDice();
        boardDice.insertDie(die);
        boardDice.insertDie(die1);
        assertEquals(2, boardDice.getDieList().size());

        try{
            boardDice.takeDie(-1);
            fail();
        }catch(IllegalArgumentException e){}
    }

    /**
     * Tests takeDice method in an empty DiceBoard
     */
    @Test
    public void takeDiceInEmptyBoardTest() {
        BoardDice boardDice = new BoardDice();
        assertEquals(0, boardDice.getDieList().size());

        try{
            boardDice.takeDie(0);
            fail();
        }catch(RuntimeException e){}
    }

    /**
     * Test clone method
     */
    @Test
    public void cloneDiceBoardTest(){
        BoardDice boardDice = new BoardDice();
        Die die2 = new Die(ColourEnum.RED);
        die2.firstRoll();
        Die die3 = new Die(ColourEnum.BLUE);
        die3.firstRoll();
        boardDice.insertDie(die);
        boardDice.insertDie(die1);
        boardDice.insertDie(die2);
        boardDice.insertDie(die3);

        BoardDice clonedBoardDice = boardDice.getClone();
        assertEquals(boardDice.getDieList().size(), clonedBoardDice.getDieList().size());

        for(int i = 0; i<boardDice.getDieList().size(); i++){
            assertNotEquals(boardDice.getDieList().get(i), clonedBoardDice.getDieList().get(i));
            assertEquals(boardDice.getDieList().get(i).getColour(), clonedBoardDice.getDieList().get(i).getColour());
            assertEquals(boardDice.getDieList().get(i).getValue(), clonedBoardDice.getDieList().get(i).getValue());
        }
    }

    /**
     * Test clone method
     */
    @Test
    public void cloneNullDiceBoardTest(){
        BoardDice boardDice = null;
        try{
            BoardDice clonedBoardDice = boardDice.getClone();
            fail();
        }catch(NullPointerException e){}

    }
}