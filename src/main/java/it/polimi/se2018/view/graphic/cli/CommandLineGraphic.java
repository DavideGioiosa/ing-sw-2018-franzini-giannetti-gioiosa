package it.polimi.se2018.view.graphic.cli;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.*;
import it.polimi.se2018.model.cards.publiccard.PublicObjCard;
import it.polimi.se2018.model.player.Player;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import static it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;
import static org.fusesource.jansi.Ansi.*;

import it.polimi.se2018.view.OutputStrategy;
import org.fusesource.jansi.AnsiConsole;

import static it.polimi.se2018.model.Config.NUMBER_OF_SCHEMA_COL;
import static it.polimi.se2018.model.Config.NUMBER_OF_SCHEMA_ROW;

/**
 * Manages input and output in Command Line
 * @author Cristian Giannetti
 */
public class CommandLineGraphic implements OutputStrategy {

    /**
     * Character used for Card frames
     */
    private static final char FRAME_CARD_CHARACTER = '#';
    /**
     * Length of card's margin
     */
    private static final int CARD_LENGTH = 35;
    /**
     * Number of rows used for displaying card's title
     */
    private static final int CARD_TITLE_HEIGHT = 3;
    /**
     * Number of rows used for displaying card's description
     */
    private static final int CARD_DESCRIPTION_HEIGHT = 9;
    /**
     * Number of rows used for displaying card's details
     */
    private static final int CARD_DETAILS_HEIGHT = 5;
    /**
     * HashMap for converting ColourEnum in Colour for coloured output
     */
    private static EnumMap<ColourEnum, Color> hashMapColours;

    /**
     * Standard String for display colours of cards
     */
    private static final String COLOUR_STRING = "######";
    /**
     * Public Objective Card Description for Value
     */
    private static final String PUBLIC_OBJ_CARD_VALUE_STRING = "Valore della carta: ";

    private static CommandLinePrint commandLinePrint;

    static{
        hashMapColours = new EnumMap<>(ColourEnum.class);
        hashMapColours.put(ColourEnum.BLUE, Color.BLUE);
        hashMapColours.put(ColourEnum.GREEN, Color.GREEN);
        hashMapColours.put(ColourEnum.PURPLE, Color.MAGENTA);
        hashMapColours.put(ColourEnum.RED, Color.RED);
        hashMapColours.put(ColourEnum.YELLOW, Color.YELLOW);
        commandLinePrint = new CommandLinePrint(hashMapColours);
    }

    /**
     * Constructor
     */
    public CommandLineGraphic(){
        //This class has only static attributes
    }

    /**
     * Print message in command line
     * @param message String to display
     */
    public void showMessage(int message){
        printMessage(message);
    }

    /**
     * Print message in command line using his ID
     * @param error ID of the message to display
     */
    public void showError(int error){
        printError(error);
    }

    /**
     * Show name and description of the chosen card
     * @param card Card to display
     */
    private void showBasicCard(Card card){
        printCardDetails(card.getName().toUpperCase(), CARD_TITLE_HEIGHT);
        printCardDetails(card.getDescription(), CARD_DESCRIPTION_HEIGHT);

    }

    /**
     * Show Public Objective Card
     * @param publicObjCard Public Objective Card to display
     */
    private void showPublicObjCard(PublicObjCard publicObjCard){
        printCardUpperFrame();
        showBasicCard(publicObjCard);
        printCardDetails(PUBLIC_OBJ_CARD_VALUE_STRING + publicObjCard.getBonus(), CARD_DETAILS_HEIGHT);
        printCardUpperFrame();
    }

    /**
     * Show Private Objective Card
     * @param card Private Objective Card to display
     */
    private static void showPrivateObjCard(PrivateObjCard card){
        if(card != null) {
            printCardUpperFrame();
            printCardDetails(card.getName().toUpperCase(), CARD_TITLE_HEIGHT);
            printCardDetails(card.getDescription(), CARD_DESCRIPTION_HEIGHT);
            printCardColour(card.getColour());
            printCardEmptyLine();
            printCardEmptyLine();
            printCardUpperFrame();
        }
    }

    /**
     * Show Tool Card
     * @param toolCard Tool Card to display
     */
    private void showToolCard(ToolCard toolCard){
        printCardUpperFrame();
        showBasicCard(toolCard);
        printCardColour(toolCard.getColour());
        printCardDetails("ID: " + toolCard.getId() + "          Token: " + toolCard.getToken(), 2);
        printCardUpperFrame();
    }

