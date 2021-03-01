package edu.duke.js895.battleship;

/**
 * This class handles Coordinate of
 * a position on board (i.e., converting it to a string to show
 * to the user).
 */
public class Coordinate {
    /**  The row, column of this coordination
    */ 
    private final int row;
    private final int column;

/**
   * Constructs a Coordinate, given the coordinate player choose.
   * @param row is the row 
   * @param column is the column
   * @throws IllegalArgumentException if the coordinate is outside 10X26
   */
    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
        // if (row > 26 || column > 10 ) {
        //     throw new IllegalArgumentException(
        //         "Coordinate must be within 10x26, but is " + column + "x" + row);
        //   }
    }

      /**
   * Constructs a Coordinate, given the coordinate player choose.
   * @param descr is the raw format of coordinate 
   * @throws IllegalArgumentException if the input's length is not match 
   * @throws IllegalArgumentException if the input's first letter is beyond the 26 letters
   * @throws IllegalArgumentException if the input's second letter/num is not from 0 - 9  
   */
    
    public Coordinate(String descr) throws IllegalArgumentException {
        if (descr == null || descr.length() != 2) {
            //throw new IllegalArgumentException("Invalid coordinate input length");
            throw new IllegalArgumentException();
        }

        char rowLetter = descr.toUpperCase().charAt(0);
        char columnNum = descr.charAt(1);
        
        if (rowLetter < 'A' || rowLetter > 'Z') {
            //throw new IllegalArgumentException("Invalid coordinate row letter");
            throw new IllegalArgumentException();
        } 
        if (columnNum < '0' || columnNum > '9') {
            //throw new IllegalArgumentException("Invalid coordinate column number");
            throw new IllegalArgumentException();
        }

        this.row = rowLetter - 'A';
        this.column = columnNum - '0';
    } 

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    /**
 * To judge whether two object are equal, compare their row and column fields
 */
    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            Coordinate c = (Coordinate) o;
            return row == c.row && column == c.column;
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
