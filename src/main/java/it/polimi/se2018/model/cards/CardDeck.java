package it.polimi.se2018.model.cards;

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
     * Constructor sets an empty Deck
     */
    public CardDeck(){
        cardList = new ArrayList<>();
    }

    /**
     * Insert a Card in the Deck
     * @param card Card to be inserted in the Deck
     */
    public void insertCard(Card card){
        if (card == null) throw new IllegalArgumentException("ERROR: Cannot insert a null Card");
        cardList.add(card);
    }

    /**
     * Exrtact the first Card in the Deck
     * @return Card in the first position
     */
    public Card extractCard(){

        if (cardList.isEmpty()) throw new RuntimeException("ERROR: Empty Deck. Cannot extract a card");

        Card card = cardList.remove(0);
        return card;
    }

    /**
     * Shuffles the entire deck of Cards mixing their positions in the ArrayList
     */
    public void shuffleCards(){
        if (cardList.isEmpty()) throw new RuntimeException("ERROR: Empty Deck. It cannot be shuffled");

        int index = 0;
        List<Card> mixedCards = new ArrayList<>();
        Random randomGenerator = new Random();

        while(!cardList.isEmpty()){
            index = (int)randomGenerator.nextInt(cardList.size());
            mixedCards.add(cardList.remove(index));
        }

        cardList = mixedCards;
    }

}
