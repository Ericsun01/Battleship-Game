package edu.duke.js895.battleship;

import java.util.HashSet;
/**
 * This class behave as rectangle ship
 */
public class RectangleShip<T> extends BasicShip<T> {
    // this field stores name of this ship
    final String name;
    /**
   * Constructs a RectangleShip, use parent class's constructor.
   * @param upperLeft ship's upperLeft location 
   * @param width is the width of this ship
   * @param height is the height of this ship
   * @param myDisplayInfo is the display method of my ship's status
   * @param enemyDisplayInfo is the display method of enemy's ship's status
   */
    public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        super(makeCoords(upperLeft, width, height), myDisplayInfo, enemyDisplayInfo);
        this.name = name;
    }

     /**
   * we will tell the paraent constructor that for my own view display:
   * data if not hit, onHit if hit
   * for the enemy view: 
   * nothing if not hit, data if hit
   * @param upperLeft ship's upperLeft location 
   * @param width is the width of this ship
   * @param height is the height of this ship
   * @param data is display content for notHit
   * @param onHit is display content for hit
   */
    public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
        this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
    }

    public RectangleShip(Coordinate upperLeft, T data, T onHit) {
        this("testship", upperLeft, 1, 1, data, onHit);
    }

    /**
   * makeCoords for a RectangleShip, add all coordinates that the ship owns.
   * @param upperLeft ship's upperLeft location 
   * @param width is the width of this ship
   * @param height is the height of this ship
   * @return the set of coordinates
   */
    static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
        HashSet<Coordinate> ans = new HashSet<Coordinate>();
        int startRow = upperLeft.getRow();
        int startColumn = upperLeft.getColumn();
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                ans.add(new Coordinate(startRow+i, startColumn+j));
            }
        }
        return ans;
    }

    /**
     * return the name of this ship
     */
    public String getName() {
        return this.name;
    }
}
