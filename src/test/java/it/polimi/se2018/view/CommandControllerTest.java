package it.polimi.se2018.view;

import it.polimi.se2018.controller.OperationString;
import it.polimi.se2018.controller.client.CommandController;
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
 * View's Test Class: Command Controller
 * test the correct functioning of restriction methods related to die placement
 * @author Davide Gioiosa
 */

public class CommandControllerTest {
    private List<PrivatePlayer> privatePlayerList = new ArrayList<>();
    private GameBoard gameBoard;
    private Player player;
    private PlayerMove playerMovePick;
    private CommandController commandController;

    @Before
    public void setUp(){
        List<Cell> cellList = new ArrayList<Cell>();
        for(int i = 0; i<18; i++){
            cellList.add(new Cell(0, null));
        }
        cellList.add(new Cell(5, null));          //cell 18 value restriction
        cellList.add(new Cell(0, ColourEnum.YELLOW));   //cell 19 colour restriction
        final int ID = 1;
        final int DIFFICULTY = 1;
        SchemaCard schemaCard = new SchemaCard(ID,"name","desc", DIFFICULTY, cellList);

        final String NICKNAME = "Nickname";
        final String NAME = "Name";
        final String DESCRIPTION = "Description";
        int indexOfTurn = 1;
        int maxNumberOfDice = 1;
        int minNumberOfDice = 1;
        String avoidedRestriction = "";
        List<List<OperationString>> operationStrings = new ArrayList<>();
        operationStrings.add(new ArrayList<>());
        operationStrings.get(0).add(new OperationString("pick", "diceboard"));
        operationStrings.get(0).add(new OperationString("leave", "diceboard"));

        final boolean CONNECTION = true;

        String strategy = "DiffColoursRow";

        final ColourEnum FRAMECOLOUR = ColourEnum.BLUE;
        player = new Player(NICKNAME, CONNECTION, FRAMECOLOUR, schemaCard, 2);
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);

        final ColourEnum COLOUR = ColourEnum.BLUE;

        ToolCard toolCard = new ToolCard(1, NAME, DESCRIPTION, COLOUR, indexOfTurn, minNumberOfDice, maxNumberOfDice, avoidedRestriction, operationStrings);

        List<ToolCard> toolCardList = new ArrayList<>();
        toolCardList.add(toolCard);

        PublicObjCard publicObjCard = new PublicObjCard(5,NAME,DESCRIPTION, 2, strategy);
        List<PublicObjCard> publicCardList = new ArrayList<>();
        publicCardList.add(publicObjCard);

        BoardDice boardDice = new BoardDice();
        TrackBoard trackBoardDice = new TrackBoard();
        BagDice bagDice = new BagDice();
        BoardCard boardCard = new BoardCard(publicCardList,toolCardList);

        gameBoard = new GameBoard(playerList, bagDice,boardDice, trackBoardDice, boardCard, privatePlayerList);

        //Sets dice into the Draft Pool
        Die die = new Die(ColourEnum.BLUE);
        die.setValue(2);
        gameBoard.getBoardDice().insertDie(die);   //0
        Die die2 = new Die(ColourEnum.RED);
        die2.setValue(5);
        gameBoard.getBoardDice().insertDie(die2);  //1
        Die die3 = new Die(ColourEnum.YELLOW);
        die3.setValue(6);
        gameBoard.getBoardDice().insertDie(die3);  //2
        Die die4 = new Die(ColourEnum.BLUE);
        die4.setValue(1);
        gameBoard.getBoardDice().insertDie(die4);  //3
        Die die5 = new Die(ColourEnum.BLUE);
        die5.setValue(4);
        gameBoard.getBoardDice().insertDie(die5);  //4
        Die die6 = new Die(ColourEnum.GREEN);
        die6.setValue(4);
        gameBoard.getBoardDice().insertDie(die6);  //5

        //PICK Move in Borders, BLUE colour 4 value
        playerMovePick = new PlayerMove();
        playerMovePick.setPlayer(player);
        playerMovePick.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        playerMovePick.setDiceBoardIndex(4);
        playerMovePick.insertDiceSchemaWhereToLeave(new Position(14));

