package edu.duke.js895.battleship;

/**
 * This class handles how to display the current status of ship
 */
public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T>{
    /**  The display of unhit/ hit status
    */ 
    T myData;  // currently , it is 's'
    T onHit; // currently, it is '*'
    
    /**
   * Constructs a SimpleShipDisplayInfo, provides the way to display ship's status.
   * @param myData is the unhit version 
   * @param onHit is the hit version
   */
    public SimpleShipDisplayInfo(T myData, T onHit) {
        this.myData = myData;
        this.onHit = onHit;
    }

    /**
   * Get the display info of this ship
   * @param where is the location of this ship 
   * @param onHit is the whether the ship is hit
   * @return the display info of this ship 
   */
    public T getInfo(Coordinate where, boolean hit) { // where?
        if (hit) {
            return onHit;
        } 
        return myData;
    }
}
