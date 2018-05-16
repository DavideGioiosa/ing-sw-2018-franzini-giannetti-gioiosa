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

public class SchemaCardTest {
    private SchemaCard schema;
    private SchemaCard backSchema;
    private Die d;

    /**
     * Initialization for the SchemaCardTest
     */
    @Before
    public void init(){
        List<Cell> cellList = new ArrayList<Cell>();
        d = new Die(ColourEnum.BLUE);
        d.firstRoll();
        int id=1;
        int idBack = 2;
        int token = 3;
        String name = "name";
        String backName = "backName";
        String desc = "desc";
        int i;
        for(i = 0; i<10; i++){
            cellList.add(new Cell(d.getValue(), d.getColour()));
        }
        schema = new SchemaCard(name, desc, id, token, cellList);
        backSchema = new SchemaCard(backName, desc,idBack, token, cellList);

    }

    /**
     * Tests the method getDifficulty by controlling the value returned
     */
    @Test
    public void getDifficulty(){
        assertEquals(schema.getDifficulty(), 3);
    }

    /**
     * Tests method setCell by controlling that the cell chosen is actually filled wth the chosen dice
     */
    @Test
    public void setCell() {
        try{
            Position pos = new Position(0,0);
            schema.setCell(pos, d);
            Cell c = schema.getCellList().get(0);
            assertEquals(c.getDie(),d);

        }catch(IllegalArgumentException e){
            fail();
        }

    }

    /**
     * Tests the method setBackSchema by controlling if the backSchema is actually the one set by the method
     */
    @Test
    public void setBackSchema() {
        schema.setBackSchema(backSchema);
        assertEquals(schema.getBackSchema(), backSchema);
    }
}