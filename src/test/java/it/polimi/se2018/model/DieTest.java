package it.polimi.se2018.model;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


/**
 * Tests methods of Dice that don't use random functions
 * 
 * @author Cristian Giannetti
 */
public class DieTest {

    public final ColourEnum colour = ColourEnum.BLUE;

    /**
     * Tests the creation of a Dice with good parameters
     */
    @Test
    public void creationDiceGoodTest(){
        Dice die = new Dice(colour);
        assertEquals(die.getColour(),colour);
        assertEquals(die.getValue(), 0);
    }

    /**
     * Tests the creation of a Dice with bad parameters
     */
    @Test
    public void creationDiceBadTest(){
        try{
            Dice die = new Dice(null);
            fail();
        } catch (IllegalArgumentException e){
        }
    }

    /**
     * Sets a correct value on a dice
     */
    @Test
    public void setValueGoodTest(){
        final int value = 6;
        Dice die = new Dice(colour);
        die.setValue(value);
        assertEquals(die.getValue(),value);
    }

    /**
     * Try to set an incorrect value on a dice
     */
    @Test
    public void setValueBadTest(){
        final int highValue = 7;
        final int lowValue = 0;
        Dice die = new Dice(colour);
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
        Dice die = new Dice(colour);
        die.setValue(value);
        die.increaseValue();
        assertEquals(die.getValue(), 1 + value);
    }

    /**
     * Try to increase the value of a dice that cannot be increased
     */
    @Test
    public void IncreaseValueBadTest(){
        final int value = 6;
        Dice die = new Dice(colour);
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
        Dice die = new Dice(colour);
        die.setValue(value);
        die.decreaseValue();
        assertEquals(die.getValue(), value - 1);

    }

    /**
     * Try to decrease the value of a dice that cannot be increased
     */
    @Test
    public void DecreaseValueBadTest(){
        final int value = 1;
        Dice die = new Dice(colour);
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
        Dice die = new Dice(colour);
        die.setValue(value);
        die.oppositeValue();
        assertEquals(die.getValue(), 6);
    }

    /**
     * Try to set the opposite value on a unsetted dice
     */
    @Test
    public void oppositeValueBadTest(){
        Dice die = new Dice(colour);
        try{
            die.oppositeValue();
            fail();

        }catch(RuntimeException e){

        }
    }

}
