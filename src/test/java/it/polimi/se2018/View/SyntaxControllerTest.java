package it.polimi.se2018.View;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PlayerTest;
import it.polimi.se2018.view.SyntaxController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests SyntaxController Class
 */
public class SyntaxControllerTest {
    SyntaxController syntaxController;
    GameBoard gameBoard;
    Player player;

    @Before
    public void setup(){
        GameBoardTest gameBoardTest = new GameBoardTest();
        gameBoard = gameBoardTest.newGameBoard();
        PlayerTest playerTest = new PlayerTest();
        player = playerTest.newPlayer();
    }

    /**
     * Tests constructor
     */
    @Test
    public void ConstructorTest(){
        syntaxController = new SyntaxController(gameBoard, player);
        assertEquals(player, syntaxController.getPlayerMove().getPlayer());
    }

    /**
     * Tests PICK message
     */
    @Test
    public void commandPickTest(){
        syntaxController = new SyntaxController(gameBoard, player);
        String message = syntaxController.validCommand("PICK");
        assertEquals(TypeOfChoiceEnum.PICK, syntaxController.getPlayerMove().getTypeOfChoice());

        message = syntaxController.validCommand("0");
        assertEquals(TypeOfChoiceEnum.PICK, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(-1, syntaxController.getPlayerMove().getDiceBoardIndex());
        assertEquals("COMANDO ERRATO", message);


    }

    /**
     * Tests TOOL message
     */
    @Test
    public void commandToolCardTest(){
        syntaxController = new SyntaxController(gameBoard, player);
        String message = syntaxController.validCommand("TOOL");
        assertEquals(TypeOfChoiceEnum.TOOL, syntaxController.getPlayerMove().getTypeOfChoice());

    }

    /**
     * Tests CANCEL message
     */
    @Test
    public void commandCancekTest(){
        syntaxController = new SyntaxController(gameBoard, player);
        String message = syntaxController.validCommand("PICK");
        assertEquals(TypeOfChoiceEnum.PICK, syntaxController.getPlayerMove().getTypeOfChoice());

        message = syntaxController.validCommand("CANCEL");
        assertEquals(null, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals("OK MESSAGE", message.toUpperCase());
    }

}
