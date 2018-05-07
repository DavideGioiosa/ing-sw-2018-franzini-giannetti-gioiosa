package it.polimi.se2018.model.cards;

import java.util.ArrayList;
import java.util.Random;

/**
 * CardDeck class contains a list representing a deck of the same type of cards.
 * It's created in the GameLoader that insert one card at a time.
 * GameStarter shuffle the deck before distributing the cards.
 */

public class CardDeck {

    private ArrayList<Card> cardList;

    public void insertCard(Card card){
        cardList.add(card);
    }

    public Card extractCard(){

        if (cardList.size() == 0) return null;

        Card card = cardList.remove(0);
        return card;
    }

    public void shuffleCards(){
        int index = 0;
        ArrayList<Card> mixedCards = null;
        Random randomGenerator = new Random();

        while(cardList.size() != 0){
            index = (int)randomGenerator.nextInt(cardList.size());
            mixedCards.add(cardList.remove(index));
        }

        cardList = mixedCards;
    }



}
