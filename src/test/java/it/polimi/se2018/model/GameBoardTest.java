package it.polimi.se2018.model;

import it.polimi.se2018.controller.GameLoader;
import it.polimi.se2018.model.cards.PrivateObjCard;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.public_card.PublicObjCard;
import it.polimi.se2018.model.cards.public_card.ScoreStrategy;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PrivatePlayer;
import org.junit.Before;
import org.junit.BeforeClass;
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
    private final String NICKNAME = "Nickname";
    private final ColourEnum FRAMECOLOUR = ColourEnum.BLUE;
    private final boolean CONNECTION = true;
    private SchemaCard schemaCard;
    private Player player;
    private List<Player> playerList;
    private ToolCard toolCard;
    private List<ToolCard> toolCardList;
    private PublicObjCard publicObjCard;
    private List<PublicObjCard> publicCardList;
    private BoardDice boardDice;
    private TrackBoard trackBoardDice;
    private BagDice bagDice;
    private BoardCard boardCard;
    private PrivateObjCard privateObjCard;
    private List<PrivatePlayer> privatePlayerList;

    /**
     * Sets a valid Schema Card
     */
    @Before
    public void setUp(){
        GameLoader gameLoader = new GameLoader();
        schemaCard = (SchemaCard) gameLoader.getSchemaDeck().extractCard();

        player = new Player(NICKNAME, CONNECTION, FRAMECOLOUR, schemaCard, 2);
        playerList = new ArrayList<>();
        playerList.add(player);

        toolCard = (ToolCard) gameLoader.getToolDeck().extractCard();
        toolCardList = new ArrayList<>();
        toolCardList.add(toolCard);

        privateObjCard = (PrivateObjCard) gameLoader.getPrivateObjDeck().extractCard();
        PrivatePlayer privatePlayer = new PrivatePlayer(player, privateObjCard);
        privatePlayerList = new ArrayList<>();
        privatePlayerList.add(privatePlayer);

        publicObjCard = (PublicObjCard) gameLoader.getPublicObjDeck().extractCard();
        publicCardList = new ArrayList<>();
        publicCardList.add(publicObjCard);

        boardDice = new BoardDice();
        trackBoardDice = new TrackBoard();
        bagDice = new BagDice();
        boardCard = new BoardCard(publicCardList,toolCardList);
   }

    /**
     * Create a new GameBoard with valid values
     */
    @Test
    public void creationDiceGoodTest(){

        GameBoard gameBoard = new GameBoard(playerList, bagDice,boardDice, trackBoardDice, boardCard, privatePlayerList);
        assertEquals(playerList, gameBoard.getPlayerList());
        assertEquals(bagDice, gameBoard.getBagDice());
        assertEquals(boardDice, gameBoard.getBoardDice());
        assertEquals(trackBoardDice, gameBoard.getTrackBoardDice());
        assertEquals(boardCard, gameBoard.getCardOnBoard());
        assertEquals(privatePlayerList, gameBoard.getPrivatePlayerList());

    }

    /**
     * Try to create a new GameBoard with unvalid values
     */
    @Test
    public void creationDiceBadTest_NullPlayerList(){

        try{
            GameBoard gameBoard = new GameBoard(null, bagDice,boardDice, trackBoardDice, boardCard, privatePlayerList);
            fail();
        }catch(NullPointerException e){}
    }

    @Test
    public void creationDiceBadTest_NullBagDice() {
        try{
            GameBoard gameBoard = new GameBoard(playerList, null,boardDice, trackBoardDice, boardCard, privatePlayerList);
            fail();
        }catch(NullPointerException e){}
    }

    @Test
    public void creationDiceBadTest_NullBoardDice() {
        try{
            GameBoard gameBoard = new GameBoard(playerList, bagDice,null, trackBoardDice, boardCard, privatePlayerList);
            fail();
        }catch(NullPointerException e){}
    }

    @Test
    public void creationDiceBadTest_NullBoardCard() {
        try{
            GameBoard gameBoard = new GameBoard(playerList, bagDice, boardDice, trackBoardDice, null, privatePlayerList);
            fail();
        }catch(NullPointerException e){}
    }

    @Test
    public void creationDiceBadTest_NullTrackBoard() {
        try{
            GameBoard gameBoard = new GameBoard(playerList, bagDice, boardDice, null, boardCard, privatePlayerList);
            fail();
        }catch(NullPointerException e){}
    }

    @Test
    public void creationDiceBadTest_NullPrivatePlayerList() {
        try{
            GameBoard gameBoard = new GameBoard(playerList, bagDice, boardDice, trackBoardDice, boardCard, null);
            fail();
        }catch(NullPointerException e){}
    }

    public GameBoard newGameBoard(){
        setUp();
        return new GameBoard(playerList, bagDice,boardDice, trackBoardDice, boardCard, privatePlayerList);
    }

}
