package it.polimi.se2018.controller;

import it.polimi.se2018.model.ColourEnum;
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

    /**
     * Builder method for GameStarter class
     */
    public GameStarter(){
        gameLoader = new GameLoader();
    }

    /**
     * Method prepares the board by extracting cards
     * @param userList
     * @return
     */
    public Gameboard startGame(List<User> userList){
        if(gameLoader.getPublicObjDeck() == null){
            throw new NullPointerException("ERROR: PublicObj deck not existing ");
        }
        if(gameLoader.getToolDeck() == null){
            throw new NullPointerException("ERROR: Tool deck not existing");
        }
        if(gameLoader.getPrivateObjDeck() == null){
            throw new NullPointerException("ERROR: PrivateObj deck not existing");
        }
            placeSeat(userList);
            for(int i=0; i<3; i++){
                gameboard.getCardOnBoard().getPublicObjCardList().add(extractCard(gameLoader.getPublicObjDeck()));
                gameboard.getCardOnBoard().getToolCardList().add(extractCard(gameLoader.getToolDeck()));
            }
            distributePrivateObjCard(gameLoader.getPrivateObjDeck());

            return gameboard;
    }

    /**
     * Creates a Player object for each use on userList (not completed, missing a PlayerChoice)
     * @param userList list of all users connected for the match
     */
    private void placeSeat(List<User> userList){
        if(userList == null){
            throw new NullPointerException("ERROR: User list does not exists");
        }
        int flag = 0;
        List<Integer> indici = new ArrayList<>();
        playerList = new ArrayList<>();
        Random rand = new Random();
        int  n = rand.nextInt(userList.size()) + 1;
        for(int i=0; i<indici.size(); i++){
            if(n== indici.get(i)){
                flag=1;
            }
        }
        if(flag==0){
            indici.add(n);
        }
        for(int i=0; i<indici.size(); i++){
            Player player = new Player(userList.get(indici.get(i)).getNickname(),connection,colour, schemacard, tokens );
            //necesario implementare comunicazioni
            userList.get(indici.get(i)).setPlayer(player);
            playerList.add(player);
        }
    }

    /**
     * Extracts a card from the deck
     * @param cardDeck Deck chosen
     * @return a card from the deck chosen
     */
    private Card extractCard(CardDeck cardDeck){
        if(cardDeck == null) {
            throw new NullPointerException("ERROR: Card deck not existing");
        }
        cardDeck.shuffleCards();
        Card card = cardDeck.extractCard();
        return card;
    }


    /**
     * Method distributes PrivateObj cards to the players
     * @param privateObjCardDeck deck of PrivateObj cards
     */
    private void distributePrivateObjCard(CardDeck privateObjCardDeck){
        if(privateObjCardDeck == null){
            throw new NullPointerException("ERROR: PrivateObjCard deck not existing");
        }
        for(int i=0; i<playerList.size(); i++){
            PrivatePlayer privatePlayer = new PrivatePlayer(playerList.get(i),(PrivateObjCard)extractCard(privateObjCardDeck));
            gameboard.getPrivatePlayerList().add(privatePlayer);
        }
    }

    /**
     * @return the list of players in the match
     */
    public List<Player> getPlayerList(){
        return this.playerList;
    }

}
