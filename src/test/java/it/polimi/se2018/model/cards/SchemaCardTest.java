package it.polimi.se2018.model.cards;

import it.polimi.se2018.controller.GameLoader;
import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Position;
import org.junit.Before;
import org.junit.Test;

import javax.xml.validation.Schema;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests SchemaCard class
 *
 * @author Silvia Franzini
 * @author Cristian Giannetti
 */
public class SchemaCardTest {
    private Die standardDie;
    private final int id = 400;
    private final String name = "name";
    private final String backName = "backName";
    private final String desc = "desc";
    private final int token = 3;
    private List<Cell> cellList;

    /**
     * Initialization for the SchemaCardTest
     */
    @Before
    public void setup(){
        cellList = new ArrayList<>();
        standardDie = new Die(ColourEnum.BLUE);
        standardDie.firstRoll();
        for(int i = 0; i < 20; i++){
            cellList.add(new Cell(standardDie.getValue(), standardDie.getColour()));
        }
    }

    /**
     * Tests creation of SchemaCard with valid parameters
     */
    @Test
    public void creationSchemaCardTest(){
        SchemaCard schemaCard = new SchemaCard(id, name, desc, token, cellList);
        assertEquals(id, schemaCard.getId());
        assertEquals(name, schemaCard.getName());
        assertEquals(desc, schemaCard.getDescription());
        assertEquals(token, schemaCard.getDifficulty());
        assertEquals(cellList, schemaCard.getCellList());
    }

    /**
     * Tests creation of SchemaCard with invalid ID
     */
    @Test
    public void creationSchemaCardWithInvalidIdTest(){
        try{
            SchemaCard schemaCard = new SchemaCard(-1, name, desc, token, cellList);
            fail();
        }catch (IllegalArgumentException e){}
    }

    /**
     * Tests creation of SchemaCard with invalid ID
     */
    @Test
    public void creationSchemaCardWithInvalidNameTest(){
        try{
            SchemaCard schemaCard = new SchemaCard(id, "", desc, token, cellList);
            fail();
        }catch (IllegalArgumentException e){}
    }

    /**
     * Tests creation of SchemaCard with invalid ID
     */
    @Test
    public void creationSchemaCardWithNullNameTest(){
        try{
            SchemaCard schemaCard = new SchemaCard(id, null, desc, token, cellList);
            fail();
        }catch (NullPointerException e){}
    }

    /**
     * Tests creation of SchemaCard with invalid ID
     */
    @Test
    public void creationSchemaCardWithInvalidDifficultyTest(){
        try{
            SchemaCard schemaCard = new SchemaCard(id, name, desc, -5, cellList);
            fail();
        }catch (IllegalArgumentException e){}
    }

    /**
     * Tests creation of SchemaCard with invalid ID
     */
    @Test
    public void creationSchemaCardWithNullCellListTest(){
        try{
            SchemaCard schemaCard = new SchemaCard(id, name, desc, token, null);
            fail();
        }catch (NullPointerException e){}
    }

    /**
     * Tests method setDiceIntoCell by controlling that the cell chosen is actually filled wth the chosen dice
     */
    @Test
    public void setCell() {
        SchemaCard schema = emptySchema();
        Position pos = new Position(0,0);
        schema.setDiceIntoCell(pos, standardDie);
        Cell c = schema.getCellList().get(0);
        assertEquals(c.getDie(), standardDie);

    }

    /**
     * Tests the method setBackSchema by controlling if the backSchema is actually the one set by the method
     */
    @Test
    public void setBackSchemaTest() {
        SchemaCard backSchema = emptySchema();
        SchemaCard schema = emptySchema();
        schema.setBackSchema(backSchema);
        assertEquals(backSchema, schema.getBackSchema());
    }

    /**
     * Tests the setting of a null backSchema
     */
    @Test
    public void setNullBackSchemaTest(){
        SchemaCard schema = emptySchema();
        try{
            schema.setBackSchema(null);
            fail();
        }catch(NullPointerException e){}
    }

    /**
     * Tests setting of a dice into Cell with null position
     */
    @Test
    public void setDieIntoCellWithValidParametersTest(){
        SchemaCard schema = emptySchema();
        Die die = new Die(ColourEnum.BLUE);
        die.firstRoll();
        schema.setDiceIntoCell(new Position(5), die);
        schema.setDiceIntoCell(new Position(6), die);
        schema.setDiceIntoCell(new Position(7), die);
        for (int i = 0; i < schema.getCellList().size(); i++){
            if (i >= 5 && i <= 7) assertEquals(die, schema.getCellList().get(i).getDie());
            else assertTrue(schema.getCellList().get(i).isEmpty());
        }
    }

    /**
     * Tests setting of a dice into Cell with null position
     */
    @Test
    public void setDieIntoCellTest(){
        SchemaCard schema = emptySchema();
        Die die = new Die(ColourEnum.BLUE);
        die.firstRoll();
        try {
            schema.setDiceIntoCell(null, die);
            fail();
        }catch (NullPointerException e){}
    }

    /**
     * Tests setting of a null die into Cell
     */
    @Test
    public void setNullDieIntoCellTest(){
        SchemaCard schema = emptySchema();
        try {
            schema.setDiceIntoCell(new Position(10), null);
            fail();
        }catch (NullPointerException e){}
    }

