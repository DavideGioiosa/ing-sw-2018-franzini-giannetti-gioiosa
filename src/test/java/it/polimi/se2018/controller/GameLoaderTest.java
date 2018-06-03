package it.polimi.se2018.controller;

import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.cards.PrivateObjCard;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.publiccard.PublicObjCard;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameLoaderTest {
    private static List<ToolCard> toolCardList;
    private static List<SchemaCard> schemaCardList;
    private static List<PublicObjCard> publicObjCardsList;
    private static List<PrivateObjCard> privateObjCardsList;
    private static List<ColourEnum> colourEnumList;

    @BeforeClass
    public static void setupClass(){
        GameLoader gameLoader;

        gameLoader = new GameLoader();

        toolCardList = new ArrayList<>();
        schemaCardList = new ArrayList<>();
        publicObjCardsList = new ArrayList<>();
        privateObjCardsList = new ArrayList<>();
        colourEnumList = new ArrayList<>();

        for (int i = 0; i<12; i++){
            toolCardList.add((ToolCard) gameLoader.getToolDeck().extractCard());
        }
        for (int i = 0; i<12; i++){
            schemaCardList.add((SchemaCard) gameLoader.getSchemaDeck().extractCard());
        }
        for (int i = 0; i<10; i++){
            publicObjCardsList.add((PublicObjCard) gameLoader.getPublicObjDeck().extractCard());
        }
        for (int i = 0; i<5; i++){
            privateObjCardsList.add((PrivateObjCard) gameLoader.getPrivateObjDeck().extractCard());
        }
        colourEnumList = gameLoader.getWindowFrame();
    }


    @Test
    public void testCellsInSchema() {
        for (SchemaCard schema: schemaCardList) {
            schema = schema.getBackSchema();
            assertEquals(20, schema.getCellList().size());
            schema = schema.getBackSchema();
            assertEquals(20, schema.getCellList().size());
        }
    }

    @Test
    public void testLoadCardFromFile_SchemaID400() {
        for (SchemaCard schema : schemaCardList) {
            if (schema.getId() == 400) {
                assertEquals("Kaleidoscopic Dream", schema.getName());
                assertEquals(4, schema.getDifficulty());

                assertEquals(ColourEnum.YELLOW, schema.getCellList().get(0).getColour());
                assertEquals(0, schema.getCellList().get(0).getValue());

                assertEquals(ColourEnum.BLUE, schema.getCellList().get(1).getColour());
                assertEquals(0, schema.getCellList().get(1).getValue());

                assertEquals(null, schema.getCellList().get(2).getColour());
                assertEquals(0, schema.getCellList().get(2).getValue());

                assertEquals(null, schema.getCellList().get(3).getColour());
                assertEquals(0, schema.getCellList().get(3).getValue());

                assertEquals(null, schema.getCellList().get(4).getColour());
                assertEquals(1, schema.getCellList().get(4).getValue());
            }
        }
    }

    @Test
    public void testLoadCardFromFile_SchemaID403() {
        for (SchemaCard schema : schemaCardList) {
            if (schema.getId() == 402) {
                schema = schema.getBackSchema();

                assertEquals("Ripples of Light", schema.getName());
                assertEquals(5, schema.getDifficulty());

                assertEquals(null, schema.getCellList().get(5).getColour());
                assertEquals(0, schema.getCellList().get(5).getValue());

                assertEquals(null, schema.getCellList().get(6).getColour());
                assertEquals(0, schema.getCellList().get(6).getValue());

                assertEquals(ColourEnum.PURPLE, schema.getCellList().get(7).getColour());
                assertEquals(0, schema.getCellList().get(7).getValue());

                assertEquals(null, schema.getCellList().get(8).getColour());
                assertEquals(4, schema.getCellList().get(8).getValue());

                assertEquals(ColourEnum.BLUE, schema.getCellList().get(9).getColour());
                assertEquals(0, schema.getCellList().get(9).getValue());
            }
        }
    }

    @Test
    public void testLoadCardFromFile_SchemaID423() {
        for (SchemaCard schema : schemaCardList) {
            if (schema.getId() == 422) {
                schema = schema.getBackSchema();
                assertEquals("Industria", schema.getName());
                assertEquals(5, schema.getDifficulty());

                assertEquals(null, schema.getCellList().get(15).getColour());
                assertEquals(0, schema.getCellList().get(15).getValue());

                assertEquals(null, schema.getCellList().get(16).getColour());
                assertEquals(0, schema.getCellList().get(16).getValue());

                assertEquals(null, schema.getCellList().get(17).getColour());
                assertEquals(0, schema.getCellList().get(17).getValue());

                assertEquals(null, schema.getCellList().get(18).getColour());
                assertEquals(3, schema.getCellList().get(18).getValue());

                assertEquals(ColourEnum.RED, schema.getCellList().get(19).getColour());
                assertEquals(0, schema.getCellList().get(19).getValue());
            }
        }
    }

    @Test
    public void testColourEnumList() {
        int[] colourCountArray = {0,0,0,0};
        for (ColourEnum colour: colourEnumList) {
            switch(colour){
                case BLUE:
                    colourCountArray[0]++;
                    break;
                case GREEN:
                    colourCountArray[1]++;
                    break;
                case RED:
                    colourCountArray[2]++;
                    break;
                case PURPLE:
                    colourCountArray[3]++;
                    break;
                default: fail();
            }
        }

        for (int counter: colourCountArray){
            if (counter != 1) fail();
        }
    }


}