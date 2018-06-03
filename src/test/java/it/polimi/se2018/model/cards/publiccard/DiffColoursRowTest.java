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
 * Test Model's Class Public card: DiffColoursRow
 * @author Davide Gioiosa
 */

public class DiffColoursRowTest {
    private SchemaCard schemaCard;
    private SchemaCard emptySchemaCard;
    private DiffColoursRow diffColoursRow;

    /**
     * Create and set of a Scheme with some dice placed
     */
    @Before
    public void init() {
        List<Cell> cellList = new ArrayList<Cell>();
        for (int i = 0; i < 20; i++) {
            cellList.add(new Cell(0, null));
        }
        List<Cell> cellList_2 = new ArrayList<Cell>();
        for (int i = 0; i < 20; i++) {
            cellList_2.add(new Cell(0, null));
        }
        final int ID = 1;
        final int DIFFICULTY = 1;
        schemaCard = new SchemaCard(ID, "name", "desc", DIFFICULTY, cellList);
        emptySchemaCard = new SchemaCard(ID, "name", "desc", DIFFICULTY, cellList_2);

        Die die_1 = new Die(ColourEnum.BLUE);
        die_1.setValue(5);
        Position position_1 = new Position(0);
        schemaCard.setDiceIntoCell(position_1, die_1);
        Die die_2 = new Die(ColourEnum.YELLOW);
        die_2.setValue(6);
        Position position_2 = new Position(1);
        schemaCard.setDiceIntoCell(position_2, die_2);
        Die die_3 = new Die(ColourEnum.GREEN);
        die_3.setValue(2);
        Position position_3 = new Position(2);
        schemaCard.setDiceIntoCell(position_3, die_3);
        Die die_4 = new Die(ColourEnum.RED);
        die_4.setValue(5);
        Position position_4 = new Position(3);
        schemaCard.setDiceIntoCell(position_4, die_4);
        Die die_5 = new Die(ColourEnum.PURPLE);
        die_5.setValue(1);
        Position position_5 = new Position(4);
        schemaCard.setDiceIntoCell(position_5, die_5);
        Die die_6 = new Die(ColourEnum.RED);
        die_6.setValue(4);
        Position position_6 = new Position(8);
        schemaCard.setDiceIntoCell(position_6, die_6);
        Die die_7 = new Die(ColourEnum.PURPLE);
        die_7.setValue(1);
        Position position_7 = new Position(15);
        schemaCard.setDiceIntoCell(position_7, die_7);
        Die die_8 = new Die(ColourEnum.RED);
        die_8.setValue(4);
        Position position_8 = new Position(16);
        schemaCard.setDiceIntoCell(position_8, die_8);
        Die die_9 = new Die(ColourEnum.GREEN);
        die_9.setValue(3);
        Position position_9 = new Position(17);
        schemaCard.setDiceIntoCell(position_9, die_9);
        Die die_10 = new Die(ColourEnum.YELLOW);
        die_10.setValue(4);
        Position position_10 = new Position(18);
        schemaCard.setDiceIntoCell(position_10, die_10);
        Die die_11 = new Die(ColourEnum.BLUE);
        die_11.setValue(6);
        Position position_11 = new Position(19);
        schemaCard.setDiceIntoCell(position_11, die_11);

        diffColoursRow = new DiffColoursRow();
    }

    /**
     * Test with an empty scheme, expecting score 0
     */
    @Test
    public void getScore_shouldReturnTheZeroScore() {
        int score = diffColoursRow.getScore(emptySchemaCard);

        assertEquals(0, score);
    }

    /**
     * Test the correct evaluation of the score
     */
    @Test
    public void getScore() {
        int score = diffColoursRow.getScore(schemaCard);

        assertEquals(12, score);
    }
}