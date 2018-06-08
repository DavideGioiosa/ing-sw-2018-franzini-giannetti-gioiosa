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

/**
 * Tests User class
 *
 * @author Silvia Franzini
 * @author Davide Gioiosa
 */
public class UserTest {

    private User user;
    private Player player;

    /**
     * Initialization of UserTest
     */
    @Before
    public void setUp() {
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
        SchemaCard schemaCard = new SchemaCard(id, name,desc,token,cellList);
        player = new Player( "nick",false,ColourEnum.BLUE,schemaCard,token);
        user = new User( TypeOfConnection.SOCKET);

    }


    /**
     * Check user's type of connection
     */
    @Test
    public void getTypeOfConnection() {
        assertEquals(TypeOfConnection.SOCKET, user.getTypeOfConnection());
}

    /**
     * Tests setPlayer method
     */
    @Test
    public void setPlayer() {
        user.setPlayer(player);
        assertEquals(player, user.getPlayer());

    }

    /**
     * Tests correct setting of the nickname
     */
    @Test
    public void setNickname() {
        user.setNickname("nickname");
        assertEquals("nickname", user.getNickname());
    }

    /**
     * Check state of the boolean connection
     */
    @Test
    public void isConnected() {
        assertTrue(user.isConnected());
    }


    /**
     * Tests correct creation of the unique code id
     */
    @Test
    public void getUniqueCode() {
        String code = user.getUniqueCode();

        assertEquals(8, code.length());
    }

}