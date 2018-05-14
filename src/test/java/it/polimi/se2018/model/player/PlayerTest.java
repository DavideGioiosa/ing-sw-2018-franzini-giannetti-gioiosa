package it.polimi.se2018.model.player;

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

public class PlayerTest {

    private Player player;
    private SchemaCard schemaCard;
    private Die d;
    private Position pos;

    /**
     * Initialization of PlayerTest
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        String nick = "nick";
        String desc = "desc";
        String name = "name";
        int id = 2;
        int token = 3;
        List<Cell> cellList = new ArrayList<Cell>();
        d = new Die(ColourEnum.RED);
        d.firstRoll();
        for(int i = 0; i<10; i++){
            cellList.add(new Cell(d.getValue(), d.getColour()));
        }
        schemaCard = new SchemaCard(name,desc,id,token,cellList);
        player = new Player(nick, false, ColourEnum.BLUE,schemaCard,token);
        pos = new Position(0,0);
    }

    /**
     * Tests method setConnectionStatus
     */
    @Test
    public void setConnectionStatus() {
        player.setConnectionStatus(true);
        assertEquals(player.getConnectionStatus(), true);
    }

    /**
     * Tests method getFrameColour
     */
    @Test
    public void getFrameColour() {
        assertEquals(player.getFrameColour(), ColourEnum.BLUE);
    }

    @Test
    public void getSchemaCard() {
        assertEquals(player.getSchemaCard(), schemaCard);
    }

    /**
     * Tests method updateSchema
     */
    @Test
    public void updateSchema() {
        try{
            player.updateSchema(d,pos);
            assertEquals(player.getSchemaCard().getCellList().get(pos.getIndexArrayPosition()).getDie(), d);
        }catch(IllegalArgumentException e){
            fail();
        }
    }

    /**
     * Tests method updateTokens
     */
    @Test
    public void updateTokens() {
        try{
            player.updateTokens(1);
            assertEquals(player.getTokens(), 2);
        }catch (IllegalArgumentException e){
            fail();
        }
    }

    /**
     * Tests method setScore
     */
    @Test
    public void setScore() {
        player.setScore(10);
        assertEquals(player.getScore(), 10);
    }


}