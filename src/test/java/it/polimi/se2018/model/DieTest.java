package it.polimi.se2018.model;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests methods of Die that don't use random functions
 * @author Cristian Giannetti
 */
public class DieTest {

    private final ColourEnum colour = ColourEnum.BLUE;

    /**
     * Tests the creation of a Die with good parameters
     */
    @Test
    public void creationDieGoodTest(){
        Die die = new Die(colour);
        assertEquals(colour, die.getColour());
        assertEquals(0, die.getValue());
    }

    /**
     * Tests the creation of a Die with bad parameters
     */
    @Test
    public void creationDieBadTest(){
        try{
            Die die = new Die((ColourEnum)null);
            fail();
        } catch (NullPointerException e){
        }
    }

    /**
     * Sets a correct value on a Die
     */
    @Test
    public void setValueGoodTest(){
        final int value = 6;
        Die die = new Die(colour);
        die.setValue(value);
        assertEquals(value, die.getValue());
    }

    /**
     * Try to set an incorrect value on a Die
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
     * Increase the value of a Die that can be increased
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
        }catch(RuntimeException e){ }
    }

    /**
     * Decrease the value of a Die that can be increased
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
     * Try to decrease the value of a Die that cannot be increased
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
     * Sets the opposite value on a set Die
     */
    @Test
    public void oppositeValueGoodTest(){
        final int value = 1;
        Die die = new Die(colour);
        die.setValue(value);
        die.setOppositeValue();
        assertEquals(6, die.getValue());
    }

    /**
     * Try to set the opposite value on a not set Die
     */
    @Test
    public void oppositeValueBadTest(){
        Die die = new Die(colour);

        try{
            die.setOppositeValue();
            fail();
        }catch(RuntimeException e){ }
    }

    /**
     * Roll die
     */
    @Test
    public void rollDieTest(){
        Die die = new Die(colour);

        assertEquals(0,die.getValue());
        die.firstRoll();
        assertNotEquals(0,die.getValue());
    }

    /**
     * ReRoll die
     */
    @Test
    public void rerollDieTest(){
        Die die = new Die(colour);

        assertEquals(0,die.getValue());
        die.firstRoll();
        die.reRoll();
        assertNotEquals(0,die.getValue());
    }

    /**
     * ReRoll die
     */
    @Test
    public void firstRollBadTest() {
        Die die = new Die(colour);

        assertEquals(0, die.getValue());
        die.firstRoll();
        try {
            die.firstRoll();
            fail();
        } catch (RuntimeException e) {
        }
    }

    /**
     * Verify that one Die has same values of his clone
     */
    @Test
    public void verifyValuesOfClonedDie(){
        Die die = new Die(colour);
        die.firstRoll();
        Die clonedDie = die.getClone();
        assertEquals(die.getValue(), clonedDie.getValue());
        assertEquals(die.getColour(), clonedDie.getColour());
    }

    /**
     * Clones a Die and changes his value. Verify that the values are different
     */
    @Test
    public void cloneDieWithValidParameters(){
        Die die = new Die(colour);
        die.firstRoll();
        Die clonedDie = die.getClone();
        clonedDie.setOppositeValue();
        assertNotEquals(die.getValue(), clonedDie.getValue());
        assertEquals(die.getColour(), clonedDie.getColour());
    }
}
