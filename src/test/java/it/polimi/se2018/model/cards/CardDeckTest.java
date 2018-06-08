package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.CardTypeEnum;
import it.polimi.se2018.model.cards.publiccard.DiffColoursRow;
import it.polimi.se2018.model.cards.publiccard.PublicObjCard;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test CardDeck Class
 * @author Davide Gioiosa
 */

public class CardDeckTest {
    private static final String NAME = "Name";
    private static final String DESCRIPTION = "Description";
    private static final int ID = 1;
    private CardDeck cardDeck;
    private CardTypeEnum cardType;
    private PublicObjCard publicObjCard;

    @Test
    public void CardDeck_shouldCatchExceptionIfNullParameter() {
        try {
            cardDeck = new CardDeck(cardType);
            fail();
        }
        catch (NullPointerException e){
        }
    }

    //TODO: check downclass cast in CardDeck before running this test + adding extraction test
    @Test
    public void insertCard_shouldReturnTrueifCorrectInsertionOfACard() {
        cardType = CardTypeEnum.PUBLICOBJCARD;
        cardDeck = new CardDeck(cardType);
        publicObjCard = new PublicObjCard(ID, NAME, DESCRIPTION, 2, "DiffColoursRow");
        cardDeck.insertCard(publicObjCard);

        Card cardExtracted = cardDeck.extractCard();

        assertEquals(publicObjCard, cardExtracted);
    }


    @Test
    public void insertCard_shouldCatchNullPointerException() {
        cardType = CardTypeEnum.PUBLICOBJCARD;
        cardDeck = new CardDeck(cardType);
        try {
            cardDeck.insertCard(publicObjCard);
            fail();
        }
        catch (NullPointerException e){
        }
    }

    @Test
    public void extractCard_shouldCatchRuntimeException() {
        cardType = CardTypeEnum.PUBLICOBJCARD;
        cardDeck = new CardDeck(cardType);
        try{
            cardDeck.extractCard();
            fail();
        }
        catch (RuntimeException e){
        }
    }
}