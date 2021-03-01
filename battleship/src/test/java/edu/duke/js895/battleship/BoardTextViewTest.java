package edu.duke.js895.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27, 'X');
    assertThrows(IllegalArgumentException.class, ()->new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, ()->new BoardTextView(tallBoard));
  }

  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  private void shipBoardHelper(int w, int h, List<Coordinate> shipLoc, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
    for (Coordinate c : shipLoc) {  // add all ships into the board for displaying
      b1.tryAddShip(new RectangleShip<Character>(c, 's', '*'));
    }
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_2by2() {
    String expectedHeader = "  0|1\n";
    String expectedBody = 
    "A  |  A\n"+
    "B  |  B\n";
    emptyBoardHelper(2, 2, expectedHeader, expectedBody);  
  }

  @Test
  public void test_display_empty_3by2() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = 
    "A  | |  A\n"+
    "B  | |  B\n";
    emptyBoardHelper(3, 2, expectedHeader, expectedBody);   
  }

  @Test
  public void test_display_empty_3by5() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = 
    "A  | |  A\n"+
    "B  | |  B\n"+
    "C  | |  C\n"+
    "D  | |  D\n"+
    "E  | |  E\n";
    emptyBoardHelper(3, 5, expectedHeader, expectedBody);   
  }

  @Test
  public void test_display_ships_4by3() {
    String expectedHeader = "  0|1|2|3\n";
    String expectedBody = 
    "A s| | |  A\n"+
    "B  |s| |  B\n"+
    "C  | |s|s C\n";
    List<Coordinate> locations = new ArrayList<Coordinate>(); // the locations we want to add ships
    locations.add(new Coordinate("A0"));
    locations.add(new Coordinate("b1"));
    locations.add(new Coordinate("C2"));
    locations.add(new Coordinate("c3"));
    shipBoardHelper(4, 3, locations, expectedHeader, expectedBody);   
  }

  @Test
  public void test_display_Enemy_Board() {
    Board<Character> b = new BattleShipBoard<Character>(4, 3, 'X');
    BoardTextView view = new BoardTextView(b);
    V1ShipFactory f = new V1ShipFactory();
    Placement v0_3 = new Placement(new Coordinate(0, 3), 'V');
    Placement h1_0 = new Placement(new Coordinate(1, 0), 'H');
    Ship<Character> ship1 = f.makeDestroyer(v0_3);
    Ship<Character> ship2 = f.makeSubmarine(h1_0);
    b.tryAddShip(ship1);
    b.tryAddShip(ship2);

    String myView1 =
      "  0|1|2|3\n" +
      "A  | | |d A\n" +
      "B s|s| |d B\n" +
      "C  | | |d C\n" +
      "  0|1|2|3\n";
    String enemyView1 =
      "  0|1|2|3\n" +
      "A X| | |  A\n" +
      "B  | | |  B\n" +
      "C  | | |  C\n" +
      "  0|1|2|3\n";   
    String enemyView2 =
      "  0|1|2|3\n" +
      "A X| | |  A\n" +
      "B s| | |  B\n" +
      "C  | | |  C\n" +
      "  0|1|2|3\n";
    String myView2 =
      "  0|1|2|3\n" +
      "A  | | |d A\n" +
      "B *|s| |d B\n" +
      "C  | | |d C\n" +
      "  0|1|2|3\n";
    assertEquals(myView1, view.displayMyOwnBoard());
    b.fireAt(new Coordinate("A0"));    
    assertEquals(enemyView1, view.displayEnemyBoard());  
    b.fireAt(new Coordinate("B0"));
    assertEquals(myView2, view.displayMyOwnBoard());
    assertEquals(enemyView2, view.displayEnemyBoard()); 

  }

  @Test
  public void test_display_MyBoard_With_Enemy_NextToIt() {
    Board<Character> myBoard = new BattleShipBoard<Character>(4, 2, 'X');
    Board<Character> enemyBoard = new BattleShipBoard<Character>(4, 2, 'X');
    BoardTextView myview = new BoardTextView(myBoard);
    BoardTextView enemyview = new BoardTextView(enemyBoard);
    V1ShipFactory f = new V1ShipFactory();
    Placement h0_0 = new Placement("A0H");
    Placement h1_1 = new Placement("B1H");
    Placement v0_0 = new Placement("A0V");
    Placement h0_1 = new Placement("A1H");
    Ship<Character> ship1 = f.makeSubmarine(h0_0);
    Ship<Character> ship2 = f.makeDestroyer(h1_1);
    Ship<Character> ship3 = f.makeSubmarine(v0_0);
    Ship<Character> ship4 = f.makeDestroyer(h0_1);
    myBoard.tryAddShip(ship1);
    myBoard.tryAddShip(ship2);
    enemyBoard.tryAddShip(ship3);
    enemyBoard.tryAddShip(ship4);

    String expected =
    "     Your ocean               Player B's ocean\n"+
    "  0|1|2|3                    0|1|2|3\n"+
    "A s|s| |  A                A  | | |  A\n"+
    "B  |d|d|d B                B  | | |  B\n"+
    "  0|1|2|3                    0|1|2|3\n";
    String ans = myview.displayMyBoardWithEnemyNextToIt(enemyview, "Your ocean", "Player B's ocean");
    assertEquals(expected, ans);

    enemyBoard.fireAt(new Coordinate("B0"));
    String expected2 =
    "     Your ocean               Player B's ocean\n"+
    "  0|1|2|3                    0|1|2|3\n"+
    "A s|s| |  A                A  | | |  A\n"+
    "B  |d|d|d B                B s| | |  B\n"+
    "  0|1|2|3                    0|1|2|3\n";
    String ans2 = myview.displayMyBoardWithEnemyNextToIt(enemyview, "Your ocean", "Player B's ocean");
    assertEquals(expected2, ans2);

    myBoard.fireAt(new Coordinate("B3"));
    String expected3 =
    "     Your ocean               Player B's ocean\n"+
    "  0|1|2|3                    0|1|2|3\n"+
    "A s|s| |  A                A  | | |  A\n"+
    "B  |d|d|* B                B s| | |  B\n"+
    "  0|1|2|3                    0|1|2|3\n";
    String ans3 = myview.displayMyBoardWithEnemyNextToIt(enemyview, "Your ocean", "Player B's ocean");
    assertEquals(expected3, ans3);
  }

}
