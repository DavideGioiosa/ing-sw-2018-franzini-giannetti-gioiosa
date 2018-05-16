package it.polimi.se2018.model.player;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.cards.SchemaCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserTest {

    private User user;
    private String nick;
    private Player player;

    /**
     * Initialization of UserTest
     */
    @Before
    public void setUp() {
        nick = "nick";
        String desc = "desc";
        String name = "name";
        int id = 2;
        int token = 3;
        List<Cell> cellList = new ArrayList<Cell>();
        Die d = new Die(ColourEnum.RED);
        d.firstRoll();
        for(int i = 0; i<10; i++){
            cellList.add(new Cell(d.getValue(), d.getColour()));
        }
        SchemaCard schemaCard = new SchemaCard(name,desc,id,token,cellList);
        player = new Player( nick,false,ColourEnum.BLUE,schemaCard,token);
        user = new User(nick);

    }

    /**
     * Tests setPlayer method
     */
    @Test
    public void setPlayer() {
        user.setPlayer(player);
        assertEquals(user.getPlayer(), player);

    }

    /**
     * Tests getNickname method
     */
    @Test
    public void getNickname() {
        assertEquals(user.getNickname(), nick);
    }


}