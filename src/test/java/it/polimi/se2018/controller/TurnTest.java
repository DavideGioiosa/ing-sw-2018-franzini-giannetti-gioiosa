package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.publiccard.PublicObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PrivatePlayer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test Controller's Class Turn
 * @author Davide Gioiosa
 */

public class TurnTest {

    private final ColourEnum FRAMECOLOUR = ColourEnum.BLUE;
    private final ColourEnum COLOUR = ColourEnum.BLUE;

    private GameBoard gameBoard;
    private Player player;
    private Turn turn;
    private PlayerMove playerMove;


    /**
     * Create and set the gameBoard and create playMove
     */
    @Before
    public void init(){
        GameLoader gameLoader = new GameLoader();
        GameBoardTest gameBoardTest = new GameBoardTest();
        gameBoard = gameBoardTest.newGameBoard();

        for(int i = 0; i<12; i++){
            gameBoard.getCardOnBoard().getToolCardList().add((ToolCard) gameLoader.getToolDeck().extractCard());
        }

        gameBoard.getPlayerList().get(0).getSchemaCard();
        Die die = new Die (ColourEnum.RED);
        die.setValue(5);
        gameBoard.getPlayerList().get(0).getSchemaCard().setDiceIntoCell(new Position(7),die);
        die = new Die (ColourEnum.GREEN);
        die.setValue(5);
        gameBoard.getPlayerList().get(0).getSchemaCard().setDiceIntoCell(new Position(5),die);
        die = new Die (ColourEnum.BLUE);
        die.setValue(1);
        gameBoard.getPlayerList().get(0).getSchemaCard().setDiceIntoCell(new Position(1),die);
        die = new Die (ColourEnum.BLUE);
        die.setValue(3);
        gameBoard.getPlayerList().get(0).getSchemaCard().setDiceIntoCell(new Position(6),die);
        die = new Die (ColourEnum.YELLOW);
        die.setValue(4);
        gameBoard.getPlayerList().get(0).getSchemaCard().setDiceIntoCell(new Position(0),die);

        die = new Die(ColourEnum.GREEN);
        die.setValue(2);
        this.gameBoard.getBoardDice().insertDie(die);
        die = new Die(ColourEnum.GREEN);
        die.setValue(3);
        this.gameBoard.getBoardDice().insertDie(die);
        die = new Die(ColourEnum.YELLOW);
        die.setValue(3);
        this.gameBoard.getBoardDice().insertDie(die);


        playerMove = new PlayerMove();
        playerMove.setPlayer(gameBoard.getPlayerList().get(0));
    }

    /**
     * Correct creation of a Turn with correct parameter
     */
    @Test
    public void Turn_shouldReturnTrueIfCorrectCreation(){
        turn = new Turn(gameBoard, player);
        assertTrue(turn.getTurnsActionsList().isEmpty());
    }

    /**
     * Wrong creation of a Turn, should throw exception for null parameter
     */
    @Test
    public void Turn_shouldCallExceptionForNullGameBoard(){
        GameBoard gameBoardEmpty = null;
        try {
            turn = new Turn(gameBoardEmpty, player);
            fail();
        }catch (NullPointerException e){
        }
    }

    /**
     * Check the correct functioning of the run of a Turn with a PICK action
     * Expecting a positive result
     */
    @Test
    public void runTurn_shouldReturnTrueIfCorrectPick(){
        turn = new Turn(gameBoard, player);
        for(int i = 0; i < 5; i++) {
            Die die = gameBoard.getBagDice().extractDice();
            die.firstRoll();
            gameBoard.getBoardDice().insertDie(die);
        }
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        playerMove.setDiceBoardIndex(2);
        playerMove.insertDiceSchemaWhereToLeave(new Position(2));

        int result = turn.runTurn(playerMove);
        assertTrue(result == 0);
    }

    /**
     * Check if a player tries to do the action 'PICK' twice in a Round,
     * operation that is not allowed for the rules of the game
     * Expecting a negative result
     */
    @Test
    public void runTurn_shouldReturnFalseBecauseOfPICKTwice(){
        turn = new Turn(gameBoard, player);
        for(int i = 0; i < 5; i++) {
            Die die = gameBoard.getBagDice().extractDice();
            die.firstRoll();
            gameBoard.getBoardDice().insertDie(die);
        }
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        playerMove.setDiceBoardIndex(3);
        playerMove.insertDiceSchemaWhereToLeave(new Position(2));

        turn.runTurn(playerMove);

        playerMove.newPlayerMove();
        playerMove.setPlayer(gameBoard.getPlayerList().get(0));
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        playerMove.setDiceBoardIndex(4);
        playerMove.insertDiceSchemaWhereToLeave(new Position(5));

        int result = turn.runTurn(playerMove);
        assertFalse(result == 0);
    }

