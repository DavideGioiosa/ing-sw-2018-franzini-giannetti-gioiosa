package it.polimi.se2018.model.cards;

import java.util.ArrayList;

public class CardDeck {

    private ArrayList<Card> cardList;

    public void insertCard(Card card){
        cardList.add(card);
    }

    public Card extractCard(){
        Card card = cardList.remove(0);
        return card;
    }

}
