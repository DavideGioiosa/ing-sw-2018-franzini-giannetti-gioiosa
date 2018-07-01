package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PickControllerTest {

    private GameBoard gameBoard;

    private PickController pickController;

    @Before
    public void setUp(){
        GameBoardTest gameBoardTest = new GameBoardTest();
        pickController = new PickController();
        gameBoard = gameBoardTest.newGameBoard();
        Die die = new Die(ColourEnum.YELLOW);
        die.firstRoll();
        gameBoard.getBoardDice().insertDie(die);
    }

    @Test
    public void constructorTest(){
        assertFalse(pickController.isComplete());
        assertFalse(pickController.isPass());
    }

    @Test
    public void doPickActionTest(){
        PlayerMove playerMove = new PlayerMove();
        playerMove.setPlayer(gameBoard.getPlayerList().get(0));
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        playerMove.setDiceBoardIndex(0);
        Die die = gameBoard.getBoardDice().getDieList().get(0);
        playerMove.insertDiceSchemaWhereToLeave(new Position(3));
        assertEquals(0, pickController.doAction(gameBoard, playerMove, null, null));
        assertEquals(die, gameBoard.getPlayerList().get(0).getSchemaCard().getCellList().get(3).getDie());
        assertTrue(gameBoard.getBoardDice().getDieList().isEmpty());
        assertTrue(pickController.isComplete());
        assertFalse(pickController.isPass());
    }

    @Test
    public void doDefaultMoveTest(){
        assertEquals(0, pickController.doDefaultMove());
    }

    @Test
    public void doPassActionTest(){
        PlayerMove playerMove = new PlayerMove();
        playerMove.setPlayer(gameBoard.getPlayerList().get(0));
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.PASS);
        assertEquals(0, pickController.doAction(gameBoard, playerMove, null, null));
        assertFalse(gameBoard.getBoardDice().getDieList().isEmpty());
        assertTrue(pickController.isComplete());
        assertTrue(pickController.isPass());
    }
}
