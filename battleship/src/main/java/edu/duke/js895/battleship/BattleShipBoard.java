package edu.duke.js895.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
/**
 * This class stores the attributes of
 * a Board 
 * It supports adding the ships and examinng
 *  whether and what ship occupy a specific location 
 */
public class BattleShipBoard<T> implements Board<T> {
  /**  The width, height of this Board
   *   list of ships in this Board
    */
  private final int width;
  private final int height;
  final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private HashSet<Coordinate> enemyMisses;
  final T missInfo;
  private HashMap<Coordinate, T> enemyHit; // coordinate and corresponding hit/miss info


  /**
   * Constructs a BattleShipBoard with the specified width
   * and height and rule checker
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @param placementChecker placementChecker is the rule checker(logic chain) we want to pass in
   * @throws IllegalArgumentException if the width or height are less than or equal to zero.
   */

  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementChecker, T missInfo) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    } 
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }

    this.width = w;
    this.height = h;
    this.myShips = new ArrayList<Ship<T>>();
    this.placementChecker = placementChecker;
    this.enemyMisses = new HashSet<Coordinate>();
    this.missInfo = missInfo;
    this.enemyHit = new HashMap<Coordinate, T>();
  }

   /**
   * Constructs a BattleShipBoard with the specified width and height, it will pass 
   * a default chain of checker to constructor 
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or equal to zero.
   */
  public BattleShipBoard(int w, int h, T missInfo) {  // check for if a ship surpass boundary 
    this(w, h, new NoCollisionRuleChecker<T>(new InBoundsRuleChecker<T>(null)), missInfo);
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

     /**
 * If the ship can be added to BattleShipBoard object(this)
 * @param toAdd is the the ship we try to add
 * @return if we add the ship successfully
 */
  public String tryAddShip(Ship<T> toAdd) { 
    String checkResult = placementChecker.checkPlacement(toAdd, this);
    if (checkResult == null) {
      myShips.add(toAdd);
      return null;
    }
    return checkResult;
  }

     /**
 * To examin whether and what ship occupy a specific location 
 * @param where is the coordinate that we want to check its display content
 * @return the content on this location, from my own perspective
 */

  public T whatIsAtForSelf (Coordinate where) {
    return whatIsAt(where, true);
  }

      /**
 * To examin whether and what ship occupy a specific location 
 * @param where is the coordinate that we want to check its display content
 * @return the content on this location, from enemy's perspective
 */
  public T whatIsAtForEnemy (Coordinate where) {
    return whatIsAt(where, false);
  }

  /**
  * To examin whether and what ship occupy a specific location 
  * @param where is the coordinate that we want to check its display content
  * @param isSelf shows if current caller of this function is self player 
  * @return display info, missInfo or null based on conditions
  */
  protected T whatIsAt(Coordinate where, boolean isSelf) {
    if (!isSelf) {
      if (enemyMisses.contains(where)) {
        return missInfo;
      }

      if (enemyHit.containsKey(where)) {
        return enemyHit.get(where);
      }
    }
    for (Ship<T> s: myShips) {
      if (s.occupiesCoordinates(where)){
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    return null;
    // if (isSelf == false && enemyMisses.contains(where)) {
    //   return missInfo;
    // } 
  }

  /**
 * Fire at a location, return the hit ship if exist, or add the fire location into enemyMisses, and return null
 * @param c is thecoordinate that we want to fire at
 * @return the ship being hit/null
 */
  public Ship<T> fireAt(Coordinate c) {
    for (Ship<T> s: myShips) {
        if (s.occupiesCoordinates(c)) {
          s.recordHitAt(c);
          enemyHit.put(c, s.getDisplayInfoAt(c, false));
          enemyMisses.remove(c);
          return s;
        }
    }
    enemyMisses.add(c);
    enemyHit.remove(c);
    return null;
  }

  /** V2
 * User input a location, return the ship if there exist, or return null if no current player's ship at that location
 * @param c is thecoordinate that player want to select
 * @return the ship being selected/null
 */
  public Ship<T> selectShip(Coordinate c) {
    for (Ship<T> s: myShips) {
      if (s.occupiesCoordinates(c)) {
        return s;
      }
    }
    return null;
  }

/**
 * move the current ship to a position of a testShip
 * @return the result of this check
 */
  public String moveShip(Ship<T> toMove, Ship<T> testShip) {
    myShips.remove(toMove);
    String checkResult = placementChecker.checkPlacement(testShip, this);
    if (checkResult == null) {
      toMove.shipMoveTo(testShip);
      myShips.add(toMove);
      return null;
    }
    myShips.add(toMove);
    return checkResult;
  }

  /**
 * detect if the owner of this board lose the game: all his ships sink
 * @return the result of this check
 */
  public boolean loseGame() {
    boolean result = true;
    for (Ship<T> s: myShips) {
      result = result && s.isSunk();
    }
    return result;
  }

  public String validatePlacement(Ship<T> tocheck) {
    String checkResult = placementChecker.checkPlacement(tocheck, this);
    if (checkResult == null) {
      return null;
    }
    return checkResult;
  }
}
