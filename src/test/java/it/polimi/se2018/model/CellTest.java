package it.polimi.se2018.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {

    private Cell c;
    private Dice d;

    /**
     * Initialization for CellTest
     */
    @Before
    public void init(){
        d = new Dice(ColourEnum.RED);
        d.firstRoll();
        int value= d.getValue();
        c= new Cell(value, ColourEnum.RED);
    }

    /**
     * Tests method pickDice that returns the dice in that specific cell
     */
    @Test
    public void pickDice() {
        c.pickDice();
        assertTrue(c.isEmpty());
    }

    /**
     * Tests the method setDice that places a dice in that specific cell
     */
    @Test
    public void setDice() {
       c.setDice(d);
       assertTrue(d.equals(c.getDice()));
    }


}