package it.polimi.se2018.model;

/**
 * Contains the configuration parameters of the game
 * @author Cristian Giannetti
 */

public class Config {
    public static final int NUMBER_OF_TOOL_CARD_ON_BOARD_IN_MULTIPLAYER = 3;
    public static final int NUMBER_OF_PUBLIC_OBJ_CARD_ON_BOARD_IN_MULTIPLAYER = 3;

    public static final int NUMBER_OF_ROUND = 10;

    public static final int NUMBER_OF_DICE_PER_COLOUR = 18;
    public static final int DIE_MIN_VALUE = 1;
    public static final int DIE_MAX_VALUE = 6;

    public static final int NUMBER_OF_TOOL_CARD = 12;
    public static final int NUMBER_OF_PUBLIC_OBJ_CARD = 10;
    public static final int NUMBER_OF_PRIVATE_OBJ_CARD = 5;
    public static final int NUMBER_OF_SCHEMA_CARD = 12;

    public static final int ID_FIRST_PUBLIC_OBJ_CARD = 100;
    public static final int ID_FIRST_PRIVATE_OBJ_CARD = 200;
    public static final int ID_FIRST_TOOL_CARD = 300;
    public static final int ID_FIRST_SCHEMA_CARD = 400;


    public static final int NUMBER_OF_SCHEMA_ROW = 4;
    public static final int NUMBER_OF_SCHEMA_COL = 5;

    public static final String RESET_COMMAND = "CANCEL";

    private int numberOfPlayer;
    private int numberOfToolCardOnBoard;
    private int numberOfPublicObjCardOnBoard;

    public Config(int numberOfPlayer, int numberOfPublicObjCard, int numberOfToolCardOnBoard){
        this.numberOfPlayer = numberOfPlayer;
        numberOfPublicObjCardOnBoard = numberOfPublicObjCard;
        this.numberOfToolCardOnBoard = numberOfToolCardOnBoard;
    }

    public Config(){
        this.numberOfPlayer = 4;
        this.numberOfToolCardOnBoard = 3;
        this.numberOfPublicObjCardOnBoard = 3;
    }

    public int getNumberOfPublicObjCardOnBoard() {
        return numberOfPublicObjCardOnBoard;
    }

    public int getNumberOfToolCardOnBoard(){
        return numberOfToolCardOnBoard;
    }

    /**
     * Gets the number of players of the match
     * @return Number of the Players of the match
     */
    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }
}
