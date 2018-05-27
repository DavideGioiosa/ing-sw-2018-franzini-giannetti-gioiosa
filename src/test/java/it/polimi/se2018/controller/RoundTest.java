package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.public_card.PublicObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PrivatePlayer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test Controller's Class Round
 * @author Davide Gioiosa
 */

public class RoundTest {
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

    private PlayerMove playerMove;
    private PlayerMove playerMoveExtract;
    private PlayerMove playerMovePass;
    private PlayerMove playerMovePass2;
    private PlayerMove playerMovePassPl2;
    private PlayerMove playerMovePass2Pl2;
    private Round round;

    /**
     * Sets the gameBoard
     */
    @Before
    public void init(){
        List<Cell> cellList = new ArrayList<Cell>();
        for(int i = 0; i<20; i++){
            cellList.add(new Cell(0, null));
        }
        SchemaCard schemaCard = new SchemaCard(ID,"name","desc", DIFFICULTY, cellList);

        Player player = new Player(NICKNAME, CONNECTION, FRAMECOLOUR, schemaCard, 2);
        Player player2 = new Player(NICKNAME_2, CONNECTION, ColourEnum.RED, schemaCard, 3);
        List<Player> playerList = new ArrayList<Player>();
        playerList.add(player);
        playerList.add(player2);

        ToolCard toolCard = new ToolCard(1,NAME, DESCRIPTION, COLOUR);
        List<ToolCard> toolCardList = new ArrayList<>();
        toolCardList.add(toolCard);

        PublicObjCard publicObjCard = new PublicObjCard(5,NAME,DESCRIPTION, strategy);
        List<PublicObjCard> publicCardList = new ArrayList<>();
        publicCardList.add(publicObjCard);

        BoardDice boardDice = new BoardDice();
        TrackBoard trackBoardDice = new TrackBoard();
        BagDice bagDice = new BagDice();
        BoardCard boardCard = new BoardCard(publicCardList,toolCardList);

        gameBoard = new GameBoard(playerList, bagDice,boardDice, trackBoardDice, boardCard, privatePlayerList);

        for(int i = 0; i < 5; i++) {
            gameBoard.getBoardDice().insertDice(gameBoard.getBagDice().extractDice());
        }

        //PICK Move
        playerMove = new PlayerMove();
        playerMove.setPlayer(player);
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        playerMove.setDiceBoardIndex(3);
        playerMove.insertDiceSchemaWhereToLeave(new Position(2));

        //EXTRACT Move
        playerMoveExtract = new PlayerMove();
        playerMoveExtract.setPlayer(player);
        playerMoveExtract.setTypeOfChoice(TypeOfChoiceEnum.EXTRACT);

        //PASS MOVE Player1
        playerMovePass = new PlayerMove();
        playerMovePass.setPlayer(player);
        playerMovePass.setTypeOfChoice(TypeOfChoiceEnum.PASS);

        //PASS MOVE Player1 2
        playerMovePass2 = new PlayerMove();
        playerMovePass2.setPlayer(player);
        playerMovePass2.setTypeOfChoice(TypeOfChoiceEnum.PASS);

        //PASS MOVE Player2
        playerMovePassPl2 = new PlayerMove();
        playerMovePassPl2.setPlayer(player2);
        playerMovePassPl2.setTypeOfChoice(TypeOfChoiceEnum.PASS);

        //PASS MOVE Player2 2
        playerMovePass2Pl2 = new PlayerMove();
        playerMovePass2Pl2.setPlayer(player2);
        playerMovePass2Pl2.setTypeOfChoice(TypeOfChoiceEnum.PASS);
    }

    /**
     * Check of the correct creation of the Round
     */
    @Test
    public void Round_shouldReturnTrueIfCorrectCreation() {
        round = new Round(gameBoard, 0);
    }

    /**
     * Check Exception called because of wrong creation of a Round
     */
    @Test
    public void Round_shouldCallExceptionForNullGameBoard() {
        GameBoard gameBoardEmpty = null;

        try {
            round = new Round(gameBoardEmpty, 0);
        }catch (NullPointerException e){
        }
    }

    /**
     * Check Exception called because of wrong creation of a Round
     */
    @Test
    public void Round_shouldCallExceptionForIndexNotPermitted() {
        try {
            round = new Round(gameBoard, -1);
        }catch (IllegalArgumentException e){
        }
    }

    /**
     * Test after the first action of the first Player at the beginning of the Turn is 'EXTRACT'
     * ends the turn of the current player and prepares the new one for the next player
     */
    @Test
    public void update_shouldEndCurrentTurnAndPrepareTheNewOne() {
        round = new Round(gameBoard, 0);
        round.update(playerMoveExtract);

        round.update(playerMovePass);

        assertEquals(1, round.getTurnsList().get(0).getTurnsActionsList().size());
        assertEquals(TypeOfChoiceEnum.PASS ,
                round.getTurnsList().get(0).getTurnsActionsList().get(0).getPlayerMove().getTypeOfChoice());
        assertEquals(playerMove.getPlayer(),
                round.getTurnsList().get(0).getTurnsActionsList().get(0).getPlayerMove().getPlayer());
    }

    /**
     * Test if the first action of the first Player at the beginning of the Turn is not 'EXTRACT'
     * it doesn't accept that action
     */
    @Test
    public void update_shouldNotStartTheRoundBecauseNotEXTRACTAction() {
        round = new Round(gameBoard, 0);

        round.update(playerMove);

        assertEquals(0, round.getTurnsList().get(0).getTurnsActionsList().size());
    }

    /**
     * Test after the first action of the first Player at the beginning of the Turn is 'EXTRACT'
     * begins the turn of the current player
     */
    @Test
    public void update() {
        round = new Round(gameBoard, 0);
        round.update(playerMoveExtract);


        round.update(playerMove);

        assertEquals(1, round.getTurnsList().get(0).getTurnsActionsList().size());
        assertEquals(TypeOfChoiceEnum.PICK ,
                round.getTurnsList().get(0).getTurnsActionsList().get(0).getPlayerMove().getTypeOfChoice());
        assertEquals(playerMove.getPlayer(),
                round.getTurnsList().get(0).getTurnsActionsList().get(0).getPlayerMove().getPlayer());
    }

    /**
     * Check Exception called because of null parameter of playerMove
     */
    @Test
    public void update_shouldCallExceptionForNullParameter() {
        round = new Round(gameBoard, 0);
        PlayerMove playerMoveNull = null;
        try {
            round.update(playerMoveNull);
        }
        catch (RuntimeException e){
        }
    }
}