package edu.duke.js895.battleship;

/**
 * This class check whether a ship on a specific Board fulfill specific requirement 
 */
public abstract class PlacementRuleChecker<T> {
   /**  next is the ref of other PlacementRuleChecker, used for recursion call
    */
    private final PlacementRuleChecker<T> next; 

     /**
   * Constructs a PlacementRuleChecker with the a ref to next PlacementRuleChecker(chain logic)
   * @param next is a ref of other PlacementRuleChecker
   */

    public PlacementRuleChecker(PlacementRuleChecker<T> next) {
        this.next = next;
    }

    protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);

      /**
   * check if the ship doesn't follow placement rules by recursively calling all rule-checking methods
   * @param theShip is the ship to check
   * @param theBoard is the board for check
   * @return null if it pass the check, return string for error message
   */
    public String checkPlacement (Ship<T> theShip, Board<T> theBoard) {
        //if we fail our own rule: stop the placement is not legal
        String checkResult = checkMyRule(theShip, theBoard);
        if (checkResult!=null) {
          return checkResult;
        }
        //other wise, ask the rest of the chain.
        if (next != null) {
          return next.checkPlacement(theShip, theBoard); 
        }
        //if there are no more rules, then the placement is legal
        return null;
      }
    
}
