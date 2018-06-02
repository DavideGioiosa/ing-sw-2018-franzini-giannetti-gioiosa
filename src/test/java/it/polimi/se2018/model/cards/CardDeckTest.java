package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.CardTypeEnum;
import org.junit.Test;

/**
 * Test CardDeck Class
 * @author Davide Gioiosa
 */

public class CardDeckTest {
    private static final String NAME = "Name";
    private static final String DESCRIPTION = "Description";
    private static final int ID = 1;
    private CardDeck cardDeck;
    private Card card;
    private CardTypeEnum cardType;

    @Test
    public void CardDeck_shouldCatchExceptionIfNullParameter() {
        try {
            cardDeck = new CardDeck(cardType);
        }
        catch (NullPointerException e){
        }
    }

    //TODO: check downclass cast in CardDeck before running this test + adding extraction test
/*    @Test
    public void insertCard_shouldReturnTrueifCorrectInsertionOfACard() {
        cardType = CardTypeEnum.PUBLICOBJCARD;
        cardDeck = new CardDeck(cardType);
        card = new Card(ID, NAME, DESCRIPTION);
        cardDeck.insertCard(card);

        Card cardExtracted = cardDeck.extractCard();

        assertEquals(card, cardExtracted);
    }
*/

    @Test
    public void insertCard_shouldCatchNullPointerException() {
        cardType = CardTypeEnum.PUBLICOBJCARD;
        cardDeck = new CardDeck(cardType);
        try {
            cardDeck.insertCard(card);
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
        }
        catch (RuntimeException e){
        }
    }
}