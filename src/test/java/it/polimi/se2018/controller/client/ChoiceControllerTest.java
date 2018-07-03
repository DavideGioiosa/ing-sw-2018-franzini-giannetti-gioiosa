package it.polimi.se2018.controller.client;

import it.polimi.se2018.controller.GameLoader;
import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.PlayerChoice;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.model.player.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ChoiceControllerTest {

    @Test
    public void checkChoiceColourWithValidParametersTest() {
        PlayerChoice playerChoice = new PlayerChoice(new User(TypeOfConnection.RMI));
        List<ColourEnum> colourEnumList = new ArrayList<>();
        colourEnumList.add(ColourEnum.RED);
        colourEnumList.add(ColourEnum.GREEN);
        playerChoice.setColourEnumList(colourEnumList);
        playerChoice.setChosenColour(ColourEnum.RED);

        ChoiceController choiceController = new ChoiceController();

        assertEquals(0, choiceController.checkChoice(playerChoice));

    }

    @Test
    public void checkChoiceColourWithInvalidParametersTest() {
        PlayerChoice playerChoice = new PlayerChoice(new User(TypeOfConnection.RMI));
        List<ColourEnum> colourEnumList = new ArrayList<>();
        colourEnumList.add(ColourEnum.RED);
        colourEnumList.add(ColourEnum.GREEN);
        playerChoice.setColourEnumList(colourEnumList);
        playerChoice.setChosenColour(ColourEnum.BLUE);

        ChoiceController choiceController = new ChoiceController();

        assertEquals(2504, choiceController.checkChoice(playerChoice));
    }

    @Test
    public void checkChoiceSchemaWithInvalidParameters() {
        PlayerChoice playerChoice = new PlayerChoice(new User(TypeOfConnection.RMI));
        List<ColourEnum> colourEnumList = new ArrayList<>();
        colourEnumList.add(ColourEnum.RED);
        playerChoice.setColourEnumList(colourEnumList);
        playerChoice.setChosenColour(ColourEnum.RED);

        List<SchemaCard> schemaCardList = new ArrayList<>();
        GameLoader gameLoader = new GameLoader();
        for(int i = 0; i< 12; i++){
            SchemaCard schema = (SchemaCard) gameLoader.getSchemaDeck().extractCard();
            if(schema.getId() == 400 || schema.getId() == 404 || schema.getId() == 406 || schema.getId() == 408) schemaCardList.add(schema);
        }

        playerChoice.setIdChosenSchema(404);
        ChoiceController choiceController = new ChoiceController();

        assertEquals(0, choiceController.checkChoice(playerChoice));
    }

    @Test
    public void checkChoiceSchemaWithValidParameters() {
        PlayerChoice playerChoice = new PlayerChoice(new User(TypeOfConnection.RMI));
        List<ColourEnum> colourEnumList = new ArrayList<>();
        colourEnumList.add(ColourEnum.RED);
        playerChoice.setColourEnumList(colourEnumList);
        playerChoice.setChosenColour(ColourEnum.RED);

        List<SchemaCard> schemaCardList = new ArrayList<>();
        GameLoader gameLoader = new GameLoader();
        for(int i = 0; i< 12; i++){
            SchemaCard schema = (SchemaCard) gameLoader.getSchemaDeck().extractCard();
            if(schema.getId() == 400 || schema.getId() == 404 || schema.getId() == 406 || schema.getId() == 408) schemaCardList.add(schema);
        }

        playerChoice.setSchemaCardList(schemaCardList);
        playerChoice.setIdChosenSchema(410);
        ChoiceController choiceController = new ChoiceController();

        assertEquals(2504, choiceController.checkChoice(playerChoice));
    }

    @Test
    public void setTooLongNicknameTest(){
        ChoiceController choiceController = new ChoiceController();
        User user = new User(TypeOfConnection.RMI);
        user.setNickname("0123456789012345678901234567890123456789");
        choiceController.checkNickname(user);
        assertEquals(2502, choiceController.checkNickname(user));
    }

    @Test
    public void setNullNicknameTest(){
        ChoiceController choiceController = new ChoiceController();
        User user = new User(TypeOfConnection.RMI);
        choiceController.checkNickname(user);
        assertEquals(2502, choiceController.checkNickname(user));
    }

    @Test
    public void setValidNicknameTest(){
        ChoiceController choiceController = new ChoiceController();
        User user = new User(TypeOfConnection.RMI);
        user.setNickname("nickname");
        choiceController.checkNickname(user);
        assertEquals(0, choiceController.checkNickname(user));
    }

}