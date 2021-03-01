package edu.duke.js895.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2ShipFactoryTest {
  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter, Coordinate... expectedLocs) {
    assertEquals(expectedName, testShip.getName());
    for (Coordinate c : expectedLocs) {
      assertEquals(true, testShip.occupiesCoordinates(c));
      assertEquals(expectedLetter, testShip.getDisplayInfoAt(c, true));
    }
  }

  @Test
  public void test_invalid_orientation() {
    V2ShipFactory f = new V2ShipFactory();
    Placement o1_2 = new Placement(new Coordinate(1,2), 'O');
    Placement u1_2 = new Placement(new Coordinate(1,2), 'u');
    assertThrows(IllegalArgumentException.class, () -> {f.makeDestroyer(u1_2);});
    assertThrows(IllegalArgumentException.class, () -> {f.makeBattleship(o1_2);});
  }

  @Test
  public void test_createSubmarine() {
    V2ShipFactory f = new V2ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1,2), 'v');
    Placement h1_2 = new Placement(new Coordinate(1,2), 'h');
    Ship<Character> sbm = f.makeSubmarine(v1_2);
    Ship<Character> sbmm = f.makeSubmarine(h1_2);
    checkShip(sbm, "Submarine", 's', new Coordinate(1, 2), new Coordinate(2, 2));
    checkShip(sbmm, "Submarine", 's', new Coordinate(1, 2), new Coordinate(1, 3));
  }

  @Test
  public void test_createDestroyer() {
    V2ShipFactory f = new V2ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1,2), 'v');
    Ship<Character> des = f.makeDestroyer(v1_2);
    checkShip(des, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));
  }

  @Test
  public void test_createBattleShip() {
    V2ShipFactory f = new V2ShipFactory();
    Placement u0_0 = new Placement(new Coordinate(0,0), 'u');
    Ship<Character> bat = f.makeBattleship(u0_0);
    checkShip(bat, "Battleship", 'b', new Coordinate(0, 1), new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(1, 2));
  }

  @Test
  public void test_createCarrier() {
    V2ShipFactory f = new V2ShipFactory();
    Placement r0_0 = new Placement(new Coordinate(0,0), 'r');
    Ship<Character> car = f.makeCarrier(r0_0);
    checkShip(car, "Carrier", 'c', new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(1, 2), new Coordinate(0, 2), new Coordinate(0, 3), new Coordinate(0, 4));
  }


}