    /**
     * Tests setting of a die in an invalid low position
     */
    @Test
    public void setDieIntoInvalidHighPositionCellTest(){
        SchemaCard schema = emptySchema();
        try {
            schema.setDiceIntoCell(new Position(-5), standardDie);
            fail();
        }catch (IllegalArgumentException e){}
    }

    /**
     * Tests setting of a die in an invalid high position
     */
    @Test
    public void setDieIntoInvalidLowPositionCellTest(){
        SchemaCard schema = emptySchema();
        try {
            schema.setDiceIntoCell(new Position(25), standardDie);
            fail();
        }catch (IllegalArgumentException e){}
    }

    /**
     * Tests getCellCol method with incorrect high parameter
     */
    @Test
    public void getCellColWithValidParameterTest(){
        SchemaCard schemaCard = schemaWithId400();
        List<Cell> cellColList = schemaCard.getCellCol(2);

        assertEquals(4, cellColList.size());

        assertEquals(null, cellColList.get(0).getColour());
        assertEquals(0, cellColList.get(0).getValue());
        assertEquals(null, cellColList.get(1).getColour());
        assertEquals(5, cellColList.get(1).getValue());
        assertEquals(ColourEnum.RED, cellColList.get(2).getColour());
        assertEquals(0, cellColList.get(2).getValue());
        assertEquals(null, cellColList.get(3).getColour());
        assertEquals(0, cellColList.get(3).getValue());
    }

    /**
     * Tests getCellCol method with incorrect high parameter
     */
    @Test
    public void getCellColWithInvalidHighParameterTest(){
        SchemaCard schemaCard = schemaWithId400();
        try{
            schemaCard.getCellCol(5);
            fail();
        }catch (IllegalArgumentException e){}
    }

    /**
     * Tests getCellCol method with incorrect low parameter
     */
    @Test
    public void getCellColWithInvalidLowParameterTest(){
        SchemaCard schemaCard = schemaWithId400();
        try{
            schemaCard.getCellCol(-1);
            fail();
        }catch (IllegalArgumentException e){}
    }

    /**
     * Tests getCellRow method with incorrect high parameter
     */
    @Test
    public void getCellRowWithValidParameterTest(){
        SchemaCard schemaCard = schemaWithId400();
        List<Cell> cellColList = schemaCard.getCellRow(2);

        assertEquals(5, cellColList.size());

        assertEquals(null, cellColList.get(0).getColour());
        assertEquals(3, cellColList.get(0).getValue());
        assertEquals(null, cellColList.get(1).getColour());
        assertEquals(0, cellColList.get(1).getValue());
        assertEquals(ColourEnum.RED, cellColList.get(2).getColour());
        assertEquals(0, cellColList.get(2).getValue());
        assertEquals(null, cellColList.get(3).getColour());
        assertEquals(0, cellColList.get(3).getValue());
        assertEquals(ColourEnum.GREEN, cellColList.get(4).getColour());
        assertEquals(0, cellColList.get(4).getValue());
    }

    /**
     * Tests getCellRow method with incorrect high parameter
     */
    @Test
    public void getCellRowWithInvalidHighParameterTest(){
        SchemaCard schemaCard = schemaWithId400();
        try{
            schemaCard.getCellRow(4);
            fail();
        }catch (IllegalArgumentException e){}
    }

    /**
     * Tests getCellRow method with incorrect low parameter
     */
    @Test
    public void getCellRowWithInvalidLowParameterTest(){
        SchemaCard schemaCard = schemaWithId400();
        try{
            schemaCard.getCellRow(-1);
            fail();
        }catch (IllegalArgumentException e){}
    }

    /**
     * Creates a schema with blank cells
     * @return New schema with blank cells
     */
    public SchemaCard emptySchema(){
        List<Cell> cellList = new ArrayList<>();
        for(int i = 0; i < 20; i++) cellList.add(new Cell(0,null));
        SchemaCard schema = new SchemaCard(420,"name","description",1, cellList);
        return schema;
    }

    /**
     * Tests getAdjacent method with right parameters
     */
    @Test
    public void getAdjacentWithValidParameterTest(){
        SchemaCard schemaCard = schemaWithId400();
        List<Cell> cellColList = schemaCard.getAdjacents(new Position(2));

        assertEquals(3, cellColList.size());
        List<Cell> cellList = new ArrayList<>();
        cellList.add(new Cell(0, null));
        cellList.add(new Cell(5, null));
        cellList.add(new Cell(0, ColourEnum.BLUE));

        for(int i = 0; i < cellColList.size(); i++){
            for (int j = 0; j < cellList.size(); j++){
                if (cellColList.get(i).getValue() == cellList.get(j).getValue() && cellColList.get(i).getColour() == cellList.get(j).getColour() )
                    cellList.remove(j);
            }
        }

        assertEquals(0, cellList.size());
    }

    /**
     * Tests getAdjacent method with incorrect high parameter
     */
    @Test
    public void getAdjacentWithInvalidHighParameterTest(){
        SchemaCard schemaCard = schemaWithId400();
        try{
            schemaCard.getAdjacents(new Position(-2));
            fail();
        }catch (IllegalArgumentException e){}
    }

    /**
     * Creates a schema with various cells
     * @return SchemaCard with ID 400
     */
    public SchemaCard schemaWithId400(){
        GameLoader gameLoader = new GameLoader();
        SchemaCard schemaCard;
        do {
            schemaCard = (SchemaCard) gameLoader.getSchemaDeck().extractCard();
        }while(schemaCard.getId() != 400);
        return schemaCard;
    }
}