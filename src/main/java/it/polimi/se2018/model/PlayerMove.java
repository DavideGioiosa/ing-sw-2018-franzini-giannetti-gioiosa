package it.polimi.se2018.model;

import it.polimi.se2018.model.player.Player;

import java.util.List;

public class PlayerMove {

    private int id;                           /*Progressive*/
    private Player player;
    private TypeOfChoiceEnum typeOfChoice;
    private int choice;
    private int schemaId;
    private ColourEnum boardColour;
    private int idToolCard;
    private int diceBoardIndex;
    private List<Position> diceSchemaWhereToTake;
    private List<Position> diceSchemaWhereToLeave;
    private Position trackBoardIndex;
    private int value;
    private View view;


    PlayerMove(){
        this.id = 0;
    }

    public void updateId(int id) {
        this.id ++;
    }

    public int getId() {
        return id;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setTypeOfChoice(TypeOfChoiceEnum typeOfChoice) {
        this.typeOfChoice = typeOfChoice;
    }

    public TypeOfChoiceEnum getTypeOfChoice() {
        return typeOfChoice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public int getChoice() {
        return choice;
    }

    public void setSchemaId(int schemaId) {
        this.schemaId = schemaId;
    }

    public int getSchemaId() {
        return schemaId;
    }

    public void setBoardColour(ColourEnum boardColour) {
        this.boardColour = boardColour;
    }

    public ColourEnum getBoardColour() {
        return boardColour;
    }

    public void setIdToolCard(int idToolCard) {
        this.idToolCard = idToolCard;
    }

    public int getIdToolCard() {
        return idToolCard;
    }

    public void setDiceBoardIndex(int diceBoardIndex) {
        this.diceBoardIndex = diceBoardIndex;
    }

    public int getDiceBoardIndex() {
        return diceBoardIndex;
    }

    public void setDiceSchemaWhereToTake(List<Position> diceSchemaWhereToTake) {
        this.diceSchemaWhereToTake = diceSchemaWhereToTake;
    }

    public List<Position> getDiceSchemaWhereToTake() {
        return diceSchemaWhereToTake;
    }

    public void setDiceSchemaWhereToLeave(List<Position> diceSchemaWhereToLeave) {
        this.diceSchemaWhereToLeave = diceSchemaWhereToLeave;
    }

    public List<Position> getDiceSchemaWhereToLeave() {
        return diceSchemaWhereToLeave;
    }

    public void setTrackBoardIndex(Position trackBoardIndex) {
        this.trackBoardIndex = trackBoardIndex;
    }

    public Position getTrackBoardIndex() {
        return trackBoardIndex;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

