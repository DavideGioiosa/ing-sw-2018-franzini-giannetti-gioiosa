package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;

import java.util.ArrayList;

/**
 * Public Class SchemaCard
 * @author Davide Gioiosa
 */

public class SchemaCard extends Card {
    private ArrayList<Cell> cellList;
    private int difficulty;
    private SchemaCard backSchema;

    public SchemaCard (String name, String description, int id, ArrayList<Cell> cellList){
        super(id, name, description);
        this.cellList = cellList;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty (int token){
        this.difficulty = token;
    }

    /**
     * insertion of the dice in the Scheme
     */
    public void setCell (Position position, Dice dice){
        cellList.get(position.getIndexArrayPosition()).setDice(dice);
    }

    public ArrayList<Cell> getCellList() {
        return cellList;
    }

    public void setBackSchema(SchemaCard backSchema) {
        this.backSchema = backSchema;
    }

    public SchemaCard getBackSchema() {
        return backSchema;
    }

    public boolean isEmpty (){
        for (Cell c : cellList){
            if (!c.isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**
     * @param index, number of the row
     * @return ArrayList with all the Cells that belong to that row
     */
    public ArrayList<Cell> getCellRow (int index) {

        ArrayList<Cell> rowList = new ArrayList<Cell>();
        int firstCellRow = index * 5;

        for(int i=0; i <= 4; i++){
            rowList.add(this.cellList.get(firstCellRow + i));
        }
        return rowList;
    }

    /**
     * @param index, number of the column
     * @return ArrayList with all the Cells that belong th that column
     */

    public ArrayList<Cell> getCellCol (int index) {

        ArrayList<Cell> colList = new ArrayList<Cell>();
        int firstCellCol = index;

        for(int i=0; i <= 3; i++){
            colList.add(this.cellList.get(firstCellCol + 5));
        }
        return colList;
    }

    /**
     * Max num of adj for a Cell: 4
     * IF: 1. (r,c-1) sx    2. (r,c+1) dx   3. (r-1, c) up  4. (r+1, c) dwn
     * @param position
     * @return Arraylist of adjacents to the position requested
     */
    public ArrayList<Cell> getAdjacents(Position position){
        ArrayList<Cell> adjList = new ArrayList<Cell>();

        if(!this.cellList.get(position.getIndexArrayPosition()).isEmpty()){
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
        }
        return adjList;
    }

    /**
     * Max num of adj for a Cell: 4
     * IF: 1. (r-1,c-1) up-sx    2. (r-1,c+1) up-dx   3. (r+1, c-1) dwn-sx  4. (r+1, c+1) dwn-dx
     * @param position
     * @return Arraylist of adjacents to the position requested
     */
    public ArrayList<Cell> getDiagonalAdjacents(Position position){
        ArrayList<Cell> adjDiagList = new ArrayList<Cell>();

        if(!this.cellList.get(position.getIndexArrayPosition()).isEmpty()){
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
        }
        return adjDiagList;
    }
}