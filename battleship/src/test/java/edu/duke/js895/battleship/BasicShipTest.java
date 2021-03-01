package edu.duke.js895.battleship;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

public class BasicShipTest {
  // @Test
  // public void test_construct_coordinate() {
  //   Coordinate c1 = null;
  //   Coordinate c2 = new Coordinate("A0");
  //   RectangleShip<Character> b2 = new RectangleShip<Character>(c2, 's', '*');
  //   HashMap<Coordinate, Boolean> expected = new HashMap<Coordinate, Boolean>();
  //   expected.put(new Coordinate("A0"), false);
  //   assertThrows(IllegalArgumentException.class, ()->new RectangleShip<Character>(c1, 's', '*'));
  //   assertEquals(expected, b2.myPieces); 
  // }

  @Test
  public void test_construct_Iterable() {
    Coordinate upperLeft = new Coordinate("A0");
    RectangleShip<Character> b = new RectangleShip<Character>("destroyer", upperLeft, 2, 2, 's','*');
    HashMap<Coordinate, Boolean> expected = new HashMap<Coordinate, Boolean>();
    expected.put(new Coordinate("A0"), false);
    expected.put(new Coordinate("A1"), false);
    expected.put(new Coordinate("B0"), false);
    expected.put(new Coordinate("B1"), false);
    assertEquals(expected, b.myPieces);      
  }

  @Test
  public void test_occupiesCoordinates() {
    Coordinate c = new Coordinate("A0");
    RectangleShip<Character> b = new RectangleShip<Character>(c, 's', '*');
    assertEquals(true, b.occupiesCoordinates(new Coordinate("A0")));
  }


}