    /**
     * Check if a player tries to do the action 'TOOL' twice in a Round,
     * operation that is not allowed for the rules of the game
     * Expecting a negative result
     */
    @Test
    public void runTurn_shouldReturnFalseBecauseOfTOOLTwice(){
        turn = new Turn(gameBoard, player);
        for(int i = 0; i < 5; i++) {
            gameBoard.getBoardDice().insertDie(gameBoard.getBagDice().extractDice());
        }
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.TOOL);
        playerMove.setDiceBoardIndex(3);
        playerMove.insertDiceSchemaWhereToLeave(new Position(2));

        turn.runTurn(playerMove);

        playerMove.newPlayerMove();
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.TOOL);
        playerMove.setDiceBoardIndex(4);
        playerMove.insertDiceSchemaWhereToLeave(new Position(5));

        int result = turn.runTurn(playerMove);
        assertFalse(result == 0);
    }


    /**
     * Wrong use of run Turn, should throw exception for null parameter
     */
    @Test
    public void runTurn_shouldCallExceptionForNullPlayerMove(){
        turn = new Turn(gameBoard, player);
        PlayerMove playerMoveNull = null;
        try {
            turn.runTurn(playerMoveNull);
            fail();
        }catch (RuntimeException e){
        }
    }

    /**
     * Check the correct functioning of the end of the current Turn with a PASS action
     * Expecting a positive result
     */
    @Test
    public void endTurn_shouldReturnTrueIfPASS(){
        turn = new Turn(gameBoard, player);
        for(int i = 0; i < 5; i++) {
            gameBoard.getBoardDice().insertDie(gameBoard.getBagDice().extractDice());
        }
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.PASS);
        turn.runTurn(playerMove);

        boolean result = turn.isFinished();
        assertTrue(result);
    }

    /**
     * Check the correct functioning of the end of the current Turn with a not PASS action
     * Expecting a negative result
     */
    @Test
    public void endTurn_shouldReturnFalseIfNotPASS(){
        turn = new Turn(gameBoard, player);
        for(int i = 0; i < 5; i++) {
            Die die = gameBoard.getBagDice().extractDice();
            die.firstRoll();
            gameBoard.getBoardDice().insertDie(die);
        }
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        playerMove.setDiceBoardIndex(3);
        playerMove.insertDiceSchemaWhereToLeave(new Position(2));
        turn.runTurn(playerMove);

        boolean result = turn.isFinished();
        assertFalse(result);
    }

    @Test
    public void useTool300(){
        Turn turn = new Turn(gameBoard, player);

        playerMove.setTypeOfChoice(TypeOfChoiceEnum.TOOL);
        playerMove.setIdToolCard(300);
        playerMove.setDiceBoardIndex(0);
        playerMove.setValue(-1);

        int error = turn.runTurn(playerMove);

        assertEquals(0, error);
        assertEquals(1, gameBoard.getBoardDice().getDieList().get(2).getValue());
        assertEquals(ColourEnum.GREEN, gameBoard.getBoardDice().getDieList().get(2).getColour());
    }

    @Test
    public void useTool301(){
        Turn turn = new Turn(gameBoard, player);

        playerMove.setTypeOfChoice(TypeOfChoiceEnum.TOOL);
        playerMove.setIdToolCard(301);
        playerMove.insertDiceSchemaWhereToTake(new Position(0,0));
        playerMove.insertDiceSchemaWhereToLeave(new Position(2,2));

        int error= turn.runTurn(playerMove);

        assertEquals(0, error);
        assertTrue(gameBoard.getPlayerList().get(0).getSchemaCard().getCellList().get(0).isEmpty());
        assertEquals(ColourEnum.YELLOW, gameBoard.getPlayerList().get(0).getSchemaCard().getCellList().get(12).getDie().getColour());
        assertEquals(4, gameBoard.getPlayerList().get(0).getSchemaCard().getCellList().get(12).getDie().getValue());
    }
}