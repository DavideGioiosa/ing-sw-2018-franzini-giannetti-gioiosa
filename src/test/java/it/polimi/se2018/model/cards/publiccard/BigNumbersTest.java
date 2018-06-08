package it.polimi.se2018.model.cards.publiccard;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.SchemaCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Test Model's Class Public card: BigNumbers
 * @author Davide Gioiosa
 */


public class BigNumbersTest {
    private SchemaCard schemaCard;
    private SchemaCard emptySchemaCard;
    private BigNumbers bigNumbers;

    /**
     * Create and set of a Scheme with some dice placed
     */
    @Before
    public void init(){
        List<Cell> cellList = new ArrayList<>();
        for(int i = 0; i<20; i++){
            cellList.add(new Cell(0, null));
        }
        List<Cell> cellList_2 = new ArrayList<>();
        for(int i = 0; i<20; i++){
            cellList_2.add(new Cell(0, null));
        }
        final int ID = 1;
        final int DIFFICULTY = 1;
        schemaCard = new SchemaCard(ID,"name","desc", DIFFICULTY, cellList);
        emptySchemaCard = new SchemaCard(ID,"name","desc", DIFFICULTY, cellList_2);

        Die die_1 = new Die(ColourEnum.BLUE);
        die_1.setValue(5);
        schemaCard.setDiceIntoCell(new Position(0), die_1);
        Die die_2 = new Die(ColourEnum.YELLOW);
        die_2.setValue(6);
        schemaCard.setDiceIntoCell(new Position(1), die_2);
        Die die_3 = new Die(ColourEnum.GREEN);
        die_3.setValue(2);
        schemaCard.setDiceIntoCell(new Position(5), die_3);
        Die die_4 = new Die(ColourEnum.RED);
        die_4.setValue(5);
        schemaCard.setDiceIntoCell(new Position(2), die_4);

        bigNumbers = new BigNumbers();
    }

    /**
     * Test with an empty scheme, expecting score 0
     */
    @Test
    public void getScore_shouldReturnTheZeroScore() {
        int score = bigNumbers.getScore(emptySchemaCard);

        assertEquals(0, score);
    }

    /**
     * Test with odd number of the dice componing the couple, to check the correct evaluation of the score
     */
    @Test
    public void getScore_shouldReturnTheCorrectScoreOdd() {
        Die die_5 = new Die(ColourEnum.RED);
        die_5.setValue(5);
        schemaCard.setDiceIntoCell(new Position(10), die_5);

        int score = bigNumbers.getScore(schemaCard);

        assertEquals(2, score);
    }

    /**
     * Test with even number of the dice componing the couple, to check the correct evaluation of the score
     */
    @Test
    public void getScore_shouldReturnTheCorrectScoreEven() {
        Die die_5 = new Die(ColourEnum.RED);
        die_5.setValue(5);
        schemaCard.setDiceIntoCell(new Position(10), die_5);
        Die die_6 = new Die(ColourEnum.PURPLE);
        die_6.setValue(6);
        schemaCard.setDiceIntoCell(new Position(11), die_6);
        Die die_7 = new Die(ColourEnum.BLUE);
        die_7.setValue(6);
        schemaCard.setDiceIntoCell(new Position(12), die_7);


        int score = bigNumbers.getScore(schemaCard);

        assertEquals(6, score);
    }


}