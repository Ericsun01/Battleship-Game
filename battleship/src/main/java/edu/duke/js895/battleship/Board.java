package edu.duke.js895.battleship;

public interface Board <T> {
    public int getWidth();
    public int getHeight();

         /**
 * If the ship can be added to BattleShipBoard object(this)
 * @param toAdd is the the ship we try to add
 * @return if we add the ship successfully
 */
    public String tryAddShip(Ship<T> toAdd);

    
     /**
 * To examin whether and what ship occupy a specific location 
 * @param where is the coordinate that we want to check its display content
 * @return the content on this location, from my own perspective
 */
    public T whatIsAtForSelf(Coordinate where);

   /**
 * To examin whether and what ship occupy a specific location 
 * @param where is the coordinate that we want to check its display content
 * @return the content on this location, from enemy's perspective
 */
    public T whatIsAtForEnemy (Coordinate where);
    /**
 * Fire at a location, return the hit ship if exist, or add the fire location into enemyMisses, and return null
 * @param c is thecoordinate that we want to fire at
 * @return the ship being hit/null
 */
    public Ship<T> fireAt(Coordinate c);

    /** V2
 * User input a location, return the ship if there exist, or return null if no current player's ship at that location
 * @param c is thecoordinate that player want to select
 * @return the ship being selected/null
 */
    public Ship<T> selectShip(Coordinate c);

    /**
 * detect if the owner of this board lose the game: all his ships sink
 * @return the result of this check
 */
    public boolean loseGame();

    public String moveShip(Ship<T> toMove, Ship<T> testShip);

    public String validatePlacement(Ship<T> toAdd);
}
