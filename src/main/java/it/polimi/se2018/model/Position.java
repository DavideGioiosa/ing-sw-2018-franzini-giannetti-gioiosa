package it.polimi.se2018.model;

/**
 * Public Class Position
 * @author Davide Gioiosa
 */

public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRowCol (int indexArrayPosition){
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

    public int getRow() {
        return row;
    }

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