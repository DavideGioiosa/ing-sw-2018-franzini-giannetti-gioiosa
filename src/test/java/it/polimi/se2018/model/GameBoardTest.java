package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.public_card.PublicObjCard;
import it.polimi.se2018.model.cards.public_card.ScoreStrategy;
import it.polimi.se2018.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests all methods of GameBoard class
 *
 * @author Cristian Giannetti
 */
public class GameBoardTest {
    public final String NICKNAME = "Nickname";
    public final String NAME = "Name";
    public final String DESCRIPTION = "Description";
    public final ColourEnum FRAMECOLOUR = ColourEnum.BLUE;
    public final ColourEnum COLOUR = ColourEnum.BLUE;
    public final boolean CONNECTION = true;
    public SchemaCard schemaCard;
    public final int ID = 1;
    public final int DIFFICULTY = 1;
    public ScoreStrategy strategy = null;

    /**
     * Sets a valid Schema Card
     */
    @Before
    public void setUp(){
        List<Cell> cellList = new ArrayList<Cell>();
        int i;
        for(i = 0; i<20; i++){
            cellList.add(new Cell(0, null));
        }
        SchemaCard schemaCard = new SchemaCard("name","desc", DIFFICULTY, ID, cellList);
    }

    /**
     * Create a new GameBoard with valid values
     */
    @Test
    public void creationDiceGoodTest(){
        Player player = new Player(NICKNAME, CONNECTION, FRAMECOLOUR, schemaCard, 2);
        List<Player> playerList = new ArrayList<Player>();
        playerList.add(player);

        ToolCard toolCard = new ToolCard(1,NAME, DESCRIPTION, COLOUR);
        List<ToolCard> toolCardList = new ArrayList<>();
        toolCardList.add(toolCard);

        PublicObjCard publicObjCard = new PublicObjCard(5,NAME,DESCRIPTION, strategy);
        List<PublicObjCard> publicCardList = new ArrayList<>();
        publicCardList.add(publicObjCard);

        BoardDice boardDice = new BoardDice();
        TrackBoard trackBoardDice = new TrackBoard();
        BagDice bagDice = new BagDice(90);
        BoardCard boardCard = new BoardCard(publicCardList,toolCardList);


        GameBoard gameBoard = new GameBoard(playerList, bagDice,boardDice, trackBoardDice, boardCard);
        assertEquals(playerList, gameBoard.getPlayerList());
        assertEquals(bagDice, gameBoard.getBagDice());
        assertEquals(boardDice, gameBoard.getBoardDice());
        assertEquals(trackBoardDice, gameBoard.getTrackBoardDice());
        assertEquals(boardCard, gameBoard.getCardOnBoard());

    }

    /**
     * Try to create a new GameBoard with unvalid values
     */
    @Test
    public void creationDiceBadTest(){
        Player player = new Player(NICKNAME, CONNECTION, FRAMECOLOUR, schemaCard, 2);
        List<Player> playerList = new ArrayList<Player>();
        playerList.add(player);

        ToolCard toolCard = new ToolCard(1,NAME, DESCRIPTION, COLOUR);
        List<ToolCard> toolCardList = new ArrayList<>();
        toolCardList.add(toolCard);

        PublicObjCard publicObjCard = new PublicObjCard(5,NAME,DESCRIPTION, strategy);
        List<PublicObjCard> publicCardList = new ArrayList<>();
        publicCardList.add(publicObjCard);

        BoardDice boardDice = new BoardDice();
        TrackBoard trackBoardDice = new TrackBoard();
        BagDice bagDice = new BagDice(90);
        BoardCard boardCard = new BoardCard(publicCardList,toolCardList);
        try{
            GameBoard gameBoard = new GameBoard(null, bagDice,boardDice, trackBoardDice, boardCard);
            fail();
        }catch(IllegalArgumentException e){}
        try{
            GameBoard gameBoard = new GameBoard(playerList, null,boardDice, trackBoardDice, boardCard);
            fail();
        }catch(IllegalArgumentException e){}
        try{
            GameBoard gameBoard = new GameBoard(playerList, bagDice,null, trackBoardDice, boardCard);
            fail();
        }catch(IllegalArgumentException e){}
        try{
            GameBoard gameBoard = new GameBoard(playerList, bagDice,boardDice, null, boardCard);
            fail();
        }catch(IllegalArgumentException e){}
        try{
            GameBoard gameBoard = new GameBoard(playerList, bagDice,boardDice, trackBoardDice, null);
            fail();
        }catch(IllegalArgumentException e){}
    }

}
