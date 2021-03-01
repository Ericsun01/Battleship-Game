package edu.duke.js895.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    NoCollisionRuleChecker<Character> nc = new NoCollisionRuleChecker<Character>(null);
    Board<Character> b = new BattleShipBoard<Character>(4, 6, nc, 'X');
    V1ShipFactory f = new V1ShipFactory();
    Placement v0_3 = new Placement(new Coordinate(0, 3), 'V');
    Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
    Ship<Character> ship1 = f.makeBattleship(v0_3);
    Ship<Character> ship2 = f.makeSubmarine(v1_2);
    b.tryAddShip(ship1);
    b.tryAddShip(ship2);

    Placement v0_0 = new Placement(new Coordinate(0, 0), 'V');
    Ship<Character> test1 = f.makeCarrier(v0_0);
    assertEquals(null, nc.checkPlacement(test1, b));

    Placement h0_0 = new Placement(new Coordinate(0, 0), 'H');
    Ship<Character> test2 = f.makeBattleship(h0_0);
    assertEquals("That placement is invalid: the ship overlaps another ship.", nc.checkPlacement(test2, b));

    Placement v0_2 = new Placement(new Coordinate(0, 2), 'V');
    Ship<Character> test3 = f.makeCarrier(v0_2);
    assertEquals("That placement is invalid: the ship overlaps another ship.", nc.checkPlacement(test3, b));
  }

  @Test
  public void test_CheckPlacement() {
    NoCollisionRuleChecker<Character> nc = new NoCollisionRuleChecker<Character>(null);
    InBoundsRuleChecker<Character> ib = new InBoundsRuleChecker<Character>(nc);
    Board<Character> b = new BattleShipBoard<Character>(4, 6, nc, 'X');
    V1ShipFactory f = new V1ShipFactory();
    Placement v0_3 = new Placement(new Coordinate(0, 3), 'V');
    Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
    Ship<Character> ship1 = f.makeBattleship(v0_3);
    Ship<Character> ship2 = f.makeSubmarine(v1_2);
    b.tryAddShip(ship1);
    b.tryAddShip(ship2);

    
    Placement v0_0 = new Placement(new Coordinate(0, 0), 'V');  // all good
    Ship<Character> test1 = f.makeCarrier(v0_0);
    assertEquals(null, ib.checkPlacement(test1, b));

    
    Placement h4_0 = new Placement(new Coordinate(4, 0), 'H');  // out of bound
    Ship<Character> test2 = f.makeCarrier(h4_0);
    assertEquals("That placement is invalid: the ship goes off the right of the board.", ib.checkPlacement(test2, b));

    
    Placement h1_1 = new Placement(new Coordinate(1, 1), 'H');  // collision
    Ship<Character> test3 = f.makeDestroyer(h1_1);
    assertEquals("That placement is invalid: the ship overlaps another ship.", ib.checkPlacement(test3, b));

    // in this test, first check inBound, than collison
    Placement h2_0 = new Placement(new Coordinate(2, 0), 'H');  // both rule violated
    Ship<Character> test4 = f.makeCarrier(h2_0);
    assertEquals("That placement is invalid: the ship goes off the right of the board.", ib.checkPlacement(test4, b));

  }

}
