package it.polimi.se2018.model.cards.publiccard;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.cards.SchemaCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test Model's Class Public card: DiffColoursColumn
 * @author Davide Gioiosa
 */

public class DiffColoursColumnTest {
    private SchemaCard schemaCard;
    private SchemaCard emptySchemaCard;
    private DiffColoursColumn diffColoursColumn;

    /**
     * Create and set of a Scheme with some dice placed
     */
    @Before
    public void init() {
        List<Cell> cellList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            cellList.add(new Cell(0, null));
        }
        List<Cell> cellList_2 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            cellList_2.add(new Cell(0, null));
        }
        final int ID = 1;
        final int DIFFICULTY = 1;
        schemaCard = new SchemaCard(ID, "name", "desc", DIFFICULTY, cellList);
        emptySchemaCard = new SchemaCard(ID, "name", "desc", DIFFICULTY, cellList_2);

        Die die_1 = new Die(ColourEnum.BLUE);
        die_1.setValue(5);
        schemaCard.setDiceIntoCell(new Position(0), die_1);
        Die die_2 = new Die(ColourEnum.YELLOW);
        die_2.setValue(6);
        schemaCard.setDiceIntoCell(new Position(5), die_2);
        Die die_3 = new Die(ColourEnum.GREEN);
        die_3.setValue(2);
        schemaCard.setDiceIntoCell(new Position(10), die_3);
        Die die_4 = new Die(ColourEnum.RED);
        die_4.setValue(5);
        schemaCard.setDiceIntoCell(new Position(15), die_4);
        Die die_5 = new Die(ColourEnum.PURPLE);
        die_5.setValue(1);
        schemaCard.setDiceIntoCell(new Position(11), die_5);
        Die die_6 = new Die(ColourEnum.PURPLE);
        die_6.setValue(4);
        schemaCard.setDiceIntoCell(new Position(3), die_6);
        Die die_7 = new Die(ColourEnum.RED);
        die_7.setValue(1);
        schemaCard.setDiceIntoCell(new Position(8), die_7);
        Die die_8 = new Die(ColourEnum.BLUE);
        die_8.setValue(2);
        schemaCard.setDiceIntoCell(new Position(13), die_8);
        Die die_9 = new Die(ColourEnum.GREEN);
        die_9.setValue(4);
        schemaCard.setDiceIntoCell(new Position(18), die_9);

        diffColoursColumn = new DiffColoursColumn();
    }

    /**
     * Test with an empty scheme, expecting score 0
     */
    @Test
    public void getScore_shouldReturnTheZeroScore() {
        int score = diffColoursColumn.getScore(emptySchemaCard);

        assertEquals(0, score);
    }

    /**
     * Test the correct evaluation of the score
     */
    @Test
    public void getScore_shouldReturnTheCorrectScore() {
        int score = diffColoursColumn.getScore(emptySchemaCard);

        assertEquals(10, score);
    }
}