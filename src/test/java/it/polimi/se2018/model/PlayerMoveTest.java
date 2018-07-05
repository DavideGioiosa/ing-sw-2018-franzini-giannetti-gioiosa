package it.polimi.se2018.model;

import it.polimi.se2018.controller.GameLoader;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerMoveTest {

    Player player;

    @Before
    public void setUpPlayerMoveTest(){
        final String NICKNAME = "Nickname";
        final boolean CONNECTION = true;
        SchemaCard schemaCard;
        GameLoader gameLoader = new GameLoader();
        do {
            schemaCard = (SchemaCard) gameLoader.getSchemaDeck().extractCard();
        }while (schemaCard.getId() != 400);

        player = new Player(NICKNAME, CONNECTION, ColourEnum.BLUE, schemaCard, 2);
    }

    @Test
    public void newPlayerMove() {
    }

    @Test
    public void getIdentifier() {
    }

    @Test
    public void setPlayer() {
    }

    @Test
    public void getPlayer() {
    }

    @Test
    public void setTypeOfChoice() {
    }

    @Test
    public void getTypeOfChoice() {
    }

    @Test
    public void setIdToolCard() {
    }

    @Test
    public void getIdToolCard() {
    }

    @Test
    public void setDiceBoardIndex() {
    }

    @Test
    public void getDiceBoardIndex() {
    }

    @Test
    public void insertDiceSchemaWhereToTake() {
    }

    @Test
    public void getDiceSchemaWhereToTake() {
    }

    @Test
    public void insertDiceSchemaWhereToLeave() {
    }

    @Test
    public void getDiceSchemaWhereToLeave() {
    }

    @Test
    public void setTrackBoardIndex() {
    }

    @Test
    public void getTrackBoardIndex() {
    }

    @Test
    public void setValue() {
    }

    @Test
    public void getValue() {
    }

    @Test
    public void getClone() {
        PlayerMove playerMove = new PlayerMove();
        playerMove.setPlayer(player);
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.PICK);
        playerMove.insertDiceSchemaWhereToLeave(new Position(1,1));
        playerMove.insertDiceSchemaWhereToTake(new Position(2,1));
        playerMove.setValue(5);
        playerMove.setValue(1);
        playerMove.setDiceBoardIndex(9);
        playerMove.setIdToolCard(100);
        playerMove.setTrackBoardIndex(1, 1);

        PlayerMove playerMoveCloned = playerMove.getClone();
        assertEquals(playerMove.getPlayer().getNickname(), playerMoveCloned.getPlayer().getNickname());
        assertEquals(playerMove.getDiceSchemaWhereToLeave().get(0).getIndexArrayPosition(), playerMoveCloned.getDiceSchemaWhereToLeave().get(0).getIndexArrayPosition());
        assertEquals(playerMove.getIdToolCard(), playerMoveCloned.getIdToolCard());
        assertEquals(playerMove.getTypeOfChoice(), playerMoveCloned.getTypeOfChoice());
        assertEquals(playerMove.getTrackBoardIndex()[0], playerMoveCloned.getTrackBoardIndex()[0]);
        assertEquals(playerMove.getTrackBoardIndex()[1], playerMoveCloned.getTrackBoardIndex()[1]);
        assertEquals(playerMove.getDiceSchemaWhereToTake().get(0).getIndexArrayPosition(), playerMoveCloned.getDiceSchemaWhereToTake().get(0).getIndexArrayPosition());
        assertEquals(playerMove.getValue(), playerMoveCloned.getValue());

    }
}