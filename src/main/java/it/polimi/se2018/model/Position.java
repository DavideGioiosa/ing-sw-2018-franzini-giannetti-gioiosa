package it.polimi.se2018.model;

import java.io.Serializable;

/**
 * Public Class Position
 * @author Davide Gioiosa
 */

public class Position implements Serializable {

    /**
     * Row index of the Scheme in matrix form
     */
    private int row;
    /**
     * Column index of the Scheme in matrix form
     */
    private int col;

    /**
     * Constructor: create an empty position to use methods of the class
     */
    public Position (){
        this.row = 0;
        this.col = 0;
    }

    /**
     * Constructor: sets row and col of Position
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
     * Copy constructor
     * @param position Position to be cloned
     */
    private Position(Position position){
        if (position == null) throw new NullPointerException("ERROR: Tried to clone a null position");
        this.row = position.row;
        this.col = position.col;
    }

    /**
     * Constructor: coord matrix (row and col) from index of Scheme in array form
     * @param indexArrayPosition index of the Scheme in array form
     */
    public Position (int indexArrayPosition){
        this.row = indexArrayPosition / 5;
        this.col = indexArrayPosition % 5;
    }

    /**
     * Sets row position
     * @param row sets the row of the Scheme in matrix form
     */
    public void setRow(int row) {
        if(row < 0 || row > 3){
            throw new IllegalArgumentException("ERROR: Cannot set a row not in the range permitted");
        }
        this.row = row;
    }

    /**
     * Sets column position
     * @param col sets the col of the Scheme in matrix form
     */
    public void setCol(int col) {
        if(col < 0 || col > 4) {
            throw new IllegalArgumentException("ERROR: Cannot set a col not in the range permitted");
        }
        this.col = col;
    }

    /**
     * Sets row and col calculated started from indexArrayPosition
     * @param indexArrayPosition
     */
    public void setRowCol (int indexArrayPosition){
        if(indexArrayPosition < 0 || indexArrayPosition > 19){
            throw new IllegalArgumentException("ERROR: indexArrayPosition insert is not in the range permitted");
        }
        this.row = indexArrayPosition / 5;
        this.col = indexArrayPosition % 5;
    }

    /**
     * Gets row position saved
     * @return row of the Scheme in matrix form
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets column position saved
     * @return Column of the Scheme in matrix form
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets row position from array index
     * @param indexArrayPosition index of the Scheme in array form
     * @return the row to which it belongs
     */
    public int getRow (int indexArrayPosition){
        return indexArrayPosition / 5;
    }

    /**
     * Gets column position from array index
     * @param indexArrayPosition index of the Scheme in array form
     * @return the col to which it belongs
     */
    public int getCol (int indexArrayPosition){
        return indexArrayPosition % 5;
    }

    /**
     * Gets array index from saved position
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

    /**
     * Gets a clone of Position
     * @return Cloned Position
     */
    public Position getClone(){
        return new Position(this);
    }
}