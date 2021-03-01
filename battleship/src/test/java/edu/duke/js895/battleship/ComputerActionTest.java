package edu.duke.js895.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ComputerActionTest {
  @Test
  public void test_placement() {
    ComputerAction ca = new ComputerAction(10, 10, 20);
    StringBuilder result = new StringBuilder();
    ca.submarinePlacement(result);
    ca.submarinePlacement(result);
    ca.destroyerPlacement(result);
    ca.destroyerPlacement(result);
    ca.destroyerPlacement(result);
    ca.battleshipPlacement(result);
    ca.battleshipPlacement(result);
    ca.battleshipPlacement(result);
    ca.carrierPlacement(result);
    ca.carrierPlacement(result);
    assertEquals("O4VI0HE3HH3HR8HI2RS0US4UA6LD9U", result.toString());
    //System.out.println(result.toString());
  }

  @Test
  public void test_randomAction() {
    ComputerAction ca = new ComputerAction(5, 10, 20);
    String ans1 = ca.createRandomActions(3, 3);
    String ans2 = ca.createRandomActions(3, 3);
    String ans3 = ca.createRandomActions(3, 3);
    String ans4 = ca.createRandomActions(3, 3);
    String ans5 = ca.createRandomActions(3, 0);
    String ans6 = ca.createRandomActions(0, 3);
    String ans7 = ca.createRandomActions(0, 0);
    assertEquals("S", ans1);
    assertEquals("M", ans2);
    assertEquals("S", ans3);
    assertEquals("S", ans4);
    assertEquals("F", ans5);
    assertEquals("S", ans6);
    assertEquals("F", ans7);
    // System.out.println(ans1);
    // System.out.println(ans2);
    // System.out.println(ans3);
    // System.out.println(ans4);
    // System.out.println(ans5);
    // System.out.println(ans6);
    // System.out.println(ans7);
  }

  @Test
  public void test_move_Coordinate() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    V2ShipFactory f = new V2ShipFactory();
    Ship<Character> toAdd = f.makeBattleship(new Placement("A0U"));
    b.tryAddShip(toAdd);

    ComputerAction ca = new ComputerAction(5, 10, 20);
    String ans1 = ca.moveCoordinate(b);
    String ans2 = ca.moveCoordinate(b);
    String ans3 = ca.moveCoordinate(b);
    assertEquals("B2", ans1);
    assertEquals("B2", ans2);
    assertEquals("A1", ans3);
    // System.out.println(ans1);
    // System.out.println(ans2);
    // System.out.println(ans3);
  }

  @Test
  public void test_random_Coordinate() {
    ComputerAction ca = new ComputerAction(5, 10, 20);
    String ans1 = ca.randomCoordinate();
    String ans2 = ca.randomCoordinate();
    String ans3 = ca.randomCoordinate();
    assertEquals("O1", ans1);
    assertEquals("B6", ans2);
    assertEquals("J9", ans3);
    //     System.out.println(ans1);
    // System.out.println(ans2);
    // System.out.println(ans3);
  }
}
