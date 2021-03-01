package edu.duke.js895.battleship;

import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class ComputerPlayerTest {

  private ComputerPlayer createComputerPlayer(String player, int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    return new ComputerPlayer(player, board, input, output, shipFactory);
  }


  @Test
  public void test_checkPlacement() {
    ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
    ComputerPlayer cp = createComputerPlayer("A", 10, 20, "", bytes1);
    String result1 = cp.checkPlacement(new Placement("A0H"), "Submarine");
    assertEquals(null, result1);
    String result2 = cp.checkPlacement(new Placement("D0H"), "Destroyer");
    assertEquals(null, result2);
    String result3 = cp.checkPlacement(new Placement("H6U"), "Battleship");
    assertEquals(null, result3);
  }

}
