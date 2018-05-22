package it.polimi.se2018.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests Cell class
 *
 * @author Silvia Franzini
 */
public class CellTest {

    private Cell c;
    private Die d;

    /**
     * Initialization for CellTest
     */
    @Before
    public void init(){
        d = new Die(ColourEnum.RED);
        d.firstRoll();
        int value= d.getValue();
        c= new Cell(value, ColourEnum.RED);
    }

    /**
     * Tests method pickDice that returns the dice in that specific cell
     */
    @Test
    public void pickDice() {
        c.insertDice(d);
        try{
            c.pickDice();
            assertTrue(c.isEmpty());
        }catch(IllegalArgumentException e){
            fail();
        }

    }

    /**
     * Tests the method setDice that places a dice in that specific cell
     */
    @Test
    public void insertDice() {
      try{
          c.insertDice(d);
          assertTrue(d.equals(c.getDie()));
      } catch(IllegalArgumentException e){
          fail();
      }
    }


}