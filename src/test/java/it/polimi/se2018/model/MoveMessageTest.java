package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.public_card.PublicObjCard;
import it.polimi.se2018.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MoveMessageTest {
    private MoveMessage moveMessage;
    private List<Player> playerList;
    private BoardDice boardDice;
    private BoardCard boardCard;
    private TrackBoard trackBoard;

    /**
     * Initialization of MoveMessageTest
     */
    @Before
    public void init(){
        List<PublicObjCard> publicObjCardList = new ArrayList<PublicObjCard>();
        List<ToolCard> toolCardList = new ArrayList<ToolCard>();
        playerList = new ArrayList<Player>();
        boardCard = new BoardCard(publicObjCardList,toolCardList);
        boardDice = new BoardDice();
        trackBoard = new TrackBoard();
        moveMessage = new MoveMessage(playerList,boardDice,boardCard,trackBoard);

    }

    /**
     * Tests getPlayerList method
     */
    @Test
    public void getPlayerList() {
        assertEquals(moveMessage.getPlayerList(), playerList);
    }

    /**
     * Tests getBoardDice method
     */
    @Test
    public void getBoardDice() {
        assertEquals(moveMessage.getBoardDice(),boardDice);
    }

    /**
     * Tests getBoardCard method
     */
    @Test
    public void getBoardCard() {
        assertEquals(moveMessage.getBoardCard(),boardCard);
    }

    /**
     * Tests getTrackboard method
     */
    @Test
    public void getTrackboard() {
        assertEquals(moveMessage.getTrackboard(),trackBoard);
    }
}