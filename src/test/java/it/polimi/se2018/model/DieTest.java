package it.polimi.se2018.model;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


/**
 * Tests methods of Die that don't use random functions
 *
 * @author Cristian Giannetti
 */
public class DieTest {

    public final ColourEnum colour = ColourEnum.BLUE;

    /**
     * Tests the creation of a Die with good parameters
     */
    @Test
    public void creationDiceGoodTest(){
        Die die = new Die(colour);
        assertEquals(colour, die.getColour());
        assertEquals(0, die.getValue());
    }

    /**
     * Tests the creation of a Die with bad parameters
     */
    @Test
    public void creationDiceBadTest(){
        try{
            Die die = new Die(null);
            fail();
        } catch (NullPointerException e){
        }
    }

    /**
     * Sets a correct value on a dice
     */
    @Test
    public void setValueGoodTest(){
        final int value = 6;
        Die die = new Die(colour);
        die.setValue(value);
        assertEquals(value, die.getValue());
    }

    /**
     * Try to set an incorrect value on a dice
     */
    @Test
    public void setValueBadTest(){
        final int highValue = 7;
        final int lowValue = 0;
        Die die = new Die(colour);
        try{
            die.setValue(highValue);
            fail();
        }catch(IllegalArgumentException e){
        }

        try{
            die.setValue(lowValue);
            fail();
        }catch(IllegalArgumentException e){
        }
    }

    /**
     * Increase the value of a dice that can be increased
     */
    @Test
    public void IncreaseValueGoodTest(){
        final int value = 5;
        Die die = new Die(colour);
        die.setValue(value);
        die.increaseValue();
        assertEquals(1 + value, die.getValue());
    }

    /**
     * Try to increase the value of a dice that cannot be increased
     */
    @Test
    public void IncreaseValueBadTest(){
        final int value = 6;
        Die die = new Die(colour);
        die.setValue(value);
        try{
            die.increaseValue();
            fail();

        }catch(RuntimeException e){

        }

    }

    /**
     * Decrease the value of a dice that can be increased
     */
    @Test
    public void DecreaseValueGoodTest(){
        final int value = 5;
        Die die = new Die(colour);
        die.setValue(value);
        die.decreaseValue();
        assertEquals(value - 1, die.getValue());

    }

    /**
     * Try to decrease the value of a dice that cannot be increased
     */
    @Test
    public void DecreaseValueBadTest(){
        final int value = 1;
        Die die = new Die(colour);
        die.setValue(value);
        try{
            die.decreaseValue();
            fail();

        }catch(RuntimeException e){

        }
    }

    /**
     * Sets the opposite value on a setted dice
     */
    @Test
    public void oppositeValueGoodTest(){
        final int value = 1;
        Die die = new Die(colour);
        die.setValue(value);
        die.oppositeValue();
        assertEquals(6, die.getValue());
    }

    /**
     * Try to set the opposite value on a unsetted dice
     */
    @Test
    public void oppositeValueBadTest(){
        Die die = new Die(colour);
        try{
            die.oppositeValue();
            fail();

        }catch(RuntimeException e){

        }
    }

}