    /**
     * Show Schema Card
     * @param schemaCard Schema Card to display
     */
    private static void showSchemaCard(SchemaCard schemaCard){
        String frameCardCharacter = " # ";

        Cell cell;
        String upperFrame = " ------- ";

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i< 4 + 9*5; i++) stringBuilder.append('#');
        String upperCardFrame = " " + stringBuilder.toString();

        println(upperCardFrame);
        print(frameCardCharacter);
        for (int col = 0; col < NUMBER_OF_SCHEMA_COL; col ++){
            print(upperFrame);
        }

        for (int row = 0; row < NUMBER_OF_SCHEMA_ROW; row++) {
            print(frameCardCharacter + "\n" + frameCardCharacter);

            for(int i = 0; i<3; i++) {

                for (int col = 0; col < NUMBER_OF_SCHEMA_COL; col++) {
                    cell = schemaCard.getCellList().get(row * NUMBER_OF_SCHEMA_COL + col);

                    if (schemaCard.getCellList().get(row * NUMBER_OF_SCHEMA_COL + col).isEmpty()) {
                        commandLinePrint.colouredDiePrint(i, cell.getColour(), cell.getValue());
                    } else {
                        commandLinePrint.colouredDiePrint(i, cell.getDie().getColour(), cell.getDie().getValue());
                    }
                }

                print(frameCardCharacter + "\n" + frameCardCharacter);
            }

            for (int col = 0; col < NUMBER_OF_SCHEMA_COL; col ++){
                print(upperFrame);
            }
        }
        print(frameCardCharacter + "\n");

