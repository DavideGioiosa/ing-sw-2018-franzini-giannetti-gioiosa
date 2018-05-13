package it.polimi.se2018.model;

/**
 * Public Class Position
 * @author Davide Gioiosa
 */

public class Position {

    /**
     * row of the Scheme in matrix form
     */
    private int row;
    /**
     * col of the Scheme in matrix form
     */
    private int col;

    /**
     * Builder: create an empty position to use methods of the class
     */
    public Position (){
    }

    /**
     * Builder: sets row and col of Position
     * @param row of the Scheme in matrix form
     * @param col of the Scheme in matrix form
     */
    public Position(int row, int col) {
        if(row < 0 || row > 3){
            throw new IllegalArgumentException("ERROR: Cannot set a row not in the range permitted");
        }
        if(col < 0 || col > 4){
            throw new IllegalArgumentException("ERROR: Cannot set a col not in the range permitted");
        }
        this.row = row;
        this.col = col;
    }

    /**
     * @param row sets the row of the Scheme in matrix form
     */
    public void setRow(int row) {
        if(row < 0 || row > 3){
            throw new IllegalArgumentException("ERROR: Cannot set a row not in the range permitted");
        }
        this.row = row;
    }

    /**
     * @param col sets the col of the Scheme in matrix form
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
     * Builder: coord matrix (row and col) from index of Scheme in array form
     * @param indexArrayPosition, index of the Scheme in array form
     */
    public Position (int indexArrayPosition){
        this.row = indexArrayPosition / 5;
        this.col = indexArrayPosition % 5;
    }

    /**
     * @return row of the Scheme in matrix form
     */
    public int getRow() {
        return row;
    }

    /**
     * @return col of the Scheme in matrix form
     */
    public int getCol() {
        return col;
    }

    /**
     * @param indexArrayPosition, index of the Scheme in array form
     * @return the row to which it belongs
     */
    public int getRow (int indexArrayPosition){
        return indexArrayPosition / 5;
    }

    /**
     * @param indexArrayPosition, index of the Scheme in array form
     * @return the col to which it belongs
     */
    public int getCol (int indexArrayPosition){
        return indexArrayPosition % 5;
    }

    /**
     * @return index of the Scheme in array form from the 2 coord of the matrix
     */
    public int getIndexArrayPosition (){
        return this.row * 5 + this.col;
    }

    /**
     * used by getAdjacents to pass row +1, row-1.. to get adj cells
     * @param row of the Scheme in matrix form
     * @param col of the Scheme in matrix form
     * @return the related IndexArrayPosition from row and col
     */
    public int getIndexArrayPosition (int row, int col){
        return row * 5 + col;
    }
}