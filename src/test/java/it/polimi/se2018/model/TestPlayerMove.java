package it.polimi.se2018.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests PlayerMove class
 *
 * @author Cristian Giannetti
 */
public class TestPlayerMove {

    PlayerMove playerMove;

    @Before
    public void newTest(){
        playerMove = new PlayerMove();
    }

    /**
     * Tests the creation of a new PlayerMove
     */
    @Test
    public void newPlayerMoveGoodTest() {
        assertNull(playerMove.getPlayer());
        assertNull(playerMove.getTypeOfChoice());
        assertEquals(0,playerMove.getIdToolCard());
        assertEquals(-1,playerMove.getDiceBoardIndex());
        assertEquals(0,playerMove.getDiceSchemaWhereToTake().size());
        assertEquals(0,playerMove.getDiceSchemaWhereToLeave().size());
        assertNull(playerMove.getTrackBoardIndex());
        assertEquals(0,playerMove.getValue());

    }

    /**
     * Tests Value setter with good parameter
     */
    @Test
    public void setValueGoodTest(){
        playerMove.setValue(2);
        assertEquals(2,playerMove.getValue());
    }

    /**
     * Tests Player setter with null parameter
     */
    @Test
    public void setPlayerBadTest(){
       try{
           playerMove.setPlayer(null);
       }catch(IllegalArgumentException e){}
    }

    /**
     * Tests TypeOfChoice setter with good parameter
     */
    @Test
    public void setTypeOfChoiceGoodTest(){
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.TOOL);
        assertEquals(TypeOfChoiceEnum.TOOL,playerMove.getTypeOfChoice());
    }

    /**
     * Tests DiceBoard setter with good parameter
     */
    @Test
    public void setDiceBoardIndexGoodTest(){
        playerMove.setDiceBoardIndex(5);
        assertEquals(5,playerMove.getDiceBoardIndex());
    }

}
