package it.polimi.se2018.model;

import it.polimi.se2018.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//TODO exceptions
/**
 * PlayerMove contains the entire command representing the Action that the Player wants to do.
 *
 * @author Cristian Giannetti
 */

public class PlayerMove implements Serializable, Cloneable {

    /**
     * Progressive identifier of the Player Move
     */
    private int identifier;
    /**
     * Player whom the action is referred
     */
    private Player player;
    /**
     * Type of move. It defines the other parameters
     */
    private TypeOfChoiceEnum typeOfChoice;
    /**
     * ID of the Tool Card to use.
     * 0 value indicates no Tool Card used
     */
    private int idToolCard;
    /**
     * Index of the Draft Pool to use
     * -1 value indicates no diceBoardIndex needed
     */
    private int diceBoardIndex;
    /**
     * List of position of cells in player's Schema Card from where the player wants to move the dice
     */
    private List<Position> diceSchemaWhereToTake;
    /**
     * List of position of cells in player's Schema Card where the player wants to move the dice
     */
    private List<Position> diceSchemaWhereToLeave;
    /**
     * Position of dice in Track Board from where the player wants to pick a die.
     */
    private int trackBoardIndexArray[];
    /**
     * Generic value used by different Tool Cards
     */
    private int value;

    /**
     * Constructor sets the identifier to 0 and it resets all value of Player Move
     */
    public PlayerMove() {
        this.identifier = 0;
        newPlayerMove();
    }

    /**
     * Sets all value to default and increments and update the identifier
     */
    public void newPlayerMove(){
        updateId();
        this.player = null;
        this.typeOfChoice = null;
        this.idToolCard = 0;
        this.diceBoardIndex = -1;
        this.diceSchemaWhereToTake = new ArrayList<>();
        this.diceSchemaWhereToLeave = new ArrayList<>();
        this.trackBoardIndexArray = null;
        this.value = 0;
    }

    /**
     * Increments the identifier of the Player Move.
     */
    private void updateId() {
        this.identifier++;
    }

    /**
     * Gets the identifier of the Player Move.
     * @return the identifier
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * Sets the Player who wants to make the Player Move.
     * @param player Player who wants to make the Player Move
     */
    public void setPlayer(Player player) {
        if (player == null) throw new NullPointerException("ERROR: No player setted in the Player Move");
        this.player = player;
    }

    /**
     * Gets the Player to whom the Player Move is referred.
     * @return Player who wants to make the Player Move
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the type of move chosen by the Player.
     * @param typeOfChoice Enum with all type of move that the Player can make
     */
    public void setTypeOfChoice(TypeOfChoiceEnum typeOfChoice) {
        if (typeOfChoice == null) throw new NullPointerException("ERROR: No TypeOfChoice setted in the Player Move");
        this.typeOfChoice = typeOfChoice;
    }

    /**
     * Gets the type of move chosen by the Player.
     * @return Enum indicating the type of move
     */
    public TypeOfChoiceEnum getTypeOfChoice() {
        return typeOfChoice;
    }

    /**
     * Sets the identifier of the Tool Card to use.
     * @param idToolCard Identifier of the Tool Card
     */
    public void setIdToolCard(int idToolCard) {
        this.idToolCard = idToolCard;
    }

    /**
     * Gets the identifier of the Tool Card that the Player wants to use.
     * @return Identifier of the Tool Card to use
     */
    public int getIdToolCard() {
        return idToolCard;
    }

    /**
     * Sets the index of the die in the Draft Pool to use in this Player Move.
     * @param diceBoardIndex Index of the die in the Draft Pool to use in this Player Move
     */
    public void setDiceBoardIndex(int diceBoardIndex) {
        this.diceBoardIndex = diceBoardIndex;
    }

    /**
     * Gets the index of the die in the Draft Pool to use in this Player Move.
     * @return Index of the die in the Draft Pool to use in this Player Move
     */
    public int getDiceBoardIndex() {
        return diceBoardIndex;
    }

    /**
     * Sets a list of Position from where the Player wants to move the dice.
     * @param diceSchemaWhereToTake List of Position from where the Player wants to move the dice
     */
    public void insertDiceSchemaWhereToTake(Position diceSchemaWhereToTake) {
        this.diceSchemaWhereToTake.add(diceSchemaWhereToTake);
    }

    /**
     * Gets a list of Position from where the Player wants to move the dice
     * @return List of Position from where the Player wants to move the dice
     */
    public List<Position> getDiceSchemaWhereToTake() {
        return diceSchemaWhereToTake;
    }

    /**
     * Sets a list of Position where the Player wants to move the dice
     * @param diceSchemaWhereToLeave List of Position where the Player wants to move the dice
     */
    public void insertDiceSchemaWhereToLeave(Position diceSchemaWhereToLeave) {
        this.diceSchemaWhereToLeave.add(diceSchemaWhereToLeave);
    }

    /**
     * Gets a list of Position where the Player wants to move the dice
     * @return List of Position where the Player wants to move the dice
     */
    public List<Position> getDiceSchemaWhereToLeave() {
        return diceSchemaWhereToLeave;
    }

    /**
     * Sets the indexes of Track Board from where the Player wants to pick a dice
     * @param roundIndex Index of the Track Board from where the Player wants to pick a dice
     * @param diceIndex Index of the Dice in selected Round cell from where the Player wants to pick a dice
     */
    public void setTrackBoardIndex(int roundIndex, int diceIndex) {
        this.trackBoardIndexArray[0] = roundIndex;
        this.trackBoardIndexArray[1] = diceIndex;
    }

    /**
     * Gets the index of Draft Pool from where the Player wants to use in this Player Move
     * @return Index of the Draft Pool from where the Player wants to use in this Player Move
     */
    public int[] getTrackBoardIndex() {
        return trackBoardIndexArray;
    }

    /**
     * Sets a generic value nedeed by different Tool Cards
     * @param value Value used in the Player Move
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Gets value nedeed by different Tool Cards
     * @return Value used in the Player Move
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets a copy of PlayerMove
     * @return copy of PlayerMove
     * @throws CloneNotSupportedException Throws exception if it isn't possible to create a copy
     */
    public PlayerMove getClone() throws CloneNotSupportedException{
        return (PlayerMove)this.clone();
    }
}

