package it.polimi.se2018.model;

import it.polimi.se2018.controller.GameLoader;
import it.polimi.se2018.model.cards.PrivateObjCard;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.publiccard.PublicObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PrivatePlayer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests all methods of GameBoard class
 * @author Cristian Giannetti
 */
public class GameBoardTest {

    private final ColourEnum FRAMECOLOUR = ColourEnum.BLUE;

    private List<Player> playerList;
    private BoardDice boardDice;
    private TrackBoard trackBoardDice;
    private BagDice bagDice;
    private BoardCard boardCard;
    private List<PrivatePlayer> privatePlayerList;

    /**
     * Sets a valid Schema Card
     */
    @Before
    public void setUp(){
        final String NICKNAME = "Nickname";
        final boolean CONNECTION = true;
        SchemaCard schemaCard;
        Player player;
        PrivateObjCard privateObjCard;

        ToolCard toolCard;
        List<ToolCard> toolCardList;
        PublicObjCard publicObjCard;
        List<PublicObjCard> publicCardList;
        GameLoader gameLoader = new GameLoader();
        do {
            schemaCard = (SchemaCard) gameLoader.getSchemaDeck().extractCard();
        }while (schemaCard.getId() != 400);

        player = new Player(NICKNAME, CONNECTION, FRAMECOLOUR, schemaCard, 2);
        playerList = new ArrayList<>();
        playerList.add(player);

        toolCardList = new ArrayList<>();
        for (int i = 0; i<12; i++) {
            toolCard = (ToolCard) gameLoader.getToolDeck().extractCard();
            toolCardList.add(toolCard);
        }

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
     * Try to create a new GameBoard with null PlayerList
     */
    @Test
    public void creationDiceBadTest_NullPlayerList(){

        try{
            GameBoard gameBoard = new GameBoard(null, bagDice,boardDice, trackBoardDice, boardCard, privatePlayerList);
            fail();
        }catch(NullPointerException e){}
    }

    /**
     * Try to create a GameBoard with null BagDice
     */
    @Test
    public void creationDiceBadTest_NullBagDice() {
        try{
            GameBoard gameBoard = new GameBoard(playerList, null,boardDice, trackBoardDice, boardCard, privatePlayerList);
            fail();
        }catch(NullPointerException e){}
    }

    /**
     * Try to create a GameBoard with null BoardDice
     */
    @Test
    public void creationDiceBadTest_NullBoardDice() {
        try{
            GameBoard gameBoard = new GameBoard(playerList, bagDice,null, trackBoardDice, boardCard, privatePlayerList);
            fail();
        }catch(NullPointerException e){}
    }

    /**
     * Try to create a GameBoard with null BoardCard
     */
    @Test
    public void creationDiceBadTest_NullBoardCard() {
        try{
            GameBoard gameBoard = new GameBoard(playerList, bagDice, boardDice, trackBoardDice, null, privatePlayerList);
            fail();
        }catch(NullPointerException e){}
    }

    /**
     * Try to create a GameBoard with null TrackBoard
     */
    @Test
    public void creationDiceBadTest_NullTrackBoard() {
        try{
            GameBoard gameBoard = new GameBoard(playerList, bagDice, boardDice, null, boardCard, privatePlayerList);
            fail();
        }catch(NullPointerException e){}
    }

    /**
     * Try to create a GameBoard with null PrivatePlayer List
     */
    @Test
    public void creationDiceBadTest_NullPrivatePlayerList() {
        try{
            GameBoard gameBoard = new GameBoard(playerList, bagDice, boardDice, trackBoardDice, boardCard, null);
            fail();
        }catch(NullPointerException e){}
    }

    @Test
    public void cloneGameBoard_shouldCloneCorrectly (){
        GameBoard gameBoard = newGameBoard();

        GameBoard gameBoardClone = gameBoard.getClone();

        boolean isEqualBoardDice = true;

        for(int i=0; i<gameBoard.getBoardDice().getDieList().size(); i++) {
            if(gameBoard.getBoardDice().getDieList().get(i) != gameBoardClone.getBoardDice().getDieList().get(i)){
                isEqualBoardDice = false;
            }
        }

        boolean isEqualTrackBoardDice = true;
        for(int i=0; i< gameBoard.getTrackBoardDice().getDiceList().size(); i++){
            if(gameBoard.getTrackBoardDice().getDiceList().get(i) != gameBoardClone.getTrackBoardDice().getDiceList().get(i)){
                isEqualTrackBoardDice = false;
            }
        }

        boolean isEqualToolCardList = true;
        for(int i=0; i<gameBoard.getCardOnBoard().getToolCardList().size(); i++){
            if(gameBoard.getCardOnBoard().getToolCardList().get(i) != gameBoard.getCardOnBoard().getToolCardList().get(i)){
                isEqualToolCardList = false;
            }
        }

        boolean isEqualPublicCardList = true;
        for(int i=0; i<gameBoard.getCardOnBoard().getPublicObjCardList().size(); i++){
            if(gameBoard.getCardOnBoard().getPublicObjCardList().get(i).getBonus() != gameBoardClone.getCardOnBoard().getPublicObjCardList().get(i).getBonus()){
                isEqualPublicCardList = false;
            }
        }

        boolean isEqualPlayerList = true;
        for(int i=0; i<gameBoard.getPlayerList().size(); i++){
            if(gameBoard.getPlayerList().get(i).getNickname() != gameBoardClone.getPlayerList().get(i).getNickname()){
                isEqualPlayerList = false;
            }
        }


        assertTrue(isEqualBoardDice);
        assertTrue(isEqualTrackBoardDice);
        assertTrue(isEqualToolCardList);
        assertTrue(isEqualPublicCardList);
        assertTrue(isEqualPlayerList);
    }


    /**
     * Creates a GameBoard with valid parameters
     * @return new GameBoard for tests
     */
    public GameBoard newGameBoard(){
        setUp();
        return new GameBoard(playerList, bagDice,boardDice, trackBoardDice, boardCard, privatePlayerList);
    }


}
