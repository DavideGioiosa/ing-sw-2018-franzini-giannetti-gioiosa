package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.cards.CardDeck;
import it.polimi.se2018.model.cards.PrivateObjCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.public_card.PublicObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PrivatePlayer;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Controller's Class GameStarter
 * @author Silvia Franzini
 */
public class GameStarter implements Observer<PlayerChoice> {
    private GameBoard gameBoard;
    private GameLoader gameLoader;
    private List<Player> playerList;
    //GESTIRE COMUNICAZIONI VIEW

    /**
     * Builder method for GameStarter class
     */
    public GameStarter(){
        gameLoader = new GameLoader();
        playerList = new ArrayList<>();
    }

    /**
     * Method prepares the board by extracting cards
     * @param userList list of users connected to the game
     * @return the game table of the match
     */
    public GameBoard startGame(List<User> userList){
        List<PublicObjCard> publicObjCardList= new ArrayList<>();
        List<ToolCard> toolCardList= new ArrayList<>();
        BoardCard boardCard;
        List<PrivatePlayer> privatePlayerList;
        BoardDice boardDice = new BoardDice();
        BagDice bagDice = new BagDice();
        TrackBoard trackBoard = new TrackBoard();
        if(gameLoader.getPublicObjDeck() == null){
            throw new NullPointerException("ERROR: PublicObj deck not existing ");
        }
        if(gameLoader.getToolDeck() == null){
            throw new NullPointerException("ERROR: Tool deck not existing");
        }
        if(gameLoader.getPrivateObjDeck() == null){
            throw new NullPointerException("ERROR: PrivateObj deck not existing");
        }
        if(playerList.size()!= userList.size()){
            throw new RuntimeException("ERROR: not enough players");
        }
        for(int i=0; i<3; i++){
            publicObjCardList.add(extractCard(gameLoader.getPublicObjDeck()));
            toolCardList.add(extractCard(gameLoader.getToolDeck()));
        }
        boardCard = new BoardCard(publicObjCardList, toolCardList);
        privatePlayerList = distributePrivateObjCard(gameLoader.getPrivateObjDeck());
        gameBoard = new GameBoard(playerList,bagDice,boardDice,trackBoard,boardCard,privatePlayerList);

        return gameBoard;
    }

    /**
     * Shuffles the list of players to make it random
     * @param playerList list of all users connected for the match
     */
    private void placeSeat(List<Player> playerList){
        if(playerList == null){
            throw new NullPointerException("ERROR: Player list does not exists");
        }
        Collections.shuffle(playerList);
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
        Card card = cardDeck.extractCard();
        return card;
    }


    /**
     * Method distributes PrivateObj cards to the players
     * @param privateObjCardDeck deck of PrivateObj cards
     */
    private List<PrivatePlayer> distributePrivateObjCard(CardDeck privateObjCardDeck){
        List<PrivatePlayer> privatePlayerList = new ArrayList<>();
        if(privateObjCardDeck == null){
            throw new NullPointerException("ERROR: PrivateObjCard deck not existing");
        }
        for(int i=0; i<playerList.size(); i++){
            PrivatePlayer privatePlayer = new PrivatePlayer(playerList.get(i),(PrivateObjCard)extractCard(privateObjCardDeck));
            privatePlayerList.add(privatePlayer);
        }
         return privatePlayerList;
    }

    /**
     * @return the list of players in the match
     */
    public List<Player> getPlayerList(){
        return this.playerList;
    }

    public void addPlayer(Player player){
        this.playerList.add(player);
    }

    /**
     *
     * @param playerChoice
     */
    public void update(PlayerChoice playerChoice)
    {
        User user = playerChoice.getUser();
        Player player = new Player(user.getNickname(), user.isConnected(), playerChoice.getChosenColour(), playerChoice.getChosenSchema(), playerChoice.getChosenSchema().getDifficulty());
        user.setPlayer(player);
        addPlayer(player);
    }

}
