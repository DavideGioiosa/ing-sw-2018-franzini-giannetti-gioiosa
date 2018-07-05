package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.restriction.Restriction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Public Class SchemaCard
 * @author Davide Gioiosa
 */

public class SchemaCard extends Card implements Serializable, DiceContainer {

    /**
     * List of the cells that make up the scheme
     */
    private List<Cell> cellList;
    /**
     * Related to the number of tokens to give to the player
     */
    private int difficulty;
    /**
     * Other scheme of the schemeCard, composed by 2 schemes
     */
    private transient SchemaCard backSchema;

    /**
     * Builder: creates a Schema Card
     * @param name name of the card
     * @param description information about the card
     * @param id identifier of the scheme
     * @param token related to the difficulty of a scheme
     * @param cellList 20 cells that compose the scheme
     */
    public SchemaCard (int id, String name, String description, int token, List<Cell> cellList){
        super(id, name, description);
        if(cellList == null){
            throw new NullPointerException("ERROR: Insert cellList null");
        }
        if(token < 0){
            throw new IllegalArgumentException("ERROR: Cannot set a value not in the range permitted");
        }
        this.cellList = cellList;
        this.difficulty = token;
    }

    /**
     * Copy Constructor
     * @param schemaCard Schema Card to be cloned
     */
    private SchemaCard(SchemaCard schemaCard){
        super(schemaCard);
        this.difficulty = schemaCard.difficulty;
        this.cellList = new ArrayList<>();
        for(Cell cell: schemaCard.cellList) cellList.add(cell.getClone());
        this.backSchema = null;
    }

    /**
     * Difficulty of the Scheme, related to the number of tokens to give to the player
     * at the beginning of the match
     * @return the difficulty of the scheme
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * insertion of the die in the Scheme
     * @param position where the player wants to put the die
     * @param die die selected
     */
    public void setDiceIntoCell(Position position, Die die){
        if(die == null){
            throw new NullPointerException("ERROR: Selected a null die");
        }
        checkPosition(position);

        cellList.get(position.getIndexArrayPosition()).insertDie(die);
    }

    public Die pickDieFromCell(Position position){
        if(position == null) throw new NullPointerException("ERROR: Impossible to pick Die from null Position");
        return this.cellList.get(position.getIndexArrayPosition()).pickDie();
    }

    /**
     * List of cell componing the Scheme Card
     * @return the list of the cells componing the scheme
     */
    public List<Cell> getCellList() {
        return cellList;
    }

    /**
     * sets the back scheme of a scheme card
     * @param backSchema related to a schema card
     */
    public void setBackSchema(SchemaCard backSchema) {
        if(backSchema == null){
            throw new NullPointerException("ERROR: Insert a backSchema null");
        }
        this.backSchema = backSchema;
    }

    /**
     * @return the scheme on the back of a scheme card
     */
    public SchemaCard getBackSchema() {
        return backSchema;
    }

