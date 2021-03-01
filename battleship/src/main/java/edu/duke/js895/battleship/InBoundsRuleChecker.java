package edu.duke.js895.battleship;

/**
 * This class check whether any part of a ship on a specific Board is out of bound
 */
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T>{
    // call the constructor of parent class
  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
  

  /**
 * To check each coordinate this ship occupy, test if it is out of bound
 * @param theShip is the ship to check
 * @param theBoard is the board for check
 * @return null when it pass the check, return string for error message
 */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> shipOccupy = theShip.getCoordinates();
    int boardWidth = theBoard.getWidth();
    int boardHeight = theBoard.getHeight();
    
    for (Coordinate c: shipOccupy) {
        int row = c.getRow();
        int col = c.getColumn();
        if (row < 0 ) {
            return "That placement is invalid: the ship goes off the top of the board.";
        } 
        
        if (row >= boardHeight) {
            return "That placement is invalid: the ship goes off the bottom of the board.";
        }

        if (col < 0) {
            return "That placement is invalid: the ship goes off the left of the board.";
        }

        if (col >= boardWidth) {
            return "That placement is invalid: the ship goes off the right of the board.";
        }
    }
    return null;
  }

}
