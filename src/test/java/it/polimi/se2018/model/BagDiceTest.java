package it.polimi.se2018.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test BagDice Class
 * @author Davide Gioiosa
 */

public class BagDiceTest {
    /**
     * total number of dice usable in the match
     * @author Davide Gioiosa
     */
    public final static int totalDice = 90;

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

        BagDice bagDice = new BagDice(totalDice);
        for (int i = 0; i < totalDice; i++) {
            Dice dice = bagDice.extractDice();
            switch (dice.getColour()) {
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
     * Create an wrong bagDice with a negative number of dice
     */
    @Test
    public void BagDice_ErrorCreationReturningFalse (){
        try {
            BagDice bagDice = new BagDice(-10);
            fail();
        }catch(IllegalArgumentException e){
        }
    }

    /**
     * Insert a die correctly in the bagDice
     */
    @Test
    public void insertDice_shouldReturnTrueIfTheDiceHasBeenAdded(){
        int numOfBlue = 0;

        BagDice bagDice = new BagDice(5);
        Dice dice = new Dice(ColourEnum.BLUE);
        bagDice.insertDice(dice);
        for(int i = 0; i < 6; i++){
            Dice diceExtracted = bagDice.extractDice();
            if(diceExtracted.getColour() == ColourEnum.BLUE){
                numOfBlue++;
            }
        }
        assertEquals(2, numOfBlue);
    }

    /**
     * Remove a die correctly from the BagDice
     */
    @Test
    public void extractDice_shouldReturnTrueIfTheDiceHasBeenRemoved(){
        BagDice bagDice = new BagDice(5);
        Dice dice = bagDice.extractDice();
        assertEquals(4, bagDice.numberOfRemainingDice());
    }
}