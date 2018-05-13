package it.polimi.se2018.model;

/**
 * Public Class Position
 * @author Davide Gioiosa
 */

public class Position {
    private int row;
    private int col;

    /**
     * Builder: empty
     */
    public Position (){
    }

    /**
     * Builder: sets row and col of Position
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @param row sets the row of Position
     */
    public void setRow(int row) {
        if(row < 0 || row > 3){
            throw new IllegalArgumentException("ERROR: Cannot set a row not in the range permitted");
        }
        this.row = row;
    }

    /**
     * @param col sets the col of Position
     */
    public void setCol(int col) {
        if(col < 0 || row > 4) {
            throw new IllegalArgumentException("ERROR: Cannot set a col not in the range permitted");
        }
        this.col = col;
    }

    /**
     * @param indexArrayPosition
     * sets row and col calculated started from indexArrayPosition
     */
    public void setRowCol (int indexArrayPosition){
        if(indexArrayPosition < 0 || indexArrayPosition > 19){
            throw new IllegalArgumentException("ERROR: indexArrayPosition insert is not in the range permitted");
        }
        this.row = indexArrayPosition / 5;
        this.col = indexArrayPosition % 5;
    }

    /**
     * Builder: coord matrix (row and col) from index of Scheme Array
     * @param indexArrayPosition
     */
    public Position (int indexArrayPosition){
        this.row = indexArrayPosition / 5;
        this.col = indexArrayPosition % 5;
    }

    /**
     * @return row of Position
     */
    public int getRow() {
        return row;
    }

    /**
     * @return col of Position
     */
    public int getCol() {
        return col;
    }

    /**
     * @param indexArrayPosition, index of the Scheme Array
     * @return the row to which it belongs
     */
    public int getRow (int indexArrayPosition){
        return indexArrayPosition / 5;
    }

    /**
     * @param indexArrayPosition, index of the Scheme Array
     * @return the col to which it belongs
     */
    public int getCol (int indexArrayPosition){
        return indexArrayPosition % 5;
    }

    /**
     * @return index of the Scheme Array from the 2 coord of the matrix
     */
    public int getIndexArrayPosition (){
        return this.row * 5 + this.col;
    }

    /**
     * used by getAdjacents to pass row +1, row-1.. to get adj cells
     */
    public int getIndexArrayPosition (int row, int col){
        return row * 5 + col;
    }
}