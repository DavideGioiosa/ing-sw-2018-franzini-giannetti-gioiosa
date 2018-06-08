package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.*;
import it.polimi.se2018.model.cards.publiccard.PublicObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PrivatePlayer;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.RemoteView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static it.polimi.se2018.model.Config.*;

/**
 * Controller's Class GameStarter
 * @author Silvia Franzini
 */
public class GameStarter {

    private GameLoader gameLoader;
    private List<Player> playerList;
    private List<User> userList;
    private List<PlayerChoice> playerChoiceList;
    private RemoteView remoteView;
    private List<ColourEnum> colourEnumList;
    private List<PlayerChoice> playerChoiceSaved;
    private GameBoard gameBoard;
    private Config config;

    /**
     * Builder method for GameStarter class
     */
    public GameStarter(List<User> userList){
        gameLoader = new GameLoader();
        playerList = new ArrayList<>();
        playerChoiceList = new ArrayList<>();
        this.userList = userList;
        playerChoiceSaved = new ArrayList<>();
        remoteView = new RemoteView();
        colourEnumList = gameLoader.getWindowFrame();
        if(userList.iterator().hasNext()){
            sendColours(userList.iterator().next(),colourEnumList);
        }
        config = new Config();

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

        if(playerChoiceList.size()!= userList.size()){
            throw new RuntimeException("ERROR: not enough players");
        }

        for(int i = 0; i < config.getNumberOfPublicObjCardOnBoard(); i++){
            try{
                publicObjCardList.add((PublicObjCard)extractCard(gameLoader.getPublicObjDeck()));
            }catch(RuntimeException e){
                remoteView.reportError(); //probabilmente necessario passare un parametro
            }
        }

        for (int i = 0; i < config.getNumberOfToolCardOnBoard(); i++){
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
             this.gameBoard = new GameBoard(playerList,bagDice,boardDice,trackBoard,boardCard,privatePlayerList);
        }catch(NullPointerException e){
            remoteView.reportError();
        }
    }

    public GameBoard getGameBoard() {
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
        for(int i = 0; i < playerList.size(); i++){
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

    /**
     * Method used to send the SchemaCards to the user in the set up phase
     * @param playerChoice Object used to send the SchemaCards extracted
     */
    public void sendCardChoices(PlayerChoice playerChoice){

        List<SchemaCard> schemaCards = new ArrayList<>();
        try{
            schemaCards.add((SchemaCard)extractCard(gameLoader.getSchemaDeck()));
            schemaCards.add((SchemaCard)extractCard(gameLoader.getSchemaDeck()));
            schemaCards.add(schemaCards.get(0).getBackSchema());
            schemaCards.add(schemaCards.get(1).getBackSchema());
            playerChoice.setSchemaCardList(schemaCards);
            for(PlayerChoice choiceSaved : playerChoiceSaved){
                if(choiceSaved.getUser().equals(playerChoice.getUser())){
                    if(choiceSaved.getSchemaCardList()== null){
                        choiceSaved.setSchemaCardList(schemaCards);
                    }else remoteView.reportError();
                }
            }
            remoteView.sendChoice(playerChoice);

        }catch (RuntimeException e){remoteView.reportError();}

        //TODO studiare interazioni con view

    }

    /**
     * Method used to send the Colours choices for the frame
     * @param user addressee of the message
     * @param colourEnumList list of remaining colours
     */
    public void sendColours(User user, List<ColourEnum> colourEnumList){
        PlayerChoice playerChoice = new PlayerChoice(user);
        playerChoice.setColourEnumList(colourEnumList);
        playerChoiceSaved.add(playerChoice);
        remoteView.sendChoice(playerChoice);
    }

    /**
     * Update method for Observer implementation
     * @param playerChoice
     */
    public boolean newChoice(PlayerChoice playerChoice){

        for(PlayerChoice choiceSaved: playerChoiceSaved){
            if(choiceSaved.getUser().equals(playerChoice.getUser())){

                if(choiceSaved.getSchemaCardList() != null) {
                    if (choiceSaved.getSchemaCardList().contains(playerChoice.getChosenSchema())) {

                        choiceSaved.setChosenSchema(playerChoice.getChosenSchema());

                        //Sets the player's colour choice in his possible choices, so it cannot be changed
                        List<SchemaCard> schemaSelected = new ArrayList<>();
                        schemaSelected.add(playerChoice.getChosenSchema());
                        choiceSaved.setSchemaCardList(schemaSelected);

                        playerChoiceList.add(choiceSaved);

                    } else remoteView.reportError();
                }

                if(choiceSaved.getColourEnumList().contains(playerChoice.getChosenColour())){

                    colourEnumList.remove(playerChoice.getChosenColour());
                    choiceSaved.setChosenColour(playerChoice.getChosenColour());

                    //Sets the player's colour choice in his possible choices, so it cannot be changed
                    List<ColourEnum> colourSelected = new ArrayList<>();
                    colourSelected.add(choiceSaved.getChosenColour());
                    choiceSaved.setColourEnumList(colourSelected);

                    sendCardChoices(playerChoice);
                    if(userList.iterator().hasNext()){
                        sendColours(userList.iterator().next(), colourEnumList);
                    }

                }else {
                    remoteView.reportError();
                    return false;
                }


            }
        }

        if(playerChoiceList.size() == userList.size()){
            startGame(playerChoiceList);
            return  true;
        }else return false;
    }

}