        println(upperCardFrame);
        println("");

    }

    /**
     * Print Private Objective Card colour
     * @param cardColour Colour to display
     */
    private static void printCardColour(ColourEnum cardColour){
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
        Color color = selectColour(cardColour);

        for(int i = 0; i < CARD_DETAILS_HEIGHT - 2; i++) {
            print(preCardDescription(COLOUR_STRING));
            System.out.print(ansi().eraseScreen().fg(color).a(COLOUR_STRING).reset());
            print(postCardDescription(COLOUR_STRING));
            println("");
        }
    }

    /**
     * Shows entire Game Board
     * @param clientModel Game Board to display
     */
    @Override
    public void showGameBoard(ClientModel clientModel){
        showPrivateObjCard(clientModel.getPrivateObjCard());
        ClientBoard clientBoard = clientModel.getClientBoard();
        for(PublicObjCard publicObjCard: clientBoard.getCardOnBoard().getPublicObjCardList()) showPublicObjCard(publicObjCard);
        for(ToolCard toolCard: clientBoard.getCardOnBoard().getToolCardList()) showToolCard(toolCard);
        showTrackBoard(clientBoard.getTrackBoardDice());
        println("");
        for(Player player: clientBoard.getPlayerList()) {
            showPlayerDetails(player);
        }
        showPlayerDetails(clientModel.getActualPlayer());
        showDieList(clientBoard.getBoardDice().getDieList());
    }

    @Override
    public void showScore(List<Player> playerList) {
        if (playerList != null && !playerList.isEmpty()){ Player winner = playerList.get(0);
            for (Player player : playerList) {
                println(player.getNickname() + " " + player.getScore());
                if(player.getScore() >= winner.getScore()) winner = player;
            }
            println("");
            showMessage(1500);
            println(winner.getNickname());
        }
        else showMessage(1501);

    }

    private void showPlayerDetails(Player player) {
        if (player != null){
            printMessage(2020);
            println(player.getNickname());
            printMessage(2021);
            println(player.getTokens());
            showSchemaCard(player.getSchemaCard());
            println("");
        }
    }

    /**
     * Shows entire Draft Pool
     * @param dieList List of Dice to display
     */
    private void showDieList(List<Die> dieList){
        if(dieList != null && !dieList.isEmpty()) {
            for (int i = 0; i < dieList.size(); i++) {
                print("\t" + i + "\t\t");
            }
            for (int j = 0; j < 3; j++) {
                println("");

                for (int i = 0; i < dieList.size(); i++) {
                    Die die = dieList.get(i);
                    colouredPrint(dieRowStringList.get(j).get(die.getValue()), die.getColour());
                    print('\t');
                }
            }
        }
        println("");
    }

    /**
     * Shows TrackBoard
     * @param trackBoard TrackBoard to display
     */
    private void showTrackBoard(TrackBoard trackBoard){
        int i = 1;
        for(List<Die> dieList: trackBoard.getDiceList()){
            println("Round " + i);
            showDieList(dieList);
            i++;
        }
    }

    /**
     * Separates string for card formatting
     * @param string String to separate
     * @return List of strings with a maximum size possible
     */
    private static List<String> separateString(String string){
        int rowMaxLength = CARD_LENGTH - 4;

        String[] stringSplitted = string.split(" ");
        List<String> stringList = new ArrayList<>();

        stringList.add(stringSplitted[0]);

        for (int i = 1; i < stringSplitted.length; i++){
            if (1 + stringSplitted[i].length() + stringList.get(stringList.size() - 1).length() < rowMaxLength){
                stringList.set(stringList.size() - 1, stringList.get(stringList.size() - 1).concat(" " + stringSplitted[i]));
            }else{
                stringList.add(stringSplitted[i]);
            }
        }

        return stringList;
    }

    /**
     * Print the upper (or lower) frame of the card
     */
    private static void printCardUpperFrame(){
        for (int i = 0; i < CARD_LENGTH; i ++) print(FRAME_CARD_CHARACTER);
        print("\n");
    }

    /**
     * Print a Card line with only margins
     */
    private static void printCardEmptyLine(){
        print(FRAME_CARD_CHARACTER);
        for (int j = 0; j < CARD_LENGTH - 2; j++) print(" ");
        print(FRAME_CARD_CHARACTER);
        print('\n');
    }

    /**
     * Show a string formatted for cards
     * @param details String to display
     * @param numberOfRows Number of command line rows dedicated to the string
     */
    private static void printCardDetails(String details, int numberOfRows){
        String string = "";

        printCardEmptyLine();
        numberOfRows --;

        List<String> stringList = separateString(details);

        int i;
        for (i = 0; i < stringList.size() && i < numberOfRows; i++){

            string = string.concat(FRAME_CARD_CHARACTER + " ");

            while(string.length() < (CARD_LENGTH - stringList.get(i).length()) / 2) string = string.concat(" ");
            string = string.concat(stringList.get(i));
            while(string.length() < CARD_LENGTH - 2) string = string.concat(" ");

            string = string.concat(" " + FRAME_CARD_CHARACTER);
            print(string + '\n');
            string = "";
        }

        while(i < numberOfRows - 1){
            printCardEmptyLine();
            i++;
        }

        printCardEmptyLine();

    }

    /**
     * Select Convert ColourEnum in Color for displaying coloured text
     * @param cardColour ColourEnum of the card
     * @return Color for Jansi
     */
    private static Color selectColour(ColourEnum cardColour) {
        return hashMapColours.get(cardColour);
    }

    /**
     * Return initial string for central formatted text in cards
     * @param string String that must be central formatted
     * @return Initial string
     */
    private static String preCardDescription(String string) {
        String startingString;
        startingString = FRAME_CARD_CHARACTER + " ";

        while(startingString.length() < (CARD_LENGTH - string.length()) / 2){
            startingString = startingString.concat(" ");
        }
        return startingString;
    }

    /**
     * Return final string for central formatted text in cards
     * @param string String that must be central formatted
     * @return Final string
     */
    private static String postCardDescription(String string) {
        String startingString;
        startingString =  " " + FRAME_CARD_CHARACTER;

        while(startingString.length() < (CARD_LENGTH - string.length() + 1) / 2){
            startingString = " ".concat(startingString);
        }
        return startingString;
    }

    /**
     * Shows Schema and Colours to be shown for Player's choice
     * @param playerChoice Containing Colours and Schemas available
     * @param commandType Indicates what the User have to chose
     */
    static void showChoice(PlayerChoice playerChoice, CommandTypeEnum commandType){

        switch(commandType){
            case IDSCHEMA:
                println("");
                printMessage(2005);
                showPrivateObjCard(playerChoice.getPrivateObjCard());
                for(SchemaCard schemaCard: playerChoice.getSchemaCardList()) {
                    println("");
                    printMessage(2000);
                    println(schemaCard.getId());
                    printMessage(2002);
                    println(schemaCard.getName());
                    printMessage(2001);
                    println(schemaCard.getDifficulty());
                    showSchemaCard(schemaCard);
                }
                return;

            case FRAMECOLOUR:
                println("");
                printMessage(2010);
                for(ColourEnum colour: playerChoice.getColourEnumList()) {
                    println(colour.toString());
                }
                return;

            default:
        }
    }

}

