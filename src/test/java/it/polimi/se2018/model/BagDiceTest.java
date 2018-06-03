package it.polimi.se2018.model;

import org.junit.Test;

import static it.polimi.se2018.model.Config.*;
import static org.junit.Assert.*;

/**
 * Test BagDice Class
 * @author Davide Gioiosa
 */

public class BagDiceTest {

    /**
     * Creates a new BagDice containing 90 dice, 18 for each colour
     */
    @Test
    public void BagDice_CorrectCreationReturningTrue() {
        int numOfBlue = 0;
        int numOfRed = 0;
        int numOfYellow = 0;
        int numOfGreen = 0;
        int numOfPurple = 0;

        BagDice bagDice = new BagDice();
        for (int i = 0; i < NUMBER_OF_DICE_PER_COLOUR * 5; i++) {
            Die die = bagDice.extractDice();
            switch (die.getColour()) {
                case BLUE:
                    numOfBlue++;
                    break;
                case RED:
                    numOfRed++;
                    break;
                case GREEN:
                    numOfGreen++;
                    break;
                case PURPLE:
                    numOfPurple++;
                    break;
                case YELLOW:
                    numOfYellow++;
                    break;
                default:
                    break;
            }
        }
        assertEquals(18, numOfBlue);
        assertEquals(18, numOfGreen);
        assertEquals(18, numOfPurple);
        assertEquals(18, numOfRed);
        assertEquals(18, numOfYellow);
    }

    /**
     * Insert a die correctly in the bagDice
     */
    @Test
    public void insertDice_shouldReturnTrueIfTheDiceHasBeenAdded(){
        int numOfBlue = 0;

        BagDice bagDice = new BagDice();
        Die die = new Die(ColourEnum.BLUE);
        bagDice.insertDice(die);
        for(int i = 0; i < 91; i++){
            Die dieExtracted = bagDice.extractDice();
            if(dieExtracted.getColour() == ColourEnum.BLUE){
                numOfBlue++;
            }
        }
        assertEquals(19, numOfBlue);
    }

    /**
     * Remove a die correctly from the BagDice
     */
    @Test
    public void extractDice_shouldReturnTrueIfTheDiceHasBeenRemoved(){
        BagDice bagDice = new BagDice();
        Die die = bagDice.extractDice();
        assertEquals(89, bagDice.numberOfRemainingDice());
    }
}