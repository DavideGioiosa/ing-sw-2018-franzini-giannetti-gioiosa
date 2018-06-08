package it.polimi.se2018.model.cards.publiccard;

import it.polimi.se2018.controller.GameLoader;
import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.cards.SchemaCard;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Test Model's Class Public card: SmallNumbers
 * @author Davide Gioiosa
 */

public class SmallNumbersTest {
    private SchemaCard schemaCard;
    private SchemaCard emptySchemaCard;
    private SmallNumbers smallNumbers;
    private static PublicObjCard publicObjCard;
    /**
     * Create and set of a Scheme with some dice placed
     */

    @BeforeClass
    public static void beforeClass(){
        String NAME = "Sfumature Chiare";
        GameLoader gameLoader = new GameLoader();
        do{
            publicObjCard = (PublicObjCard) gameLoader.getPublicObjDeck().extractCard();
        }while(!publicObjCard.getName().equals(NAME));
    }

    @Before
    public void init(){
        List<Cell> cellList = new ArrayList<>();
        for(int i = 0; i<20; i++){
            cellList.add(new Cell(0, null));
        }
        List<Cell> cellList_2 = new ArrayList<Cell>();
        for(int i = 0; i<20; i++){
            cellList_2.add(new Cell(0, null));
        }
        final int ID = 1;
        final int DIFFICULTY = 1;
        schemaCard = new SchemaCard(ID,"name","desc", DIFFICULTY, cellList);
        emptySchemaCard = new SchemaCard(ID,"name","desc", DIFFICULTY, cellList_2);

        Die die_1 = new Die(ColourEnum.BLUE);
        die_1.setValue(1);
        Position position_1 = new Position(0);
        schemaCard.setDiceIntoCell(position_1, die_1);
        Die die_2 = new Die(ColourEnum.YELLOW);
        die_2.setValue(3);
        Position position_2 = new Position(1);
        schemaCard.setDiceIntoCell(position_2, die_2);
        Die die_3 = new Die(ColourEnum.GREEN);
        die_3.setValue(2);
        Position position_3 = new Position(5);
        schemaCard.setDiceIntoCell(position_3, die_3);
        Die die_4 = new Die(ColourEnum.RED);
        die_4.setValue(2);
        Position position_4 = new Position(2);
        schemaCard.setDiceIntoCell(position_4, die_4);
        Die die_5 = new Die(ColourEnum.PURPLE);
        die_5.setValue(1);
        Position position_5 = new Position(11);
        schemaCard.setDiceIntoCell(position_5, die_5);
        Die die_6 = new Die(ColourEnum.BLUE);
        die_6.setValue(2);
        Position position_6 = new Position(15);
        schemaCard.setDiceIntoCell(position_6, die_6);

        smallNumbers = new SmallNumbers();
    }

    /**
     * Test with an empty scheme, expecting score 0
     */
    @Test
    public void getScore_shouldReturnTheZeroScore() {
        int score = publicObjCard.scoreCalculation(emptySchemaCard);

        assertEquals(0, score);
    }

    /**
     * Test check the correct evaluation of the score
     */
    @Test
    public void getScore_shouldReturnTheCorrectScore() {
        int score = publicObjCard.scoreCalculation(schemaCard);

        assertEquals(4, score);
    }

}