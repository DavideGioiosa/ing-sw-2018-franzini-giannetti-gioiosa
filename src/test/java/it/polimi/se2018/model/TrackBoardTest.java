package it.polimi.se2018.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TrackBoardTest {

    private TrackBoard trackBoard;
    private List<Dice> surplus;
    private Dice d;
    private Dice d1;
    private Dice d2;

    /**
     * Initialization for TrackBoardTest
     */
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

    /**
     * Tests the method insertDice by controlling if the List insert is in the last position
     */
    @Test
    public void insertDice() {
        try{
            trackBoard.insertDice(surplus);
            assertEquals(trackBoard.getDiceList().get(trackBoard.getDiceList().size() -1), surplus);
        }catch(IllegalArgumentException e){
            fail();
        }

    }

    /**
     * Tests the method exchangeDice by controlling if the element removed is returned and replaced by the
     * chosen dice
     */
    @Test
    public void exchangeDice() {
       try{
           surplus.add(d);
           trackBoard.getDiceList().remove(surplus);
           trackBoard.insertDice(surplus);
           if(trackBoard.getDiceList().get(trackBoard.getDiceList().size()-1).equals(surplus)){
               Dice die = trackBoard.exchangeDice(trackBoard.getDiceList().size()-1,0,d1);
               if(die.equals(d2)){
                   assertEquals(trackBoard.getDiceList().get(trackBoard.getDiceList().size()-1).get(0), d);
               }

           }
       }catch(IllegalArgumentException e){
           fail();
       }

    }
}