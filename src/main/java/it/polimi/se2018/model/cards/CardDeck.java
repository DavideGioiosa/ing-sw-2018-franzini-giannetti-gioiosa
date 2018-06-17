package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.CardTypeEnum;
import it.polimi.se2018.model.cards.publiccard.PublicObjCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * CardDeck class contains a list representing a deck of the same type of cards.
 *
 * @author Cristian Giannetti
 */

public class CardDeck {

    /**
     * List of Card inserted in the Deck
     */
    private List<Card> cardList;
    /**
     * Type of card contained in the Deck
     */
    private CardTypeEnum cardType;
    /**
     * Boolean indicating if the deck has been shuffled
     */
    private boolean shuffled;

    /**
     * Constructor sets an empty Deck
     * @param cardType Type of deck
     */
    public CardDeck(CardTypeEnum cardType) {
        if (cardType == null) throw new NullPointerException("ERROR: Cannot create a null type deck");
        this.cardType = cardType;
        cardList = new ArrayList<>();
        shuffled = false;
    }

    /**
     * Inserts a Card in the Deck
     * @param card Card to be inserted in the Deck
     */
    public void insertCard(Card card){
        if (card == null) throw new NullPointerException("ERROR: Cannot insert a null Card");

        //TODO: exceptions in card and deck with different types
        switch (cardType){

            case PUBLICOBJCARD:
                PublicObjCard publicObjCard = (PublicObjCard) card;
                cardList.add(publicObjCard);
                break;

            case PRIVATEOBJCARD:
                PrivateObjCard privateObjCard = (PrivateObjCard) card;
                cardList.add(privateObjCard);
                break;

            case SCHEMACARD:
                SchemaCard schemaCard = (SchemaCard) card;
                cardList.add(schemaCard);
                break;

            case TOOLCARD:
                ToolCard toolCard = (ToolCard) card;
                cardList.add(toolCard);
                break;

            default: throw new RuntimeException("ERROR: No type of deck");
        }
        shuffled = false;
    }

    /**
     * Shuffles the Deck if never shuffled and extracts the first Card
     * @return Card in the first position
     */
    public Card extractCard(){

        if (cardList.isEmpty()) throw new RuntimeException("ERROR: Empty Deck. Cannot extract a card");
        if(!shuffled) shuffleCards();
        return cardList.remove(0);
    }

    /**
     * Shuffles the entire deck of Cards mixing their positions in the ArrayList
     */
    private void shuffleCards(){
        if (cardList.isEmpty()) throw new RuntimeException("ERROR: Empty Deck. It cannot be shuffled");

        int index = 0;
        List<Card> mixedCards = new ArrayList<>();
        Random randomGenerator = new Random();

        while(!cardList.isEmpty()){
            index = randomGenerator.nextInt(cardList.size());
            mixedCards.add(cardList.remove(index));
        }

        cardList = mixedCards;
        shuffled = true;
    }

}
