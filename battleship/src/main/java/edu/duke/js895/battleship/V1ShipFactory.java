package edu.duke.js895.battleship;

public class V1ShipFactory implements AbstractShipFactory<Character>{
        /**
   * Make a ship based on request.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @param w specifies the width (horizontally) of ship
   * @param h specifies the height (vertically) of ship
   * @param letter specifies the represent name of ship
   * @param name specifies the formal name of ship
   * @return the Ship created for the submarine.
   */

   // createShip takes w and h assuming a vertical orientation.  It should
   // check for horizontal orientation and reverse those values
    // Submarine:   1x2 's'
    // Destroyer:   1x3 'd'
    // Battleship:  1x4 'b'
    // Carrier:     1x6 'c'


    protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
        Coordinate upperLeft = where.getWhere();
        char orientation = where.getOrientation();

        if (orientation != 'H' && orientation != 'V') {
            throw new IllegalArgumentException();
        } 
        if (orientation == 'H') {
            return new RectangleShip<Character>(name, upperLeft, h, w, letter, '*');
        }
        return new RectangleShip<Character>(name, upperLeft, w, h, letter, '*');
    }

        /**
   * Make a submarine.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the submarine.
   */
  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    return createShip(where, 1, 2, 's', "Submarine");
  }

  /**
   * Make a battleship.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the battleship.
   */
  @Override
  public Ship<Character> makeBattleship(Placement where) {
    return createShip(where, 1, 4, 'b', "Battleship");
  }

  /**
   * Make a carrier.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the carrier.
   */
  @Override
  public Ship<Character> makeCarrier(Placement where) {
    return createShip(where, 1, 6, 'c', "Carrier");
  }

  /**
   * Make a destroyer.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the destroyer.
   */
  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createShip(where, 1, 3, 'd', "Destroyer");
  }
}
