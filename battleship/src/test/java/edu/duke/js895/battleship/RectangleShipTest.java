package edu.duke.js895.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.HashSet;


import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_constructor() {
    RectangleShip<Character> rts = new RectangleShip<Character>("destroyer", new Coordinate("A0"), 2, 3, 's', '*');
    HashMap<Coordinate, Boolean> expected = new HashMap<Coordinate, Boolean>();
    expected.put(new Coordinate("A0"), false);
    expected.put(new Coordinate("A1"), false);
    expected.put(new Coordinate("B0"), false);
    expected.put(new Coordinate("B1"), false);
    expected.put(new Coordinate("C0"), false);
    expected.put(new Coordinate("C1"), false);
    assertEquals(expected, rts.myPieces);

  }

  @Test
  public void test_makeCoords() {
    Coordinate upperLeft = new Coordinate("A0");
    int width = 2;
    int height = 3;
    HashSet<Coordinate> ans = RectangleShip.makeCoords(upperLeft, width, height);
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate("A0"));
    expected.add(new Coordinate("A1"));
    expected.add(new Coordinate("B0"));
    expected.add(new Coordinate("B1"));
    expected.add(new Coordinate("C0"));
    expected.add(new Coordinate("C1"));
    assertEquals(expected, ans);

  }

  @Test
  public void test_recordHitAt() {
    Coordinate c1 = new Coordinate("A3");
    Coordinate c2 = new Coordinate("A1");
    Coordinate upperLeft = new Coordinate("A0");
    RectangleShip<Character> b = new RectangleShip<Character>("destroyer", upperLeft, 2, 2, 's', '*');
    assertThrows(IllegalArgumentException.class, ()->{b.recordHitAt(c1);});
    b.recordHitAt(c2);
    assertEquals(true, b.myPieces.get(c2));
  }

  @Test
  public void test_wasHitAt() {
    Coordinate c1 = new Coordinate("A3");
    Coordinate c2 = new Coordinate("A1");
    Coordinate upperLeft = new Coordinate("A0");
    RectangleShip<Character> b = new RectangleShip<Character>("destroyer", upperLeft, 2, 2, 's', '*');
    assertThrows(IllegalArgumentException.class, ()->{b.recordHitAt(c1);});
    assertEquals(false, b.wasHitAt(c2));
  }

  @Test
  public void test_isSunk() {
    Coordinate upperLeft = new Coordinate("A0");
    RectangleShip<Character> b = new RectangleShip<Character>("destroyer", upperLeft, 2, 2, 's', '*');
    assertEquals(false, b.isSunk());
    b.recordHitAt(new Coordinate("A0"));
    b.recordHitAt(new Coordinate("A1"));
    b.recordHitAt(new Coordinate("B0"));
    b.recordHitAt(new Coordinate("B1"));
    assertEquals(true, b.isSunk());
  }

  @Test
  public void test_getDisplayInfoAt() {
    Coordinate upperLeft = new Coordinate("A0");
    Coordinate c1 = new Coordinate("A1");
    RectangleShip<Character> b = new RectangleShip<Character>("destroyer", upperLeft, 2, 2, 's', '*');
    assertEquals('s', b.getDisplayInfoAt(c1, true));
    assertEquals(null, b.getDisplayInfoAt(c1, false));
    b.recordHitAt(c1);
    assertEquals('*', b.getDisplayInfoAt(c1, true));
    assertEquals('s', b.getDisplayInfoAt(c1, false));
  }

  @Test
  public void test_getName() {
    Coordinate upperLeft = new Coordinate("A0");
    RectangleShip<Character> b = new RectangleShip<Character>("destroyer", upperLeft, 2, 2, 's', '*');
    assertEquals("destroyer", b.getName());
  }

  @Test
  public void test_getCoordinates() {
    V1ShipFactory f = new V1ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1,2), 'v');
    Ship<Character> sbm = f.makeDestroyer(v1_2); // occupy (1,2), (2,2), (3,2)
    Iterable<Coordinate> list = sbm.getCoordinates();  // ?
    HashSet<Coordinate> s = new HashSet<Coordinate>();
    s.add(new Coordinate(1,2));
    s.add(new Coordinate(2,2));
    s.add(new Coordinate(3,2));
    assertEquals(s, list);

  }
}
