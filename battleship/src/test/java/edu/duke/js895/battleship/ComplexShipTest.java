package edu.duke.js895.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;


import org.junit.jupiter.api.Test;

public class ComplexShipTest {
  @Test
  public void test_constructor() {
    ComplexShip<Character> cs = new ComplexShip<Character>("Battleship", new Coordinate("A0"), 'U', 's', '*');
    HashMap<Coordinate, Boolean> expected = new HashMap<Coordinate, Boolean>();
    expected.put(new Coordinate("A1"), false);
    expected.put(new Coordinate("B0"), false);
    expected.put(new Coordinate("B1"), false);
    expected.put(new Coordinate("B2"), false);
    assertEquals(expected, cs.myPieces);
  }

  @Test
  public void test_takeCoord() {
    Coordinate upperLeft = new Coordinate("A0");
    ArrayList<Coordinate> battleshipU = ComplexShip.takeCoords(upperLeft, "Battleship", 'U');
    ArrayList<Coordinate> battleshipR = ComplexShip.takeCoords(upperLeft, "Battleship", 'R');
    ArrayList<Coordinate> battleshipD = ComplexShip.takeCoords(upperLeft, "Battleship", 'D');
    ArrayList<Coordinate> battleshipL = ComplexShip.takeCoords(upperLeft, "Battleship", 'L');
    ArrayList<Coordinate> carrierU = ComplexShip.takeCoords(upperLeft, "Carrier", 'U');
    ArrayList<Coordinate> carrierR = ComplexShip.takeCoords(upperLeft, "Carrier", 'R');
    ArrayList<Coordinate> carrierD = ComplexShip.takeCoords(upperLeft, "Carrier", 'D');
    ArrayList<Coordinate> carrierL = ComplexShip.takeCoords(upperLeft, "Carrier", 'L');
    ArrayList<Coordinate> submarineH = ComplexShip.takeCoords(upperLeft, "Submarine", 'H');
    ArrayList<Coordinate> submarineV = ComplexShip.takeCoords(upperLeft, "Submarine", 'V');
    ArrayList<Coordinate> DestroyerH = ComplexShip.takeCoords(upperLeft, "Destroyer", 'H');
    ArrayList<Coordinate> DestroyerV = ComplexShip.takeCoords(upperLeft, "Destroyer", 'V');

    ArrayList<Coordinate> expected1 = new ArrayList<Coordinate>();
    expected1.add(new Coordinate("A1"));
    expected1.add(new Coordinate("B0"));
    expected1.add(new Coordinate("B1"));
    expected1.add(new Coordinate("B2"));
    assertEquals(expected1, battleshipU);

    ArrayList<Coordinate> expected2 = new ArrayList<Coordinate>();
    expected2.add(new Coordinate("B1"));
    expected2.add(new Coordinate("A0"));
    expected2.add(new Coordinate("B0"));
    expected2.add(new Coordinate("C0"));
    assertEquals(expected2, battleshipR);

    ArrayList<Coordinate> expected3 = new ArrayList<Coordinate>();
    expected3.add(new Coordinate("B1"));
    expected3.add(new Coordinate("A2"));
    expected3.add(new Coordinate("A1"));
    expected3.add(new Coordinate("A0"));
    assertEquals(expected3, battleshipD);

    ArrayList<Coordinate> expected4 = new ArrayList<Coordinate>();
    expected4.add(new Coordinate("B0"));
    expected4.add(new Coordinate("C1"));
    expected4.add(new Coordinate("B1"));
    expected4.add(new Coordinate("A1"));
    assertEquals(expected4, battleshipL);

    ArrayList<Coordinate> expected5 = new ArrayList<Coordinate>();
    expected5.add(new Coordinate("A0"));
    expected5.add(new Coordinate("B0"));
    expected5.add(new Coordinate("C0"));
    expected5.add(new Coordinate("C1"));
    expected5.add(new Coordinate("D1"));
    expected5.add(new Coordinate("E1"));
    assertEquals(expected5, carrierU);

    ArrayList<Coordinate> expected6 = new ArrayList<Coordinate>();
    expected6.add(new Coordinate("B0"));
    expected6.add(new Coordinate("B1"));
    expected6.add(new Coordinate("B2"));
    expected6.add(new Coordinate("A2"));
    expected6.add(new Coordinate("A3"));
    expected6.add(new Coordinate("A4"));
    assertEquals(expected6, carrierR);

    ArrayList<Coordinate> expected7 = new ArrayList<Coordinate>();
    expected7.add(new Coordinate("E0"));
    expected7.add(new Coordinate("D0"));
    expected7.add(new Coordinate("C0"));
    expected7.add(new Coordinate("C1"));
    expected7.add(new Coordinate("B1"));
    expected7.add(new Coordinate("A1"));
    assertEquals(expected7, carrierD);

    ArrayList<Coordinate> expected8 = new ArrayList<Coordinate>();
    expected8.add(new Coordinate("B4"));
    expected8.add(new Coordinate("B3"));
    expected8.add(new Coordinate("B2"));
    expected8.add(new Coordinate("A2"));
    expected8.add(new Coordinate("A1"));
    expected8.add(new Coordinate("A0"));
    assertEquals(expected8, carrierL);

    ArrayList<Coordinate> expected9 = new ArrayList<Coordinate>();
    expected9.add(new Coordinate("A0"));
    expected9.add(new Coordinate("A1"));
    assertEquals(expected9, submarineH);

    ArrayList<Coordinate> expected10 = new ArrayList<Coordinate>();
    expected10.add(new Coordinate("A0"));
    expected10.add(new Coordinate("B0"));
    assertEquals(expected10, submarineV);

    ArrayList<Coordinate> expected11 = new ArrayList<Coordinate>();
    expected11.add(new Coordinate("A0"));
    expected11.add(new Coordinate("A1"));
    expected11.add(new Coordinate("A2"));
    assertEquals(expected11, DestroyerH);

    ArrayList<Coordinate> expected12 = new ArrayList<Coordinate>();
    expected12.add(new Coordinate("A0"));
    expected12.add(new Coordinate("B0"));
    expected12.add(new Coordinate("C0"));
    assertEquals(expected12, DestroyerV);
  }

  @Test
  public void test_getName() {
    Coordinate upperLeft = new Coordinate("A0");
    ComplexShip<Character> c = new ComplexShip<Character>("Battleship", upperLeft, 'D', 'b', '*');
    assertEquals("Battleship", c.getName());
  }

  @Test
  public void test_constructor_piecesIndex() {
    Coordinate upperLeft = new Coordinate("A0");
    ComplexShip<Character> battleshipU = new ComplexShip<Character>("Battleship", upperLeft, 'U', 'b', '*');
    ArrayList<Coordinate> expected = new ArrayList<>();
    expected.add(new Coordinate("A1"));
    expected.add(new Coordinate("B0"));
    expected.add(new Coordinate("B1"));
    expected.add(new Coordinate("B2"));
    assertEquals(expected, battleshipU.piecesIndex);
  }

}
