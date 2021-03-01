package edu.duke.js895.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X'); 
    // BattleShipBoard b1 = new BattleShipBoard(10, 20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));
  }

  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {
    // Given a BattleShipBoard first, with expected 2-D array.
    // Comparing the return value of whatIsAt() with corresponding expected's element  
    for (int i=0; i < b.getHeight(); i++) {
      for (int j=0; j < b.getWidth(); j++) {
        assertEquals(expected[i][j], b.whatIsAtForSelf(new Coordinate(i,j)));  
      }
    }
  }

  @Test
  public void test_add_ships_and_locate() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(3, 3, 'X');
    Character[][] expected1 = 
    {{null, null, null},
     {null, null, null},
     {null, null, null}
    };
    checkWhatIsAtBoard(b1, expected1);
    RectangleShip<Character> bs1 = new RectangleShip<Character>(new Coordinate("A1"), 's', '*');
    RectangleShip<Character> bs2 = new RectangleShip<Character>(new Coordinate("C0"), 's', '*');
    b1.tryAddShip(bs1); 
    b1.tryAddShip(bs2);
    Character[][] expected2 = 
    {{null, 's', null},
     {null, null, null},
     {'s', null, null}
    };
    checkWhatIsAtBoard(b1, expected2);
  } 

  @Test
  public void test_tryAddShip() {
    Board<Character> b = new BattleShipBoard<Character>(4, 6, 'X');
    V1ShipFactory f = new V1ShipFactory();
    Placement v0_3 = new Placement(new Coordinate(0, 3), 'V');
    Ship<Character> ship1 = f.makeBattleship(v0_3);
    assertEquals(null, b.tryAddShip(ship1));

    Placement v0_2 = new Placement(new Coordinate(0, 2), 'V');
    Ship<Character> ship2 = f.makeSubmarine(v0_2);
    assertEquals(null, b.tryAddShip(ship2));

    Placement v4_2 = new Placement(new Coordinate(4, 2), 'V');
    Ship<Character> ship3 = f.makeDestroyer(v4_2);
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.", b.tryAddShip(ship3));

    Placement h0_0 = new Placement(new Coordinate(0, 0), 'h');
    Ship<Character> ship4 = f.makeBattleship(h0_0);
    assertEquals("That placement is invalid: the ship overlaps another ship.", b.tryAddShip(ship4));
  }

  @Test
  public void test_fireAt() {
    Board<Character> b = new BattleShipBoard<Character>(4, 4, 'X');
    V1ShipFactory f = new V1ShipFactory();
    Placement v0_0 = new Placement(new Coordinate(0, 0), 'V');
    Ship<Character> ship1 = f.makeDestroyer(v0_0);
    b.tryAddShip(ship1);
    assertSame(ship1, b.fireAt(new Coordinate("B0")));

    assertFalse(ship1.isSunk());
    b.fireAt(new Coordinate("A0"));
    assertFalse(ship1.isSunk());
    b.fireAt(new Coordinate("C0"));
    assertTrue(ship1.isSunk());
  }

  @Test
  public void test_whatIsAtForEnemy() {
    Board<Character> b = new BattleShipBoard<Character>(4, 4, 'X');
    V2ShipFactory f = new V2ShipFactory();
    Placement v0_0 = new Placement(new Coordinate(0, 0), 'V');
    Placement v0_1 = new Placement(new Coordinate(0, 1), 'V');
    Ship<Character> ship1 = f.makeDestroyer(v0_0);
    Ship<Character> ship2 = f.makeDestroyer(v0_1);
    b.tryAddShip(ship1);

    b.fireAt(new Coordinate("B0")); // hit
    b.fireAt(new Coordinate("B1")); // miss
    assertEquals('d', b.whatIsAtForEnemy(new Coordinate("B0")));
    assertEquals('X', b.whatIsAtForEnemy(new Coordinate("B1")));
    assertEquals(null, b.whatIsAtForSelf(new Coordinate("B1")));

    b.moveShip(ship1, ship2);
    b.fireAt(new Coordinate("B1")); // once "miss", now hit
    assertEquals('d', b.whatIsAtForEnemy(new Coordinate("B0")));
    assertEquals('d', b.whatIsAtForEnemy(new Coordinate("B1")));
  
  }

  @Test
  public void test_lose_game() {
    Board<Character> b = new BattleShipBoard<Character>(4, 4, 'X');
    V1ShipFactory f = new V1ShipFactory();
    Placement v0_0 = new Placement(new Coordinate(0, 0), 'V');
    Ship<Character> ship1 = f.makeDestroyer(v0_0);
    b.tryAddShip(ship1);

    b.fireAt(new Coordinate("A0"));
    assertFalse(b.loseGame());
    b.fireAt(new Coordinate("B0"));
    assertFalse(b.loseGame());
    b.fireAt(new Coordinate("B1"));
    assertFalse(b.loseGame());
    b.fireAt(new Coordinate("C0"));
    assertTrue(b.loseGame());
  }

  @Test
  public void test_select_ship() {
    Board<Character> b = new BattleShipBoard<Character>(4, 4, 'X');
    V2ShipFactory f = new V2ShipFactory();
    Placement u0_0 = new Placement(new Coordinate(0, 0), 'U');
    Ship<Character> ship1 = f.makeBattleship(u0_0);
    b.tryAddShip(ship1);

    assertSame(ship1, b.selectShip(new Coordinate("A1")));
    assertSame(ship1, b.selectShip(new Coordinate("B0")));
    assertSame(ship1, b.selectShip(new Coordinate("B1")));
    assertSame(ship1, b.selectShip(new Coordinate("B2")));
    assertEquals(null, b.selectShip(new Coordinate("A0")));
  }

  @Test
  public void test_shipMoveTo() {
    Board<Character> b = new BattleShipBoard<Character>(4, 4, 'X');
    Coordinate A0 = new Coordinate("A0");
    Coordinate A2 = new Coordinate("A2");
    Coordinate D0 = new Coordinate("D0");
    Coordinate A3 = new Coordinate("A3");
    Coordinate C0 = new Coordinate("C0");
    ComplexShip<Character> bat = new ComplexShip<Character>("Battleship", A0, 'U', 'b', '*');
    ComplexShip<Character> test1 = new ComplexShip<Character>("Battleship", A2, 'R', 'b', '*'); // move to a place that may overlap with itself 
    ComplexShip<Character> test2 = new ComplexShip<Character>("Battleship", A3, 'U', 'b', '*'); // out of bound
    ComplexShip<Character> test3 = new ComplexShip<Character>("Battleship", C0, 'U', 'b', '*'); // collide
    ComplexShip<Character> des = new ComplexShip<Character>("Destroyer", D0, 'H', 'd', '*');
    b.tryAddShip(bat);
    b.tryAddShip(des);
    b.fireAt(new Coordinate("A1"));
    b.fireAt(new Coordinate("B1"));


    String result = b.moveShip(bat, test1);
    assertEquals(null, result);
    HashMap<Coordinate, Boolean> expected = new HashMap<>();
    expected.put(new Coordinate("B3"), true);
    expected.put(new Coordinate("A2"), false);
    expected.put(new Coordinate("B2"), true);
    expected.put(new Coordinate("C2"), false);
    assertEquals(expected, bat.myPieces);

    ArrayList<Coordinate> array = new ArrayList<>();
    array.add(new Coordinate("B3"));
    array.add(new Coordinate("A2"));
    array.add(new Coordinate("B2"));
    array.add(new Coordinate("C2"));
    assertEquals(array, bat.piecesIndex);

    assertEquals("That placement is invalid: the ship goes off the right of the board.", b.moveShip(bat, test2));
    assertEquals("That placement is invalid: the ship overlaps another ship.", b.moveShip(bat, test3));
  }
}
