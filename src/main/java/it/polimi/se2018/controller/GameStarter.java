package it.polimi.se2018.controller;

import it.polimi.se2018.model.Gameboard;
import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.cards.CardDeck;
import it.polimi.se2018.model.cards.PrivateObjCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.public_card.PublicObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PrivatePlayer;
import it.polimi.se2018.model.player.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller's Class GameStarter
 * @author Silvia Franzini
 */
public class GameStarter {
    private Gameboard gameboard;
    private GameLoader gameLoader;
    private List<Player> playerList;

    public GameStarter(){
        gameLoader = new GameLoader();
    }

    public void startGame(List<User> userList){

    }

    private void placeSeat(List<User> userList){

        Random rand = new Random();


    }

    private Card extractCard(CardDeck cardDeck){
        cardDeck.shuffleCards();
        Card card = cardDeck.extractCard();
        return card;
    }

    private List<PublicObjCard> distributePublicObjCard(CardDeck publicObjCardDeck){
        List<PublicObjCard> publicObjCardList = new ArrayList<PublicObjCard>();
        for(int i=0; i<3; i++){
            publicObjCardList.add((PublicObjCard)extractCard(publicObjCardDeck));
        }
      return publicObjCardList;
    }

    private List<ToolCard> distributeToolCard(CardDeck toolCardDeck){
        List<ToolCard> toolCardList = new ArrayList<ToolCard>();
        for(int i=0; i<3; i++){
            toolCardList.add((ToolCard)extractCard(toolCardDeck));
        }
        return toolCardList;
    }

    private void distributePrivateObjCard(CardDeck privateObjCardDeck){

        for(int i=0; i<playerList.size(); i++){
            PrivatePlayer privatePlayer = new PrivatePlayer(playerList.get(i),(PrivateObjCard)extractCard(privateObjCardDeck));
            gameboard.getPrivatePlayerList().add(privatePlayer);
        }
    }

}
