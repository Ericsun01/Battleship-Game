package edu.duke.js895.battleship;

/**
 * This class handles Placement movement by players
 * It can still be converted into string form to present
 */
public class Placement {
    /**  The coordinate players choose, the orientation they place their ships
    */ 
    private final Coordinate where;
    private final char orientation;

    /**
   * Constructs a Placement, given the coordinate, orientation player choose.
   * @param where is the coordinate 
   * @param orientation is the the orientation players choose to place
   * @throws IllegalArgumentException if the coordinate is invalid
   * @throws IllegalArgumentException if the orientation is invalid, not V or H
   */
    public Placement(Coordinate where, char orientation) {
        char ori = Character.toUpperCase(orientation);
        if (where == null) {
            //throw new IllegalArgumentException("Invalid placement where"); 
            throw new IllegalArgumentException(); 
        }

        // if (ori != 'H' && ori != 'V') {
        //     throw new IllegalArgumentException("Invalid placement orientation");
        // }

        this.where = where;
        this.orientation = ori;
    }

    /**
   * Constructs a Placement, given the string input.
   * @param descr is the raw format of player's placement 
   * @throws IllegalArgumentException if the input's length is not match 
   * @throws IllegalArgumentException if the orientation is invalid, not V or H
   */
    public Placement(String place) throws IllegalArgumentException {
        if (place == null || place.length() != 3) {
            //throw new IllegalArgumentException("That placement is invalid: it does not have the correct format");
            throw new IllegalArgumentException();
        }

        String descr = place.toUpperCase().substring(0, 2);
        char orientation = place.toUpperCase().charAt(2);
        
        // have been checked in creatship
        // if (orientation != 'H' && orientation != 'V') {
        //     throw new IllegalArgumentException("That placement is invalid: it does not have the correct format");
        // }
        

        this.where = new Coordinate(descr);       
        this.orientation = orientation;
    
    }

    public Coordinate getWhere() {
        return this.where;
    }

    public char getOrientation() {
        return this.orientation;
    }

   /**
 * To judge whether two object are equal, compare their coordinate field and orientation field
 */
    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            Placement p = (Placement) o;
            return where.equals(p.where) && orientation == p.orientation;
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + where.getRow() + ", " + where.getColumn() + ", " + orientation + ")";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
