package it.polimi.se2018.model;

/**
 * Contains the configuration parameters of the game
 *
 * @author Cristian Giannetti
 */

public class Config {
    public static final int NUMBEROFROUND = 10;

    public static final int NUMBEROFDICEPERCOLOUR = 18;
    public static final int DIEMINVALUE = 1;
    public static final int DIEMAXVALUE = 6;

    public static final int NUMBEROFTOOLCARD = 12;
    public static final int NUMBEROFPUBLICOBJCARD = 10;
    public static final int NUMBEROFPRIVATEOBJCARD = 5;

    private static int numberOfToolCardOnBoard;
    private static int numberOfPublicObjCardOnBoard;

    public static final int IDFIRSTPUBLICOBJCARD = 100;
    public static final int IDFIRSTPRIVATEOBJCARD = 200;
    public static final int IDFIRSTTOOLCARDS = 300;
    public static final int IDFIRSTSCHEMACARD = 400;


    public static final int NUMBEROFSCHEMAROW = 4;
    public static final int NUMBEROFSCHEMACOL = 5;

    public static final String RESETCOMMAND = "CANCEL";

    private int numberOfPlayer;

    public Config(int numberOfPlayer, int numberOfPublicObjCardOnBoard, int numberOfToolCardOnBoard){
        this.numberOfPlayer = numberOfPlayer;
        this.numberOfPublicObjCardOnBoard = numberOfPublicObjCardOnBoard;
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

    public static int getNumberOfToolCardOnBoard(){
        return numberOfToolCardOnBoard;
    }
}
