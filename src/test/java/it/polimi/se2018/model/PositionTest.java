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
    public void setRowGood() {
        int row = 2;
        try{
            pos.setRow(row);
            assertEquals(pos.getRow(), row);
        }catch(IllegalArgumentException e){
            fail();
        }

    }

    /**
     * Tests the method setRow (should fail)
     */
    @Test
    public void setRowBad(){
        int row = 5;
        try{
            pos.setRow(row);
            fail();
        }catch(IllegalArgumentException e){

        }
    }

    /**
     * Tests the method setCol by controlling if the col value is the same of the one set
     */
    @Test
    public void setColGood() {
        int col = 2;
        try{
            pos.setCol(col);
            assertEquals(pos.getCol(), col);

        }catch(IllegalArgumentException e){
            fail();
        }
     }

    /**
     * Tests the method setCol (should fail)
     */
    @Test
    public void setColBad() {
        int col = 6;
        try{
            pos.setCol(col);
            fail();

        }catch(IllegalArgumentException e){

        }
    }

    /**
     * Tests method setRowCol by controlling the values of col and row set
     */
    @Test
    public void setRowColGood() {
        int index = 10;
        try{
            pos.setRowCol(index);
            assertTrue(pos.getCol()==0 && pos.getRow()==2);

        }catch(IllegalArgumentException e){
            fail();
        }
    }

    /**
     * Tests the method setRowCol (should fail)
     */
    @Test
    public void setRowColBad() {
        int index = 25;
        try{
            pos.setRowCol(index);
            fail();

        }catch(IllegalArgumentException e){

        }
    }
}