package it.polimi.se2018.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BoardDiceTest {

    private BoardDice boardDice;
    private List<Die> dieList;
    private Die d;
    private Die d1;

    /**
     * Initialization of BoardDiceTest
     */
    @Before
    public void setUp(){
        boardDice = new BoardDice();
        d = new Die(ColourEnum.RED);
        d.firstRoll();
        d1 = new Die(ColourEnum.GREEN);
        d1.firstRoll();

    }

    /**
     * Tests insertDice method
     */
    @Test
    public void insertDice() {
        boardDice.insertDice(d);
        assertEquals(boardDice.getDieList().get(boardDice.getDieList().size()-1), d);
    }

    /**
     * Tests takeDice method
     */
    @Test
    public void takeDice() {
        boardDice.insertDice(d1);
        Die die = boardDice.getDieList().get(boardDice.getDieList().size()-1);
        assertEquals(boardDice.takeDice(boardDice.getDieList().size()-1), die);
    }
}