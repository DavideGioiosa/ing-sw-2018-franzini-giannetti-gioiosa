package it.polimi.se2018.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardDiceTest {

    private BoardDice boardDice;
    private List<Dice> diceList;
    private Dice d;
    private Dice d1;

    /**
     * Initialization of BoardDiceTest
     */
    @Before
    public void setUp(){
        boardDice = new BoardDice();
        d = new Dice(ColourEnum.RED);
        d.firstRoll();
        d1 = new Dice(ColourEnum.GREEN);
        d1.firstRoll();

    }

    /**
     * Tests insertDice method
     */
    @Test
    public void insertDice() {
        boardDice.insertDice(d);
        assertEquals(boardDice.getDiceList().get(boardDice.getDiceList().size()-1), d);
    }

    /**
     * Tests takeDice method
     */
    @Test
    public void takeDice() {
        boardDice.insertDice(d1);
        Dice dice = boardDice.getDiceList().get(boardDice.getDiceList().size()-1);
        assertEquals(boardDice.takeDice(boardDice.getDiceList().size()-1), dice);
    }
}