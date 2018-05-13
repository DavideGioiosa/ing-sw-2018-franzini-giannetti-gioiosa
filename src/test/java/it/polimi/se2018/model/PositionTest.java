package it.polimi.se2018.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {
    private Position pos;

    /**
     * Initialization for PositionTest
     */
    @Before
    public void init(){
        pos = new Position(0,0);

    }

    /**
     * Tests the method setRow by controlling if the row value is the same of the one set
     */
    @Test
    public void setRow() {
        int row = 5;
        pos.setRow(5);
        assertEquals(pos.getRow(), row);
    }

    /**
     * Tests the method setCol by controlling if the col value is the same of the one set
     */
    @Test
    public void setCol() {
        int col = 5;
        pos.setCol(col);
        assertEquals(pos.getCol(), col);
    }

    /**
     * Tests method setRowCol by controlling the values of col and row set
     */
    @Test
    public void setRowCol() {
        int index = 25;
        pos.setRowCol(index);
        assertTrue(pos.getCol()==0 && pos.getRow()==5);
    }
}