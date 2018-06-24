package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.model.player.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test Model's Class: PlayerMessage
 * @author Davide Gioiosa
 */

public class PlayerMessageTest {
    private PlayerMessage playerMessage;

    @Before
    public void setUp() throws Exception {
        playerMessage = new PlayerMessage();
    }

    /**
     * Check the correct creation of the object
     */
    @Test
    public void creation_checkCorrectConstruction() {
        assertEquals(null, playerMessage.getPlayerMove());
        assertEquals(null, playerMessage.getPlayerChoice());
        assertEquals(null, playerMessage.getMoveMessage());
        assertEquals(null, playerMessage.getId());
        assertFalse(playerMessage.isClosed());
    }

    /**
     * Correct setting of the playerMove in the PlayerMessage
     */
    @Test
    public void setMove() {
        PlayerMove playerMove = new PlayerMove();
        playerMessage.setCheckMove(playerMove);

        assertEquals(PlayerMessageTypeEnum.CHECK_MOVE, playerMessage.getId());
        assertEquals(playerMove, playerMessage.getPlayerMove());
    }

    /**
     * Incorrect setting of the playerMove in the PlayerMessage, catches the exception for a null parameter
     */
    @Test
    public void setMove_shouldCallException() {
        try {
            playerMessage.setCheckMove(null);
            fail();
        } catch (NullPointerException e){}
    }

    /**
     * Correct setting of the playerChoice in the PlayerMessage
     */
    @Test
    public void setChoice() {
        User user = new User(TypeOfConnection.SOCKET);
        PlayerChoice playerChoice = new PlayerChoice(user);
        playerMessage.setChoice(playerChoice);

        assertEquals(PlayerMessageTypeEnum.CHOICE, playerMessage.getId());
        assertEquals(playerChoice, playerMessage.getPlayerChoice());
    }

    /**
     * Incorrect setting of the playerChoice in the PlayerMessage, catches the exception for a null parameter
     */
    @Test
    public void setChoice_shouldCallException() {
        try {
            playerMessage.setChoice(null);
            fail();
        } catch (NullPointerException e){}

    }

    /**
     * Correct setting of the playerMessage in the PlayerMessage
     */
    @Test
    public void setMessage() {
        SchemaCard schemaCard = new SchemaCard(1, "name", "descrip", 3, new ArrayList<>());
        Player player = new Player("nickname", true, ColourEnum.BLUE, schemaCard, 3);
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        BoardDice boardDice = new BoardDice();
        BoardCard boardCard = new BoardCard(new ArrayList<>(), new ArrayList<>());
        TrackBoard trackboard = new TrackBoard();

        MoveMessage moveMessage = new MoveMessage(playerList, boardDice, boardCard, trackboard);
        playerMessage.setMessage(moveMessage);

        assertEquals(PlayerMessageTypeEnum.UPDATE, playerMessage.getId());
        assertEquals(moveMessage, playerMessage.getMoveMessage());
    }

    /**
     * Incorrect setting of the moveMessage in the PlayerMessage, catches the exception for a null parameter
     */
    @Test
    public void setMessage_shouldCallException() {
        try {
            playerMessage.setMessage(null);
            fail();
        } catch (NullPointerException e){}
    }

    /**
     * Correct setting of the user in the PlayerMessage
     */
    @Test
    public void setUser_shouldBeCorrect() {
        User user = new User(TypeOfConnection.SOCKET);
        playerMessage.setUser(user);

        assertEquals(user, playerMessage.getUser());
    }

    /**
     * Incorrect setting of the moveMessage in the PlayerMessage, catches the exception for a null parameter
     */
    @Test
    public void setUser_shouldCallException() {
        try {
            playerMessage.setUser(null);
            fail();
        } catch (NullPointerException e){}
    }


    /**
     * Check state of the boolean closure, first is false and then true
     */
    @Test
    public void setClosure() {
        assertFalse(playerMessage.isClosed());
        playerMessage.setClosure();
        assertTrue(playerMessage.isClosed());
    }

}