    /**
     * Check if every cell of the scheme is empty
     * @return true if all the cells are empty
     */
    public boolean isEmpty (){
        for (Cell c : cellList){
            if (!c.isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**
     * @param index number of the row
     * @return ArrayList with all the Cells that belong to that row
     */
    public List<Cell> getCellRow (int index) {
        if(index <0 || index > 3){
            throw new IllegalArgumentException("Insert index value out of the range permitted");
        }

        List<Cell> rowList = new ArrayList<>();
        int firstCellRow = index * 5;

        for(int i=0; i <= 4; i++){
            rowList.add(this.cellList.get(firstCellRow + i));
        }
        return rowList;
    }

    /**
     * @param index number of the column
     * @return ArrayList with all the Cells that belong to that column
     */

    public List<Cell> getCellCol (int index) {
        if(index <0 || index > 4){
            throw new IllegalArgumentException("Insert index value out of the range permitted");
        }

        List<Cell> colList = new ArrayList<>();
        int firstCellCol = index;

        for(int i=0; i <= 3; i++){
            colList.add(this.cellList.get(firstCellCol));
            firstCellCol += 5;
        }
        return colList;
    }

    /**
     * Creates a list with horizontal and vertical adjacent cells of the position requested
     * @param position of the Cell to analyze adjacents
     * @return Arraylist of adjacent Cells of the position requested
     */
    public List<Cell> getAdjacents(Position position){
        checkPosition(position);

        List<Cell> adjList = new ArrayList<>();

        if(position.getCol() - 1 >= 0) {
            adjList.add(this.cellList.get(position.getIndexArrayPosition(position.getRow(), position.getCol() - 1)));
        }
        if(position.getCol() + 1 <= 4) {
            adjList.add(this.cellList.get(position.getIndexArrayPosition(position.getRow(), position.getCol() + 1)));
        }
        if(position.getRow() - 1 >= 0) {
            adjList.add(this.cellList.get(position.getIndexArrayPosition(position.getRow() - 1, position.getCol())));
        }
        if(position.getRow() + 1 <= 3) {
            adjList.add(this.cellList.get(position.getIndexArrayPosition(position.getRow() + 1, position.getCol())));
        }
        return adjList;
    }

    /**
     * Creates a list with diagonal adjacent cells of the position requested
     * @param position Position of the Cell to analyze adjacents
     * @return Arraylist of adjacents to the position requested
     */
    public List<Cell> getDiagonalAdjacents(Position position){
        checkPosition(position);

        List<Cell> adjDiagList = new ArrayList<>();

        if(position.getCol() - 1 >= 0 && position.getRow() - 1 >= 0) {
            adjDiagList.add(this.cellList.get(position.getIndexArrayPosition(position.getRow() - 1,
                    position.getCol() - 1)));
        }
        if(position.getRow() - 1 >= 0 && position.getCol() + 1 <= 4) {
            adjDiagList.add(this.cellList.get(position.getIndexArrayPosition(position.getRow() - 1,
                    position.getCol() + 1)));
        }
        if(position.getRow() + 1 <= 3 && position.getCol() - 1 >= 0) {
            adjDiagList.add(this.cellList.get(position.getIndexArrayPosition(position.getRow() + 1,
                    position.getCol() - 1)));
        }
        if(position.getRow() + 1 <= 3 && position.getCol() + 1 <= 4) {
            adjDiagList.add(this.cellList.get(position.getIndexArrayPosition(position.getRow() + 1,
                    position.getCol() + 1)));
        }
        return adjDiagList;
    }

    /**
     * Check validity of Position
     * @param position position to check
     */
    public void checkPosition(Position position){
        if (position == null) throw new NullPointerException("ERROR: Impossible to get adjacent of a null position");
        if (position.getIndexArrayPosition() < 0 || position.getIndexArrayPosition() > 19) {
            throw new IllegalArgumentException("ERROR: The position inserted is out of the range permitted");
        }
    }

    /**
     * Gets a clone of Schema Card
     * @return Cloned SchemaCard
     */
    @Override
    public SchemaCard getClone(){
        return new SchemaCard(this);
    }

    /**
     *
     * @param position
     * @return
     */
    private Die pickDie(Position position){
        return cellList.get(position.getIndexArrayPosition()).pickDie();
    }

    /**
     *
     * @param playerMove
     * @param dieList
     */
    @Override
    public boolean pickDice(PlayerMove playerMove, List<Die> dieList, int min, int max){
        try {
            for (int i = 0; i < playerMove.getDiceSchemaWhereToTake().size() && i < max; i++) {
                dieList.add(pickDie(playerMove.getDiceSchemaWhereToTake().get(0)));
                playerMove.getDiceSchemaWhereToTake().remove(0);
            }
        }catch(IndexOutOfBoundsException | NullPointerException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean exchangeDice(PlayerMove playerMove, List<Die> dieList){
        try {
            for (int i = 0; i < playerMove.getDiceSchemaWhereToLeave().size() && i < dieList.size(); i++) {
                setDiceIntoCell(playerMove.getDiceSchemaWhereToLeave().get(i), dieList.remove(i));
                dieList.add(i, pickDie(playerMove.getDiceSchemaWhereToLeave().get(i)));
            }
        }catch(IndexOutOfBoundsException | NullPointerException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean leaveDice(PlayerMove playerMove, List<Die> dieList, List<Restriction> restrictionList){
        try {
            for (int i = 0; i < playerMove.getDiceSchemaWhereToLeave().size() && i < dieList.size(); i++) {
                for (Restriction restriction : restrictionList) {
                    int errorId = restriction.checkRestriction(this, dieList.get(0), playerMove.getDiceSchemaWhereToLeave().get(i));
                    if (errorId != 0) return false;
                    //TODO: ERROR O NEW EXCEPTION;
                }
                setDiceIntoCell(playerMove.getDiceSchemaWhereToLeave().remove(0), dieList.get(i));
            }
            dieList.remove(0);
        }catch(NullPointerException | IndexOutOfBoundsException e){
            return false;
        }
        return true;
    }

    @Override
    public List<Die> getClonedDieList(){
        List<Die> dieList = new ArrayList<>();
        for(Cell cell: cellList) if (!cell.isEmpty()) dieList.add(cell.getDie().getClone());
        return dieList;
    }

}