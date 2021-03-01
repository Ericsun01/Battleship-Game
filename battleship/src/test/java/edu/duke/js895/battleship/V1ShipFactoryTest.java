package edu.duke.js895.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter, Coordinate... expectedLocs) {
    assertEquals(expectedName, testShip.getName());
    for (Coordinate c : expectedLocs) {
      assertEquals(true, testShip.occupiesCoordinates(c));
      assertEquals(expectedLetter, testShip.getDisplayInfoAt(c, true));
    }
  }

  @Test
  public void test_invalid_orientation() {
    V1ShipFactory f = new V1ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1,2), 'G');
    assertThrows(IllegalArgumentException.class, () -> {f.makeSubmarine(v1_2);});
  }

  @Test
  public void test_createSubmarine() {
    V1ShipFactory f = new V1ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1,2), 'v');
    Ship<Character> sbm = f.makeSubmarine(v1_2);
    checkShip(sbm, "Submarine", 's', new Coordinate(1, 2), new Coordinate(2, 2));
  }

  @Test
  public void test_createBattleShip() {
    V1ShipFactory f = new V1ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1,2), 'v');
    Ship<Character> sbm = f.makeBattleship(v1_2);
    checkShip(sbm, "Battleship", 'b', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2), new Coordinate(4, 2));
  }

  @Test
  public void test_createDestroyer() {
    V1ShipFactory f = new V1ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1,2), 'v');
    Ship<Character> sbm = f.makeDestroyer(v1_2);
    checkShip(sbm, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));
  }

  @Test
  public void test_createCarrier() {
    V1ShipFactory f = new V1ShipFactory();
    Placement v0_0 = new Placement(new Coordinate(0, 0), 'h');
    Ship<Character> sbm = f.makeCarrier(v0_0);
    checkShip(sbm, "Carrier", 'c', new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(0, 2), new Coordinate(0, 3), new Coordinate(0, 4), new Coordinate(0, 5));
  }
}
