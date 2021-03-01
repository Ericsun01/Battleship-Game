package edu.duke.js895.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_constructor_input() {
    Coordinate c1 = new Coordinate("A1");
    Placement p = new Placement(c1, 'h');
    assertEquals(c1, p.getWhere());
    assertEquals('H', p.getOrientation());
  } 

  @Test
  public void test_constructor_invalid_input() {
    Coordinate c1 = null;
    assertThrows(IllegalArgumentException.class, ()->new Placement(c1, 'V'));
  }

  @Test
  public void test_string_constructor_valid_cases() {
    Placement p1 = new Placement("A0V");
    assertEquals('V', p1.getOrientation());
    Placement p2 = new Placement("B3h");
    assertEquals('H', p2.getOrientation());
    Placement p3 = new Placement("c9h");
    assertEquals('H', p3.getOrientation());
  }

  @Test
  public void test_string_constructor_invalid_cases() {
    assertThrows(IllegalArgumentException.class, () -> new Placement(""));  // empty input
    assertThrows(IllegalArgumentException.class, () -> new Placement("AAAAAAAAAA")); // beyond restricted length
  }

  @Test
  public void test_equals() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(1, 3);
    Coordinate c4 = new Coordinate(3, 2);
    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c2, 'V');
    Placement p3 = new Placement(c3, 'v');
    Placement p4 = new Placement(c1, 'h');
    Placement p5 = new Placement(c4, 'h');
    assertEquals(p1, p2);   //case insensitive
    assertEquals(p1, p1);   //equals should be reflected
    assertNotEquals(p1, p3);  //different contents
    assertNotEquals(p1, p4);
    assertNotEquals(p1, p5);
    assertNotEquals(p5, "(3, 2, H)"); // different type
  }

  @Test
  public void test_toString() {
    Placement p1 = new Placement("A0v");
    Placement p2 = new Placement("z5h");
    assertEquals("(0, 0, V)", p1.toString());
    assertEquals("(25, 5, H)", p2.toString());
    assertNotEquals("(26, 5, H)", p2.toString());
  }

  @Test
  public void test_hashCode() {
    Placement p1 = new Placement("A0v");
    Placement p2 = new Placement("A0v");
    Placement p3 = new Placement("C1H");
    Placement p4 = new Placement("D7V"); 
    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p1.hashCode(), p3.hashCode());
    assertNotEquals(p1.hashCode(), p4.hashCode());
  }
}
