package it.polimi.se2018.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TrackBoardTest {

    private TrackBoard trackBoard;
    private List<Die> surplus;
    private Die die;
    private Die die1;
    private Die die2;

    /**
     * Initialization for TrackBoardTest
     */
    @Before
    public void init(){
        trackBoard = new TrackBoard();
        surplus = new ArrayList<>();
        die = new Die(ColourEnum.BLUE);
        die.firstRoll();
        die1 = new Die(ColourEnum.GREEN);
        die1.firstRoll();
        die2 = new Die(ColourEnum.RED);
        die2.firstRoll();
        surplus.add(die2);
    }

    /**
     * Tests the method insertDie by controlling if the List insert is in the last position
     */
    @Test
    public void insertDice() {
        trackBoard.insertDice(surplus);
        assertEquals(surplus, trackBoard.getDiceList().get(trackBoard.getDiceList().size() -1));
    }

    /**
     * Tests the method exchangeDice by controlling if the element removed is returned and replaced by the
     * chosen dice
     */
    @Test
    public void exchangeDice() {
        trackBoard.insertDice(surplus);

        Die die = trackBoard.exchangeDice(trackBoard.getDiceList().size()-1,0,die1);

        assertEquals(trackBoard.getDiceList().get(trackBoard.getDiceList().size()-1).get(0), die1);

    }

    @Test
    public void trackBoardCopyConstructorTest(){
        List<Die> dieList = new ArrayList<>();
        Die die = new Die(ColourEnum.RED);
        die.setValue(5);
        dieList.add(die);
        die = new Die(ColourEnum.RED);
        die.setValue(1);
        dieList.add(die);
        List<Die> dieList1 = new ArrayList<>();
        die = new Die(ColourEnum.GREEN);
        die.setValue(5);
        dieList1.add(die);
        die = new Die(ColourEnum.BLUE);
        die.setValue(4);
        dieList1.add(die);

        TrackBoard trackBoard = new TrackBoard();
        trackBoard.insertDice(dieList);
        trackBoard.insertDice(dieList1);

        TrackBoard trackBoardClone = trackBoard.getClone();
        trackBoard.getDiceList().get(1).get(1).setValue(1);

        assertEquals(4,trackBoardClone.getDiceList().get(1).get(1).getValue());
        assertEquals(ColourEnum.BLUE,trackBoardClone.getDiceList().get(1).get(1).getColour());
        assertEquals(5,trackBoardClone.getDiceList().get(1).get(0).getValue());
        assertEquals(ColourEnum.GREEN,trackBoardClone.getDiceList().get(1).get(0).getColour());
        assertEquals(1,trackBoardClone.getDiceList().get(0).get(1).getValue());
        assertEquals(ColourEnum.RED,trackBoardClone.getDiceList().get(0).get(1).getColour());
        assertEquals(5,trackBoardClone.getDiceList().get(0).get(0).getValue());
        assertEquals(ColourEnum.RED,trackBoardClone.getDiceList().get(0).get(0).getColour());

    }

    @Test
    public void pick_shouldCallxception (){
        TrackBoard trackBoard = new TrackBoard();

        boolean result = trackBoard.pickDice(null, null, 0, 0);
        assertFalse(result);
    }

    @Test
    public void insert_shouldCatchNullPointerException (){
        TrackBoard trackBoard = new TrackBoard();

        try{
            trackBoard.insertDice(null);
            fail();
        }catch (NullPointerException e){}
    }

    @Test
    public void insert_shouldCatchIllegalArgumentException (){
        TrackBoard trackBoard = new TrackBoard();
        List<Die> dieList = new ArrayList<>();

        try{
            trackBoard.insertDice(dieList);
            fail();
        }catch (IllegalArgumentException e){}
    }


    @Test
    public void clone_shouldBeCorrect (){
        List<Die> dieList = new ArrayList<>();
        TrackBoard trackBoard = new TrackBoard();

        for (int i = 0; i < 10; i++) {
            Die die = new Die(ColourEnum.BLUE);
            die.firstRoll();
            dieList.add(die);
        }

        trackBoard.insertDice(dieList);

        TrackBoard trackBoardClone = trackBoard.getClone();


        boolean isEqualArray = true;
        for (int i = 0; i < dieList.size(); i++) {
            if (trackBoard.getDiceList().get(0).get(i).getValue() != trackBoardClone.getDiceList().get(0).get(i).getValue() ||
                    trackBoard.getDiceList().get(0).get(i).getColour() !=trackBoardClone.getDiceList().get(0).get(i).getColour()) {
                isEqualArray = false;
            }
        }

        assertTrue(isEqualArray);
    }

    @Test
    public void exchangeDice_shouldCatchIllegalArgumentException () {
        TrackBoard trackBoard = new TrackBoard();
        List<Die> dieList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Die die = new Die(ColourEnum.BLUE);
            die.firstRoll();
            dieList.add(die);
        }
        Die die = new Die (ColourEnum.RED);
        trackBoard.getDiceList().add(dieList);

        try{
            trackBoard.exchangeDice(-1, 0, die);
            fail();
        }catch (IllegalArgumentException e){}
        try {
            trackBoard.exchangeDice(0, -1, die);
            fail();
        }catch (IllegalArgumentException e){}
        try {
            trackBoard.exchangeDice(0, 0, null);
            fail();
        }catch (IllegalArgumentException e){}

    }

}