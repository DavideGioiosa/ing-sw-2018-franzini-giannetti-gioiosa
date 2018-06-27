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
    private final String NICKNAME = "Nickname";
    private final String NICKNAME_2 = "Nickname2";
    private final String NAME = "Name";
    private final String DESCRIPTION = "Description";
    private final ColourEnum FRAMECOLOUR = ColourEnum.BLUE;
    private final ColourEnum COLOUR = ColourEnum.BLUE;
    private final boolean CONNECTION = true;
    private SchemaCard schemaCard;
    private final int ID = 1;
    private final int DIFFICULTY = 1;
    private String strategy = "DiffColoursRow";
    private List<PrivatePlayer> privatePlayerList = new ArrayList<>();
    private GameBoard gameBoard;

    private Turn turn;
    private PlayerMove playerMove;


    /**
     * Create and set the gameBoard and create playMove
     */
    @Before
    public void init(){

        List<Cell> cellList = new ArrayList<>();
        int indexOfTurn = 1;
        int maxNumberOfDice = 1;
        int minNumberOfDice = 1;
        String avoidedRestriction = "";
        List<List<OperationString>> operationStrings = new ArrayList<>();
        operationStrings.add(new ArrayList<>());
        operationStrings.get(0).add(new OperationString("pick", "diceboard"));
        operationStrings.get(0).add(new OperationString("leave", "diceboard"));

        for(int j = 0; j < 20; j++){
            cellList.add(new Cell(0, null));
        }
        SchemaCard schemaCard = new SchemaCard(ID,"name","desc", DIFFICULTY, cellList);

        Player player = new Player(NICKNAME, CONNECTION, FRAMECOLOUR, schemaCard, 2);
        Player player2 = new Player(NICKNAME_2, CONNECTION, ColourEnum.RED, schemaCard, 3);
        List<Player> playerList = new ArrayList<Player>();
        playerList.add(player);
        playerList.add(player2);

        ToolCard toolCard = new ToolCard(1, NAME, DESCRIPTION, COLOUR, indexOfTurn, minNumberOfDice, maxNumberOfDice, avoidedRestriction, operationStrings);
        List<ToolCard> toolCardList = new ArrayList<>();
        toolCardList.add(toolCard);

        PublicObjCard publicObjCard = new PublicObjCard(5,NAME,DESCRIPTION,2, strategy);
        List<PublicObjCard> publicCardList = new ArrayList<>();
        publicCardList.add(publicObjCard);

        BoardDice boardDice = new BoardDice();
        TrackBoard trackBoardDice = new TrackBoard();
        BagDice bagDice = new BagDice();
        BoardCard boardCard = new BoardCard(publicCardList,toolCardList);

        gameBoard = new GameBoard(playerList, bagDice,boardDice, trackBoardDice, boardCard, privatePlayerList);
        playerMove = new PlayerMove();
        playerMove.setPlayer(player);
    }

    /**
     * Correct creation of a Turn with correct parameter
     */
    @Test
    public void Turn_shouldReturnTrueIfCorrectCreation(){
        turn = new Turn(gameBoard);
    }

    /**
     * Wrong creation of a Turn, should throw exception for null parameter
     */
    @Test
    public void Turn_shouldCallExceptionForNullGameBoard(){
        GameBoard gameBoardEmpty = null;
        try {
            turn = new Turn(gameBoardEmpty);
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
        turn = new Turn(gameBoard);
        for(int i = 0; i < 5; i++) {
            Die die = gameBoard.getBagDice().extractDice();
            die.firstRoll();
            gameBoard.getBoardDice().insertDie(die);
        }
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        playerMove.setDiceBoardIndex(3);
        playerMove.insertDiceSchemaWhereToLeave(new Position(2));

        boolean result = turn.runTurn(playerMove);
        assertTrue(result);
    }

    /**
     * Check if a player tries to do the action 'PICK' twice in a Round,
     * operation that is not allowed for the rules of the game
     * Expecting a negative result
     */
    @Test
    public void runTurn_shouldReturnFalseBecauseOfPICKTwice(){
        turn = new Turn(gameBoard);
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
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        playerMove.setDiceBoardIndex(4);
        playerMove.insertDiceSchemaWhereToLeave(new Position(5));

        boolean result = turn.runTurn(playerMove);
        assertFalse(result);
    }

    /**
     * Check the correct functioning of the run of a Turn with a TOOL action
     * Expecting a positive result
     */
    @Test
    public void runTurn_shouldReturnTrueIfCorrectTool(){
        turn = new Turn(gameBoard);
        for(int i = 0; i < 5; i++) {
            gameBoard.getBoardDice().insertDie(gameBoard.getBagDice().extractDice());
        }
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.TOOL);
        playerMove.setDiceBoardIndex(3);
        playerMove.insertDiceSchemaWhereToLeave(new Position(2));

        boolean result = turn.runTurn(playerMove);
        assertTrue(result);
    }

    /**
     * Check if a player tries to do the action 'TOOL' twice in a Round,
     * operation that is not allowed for the rules of the game
     * Expecting a negative result
     */
    @Test
    public void runTurn_shouldReturnFalseBecauseOfTOOLTwice(){
        turn = new Turn(gameBoard);
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

        boolean result = turn.runTurn(playerMove);
        assertFalse(result);
    }


    /**
     * Wrong use of run Turn, should throw exception for null parameter
     */
    @Test
    public void runTurn_shouldCallExceptionForNullPlayerMove(){
        turn = new Turn(gameBoard);
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
        turn = new Turn(gameBoard);
        for(int i = 0; i < 5; i++) {
            gameBoard.getBoardDice().insertDie(gameBoard.getBagDice().extractDice());
        }
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.PASS);
        turn.runTurn(playerMove);

        boolean result = turn.endTurn();
        assertTrue(result);
    }

    /**
     * Check the correct functioning of the end of the current Turn with a not PASS action
     * Expecting a negative result
     */
    @Test
    public void endTurn_shouldReturnFalseIfNotPASS(){
        turn = new Turn(gameBoard);
        for(int i = 0; i < 5; i++) {
            Die die = gameBoard.getBagDice().extractDice();
            die.firstRoll();
            gameBoard.getBoardDice().insertDie(die);
        }
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        playerMove.setDiceBoardIndex(3);
        playerMove.insertDiceSchemaWhereToLeave(new Position(2));
        turn.runTurn(playerMove);

        boolean result = turn.endTurn();
        assertFalse(result);
    }
}