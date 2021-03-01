package edu.duke.js895.battleship;

import java.util.ArrayList;

/**
 * This class behave as complex ship
 */
public class ComplexShip<T> extends BasicShip<T> {
    // this field stores name of this ship
    final String name;

    /**
   * Constructs a RectangleShip, use parent class's constructor.
   * @param upperLeft ship's upperLeft location 
   * @param name is ship's name
   * @param orientation is the orientation of this ship
   * @param myDisplayInfo is the display method of my ship's status
   * @param enemyDisplayInfo is the display method of enemy's ship's status
   */
    public ComplexShip(String name, Coordinate upperLeft, char orientation, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {      
        super(takeCoords(upperLeft, name, orientation), myDisplayInfo, enemyDisplayInfo);
        this.name = name;
    }

         /**
   * we will tell the paraent constructor that for my own view display:
   * data if not hit, onHit if hit
   * for the enemy view: 
   * nothing if not hit, data if hit
   * @param upperLeft ship's upperLeft location 
   * @param name is ship's name
   * @param ori is the orientation of this ship
   * @param data is display content for notHit
   * @param onHit is display content for hit
   */
    public ComplexShip(String name, Coordinate upperLeft, char ori, T data, T onHit) {
        this(name, upperLeft, ori, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
    }

       /**
   * makeCoords for a RectangleShip, add all coordinates that the ship owns.
   * @param upperLeft ship's upperLeft location 
   * @param name is ship's name
   * @param orientation is the orientation of this ship
   * @return the an arraylist represent mapping between index of ships' piece and coordinates
   */
    static ArrayList<Coordinate> takeCoords(Coordinate upperLeft, String name, char orientation) {
        ArrayList<Coordinate> ans = new ArrayList<>();
        int startRow = upperLeft.getRow();
        int startColumn = upperLeft.getColumn();
        
        if (name == "Battleship") {  
            if (orientation == 'U') {
                ans.add(new Coordinate(startRow, startColumn+1));
                for (int i=0; i<3; i++) {
                    ans.add(new Coordinate(startRow+1, startColumn+i));
                }
            }

            if (orientation == 'R') {
                ans.add(new Coordinate(startRow+1, startColumn+1));
                for (int i=0; i<3; i++) {
                    ans.add(new Coordinate(startRow+i, startColumn));
                }
            }

            if (orientation == 'D') {
                ans.add(new Coordinate(startRow+1, startColumn+1));
                for (int i=0; i<3; i++) {
                    ans.add(new Coordinate(startRow, startColumn+2-i));
                }
            }

            if (orientation == 'L') {
                ans.add(new Coordinate(startRow+1, startColumn));
                for (int i=0; i<3; i++) {
                    ans.add(new Coordinate(startRow+2-i, startColumn+1));
                }
            }
        }

        if (name == "Carrier") {
            if (orientation == 'U') {
                for (int i=0; i<3; i++) {
                    ans.add(new Coordinate(startRow+i, startColumn));
                }
                for (int j=2; j<5; j++) {
                    ans.add(new Coordinate(startRow+j, startColumn+1));
                }
            }

            if (orientation == 'R') {
                for (int i=0; i<3; i++) {
                    ans.add(new Coordinate(startRow+1, startColumn+i));
                }
                for (int j=2; j<5; j++) {
                    ans.add(new Coordinate(startRow, startColumn+j));
                }
            }

            if (orientation == 'D') {
                for (int i=0; i<3; i++) {
                    ans.add(new Coordinate(startRow+4-i, startColumn));
                }
                for (int j=0; j<3; j++) {
                    ans.add(new Coordinate(startRow+2-j, startColumn+1));
                }
            }

            if (orientation == 'L') {
                for (int i=0; i<3; i++) {
                    ans.add(new Coordinate(startRow+1, startColumn+4-i));
                }
                for (int j=0; j<3; j++) {
                    ans.add(new Coordinate(startRow, startColumn+2-j));
                }
            }        
        }

        if (name == "Submarine") {
            if (orientation == 'H') {
                ans.add(new Coordinate(startRow, startColumn));
                ans.add(new Coordinate(startRow, startColumn+1));
            }
            if (orientation == 'V') {
                ans.add(new Coordinate(startRow, startColumn));
                ans.add(new Coordinate(startRow+1, startColumn));
            }
        }

        if (name == "Destroyer") {
            if (orientation == 'H') {
                ans.add(new Coordinate(startRow, startColumn));
                ans.add(new Coordinate(startRow, startColumn+1));
                ans.add(new Coordinate(startRow, startColumn+2));
            }
            if (orientation == 'V') {
                ans.add(new Coordinate(startRow, startColumn));
                ans.add(new Coordinate(startRow+1, startColumn));
                ans.add(new Coordinate(startRow+2, startColumn));
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
