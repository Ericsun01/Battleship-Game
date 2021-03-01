package edu.duke.js895.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_checkPlacement() {
    InBoundsRuleChecker<Character> ib = new InBoundsRuleChecker<Character>(null);
    Board<Character> b = new BattleShipBoard<Character>(4, 6, ib, 'X');
    V1ShipFactory f = new V1ShipFactory();
    Placement v0_0 = new Placement(new Coordinate(0, 0), 'V');
    Ship<Character> ship1 = f.makeCarrier(v0_0);
    Placement v3_0 = new Placement(new Coordinate(-3, 0), 'V');
    Ship<Character> ship5_false = f.makeCarrier(v3_0);
    Placement h0_3 = new Placement(new Coordinate(0, 3), 'h');
    Ship<Character> ship2_false = f.makeCarrier(h0_3);
    Placement v5_0 = new Placement(new Coordinate(5, 0), 'v');
    Ship<Character> ship3_false = f.makeCarrier(v5_0);
    Placement v2_4 = new Placement(new Coordinate(2, -4), 'h');
    Ship<Character> ship4_false = f.makeCarrier(v2_4);

    
    assertEquals(null, ib.checkPlacement(ship1, b));
    assertEquals("That placement is invalid: the ship goes off the top of the board.", ib.checkPlacement(ship5_false, b)); // off the top
    assertEquals("That placement is invalid: the ship goes off the right of the board.", ib.checkPlacement(ship2_false, b)); // rightbound outrange
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.", ib.checkPlacement(ship3_false, b)); // bottombound outrange
    assertEquals("That placement is invalid: the ship goes off the left of the board.", ib.checkPlacement(ship4_false, b)); // left outrange



  }

}
