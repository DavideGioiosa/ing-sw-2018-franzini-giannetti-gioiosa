package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.cards.publiccard.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests PublicObjCards class
 *
 * @author Cristian Giannetti
 */

public class TestPublicObjCard {

    private SchemaCard schemaCard;

    private final String NAME = "Name";
    private final String DESCRIPTION = "Description";

    private final ColourEnum colour = ColourEnum.BLUE;
    private final int BONUS = 1;
    private final int ID = 1;

    /**
     * Creates a new Card with correct parameters
     */
    @Test
    public void publicCreationGoodTest() {
        PublicObjCard publicObjCard = new PublicObjCard(ID, NAME, DESCRIPTION, BONUS, "DiffNumbersRow");

        assertEquals(ID,publicObjCard.getId());
        assertEquals(NAME, publicObjCard.getName());
        assertEquals(DESCRIPTION, publicObjCard.getDescription());
        assertEquals(BONUS, publicObjCard.getBonus());
    }

    /**
     * Creates a Public Objective Card with null ScoreStrategy
     */
    @Test
    public void publicCreationBadTest_NullScoreStrategy(){
        try{
            PublicObjCard publicObjCard = new PublicObjCard(ID, NAME, DESCRIPTION, BONUS, null);
            fail();
        }catch(NullPointerException e){ }
    }

    /**
     * Creates a Public Objective Card with unvalid ScoreStrategy
     */
    @Test
    public void publicCreationBadTest_UnvalidScoreStrategy(){
        try{
            PublicObjCard publicObjCard = new PublicObjCard(ID, NAME, DESCRIPTION, BONUS, "Unvalid ScoreStrategy");
            fail();
        }catch(NullPointerException e){ }
    }

    /**
     * Creates a Public Objective Card with unvalid ID
     */
    @Test
    public void publicCardCreationBadTest_UnvalidID(){
        try{
            PublicObjCard publicObjCard = new PublicObjCard(0, NAME, DESCRIPTION, BONUS, "DiffNumbersRow");
            fail();
        }catch(IllegalArgumentException e){ }
    }

    /**
     * Creates a Public Objective Card with unvalid name
     */
    @Test
    public void publicCardCreationBadTest_UnvalidName() {
        try{
            PublicObjCard publicObjCard = new PublicObjCard(ID, null, DESCRIPTION, BONUS, "DiffNumbersRow");
            fail();
        }catch(NullPointerException e){ }
    }

    /**
     * Calculates score of a SchemaCard
     */
    @Test
    public void publicCardCalculationTest(){
        PublicObjCard publicObjCard = new PublicObjCard(ID, NAME, DESCRIPTION, BONUS, "ColouredDiagonal");
        List<Cell> cellList = new ArrayList<>();
        for (int i = 0; i < 20; i++) cellList.add(new Cell(0,null));
        schemaCard = new SchemaCard(10,NAME, DESCRIPTION, 5,cellList);
        Die die = new Die(ColourEnum.BLUE);
        die.firstRoll();
        schemaCard.setDiceIntoCell(new Position(1),die);
        schemaCard.setDiceIntoCell(new Position(5),die);
        schemaCard.setDiceIntoCell(new Position(11),die);
        schemaCard.setDiceIntoCell(new Position(12),die);
        schemaCard.setDiceIntoCell(new Position(7),die);

        assertEquals(4, publicObjCard.scoreCalculation(schemaCard));

    }

    /**
     * Calculates score of a null SchemaCard
     */
    @Test
    public void publicCardCalculationTest_NullSchema(){
        PublicObjCard publicObjCard = new PublicObjCard(ID, NAME, DESCRIPTION, BONUS, "ColouredDiagonal");
        try {
            assertEquals(4, publicObjCard.scoreCalculation(null));
            fail();
        }catch(NullPointerException e){ }
    }

}

