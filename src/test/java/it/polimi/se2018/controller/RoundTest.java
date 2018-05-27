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
 * Test Round Class
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
    private Round round;

    /**
     * Sets the gameBoard
     */
    @Before
    public void init(){
        List<Cell> cellList = new ArrayList<Cell>();
        int i;
        for(i = 0; i<20; i++){
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

    //TODO: Related to GameManager to notify
    @Test
    public void update() {
    }

    /**
     * Check Exception called because of null parameter of playerMove
     */
    @Test
    public void update_shouldCallExceptionForNullParameter() {
        round = new Round(gameBoard, 0);
        playerMove = null;
        try {
            round.update(playerMove);
        }
        catch (RuntimeException e){
        }
    }
}