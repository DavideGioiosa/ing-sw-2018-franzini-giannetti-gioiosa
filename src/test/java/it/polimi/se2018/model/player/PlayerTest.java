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

/**
 * Tests Player class
 *
 * @author Silvia Franzini
 */
public class PlayerTest {

    private Player player;
    private SchemaCard schemaCard;
    private Die die;
    private Position pos;

    /**
     * Initialization of PlayerTest
     */
    @Before
    public void setUp(){
        String nick = "nick";
        String desc = "desc";
        String name = "name";
        int id = 2;
        int token = 3;
        List<Cell> cellList = new ArrayList<>();
        die = new Die(ColourEnum.RED);
        die.firstRoll();
        for(int i = 0; i<10; i++){
            cellList.add(new Cell(die.getValue(), die.getColour()));
        }
        schemaCard = new SchemaCard(id,name,desc,token,cellList);
        player = new Player(nick, false, ColourEnum.BLUE,schemaCard,token);
        pos = new Position(0,0);
    }

    /**
     * Tests method setConnectionStatus
     */
    @Test
    public void setConnectionStatus() {
        player.setConnectionStatus(true);
        assertEquals(true, player.getConnectionStatus());
    }

    /**
     * Tests method getFrameColour
     */
    @Test
    public void getFrameColour() {
        assertEquals(ColourEnum.BLUE, player.getFrameColour());
    }

    @Test
    public void getSchemaCard() {
        assertEquals(schemaCard, player.getSchemaCard());
    }

    /**
     * Tests method updateSchema
     */
    @Test
    public void updateSchema() {
        try{
            player.updateSchema(die,pos);
            assertEquals(player.getSchemaCard().getCellList().get(pos.getIndexArrayPosition()).getDie(), die);
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
            assertEquals(2, player.getTokens());
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
        assertEquals(10, player.getScore());
    }

    public Player newPlayer(){
        setUp();
        return player;
    }

}