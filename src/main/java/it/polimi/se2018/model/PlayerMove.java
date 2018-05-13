package it.polimi.se2018.model;

import it.polimi.se2018.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * PlayerMove contains the entire command representing the Action that the Player wants to do.
 *
 * @author Cristian Giannetti
 */

public class PlayerMove {

    /**
     * Progressive identifier of the Player Move
     */
    private int id;
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
    private Position trackBoardIndex;
    /**
     * Value used by different toolcards
     */
    private int value;

    /**
     * Constructor sets the identifier to 0 and it resets all value of Player Move
     */
    PlayerMove() {
        this.id = 0;
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
        this.trackBoardIndex = null;
        this.value = 0;
    }

    /**
     * Increments the identifier of the Player Move.
     */
    private void updateId() {
        this.id++;
    }

    /**
     * Gets the identifier of the Player Move.
     * @return the identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the Player who wants to make the Player Move.
     * @param player Player who wants to make the Player Move
     */
    public void setPlayer(Player player) {
        if (player == null) throw new IllegalArgumentException("ERROR: No player setted in the Player Move");
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
        if (typeOfChoice == null) throw new IllegalArgumentException("ERROR: No TypeOfChoice setted in the Player Move");
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
        //TODO: check existent Tool Card id
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
    public void setDiceSchemaWhereToTake(List<Position> diceSchemaWhereToTake) {
        this.diceSchemaWhereToTake = diceSchemaWhereToTake;
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
    public void setDiceSchemaWhereToLeave(List<Position> diceSchemaWhereToLeave) {
        this.diceSchemaWhereToLeave = diceSchemaWhereToLeave;
    }

    /**
     * Gets a list of Position where the Player wants to move the dice
     * @return List of Position where the Player wants to move the dice
     */
    public List<Position> getDiceSchemaWhereToLeave() {
        return diceSchemaWhereToLeave;
    }

    /**
     * Sets the index of Draft Pool from where the Player wants to pick a dice
     * @param trackBoardIndex Index of the Draft Pool from where the Player wants to pick a dice
     */
    public void setTrackBoardIndex(Position trackBoardIndex) {
        this.trackBoardIndex = trackBoardIndex;
    }

    /**
     * Gets the index of Draft Pool from where the Player wants to use in this Player Move
     * @return Index of the Draft Pool from where the Player wants to use in this Player Move
     */
    public Position getTrackBoardIndex() {
        return trackBoardIndex;
    }

    /**
     * Sets a generic value nedeed by different Tool Cards
     * @param value Value used in this Player Move
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Gets value nedeed by different Tool Cards
     * @return Value used in this Player Move
     */
    public int getValue() {
        return value;
    }

}

