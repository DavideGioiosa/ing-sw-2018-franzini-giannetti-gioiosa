package it.polimi.se2018.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TrackBoardTest {

    private TrackBoard trackBoard;
    private List<Dice> surplus;
    private Dice d, d1, d2;

    @Before
    public void init(){
            trackBoard = new TrackBoard();
            surplus = new ArrayList<Dice>();
            d= new Dice(ColourEnum.BLUE);
            d.firstRoll();
            d1= new Dice(ColourEnum.GREEN);
            d1.firstRoll();
            d2= new Dice(ColourEnum.RED);
            d2.firstRoll();
            surplus.add(d2);
    }

    @Test
    public void insertDice() {
        trackBoard.insertDice(surplus);
        assertEquals(trackBoard.getDiceList().get(trackBoard.getDiceList().size() -1), surplus);
    }

    @Test
    public void exchangeDice() {
        surplus.add(d);
        trackBoard.insertDice(surplus);
        trackBoard.exchangeDice(trackBoard.getDiceList().size(),0,d1);
        assertEquals(trackBoard.getDiceList().get(trackBoard.getDiceList().size()).get(0), d1);
    }
}