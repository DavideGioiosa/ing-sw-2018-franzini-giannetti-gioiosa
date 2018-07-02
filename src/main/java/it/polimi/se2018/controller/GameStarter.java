package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.*;
import it.polimi.se2018.model.cards.publiccard.PublicObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PrivatePlayer;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.view.RemoteView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
    private int indexNextUser;

    /**
     * Builder method for GameStarter class
     * @param userList List of User connected to the game
     */
    public GameStarter(List<User> userList, RemoteView remoteView){
        gameLoader = new GameLoader();
        playerList = new ArrayList<>();
        playerChoiceList = new ArrayList<>();
        this.userList = userList;
        playerChoiceSaved = new ArrayList<>();
        this.remoteView = remoteView;
        colourEnumList = gameLoader.getWindowFrame();
        indexNextUser = 0;

        Collections.shuffle(userList);
        for(User user: userList){
            playerChoiceSaved.add(new PlayerChoice(user));
        }

        sendColours(colourEnumList,playerChoiceSaved.get(indexNextUser));
        config = new Config();
    }


    void defaultMove(){
        if(playerChoiceSaved.size() == userList.size()){
            for(PlayerChoice playerChoice : playerChoiceSaved){
                if(playerChoice.getChosenColour() == null){
                    ColourEnum colour = playerChoice.getColourEnumList().get(new Random().nextInt(playerChoice.getColourEnumList().size()));
                    playerChoice.setChosenColour(colour);
                    List<ColourEnum> colours = new ArrayList<>();
                    colours.add(colour);
                    playerChoice.setColourEnumList(colours);
                }
                if(playerChoice.getIdChosenSchema() == 0){
                    SchemaCard card = playerChoice.getSchemaCardList().get(new Random().nextInt(playerChoice.getSchemaCardList().size()));
                    playerChoice.setIdChosenSchema(card.getId());
                    List<SchemaCard> schemas = new ArrayList<>();
                    schemas.add(card);
                    playerChoice.setSchemaCardList(schemas);
                }
            }
            remoteView.sendDefaultChoices(playerChoiceSaved);
            playerChoiceList = playerChoiceSaved;
            startGame(playerChoiceList);
        }
    }

    /**
     * Method prepares the board by extracting cards
     * @param playerChoiceList list player choices made to set up the game
     */
    private void startGame(List<PlayerChoice> playerChoiceList){

        BoardCard boardCard;
        List<PrivatePlayer> privatePlayerList = new ArrayList<>();
        List<PublicObjCard> publicObjCardList = new ArrayList<>();
        List<ToolCard> toolCardList = new ArrayList<>();
        BoardDice boardDice = new BoardDice();
        BagDice bagDice = new BagDice();
        TrackBoard trackBoard = new TrackBoard();

        for(int i = 0; i < config.getNumberOfPublicObjCardOnBoard(); i++){
            try{
                publicObjCardList.add((PublicObjCard)extractCard(gameLoader.getPublicObjDeck()));
            }catch(RuntimeException e){
                remoteView.reportError(1000, null); //probabilmente necessario passare un parametro
            }
        }

        for (int i = 0; i < config.getNumberOfToolCardOnBoard(); i++){
            try{
                toolCardList.add((ToolCard)extractCard(gameLoader.getToolDeck()));
            }catch(RuntimeException e){
                remoteView.reportError(1000, null);
            }
        }
        boardCard = new BoardCard(publicObjCardList, toolCardList);

        for(PlayerChoice playerChoice : playerChoiceList){
            User user = playerChoice.getUser();
            Player player = new Player(user.getNickname(), user.isConnected(), playerChoice.getChosenColour(), playerChoice.getSchemaCardList().get(0), playerChoice.getSchemaCardList().get(0).getDifficulty());
            user.setPlayer(player);
            addPlayer(player);

            PrivatePlayer privatePlayer = new PrivatePlayer(player,playerChoice.getPrivateObjCard());
            privatePlayerList.add(privatePlayer);

        }

        this.gameBoard = new GameBoard(playerList,bagDice, boardDice, trackBoard, boardCard, privatePlayerList);
    }

    public GameBoard getGameBoard() {
        return gameBoard;
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
    private void addPlayer(Player player){
        this.playerList.add(player);
    }

    /**
     * Method used to send the SchemaCards to the user in the set up phase
     * @param playerChoice Object used to send the SchemaCards extracted
     */
    private void sendCardChoices(PlayerChoice playerChoice){

        List<SchemaCard> schemaCards = new ArrayList<>();
        try{
            schemaCards.add((SchemaCard)extractCard(gameLoader.getSchemaDeck()));
            schemaCards.add(schemaCards.get(schemaCards.size() - 1).getBackSchema());
            schemaCards.add((SchemaCard)extractCard(gameLoader.getSchemaDeck()));
            schemaCards.add(schemaCards.get(schemaCards.size() - 1).getBackSchema());
            playerChoice.setSchemaCardList(schemaCards);

            if(playerChoice.getPrivateObjCard() == null){
                playerChoice.setPrivateObjCard((PrivateObjCard)gameLoader.getPrivateObjDeck().extractCard());
            }
            remoteView.sendChoice(playerChoice);

        }catch (RuntimeException e){remoteView.reportError(1000, playerChoice.getUser().getNickname());}

    }

    /**
     * Method used to send the Colours choices for the frame
     * @param colourEnumList list of remaining colours
     * @param playerChoice choice message sent to the client
     */
    private void sendColours(List<ColourEnum> colourEnumList, PlayerChoice playerChoice){

        playerChoice.setColourEnumList(colourEnumList);
        remoteView.sendChoice(playerChoice);
    }

    private void receivedSchema(PlayerChoice playerChoice, PlayerChoice choiceSaved){

        for(SchemaCard schemaCard: choiceSaved.getSchemaCardList()) {
            if (schemaCard.getId() == playerChoice.getIdChosenSchema()) {

                choiceSaved.setIdChosenSchema(playerChoice.getIdChosenSchema());

                //Sets the player's colour choice in his possible choices, so it cannot be changed
                List<SchemaCard> schemaSelected = new ArrayList<>();
                schemaSelected.add(schemaCard);
                choiceSaved.setSchemaCardList(schemaSelected);

                playerChoiceList.add(choiceSaved);
            }
        }
        if(choiceSaved.getIdChosenSchema() == 0){

            remoteView.reportError(1000, choiceSaved.getUser().getNickname());
        }
    }


    private void receivedColour(PlayerChoice playerChoice, PlayerChoice choiceSaved){

        if(choiceSaved.getColourEnumList().contains(playerChoice.getChosenColour())) {

            colourEnumList.remove(playerChoice.getChosenColour());
            choiceSaved.setChosenColour(playerChoice.getChosenColour());

            //Sets the player's colour choice in his possible choices, so it cannot be changed
            List<ColourEnum> colourSelected = new ArrayList<>();
            colourSelected.add(choiceSaved.getChosenColour());
            choiceSaved.setColourEnumList(colourSelected);

            indexNextUser++;
            sendCardChoices(choiceSaved);
            if (indexNextUser < userList.size()) {
                sendColours(colourEnumList, playerChoiceSaved.get(indexNextUser));
            }
        }else{
            remoteView.reportError(1000, null);
        }

    }

    /**
     * Update method for Observer implementation
     * @param playerChoice Contains choices for setup made by the player
     * @return status of game: true if the game setup is completed
     */
     boolean newChoice(PlayerChoice playerChoice){

        for(PlayerChoice choiceSaved: playerChoiceSaved){
            if(choiceSaved.getUser().getNickname().equals(playerChoice.getUser().getNickname())){

                if(!choiceSaved.getSchemaCardList().isEmpty()) {
                    receivedSchema(playerChoice, choiceSaved);
                }
                if(choiceSaved.getChosenColour() == null){
                    receivedColour(playerChoice, choiceSaved);
                }
            }
        }
        if(playerChoiceList.size() == userList.size()){
            startGame(playerChoiceList);
            remoteView.sendTable( new MoveMessage(gameBoard.getPlayerList(), gameBoard.getBoardDice(), gameBoard.getCardOnBoard(), gameBoard.getTrackBoardDice()));
            return true;
        }else return false;
    }

}
