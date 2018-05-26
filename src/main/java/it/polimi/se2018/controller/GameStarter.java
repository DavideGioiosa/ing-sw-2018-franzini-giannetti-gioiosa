package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.*;
import it.polimi.se2018.model.cards.public_card.PublicObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PrivatePlayer;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.RemoteView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controller's Class GameStarter
 * @author Silvia Franzini
 */
public class GameStarter implements Observer<PlayerChoice> {
    private GameBoard gameBoard;
    private GameLoader gameLoader;
    private List<Player> playerList;
    private List<User> userList;
    private List<PlayerChoice> playerChoiceList;
    private GameManager gameManager;
    private RemoteView remoteView;
    private List<ColourEnum> colourEnumList;
    private List<SchemaCard> schemaCardList;


    /**
     * Builder method for GameStarter class
     */
    public GameStarter(List<User> userList, GameManager gameManager){
        gameLoader = new GameLoader();
        playerList = new ArrayList<>();
        playerChoiceList = new ArrayList<>();
        this.gameManager= gameManager;
        this.userList = userList;
        colourEnumList = new ArrayList<>();
        schemaCardList = new ArrayList<>();
        remoteView = new RemoteView();
    }

    /**
     * Method prepares the board by extracting cards
     * @param playerChoiceList list player choices made to set up the game
     */
    public void startGame(List<PlayerChoice> playerChoiceList){

        BoardCard boardCard;
        List<PrivatePlayer> privatePlayerList;
        List<PublicObjCard> publicObjCardList = new ArrayList<>();
        List<ToolCard> toolCardList = new ArrayList<>();
        BoardDice boardDice = new BoardDice();
        BagDice bagDice = new BagDice();
        TrackBoard trackBoard = new TrackBoard();

        if(playerList.size()!= userList.size()){
            throw new RuntimeException("ERROR: not enough players");
        }
        for(int i=0; i<3; i++){
            try{
                publicObjCardList.add((PublicObjCard)extractCard(gameLoader.getPublicObjDeck()));
            }catch(RuntimeException e){
                remoteView.reportError(); //probabilmente necessario passare un parametro
            }
            try{
                toolCardList.add((ToolCard)extractCard(gameLoader.getToolDeck()));
            }catch(RuntimeException e){
                remoteView.reportError();
            }

        }
        boardCard = new BoardCard(publicObjCardList, toolCardList);

        for(PlayerChoice playerChoice : playerChoiceList){
            User user = playerChoice.getUser();
            Player player = new Player(user.getNickname(), user.isConnected(), playerChoice.getChosenColour(), playerChoice.getChosenSchema(), playerChoice.getChosenSchema().getDifficulty());
            user.setPlayer(player);
            addPlayer(player);
        }

        placeSeat(playerList);
        try{
            privatePlayerList = distributePrivateObjCard(gameLoader.getPrivateObjDeck());
            gameBoard = new GameBoard(playerList,bagDice,boardDice,trackBoard,boardCard,privatePlayerList);
            gameManager.setGameBoard(gameBoard);
        }catch(NullPointerException e){
            remoteView.reportError();
        }
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
        return cardDeck.extractCard();
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
     * Getter method of Player's list
     * @return the list of players in the match
     */
    public List<Player> getPlayerList(){
        return this.playerList;
    }

    /**
     * Method used to add players to the playerList
     * @param player new object Player to add
     */
    public void addPlayer(Player player){
        this.playerList.add(player);
    }

    public void sendCardChoices(PlayerChoice playerChoice){

        List<SchemaCard> schemaCards = new ArrayList<>();
        try{
            schemaCards.add((SchemaCard)extractCard(gameLoader.getSchemaDeck()));
            schemaCards.add((SchemaCard)extractCard(gameLoader.getSchemaDeck()));
            playerChoice.setSchemaCardList(schemaCards);
            remoteView. send(playerChoice);

        }catch (RuntimeException e){remoteView.reportError();}

        //studiare interazioni con view

    }

    /**
     *Update method for Observer implementation
     * @param playerChoice
     */
    public void update(PlayerChoice playerChoice)
    {
        boolean set = false;
        User user = playerChoice.getUser();
        for(PlayerChoice player: playerChoiceList){
            if(player.getUser().equals(user)){

                if(playerChoice.getChosenSchema()!=null){
                    if(player.getChosenSchema() != null){
                        remoteView.reportError(); //da definire parametro

                    }else player.setChosenSchema(playerChoice.getChosenSchema());
                }
                set = true;
                sendCardChoices(playerChoice);
            }
        }
        if(!set){
            colourEnumList.remove(playerChoice.getChosenColour());
            playerChoiceList.add(playerChoice);
            remoteView.send(playerChoice);
        }

        if(playerChoiceList.size()== userList.size()){
            startGame(playerChoiceList);
        }
    }

}
