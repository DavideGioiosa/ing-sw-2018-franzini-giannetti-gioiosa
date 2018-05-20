package it.polimi.se2018.model;

import it.polimi.se2018.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

public class TestPlayerMove {

    PlayerMove playerMove;

    @Before
    public void newTest(){
        playerMove = new PlayerMove();
    }

    /**
     *
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

    @Test
    public void setValueGoodTest(){
        playerMove.setValue(2);
        assertEquals(2,playerMove.getValue());
    }

    @Test
    public void setPlayerBadTest(){
       try{
           playerMove.setPlayer(null);
       }catch(IllegalArgumentException e){}
    }

    @Test
    public void setTypeOfChoiceGoodTeest(){
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.TOOL);
        assertEquals(TypeOfChoiceEnum.TOOL,playerMove.getTypeOfChoice());
    }

    @Test
    public void setDiceBoardIndexGoodTest(){
        playerMove.setDiceBoardIndex(5);
        assertEquals(5,playerMove.getDiceBoardIndex());
    }

}
