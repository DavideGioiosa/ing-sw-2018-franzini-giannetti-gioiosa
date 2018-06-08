package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PrivateObjCardTest {
    private static final String NAME = "Name";
    private static final String DESCRIPTION = "Description";
    private static final int ID = 1;
    private static final ColourEnum COLOUR_ENUM = ColourEnum.RED;
    private PrivateObjCard privateObjCard;
    private SchemaCard schemaCard;

    /**
     * Initialization for CellTest
     */
    @Before
    public void init(){
        List<Cell> cellList = new ArrayList<>();
        for(int i = 0; i<20; i++){
            cellList.add(new Cell(0, null));
        }
        final int ID = 1;
        final int DIFFICULTY = 1;
        schemaCard = new SchemaCard(ID,"name","desc", DIFFICULTY, cellList);

        privateObjCard = new PrivateObjCard(ID, NAME, DESCRIPTION, COLOUR_ENUM);
    }

    /**
     * Test correct colour setted
     */
    @Test
    public void getColour_shouldReturnCorrectColour() {
        ColourEnum colourEnum = privateObjCard.getColour();
        assertEquals(COLOUR_ENUM, colourEnum);
    }

    /**
     * Calculation of the score of the obj card, expecting correct evaluation
     */
    @Test
    public void getScore_shouldReturnCorrectResult() {
        Die die_1 = new Die(ColourEnum.RED);
        die_1.setValue(5);
        schemaCard.setDiceIntoCell(new Position(0), die_1);
        Die die_2 = new Die(ColourEnum.YELLOW);
        die_2.setValue(6);
        schemaCard.setDiceIntoCell(new Position(5), die_2);
        Die die_3 = new Die(ColourEnum.RED);
        die_3.setValue(2);
        schemaCard.setDiceIntoCell(new Position(10), die_3);
        Die die_4 = new Die(ColourEnum.RED);
        die_4.setValue(5);
        schemaCard.setDiceIntoCell(new Position(6), die_4);

        int score = privateObjCard.getScore(schemaCard);

        assertEquals(12, score);
    }

    /**
     * Calculation of the score of the obj card, expecting call of the exception for null parameter
     */
    @Test
    public void getScore_shouldCallException() {
        SchemaCard nullSchemaCard = null;
        try {
            privateObjCard.getScore(nullSchemaCard);
            fail();
        }catch (IllegalArgumentException e ){}
    }
}