        commandController = new CommandController(null);
    }

    /**
     * Check that first placement of a die is on the border, correct player Move
     */
    @Test
    public void checkMoveValidity_positiveResultIsOnTheBorderCheck() {
        int resultCode = commandController.checkMoveValidity(playerMovePick, gameBoard);

        assertEquals(0, resultCode);
    }

    /**
     * Check that first placement of a die is on the border, incorrect player Move
     */
    @Test
    public void checkMoveValidity_negativeResultIsOnTheBorderCheck() {
        //PICK Move out of Borders at the beginning
        PlayerMove pmPickOutOfBorderRestriction = new PlayerMove();
        pmPickOutOfBorderRestriction.setPlayer(player);
        pmPickOutOfBorderRestriction.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        pmPickOutOfBorderRestriction.setDiceBoardIndex(2);
        pmPickOutOfBorderRestriction.insertDiceSchemaWhereToLeave(new Position(6));

        int resultCode = commandController.checkMoveValidity(pmPickOutOfBorderRestriction, gameBoard);

        assertEquals(1, resultCode);
    }

    /**
     * Check that after the first placement of a die, the die is placed adjacent at least near one, correct player Move
     */
    @Test
    public void checkMoveValidity_positiveResultIsAdjacent() {
        playerMovePick.getPlayer().getSchemaCard().setDiceIntoCell(playerMovePick.getDiceSchemaWhereToLeave().get(0),
                gameBoard.getBoardDice().takeDie(playerMovePick.getDiceBoardIndex())); //pos: 14, die: BLUE, 4

        //PICK Move adj to a die (diagonal), RED colour 5 value
        PlayerMove playerMovePickAdj = new PlayerMove();
        playerMovePickAdj.setPlayer(player);
        playerMovePickAdj.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        playerMovePickAdj.setDiceBoardIndex(1);
        playerMovePickAdj.insertDiceSchemaWhereToLeave(new Position(8));

        int resultCode = commandController.checkMoveValidity(playerMovePickAdj, gameBoard);
        boolean isSchemeEmpty = playerMovePick.getPlayer().getSchemaCard().isEmpty();

        assertEquals(0, resultCode);
        assertFalse(isSchemeEmpty);
    }

    /**
     * Check that after the first placement of a die, the die is placed adjacent at least near one, incorrect player Move
     */
    @Test
    public void checkMoveValidity_negativeResultIsAdjacent() {
        playerMovePick.getPlayer().getSchemaCard().setDiceIntoCell(playerMovePick.getDiceSchemaWhereToLeave().get(0),
                gameBoard.getBoardDice().takeDie(playerMovePick.getDiceBoardIndex())); //pos: 14, die: BLUE, 4

        //PICK move to check Adjacent Die restriction
        PlayerMove pmAdjNegativeRestriction = new PlayerMove();
        pmAdjNegativeRestriction.setPlayer(player);
        pmAdjNegativeRestriction.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        pmAdjNegativeRestriction.setDiceBoardIndex(2);
        pmAdjNegativeRestriction.insertDiceSchemaWhereToLeave(new Position(5));

        int resultCode = commandController.checkMoveValidity(pmAdjNegativeRestriction, gameBoard);

        assertEquals(2, resultCode);
    }

    /**
     * Check that if there is a Colour limitation on the cell, the die is the right colour requested,
     * correct player Move
     */
    @Test
    public void checkMoveValidity_positiveResultColourRestriction() {
        playerMovePick.getPlayer().getSchemaCard().setDiceIntoCell(playerMovePick.getDiceSchemaWhereToLeave().get(0),
                gameBoard.getBoardDice().takeDie(playerMovePick.getDiceBoardIndex())); //pos: 14, die: BLUE, 4

        //PICK Move to check colour Restriction, YELLOW colour 6 value
        PlayerMove pmPickColourRestriction = new PlayerMove();
        pmPickColourRestriction.setPlayer(player);
        pmPickColourRestriction.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        pmPickColourRestriction.setDiceBoardIndex(2);
        pmPickColourRestriction.insertDiceSchemaWhereToLeave(new Position(19));

        int resultCode = commandController.checkMoveValidity(pmPickColourRestriction, gameBoard);

        assertEquals(0, resultCode);
    }

    /**
     * Check that if there is a Colour limitation on the cell, the die is the right colour requested,
     * incorrect player Move
     */
    @Test
    public void checkMoveValidity_negativeResultColourRestriction() {
        playerMovePick.getPlayer().getSchemaCard().setDiceIntoCell(playerMovePick.getDiceSchemaWhereToLeave().get(0),
                gameBoard.getBoardDice().takeDie(playerMovePick.getDiceBoardIndex())); //pos: 14, die: BLUE, 4

        //PICK Move to check colour Restriction, RED colour 5 value
        PlayerMove pmPickNegativeColourRestriction = new PlayerMove();
        pmPickNegativeColourRestriction.setPlayer(player);
        pmPickNegativeColourRestriction.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        pmPickNegativeColourRestriction.setDiceBoardIndex(1);
        pmPickNegativeColourRestriction.insertDiceSchemaWhereToLeave(new Position(19));

        int resultCode = commandController.checkMoveValidity(pmPickNegativeColourRestriction, gameBoard);

        assertEquals(3, resultCode);
    }

    /**
     * Check that if there is a Value limitation on the cell, the die is the right value requested, correct player Move
     */
    @Test
    public void checkMoveValidity_positiveResultValueRestriction() {
        playerMovePick.getPlayer().getSchemaCard().setDiceIntoCell(playerMovePick.getDiceSchemaWhereToLeave().get(0),
                gameBoard.getBoardDice().takeDie(playerMovePick.getDiceBoardIndex())); //pos: 14, die: BLUE, 4

        //PICK Move to check colour Restriction, RED colour 5 value
        PlayerMove pmPickValueRestriction = new PlayerMove();
        pmPickValueRestriction.setPlayer(player);
        pmPickValueRestriction.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        pmPickValueRestriction.setDiceBoardIndex(1);
        pmPickValueRestriction.insertDiceSchemaWhereToLeave(new Position(18));

        int resultCode = commandController.checkMoveValidity(pmPickValueRestriction, gameBoard);

        assertEquals(0, resultCode);
    }

    /**
     * Check that if there is a Value limitation on the cell, the die is the right value requested, incorrect player Move
     */
    @Test
    public void checkMoveValidity_negativeResultValueRestriction() {
        playerMovePick.getPlayer().getSchemaCard().setDiceIntoCell(playerMovePick.getDiceSchemaWhereToLeave().get(0),
                gameBoard.getBoardDice().takeDie(playerMovePick.getDiceBoardIndex())); //pos: 14, die: BLUE, 4

        //PICK Move to check colour Restriction, BLUE colour 4 value
        PlayerMove pmPickNegativeValueRestriction = new PlayerMove();
        pmPickNegativeValueRestriction.setPlayer(player);
        pmPickNegativeValueRestriction.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        pmPickNegativeValueRestriction.setDiceBoardIndex(0);
        pmPickNegativeValueRestriction.insertDiceSchemaWhereToLeave(new Position(18));

        int resultCode = commandController.checkMoveValidity(pmPickNegativeValueRestriction, gameBoard);

        assertEquals(4, resultCode);
    }

    /**
     * Check that after the first placement of a die, the die is placed adjacent at least near one, respecting adj Colour
     * Restriction, correct player Move
     */
    @Test
    public void checkMoveValidity_positiveResultAdjacentColourRestriction() {
        playerMovePick.getPlayer().getSchemaCard().setDiceIntoCell(playerMovePick.getDiceSchemaWhereToLeave().get(0),
                gameBoard.getBoardDice().takeDie(playerMovePick.getDiceBoardIndex())); //pos: 14, die: BLUE, 4

        //PICK Move to check ADJ-Colour Restriction, RED colour 5 value
        PlayerMove pmPickAdjColourRestriction = new PlayerMove();
        pmPickAdjColourRestriction.setPlayer(player);
        pmPickAdjColourRestriction.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        pmPickAdjColourRestriction.setDiceBoardIndex(1);
        pmPickAdjColourRestriction.insertDiceSchemaWhereToLeave(new Position(13));

        int resultCode = commandController.checkMoveValidity(pmPickAdjColourRestriction, gameBoard);

        assertEquals(0, resultCode);
    }

    /**
     * Check that after the first placement of a die, the die is placed adjacent at least near one, respecting adj Colour
     * Restriction, incorrect player Move
     */
    @Test
    public void checkMoveValidity_negativeResultAdjacentColourRestriction() {
        playerMovePick.getPlayer().getSchemaCard().setDiceIntoCell(playerMovePick.getDiceSchemaWhereToLeave().get(0),
                gameBoard.getBoardDice().takeDie(playerMovePick.getDiceBoardIndex())); //pos: 14, die: BLUE, 4

        //PICK Move to check ADJ-Colour Restriction, RED colour 5 value
        PlayerMove pmPickNegativeAdjColourRestriction = new PlayerMove();
        pmPickNegativeAdjColourRestriction.setPlayer(player);
        pmPickNegativeAdjColourRestriction.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        pmPickNegativeAdjColourRestriction.setDiceBoardIndex(3);
        pmPickNegativeAdjColourRestriction.insertDiceSchemaWhereToLeave(new Position(13));

        int resultCode = commandController.checkMoveValidity(pmPickNegativeAdjColourRestriction, gameBoard);

        assertEquals(5, resultCode);
    }

    /**
     * Check that after the first placement of a die, the die is placed adjacent at least near one, respecting adj Value
     * Restriction, correct player Move
     */
    @Test
    public void checkMoveValidity_positiveResultAdjacentValueRestriction() {
        playerMovePick.getPlayer().getSchemaCard().setDiceIntoCell(playerMovePick.getDiceSchemaWhereToLeave().get(0),
                gameBoard.getBoardDice().takeDie(playerMovePick.getDiceBoardIndex())); //pos: 14, die: BLUE, 4


        //PICK Move to check ADJ-Value Restriction, YELLOW colour 6 value
        PlayerMove playerMovePickAdjValue = new PlayerMove();
        playerMovePickAdjValue.setPlayer(player);
        playerMovePickAdjValue.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        playerMovePickAdjValue.setDiceBoardIndex(2);
        playerMovePickAdjValue.insertDiceSchemaWhereToLeave(new Position(13));

        int resultCode = commandController.checkMoveValidity(playerMovePickAdjValue, gameBoard);

        assertEquals(0, resultCode);
    }

    /**
     * Check that after the first placement of a die, the die is placed adjacent at least near one, respecting adj Colour
     * Restriction, incorrect player Move
     */
    @Test
    public void checkMoveValidity_negativeResultAdjacentValueRestriction() {
        playerMovePick.getPlayer().getSchemaCard().setDiceIntoCell(playerMovePick.getDiceSchemaWhereToLeave().get(0),
                gameBoard.getBoardDice().takeDie(playerMovePick.getDiceBoardIndex())); //pos: 14, die: BLUE, 4

        //PICK Move to check ADJ-Value Restriction, GREEN colour 4 value
        PlayerMove pmPickNegativeAdjValueRestriction = new PlayerMove();
        pmPickNegativeAdjValueRestriction.setPlayer(player);
        pmPickNegativeAdjValueRestriction.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        pmPickNegativeAdjValueRestriction.setDiceBoardIndex(4);
        pmPickNegativeAdjValueRestriction.insertDiceSchemaWhereToLeave(new Position(13));

        int resultCode = commandController.checkMoveValidity(pmPickNegativeAdjValueRestriction, gameBoard);

        assertEquals(6, resultCode);
    }

}