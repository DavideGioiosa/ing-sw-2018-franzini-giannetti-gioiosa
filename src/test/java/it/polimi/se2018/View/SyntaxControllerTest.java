package it.polimi.se2018.View;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PlayerTest;
import it.polimi.se2018.view.CommandTypeEnum;
import it.polimi.se2018.view.SyntaxController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests SyntaxController Class
 *
 * @author Cristian Giannetti
 */
public class SyntaxControllerTest {
    private SyntaxController syntaxController;
    private GameBoard gameBoard;
    private Player player;
    private final String OKMESSAGE = "OK MESSAGE";
    private final String ERRORMESSAGE = "COMANDO ERRATO";


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
        String message;
        message = syntaxController.validCommand("PICK");
        assertEquals(TypeOfChoiceEnum.PICK, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(OKMESSAGE, message);
        assertEquals(CommandTypeEnum.DICEBOARDINDEX, syntaxController.getNextCommandType());

        message = syntaxController.validCommand("0");
        assertEquals(TypeOfChoiceEnum.PICK, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(ERRORMESSAGE, message);
        assertEquals(-1, syntaxController.getPlayerMove().getDiceBoardIndex());
        assertEquals(CommandTypeEnum.DICEBOARDINDEX, syntaxController.getNextCommandType());
    }

    /**
     * Tests TOOL message with valid ID
     */
    @Test
    public void commandToolCardOKTest(){
        syntaxController = new SyntaxController(gameBoard, player);
        String message = syntaxController.validCommand("TOOL");
        assertEquals(TypeOfChoiceEnum.TOOL, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(OKMESSAGE, message.toUpperCase());
        assertEquals(CommandTypeEnum.TOOLCARDID, syntaxController.getNextCommandType());

        message = syntaxController.validCommand("300");
        assertEquals(TypeOfChoiceEnum.TOOL, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(CommandTypeEnum.TOOLCARDID, syntaxController.getNextCommandType());
        assertEquals(OKMESSAGE, message);
    }

    /**
     * Tests TOOL message with wrong ID
     */
    @Test
    public void commandToolCardTest(){
        syntaxController = new SyntaxController(gameBoard, player);
        String message = syntaxController.validCommand("TOOL");
        assertEquals(TypeOfChoiceEnum.TOOL, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(OKMESSAGE, message.toUpperCase());
        assertEquals(CommandTypeEnum.TOOLCARDID, syntaxController.getNextCommandType());

        message = syntaxController.validCommand("100");
        assertEquals(TypeOfChoiceEnum.TOOL, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(CommandTypeEnum.TOOLCARDID, syntaxController.getNextCommandType());
        assertEquals(ERRORMESSAGE, message);

    }
    /**
     * Tests CANCEL message
     */
    @Test
    public void commandResetTest(){
        syntaxController = new SyntaxController(gameBoard, player);
        String message = syntaxController.validCommand("PICK");
        assertEquals(OKMESSAGE, message.toUpperCase());
        assertEquals(TypeOfChoiceEnum.PICK, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(CommandTypeEnum.DICEBOARDINDEX, syntaxController.getNextCommandType());

        message = syntaxController.validCommand("CANCEL");
        assertEquals(OKMESSAGE, message.toUpperCase());
        assertEquals(null, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(CommandTypeEnum.TYPEOFCHOICE, syntaxController.getNextCommandType());
    }

    /**
     * Tests PASS command
     */
    @Test
    public void commandPassTest(){
        syntaxController = new SyntaxController(gameBoard, player);
        String message = syntaxController.validCommand("PASS");
        assertEquals(OKMESSAGE, message.toUpperCase());
        assertEquals(TypeOfChoiceEnum.PASS, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(CommandTypeEnum.COMPLETE, syntaxController.getNextCommandType());
    }

    /**
     * Tests empty message
     */
    @Test
    public void commandNotSettedTest(){
        syntaxController = new SyntaxController(gameBoard, player);
        String message = syntaxController.validCommand("");
        assertEquals(ERRORMESSAGE, message.toUpperCase());
        assertEquals(null, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(CommandTypeEnum.TYPEOFCHOICE, syntaxController.getNextCommandType());
    }

    /**
     * Tests wrong message
     */
    @Test
    public void commandWrongTest(){
        syntaxController = new SyntaxController(gameBoard, player);
        String message = syntaxController.validCommand("NOTSUPPORTED");
        assertEquals(ERRORMESSAGE, message.toUpperCase());
        assertEquals(null, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(CommandTypeEnum.TYPEOFCHOICE, syntaxController.getNextCommandType());
    }

    /**
     * Tests null message
     */
    @Test
    public void commandNullTest(){
        syntaxController = new SyntaxController(gameBoard, player);
        String message = syntaxController.validCommand(null);
        assertEquals(ERRORMESSAGE, message.toUpperCase());
        assertEquals(null, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(CommandTypeEnum.TYPEOFCHOICE, syntaxController.getNextCommandType());
    }

    /**
     * Tests ROLL message
     */
    @Test
    public void commandRollTest(){
        syntaxController = new SyntaxController(gameBoard, player);
        String message = syntaxController.validCommand("ROLL");
        assertEquals(OKMESSAGE, message.toUpperCase());
        assertEquals(TypeOfChoiceEnum.ROLL, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(CommandTypeEnum.COMPLETE, syntaxController.getNextCommandType());
    }

    /**
     * Tests EXTRACT message
     */
    @Test
    public void commandExtractTest(){
        syntaxController = new SyntaxController(gameBoard, player);
        String message = syntaxController.validCommand("EXTRACT");
        assertEquals(OKMESSAGE, message.toUpperCase());
        assertEquals(TypeOfChoiceEnum.EXTRACT, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(CommandTypeEnum.COMPLETE, syntaxController.getNextCommandType());
    }

    /**
     * Tests Complete Pick move
     */
    @Test
    public void commandPickDiceTest(){
        syntaxController = new SyntaxController(gameBoard, player);
        gameBoard.getBoardDice().insertDice(gameBoard.getBagDice().extractDice());

        String message = syntaxController.validCommand("PICK");
        assertEquals(TypeOfChoiceEnum.PICK, syntaxController.getPlayerMove().getTypeOfChoice());
        assertEquals(OKMESSAGE, message);
        assertEquals(CommandTypeEnum.DICEBOARDINDEX, syntaxController.getNextCommandType());

        message = syntaxController.validCommand("0");
        assertEquals(CommandTypeEnum.DICESCHEMAWHERETOLEAVE, syntaxController.getNextCommandType());
        assertEquals(OKMESSAGE, message);

        message = syntaxController.validCommand(("1 1"));
        assertEquals(CommandTypeEnum.COMPLETE, syntaxController.getNextCommandType());
        assertEquals(OKMESSAGE, message);
    }

}
