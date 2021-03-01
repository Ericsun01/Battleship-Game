package edu.duke.js895.battleship;

/**
 * This class check whether a ship on a specific Board doesn't collide with other ships 
 */
public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
     /**
   * Constructs a NoCollisionRuleChecker with the a ref to next PlacementRuleChecker(chain logic)
   * @param next is a ref of other PlacementRuleChecker
   */
    public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

     /**
 * To check each coordinate this ship occupy, test if it is collide with other ships
 * @param theShip is the ship to check
 * @param theBoard is the board for check
 * @return null when it pass the check, return string for error message
 */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> shipOccupy = theShip.getCoordinates();
    for (Coordinate c: shipOccupy) {
        if (theBoard.whatIsAtForSelf(c) != null) {
            return "That placement is invalid: the ship overlaps another ship.";
        }
    }
    return null;
  }

}
