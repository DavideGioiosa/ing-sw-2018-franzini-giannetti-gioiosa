package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ToolControllerTest {

    private GameBoard gameBoard;
    private Player player;
    private Turn turn;
    private PlayerMove playerMove;
    private GameLoader gameLoader;

    //TODO: Testare la carta 7

    @Before
    public void setUp(){
        GameBoardTest gameBoardTest = new GameBoardTest();

        gameBoard = gameBoardTest.newGameBoard();
        player = gameBoard.getPlayerList().get(0);

        playerMove = new PlayerMove();
        playerMove.setPlayer(player);

        Die die1 = new Die(ColourEnum.YELLOW);
        die1.setValue(5);
        Die die2 = new Die(ColourEnum.BLUE);
        die2.setValue(4);
        Die die3 = new Die(ColourEnum.RED);
        die3.setValue(3);
        Die die4 = new Die(ColourEnum.PURPLE);
        die4.setValue(1);
        Die die5 = new Die(ColourEnum.BLUE);
        die5.setValue(2);
        Die die6 = new Die(ColourEnum.RED);
        die6.setValue(1);
        Die die7 = new Die(ColourEnum.YELLOW);
        die7.setValue(6);
        Die die8 = new Die(ColourEnum.GREEN);
        die8.setValue(4);
        Die die9 = new Die(ColourEnum.YELLOW);
        die9.setValue(1);

        player.getSchemaCard().setDiceIntoCell(new Position(0,0), die1);
        player.getSchemaCard().setDiceIntoCell(new Position(0,1), die2);
        player.getSchemaCard().setDiceIntoCell(new Position(1,1), die3);

        gameBoard.getBoardDice().insertDie(die4);
        gameBoard.getBoardDice().insertDie(die5);
        gameBoard.getBoardDice().insertDie(die9);

        List<Die> dieList = new ArrayList<>();
        dieList.add(die6);
        gameBoard.getTrackBoardDice().insertDice(dieList);

        dieList = new ArrayList<>();
        dieList.add(die7);
        dieList.add(die8);
        gameBoard.getTrackBoardDice().insertDice(dieList);

        gameLoader = new GameLoader();

    }

    @Test
    public void creationToolCardTest(){

    }

    @Test
    public void useToolWithID1Test() {
        playerMove.setDiceBoardIndex(1);
        playerMove.setValue(1);

        List<List<OperationString>> operationList = new ArrayList<>();
        operationList.add(new ArrayList<>());

        operationList.get(0).add(new OperationString("pick", "diceboard"));
        operationList.get(0).add(new OperationString("incdecvalue", ""));
        operationList.get(0).add(new OperationString("leave", "diceboard"));
        ToolCard toolCard;
        do {
            toolCard = (ToolCard) gameLoader.getToolDeck().extractCard();
        }while (!toolCard.getName().equals("Pinza Sgrossatrice"));
        ToolController toolController = new ToolController(toolCard);

        toolController.doAction(gameBoard, playerMove, gameBoard.getPlayerList(),turn);
        assertEquals(3, gameBoard.getBoardDice().getDieList().get(gameBoard.getBoardDice().getDieList().size()-1).getValue());
        assertEquals(ColourEnum.BLUE, gameBoard.getBoardDice().getDieList().get(gameBoard.getBoardDice().getDieList().size()-1).getColour());

    }

    @Test
    public void useToolWithID2Test() {

        playerMove.insertDiceSchemaWhereToTake(new Position(1,1));
        playerMove.insertDiceSchemaWhereToLeave(new Position(1,0));

        List<List<OperationString>> operationList = new ArrayList<>();
        operationList.add(new ArrayList<>());


        operationList.get(0).add(new OperationString("pick", "schemacard"));
        operationList.get(0).add(new OperationString("leave", "schemacard"));

        ToolCard toolCard;
        do {
            toolCard = (ToolCard) gameLoader.getToolDeck().extractCard();
        }while (!toolCard.getName().equals("Pennello per Eglomise"));
        ToolController toolController = new ToolController(toolCard);

        toolController.doAction(gameBoard, playerMove, gameBoard.getPlayerList(),turn);

        assertEquals(ColourEnum.RED, player.getSchemaCard().getCellList().get(5).getDie().getColour());
        assertEquals(3, player.getSchemaCard().getCellList().get(5).getDie().getValue());
        assertTrue(player.getSchemaCard().getCellList().get(6).isEmpty());

    }

    @Test
    public void useToolWithID3Test() {

        playerMove.insertDiceSchemaWhereToTake(new Position(1,1));
        playerMove.insertDiceSchemaWhereToLeave(new Position(1,2));

        List<List<OperationString>> operationList = new ArrayList<>();
        operationList.add(new ArrayList<>());


        operationList.get(0).add(new OperationString("pick", "schemacard"));
        operationList.get(0).add(new OperationString("leave", "schemacard"));

        ToolCard toolCard;
        do {
            toolCard = (ToolCard) gameLoader.getToolDeck().extractCard();
        }while (!toolCard.getName().equals("Alesatore per lamina di rame"));
        ToolController toolController = new ToolController(toolCard);
        toolController.doAction(gameBoard, playerMove, gameBoard.getPlayerList(),turn);

        assertEquals(ColourEnum.RED, player.getSchemaCard().getCellList().get(7).getDie().getColour());
        assertEquals(3, player.getSchemaCard().getCellList().get(7).getDie().getValue());
        assertTrue(player.getSchemaCard().getCellList().get(6).isEmpty());

    }

    @Test
    public void useToolWithID4Test() {
        ToolCard toolCard;
        do {
            toolCard = (ToolCard) gameLoader.getToolDeck().extractCard();
        } while (!toolCard.getName().equals("Lathekin"));
        ToolController toolController = new ToolController(toolCard);


        playerMove.insertDiceSchemaWhereToTake(new Position(1, 1));
        playerMove.insertDiceSchemaWhereToTake(new Position(0, 0));
        playerMove.insertDiceSchemaWhereToLeave(new Position(0, 2));
        playerMove.insertDiceSchemaWhereToLeave(new Position(0, 3));

        int error = toolController.doAction(gameBoard, playerMove, gameBoard.getPlayerList(), turn);

        assertEquals(0, error);

        assertTrue(player.getSchemaCard().getCellList().get(6).isEmpty());
        assertTrue(player.getSchemaCard().getCellList().get(0).isEmpty());

        assertEquals(ColourEnum.RED, player.getSchemaCard().getCellList().get(2).getDie().getColour());
        assertEquals(3, player.getSchemaCard().getCellList().get(2).getDie().getValue());

        assertEquals(5, player.getSchemaCard().getCellList().get(3).getDie().getValue());
        assertEquals(ColourEnum.YELLOW, player.getSchemaCard().getCellList().get(3).getDie().getColour());
    }

    @Test
    public void useToolWithID5Test() {

        playerMove.setDiceBoardIndex(1);
        playerMove.setTrackBoardIndex(1,1);

        List<List<OperationString>> operationList = new ArrayList<>();
        operationList.add(new ArrayList<>());

        operationList.get(0).add(new OperationString("pick", "diceboard"));
        operationList.get(0).add(new OperationString("exchange", "trackboard"));
        operationList.get(0).add(new OperationString("leave", "diceboard"));

        ToolCard toolCard;
        do {
            toolCard = (ToolCard) gameLoader.getToolDeck().extractCard();
        }while (!toolCard.getName().equals("Taglierina circolare"));
        ToolController toolController = new ToolController(toolCard);
        toolController.doAction(gameBoard, playerMove, gameBoard.getPlayerList(), turn);

        assertEquals(ColourEnum.GREEN, gameBoard.getBoardDice().getDieList().get(gameBoard.getBoardDice().getDieList().size() - 1).getColour());
        assertEquals(4, gameBoard.getBoardDice().getDieList().get(gameBoard.getBoardDice().getDieList().size() - 1).getValue());

        assertEquals(ColourEnum.BLUE, gameBoard.getTrackBoardDice().getDiceList().get(1).get(1).getColour());
        assertEquals(2, gameBoard.getTrackBoardDice().getDiceList().get(1).get(1).getValue());

    }

    @Test
    public void useToolWithID6Test() {
        playerMove.setDiceBoardIndex(1);

        List<List<OperationString>> operationList = new ArrayList<>();
        operationList.add(new ArrayList<>());

        operationList.get(0).add(new OperationString("pick", "diceboard"));
        operationList.get(0).add(new OperationString("reroll", ""));
        operationList.get(0).add(new OperationString("leave", "diceboard"));

        ToolCard toolCard;
        do {
            toolCard = (ToolCard) gameLoader.getToolDeck().extractCard();
        }while (!toolCard.getName().equals("Pennello per Pasta Salda"));
        ToolController toolController = new ToolController(toolCard);
        toolController.doAction(gameBoard, playerMove, gameBoard.getPlayerList(),turn);

        //assertEquals(3, gameBoard.getBoardDice().getDieList().size());
        //assertEquals(ColourEnum.BLUE, gameBoard.getBoardDice().getDieList().get(gameBoard.getBoardDice().getDieList().size()-1).getColour());
        //TODO: DA COMPLETARE
    }

    @Test
    public void useToolWithID7Test() {

        List<List<OperationString>> operationList = new ArrayList<>();
        operationList.add(new ArrayList<>());

        operationList.get(0).add(new OperationString("pick", "diceboard"));
        operationList.get(0).add(new OperationString("reroll", ""));
        operationList.get(0).add(new OperationString("leave", "diceboard"));

        ToolCard toolCard;
        do {
            toolCard = (ToolCard) gameLoader.getToolDeck().extractCard();
        }while (!toolCard.getName().equals("Martelletto"));
        ToolController toolController = new ToolController(toolCard);

        toolController.doAction(gameBoard,playerMove, gameBoard.getPlayerList(),turn);

        assertEquals(3, gameBoard.getBoardDice().getDieList().size());
        assertEquals(ColourEnum.PURPLE, gameBoard.getBoardDice().getDieList().get(0).getColour());
        assertEquals(ColourEnum.BLUE, gameBoard.getBoardDice().getDieList().get(1).getColour());
        assertEquals(ColourEnum.YELLOW, gameBoard.getBoardDice().getDieList().get(2).getColour());


    }

    @Test
    public void useToolWithID9Test() {

        playerMove.setDiceBoardIndex(2);
        playerMove.insertDiceSchemaWhereToLeave(new Position(0,4));

        List<List<OperationString>> operationList = new ArrayList<>();
        operationList.add(new ArrayList<>());

        operationList.get(0).add(new OperationString("pick", "diceboard"));
        operationList.get(0).add(new OperationString("leave", "schemacard"));

        ToolCard toolCard;
        do {
            toolCard = (ToolCard) gameLoader.getToolDeck().extractCard();
        }while (!toolCard.getName().equals("Riga in Sughero"));
        ToolController toolController = new ToolController(toolCard);

        toolController.doAction(gameBoard,playerMove, gameBoard.getPlayerList(),turn);

        assertEquals(ColourEnum.YELLOW, player.getSchemaCard().getCellList().get(4).getDie().getColour());
        assertEquals(1, player.getSchemaCard().getCellList().get(4).getDie().getValue());
        assertEquals(2, gameBoard.getBoardDice().getDieList().size());

    }

    @Test
    public void useToolWithID10Test() {
        playerMove.setDiceBoardIndex(0);

        List<List<OperationString>> operationList = new ArrayList<>();
        operationList.add(new ArrayList<>());

        int value = gameBoard.getBoardDice().getDieList().get(0).getValue();

        operationList.get(0).add(new OperationString("pick", "diceboard"));
        operationList.get(0).add(new OperationString("opposite", ""));
        operationList.get(0).add(new OperationString("leave", "diceboard"));

        ToolCard toolCard;
        do {
            toolCard = (ToolCard) gameLoader.getToolDeck().extractCard();
        }while (!toolCard.getName().equals("Tampone Diamantato"));
        ToolController toolController = new ToolController(toolCard);

        toolController.doAction(gameBoard, playerMove, gameBoard.getPlayerList(),turn);
        assertEquals(value, 7 - gameBoard.getBoardDice().getDieList().get(gameBoard.getBoardDice().getDieList().size() - 1).getValue());
    }

    @Test
    public void useToolWithID11Test() {

        playerMove.setDiceBoardIndex(0);

        List<List<OperationString>> operationList = new ArrayList<>();

        ToolCard toolCard;
        do {
            toolCard = (ToolCard) gameLoader.getToolDeck().extractCard();
        }while (!toolCard.getName().equals("Diluente per Pasta Salda"));
        ToolController toolController = new ToolController(toolCard);

        toolController.doAction(gameBoard, playerMove, gameBoard.getPlayerList(),turn);

        playerMove = new PlayerMove();
        playerMove.setPlayer(player);
        playerMove.setValue(3);

        toolController.doAction(gameBoard, playerMove, gameBoard.getPlayerList(),turn);

        assertEquals(3, gameBoard.getBoardDice().getDieList().size());
        assertEquals(3, gameBoard.getBoardDice().getDieList().get(2).getValue());
    }

    @Test
    public void useToolWithID12Test() {

        ToolCard toolCard;
        do {
            toolCard = (ToolCard) gameLoader.getToolDeck().extractCard();
        }while (!toolCard.getName().equals("Taglierina Manuale"));
        ToolController toolController = new ToolController(toolCard);

        Die die = new Die(ColourEnum.YELLOW);
        die.setValue(2);
        player.getSchemaCard().setDiceIntoCell(new Position(0,2), die);

        playerMove.insertDiceSchemaWhereToTake(new Position(0,0));
        playerMove.insertDiceSchemaWhereToLeave(new Position(2,1));
        int error = toolController.doAction(gameBoard, playerMove, gameBoard.getPlayerList(),turn);
        assertTrue(error == 0);

        playerMove = new PlayerMove();
        playerMove.insertDiceSchemaWhereToTake(new Position(0,2));
        playerMove.insertDiceSchemaWhereToLeave(new Position(3,0));
        playerMove.setPlayer(player);
        error = toolController.doAction(gameBoard, playerMove, gameBoard.getPlayerList(),turn);

        assertEquals(0, error);
        assertTrue(player.getSchemaCard().getCellList().get(0).isEmpty());
        assertTrue(player.getSchemaCard().getCellList().get(2).isEmpty());

        assertEquals(ColourEnum.YELLOW, player.getSchemaCard().getCellList().get(11).getDie().getColour());
        assertEquals(5, player.getSchemaCard().getCellList().get(11).getDie().getValue());

        assertEquals(ColourEnum.YELLOW, player.getSchemaCard().getCellList().get(15).getDie().getColour());
        assertEquals(2, player.getSchemaCard().getCellList().get(15).getDie().getValue());

    }


    @Test
    public void useToolWithID12WithOneDieTest() {
        ToolCard toolCard;
        do {
            toolCard = (ToolCard) gameLoader.getToolDeck().extractCard();
        }while (!toolCard.getName().equals("Taglierina Manuale"));
        ToolController toolController = new ToolController(toolCard);

        Die die = new Die(ColourEnum.GREEN);
        die.setValue(2);
        player.getSchemaCard().setDiceIntoCell(new Position(0,2), die);

        playerMove.insertDiceSchemaWhereToTake(new Position(0,0));
        playerMove.insertDiceSchemaWhereToLeave(new Position(1,2));
        int error = toolController.doAction(gameBoard, playerMove, gameBoard.getPlayerList(),turn);

        assertEquals(0,error);
        assertTrue(player.getSchemaCard().getCellList().get(0).isEmpty());

        playerMove = new PlayerMove();
        playerMove.setPlayer(player);
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.PASS);

        error = toolController.doAction(gameBoard, playerMove, gameBoard.getPlayerList(),turn);

        assertEquals(0,error);
        assertTrue(player.getSchemaCard().getCellList().get(0).isEmpty());

        assertEquals(ColourEnum.YELLOW, player.getSchemaCard().getCellList().get(7).getDie().getColour());
        assertEquals(5, player.getSchemaCard().getCellList().get(7).getDie().getValue());
    }


    @Test
    public void useTool303() {
        GameBoardTest gameBoardTest = new GameBoardTest();

        gameBoard = gameBoardTest.newGameBoard();
        player = gameBoard.getPlayerList().get(0);

        playerMove = new PlayerMove();
        playerMove.setPlayer(player);

        Die die1 = new Die(ColourEnum.YELLOW);
        die1.setValue(5);
        Die die2 = new Die(ColourEnum.BLUE);
        die2.setValue(4);
        Die die3 = new Die(ColourEnum.RED);
        die3.setValue(3);
        Die die4 = new Die(ColourEnum.PURPLE);
        die4.setValue(1);
        Die die5 = new Die(ColourEnum.BLUE);
        die5.setValue(2);
        Die die6 = new Die(ColourEnum.RED);
        die6.setValue(1);
        Die die7 = new Die(ColourEnum.YELLOW);
        die7.setValue(6);
        Die die8 = new Die(ColourEnum.GREEN);
        die8.setValue(4);
        Die die9 = new Die(ColourEnum.YELLOW);
        die9.setValue(1);

        gameBoard.getBoardDice().insertDie(die4);
        gameBoard.getBoardDice().insertDie(die5);
        gameBoard.getBoardDice().insertDie(die9);

        List<Die> dieList = new ArrayList<>();
        dieList.add(die6);
        gameBoard.getTrackBoardDice().insertDice(dieList);

        dieList = new ArrayList<>();
        dieList.add(die7);
        dieList.add(die8);
        gameBoard.getTrackBoardDice().insertDice(dieList);

        gameLoader = new GameLoader();

        ToolCard toolCard;
        do {
            toolCard = (ToolCard) gameLoader.getToolDeck().extractCard();
        }while (!toolCard.getName().equalsIgnoreCase("Pennello per Eglomise"));
        ToolController toolController = new ToolController(toolCard);

        player.getSchemaCard().setDiceIntoCell(new Position(0,0), die1);

        PlayerMove playerMove = new PlayerMove();
        playerMove.setPlayer(player);
        playerMove.insertDiceSchemaWhereToTake(new Position(0,0));
        playerMove.insertDiceSchemaWhereToLeave(new Position(0,4));

        int error = toolController.doAction(gameBoard, playerMove, gameBoard.getPlayerList(),turn);
        assertEquals(2103,error);
    }

}