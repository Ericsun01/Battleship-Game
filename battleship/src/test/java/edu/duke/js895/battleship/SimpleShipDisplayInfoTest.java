package edu.duke.js895.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_constructor() {
    String myData = "C";
    String onHit = "Yes";

    SimpleShipDisplayInfo<String> ssd = new SimpleShipDisplayInfo<String>(myData, onHit);
    assertEquals(myData, ssd.myData);
    assertEquals(onHit, ssd.onHit);
  }

  @Test
  public void test_getInfo() {
    String myData = "C";
    String onHit = "Yes";

    SimpleShipDisplayInfo<String> ssd = new SimpleShipDisplayInfo<String>(myData, onHit);
    assertEquals(onHit, ssd.getInfo(new Coordinate("A0"), true));
    assertEquals(myData, ssd.getInfo(new Coordinate("A0"), false));
  }

}
