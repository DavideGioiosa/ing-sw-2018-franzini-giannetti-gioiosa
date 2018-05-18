package it.polimi.se2018.model.player;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.cards.PrivateObjCard;
import it.polimi.se2018.model.cards.SchemaCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PrivatePlayerTest {

    private PrivatePlayer privatePlayer;
    private Player player;
    private PrivateObjCard privateObjCard;

    /**
     * Initialization of PrivatePlayerTest
     */
    @Before
    public void setUp() {
        String nick = "nick";
        int token = 3;
        int id = 1;
        String name = "name";
        String desc = "desc";
        List<Cell> cellList = new ArrayList<Cell>();
        Die d = new Die(ColourEnum.RED);
        d.firstRoll();
        for(int i = 0; i<10; i++){
            cellList.add(new Cell(d.getValue(), d.getColour()));
        }
        SchemaCard schemaCard = new SchemaCard(id, name, desc, token, cellList);
        privateObjCard = new PrivateObjCard(id,name,desc, ColourEnum.GREEN);
        player = new Player(nick, false, ColourEnum.BLUE, schemaCard, token);
        privatePlayer = new PrivatePlayer(player,privateObjCard);
    }

    /**
     * Tests getPrivateObj method
     */
    @Test
    public void getPrivateObj() {
        assertEquals(privatePlayer.getPrivateObj(), privateObjCard);
    }

    /**
     * Tests getPlayer method
     */
    @Test
    public void getPlayer() {
        assertEquals(privatePlayer.getPlayer(), player);
    }
}