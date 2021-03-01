package edu.duke.js895.battleship;

public class V2ShipFactory implements AbstractShipFactory<Character>{

          /**
   * Make a rectangle ship based on request.
   * To be specific: submarine and destroyer
   * @param where specifies the location and orientation of the ship to make
   * @param w specifies the width (horizontally) of ship
   * @param h specifies the height (vertically) of ship
   * @param letter specifies the represent name of ship
   * @param name specifies the formal name of ship
   * @return the Ship created
   */
    protected Ship<Character> createRectangleShip(Placement where, char letter, String name) {
        Coordinate upperLeft = where.getWhere();
        char orientation = where.getOrientation();

        if (orientation != 'H' && orientation != 'V') {
            throw new IllegalArgumentException();
        } 
        return new ComplexShip<Character>(name, upperLeft, orientation, letter, '*');
    }

    protected Ship<Character> createNonRectangleShip(Placement where, char letter, String name) {
        Coordinate upperLeft = where.getWhere();
        char orientation = where.getOrientation();
        if (orientation != 'U' && orientation != 'R' && orientation != 'D' && orientation != 'L') {
            throw new IllegalArgumentException();
        }
        return new ComplexShip<Character>(name, upperLeft, orientation, letter, '*');
    }

     /**
   * Make a submarine.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the submarine.
   */
  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    return createRectangleShip(where, 's', "Submarine");
  }

  /**
   * Make a battleship.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the battleship.
   */
  @Override
  public Ship<Character> makeBattleship(Placement where) {
    return createNonRectangleShip(where, 'b', "Battleship");
  }

  /**
   * Make a carrier.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the carrier.
   */
  @Override
  public Ship<Character> makeCarrier(Placement where) {
    return createNonRectangleShip(where, 'c', "Carrier");
  }

  /**
   * Make a destroyer.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the destroyer.
   */
  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createRectangleShip(where, 'd', "Destroyer");
  }
}
