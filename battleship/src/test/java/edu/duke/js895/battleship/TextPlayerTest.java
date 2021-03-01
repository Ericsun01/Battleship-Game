package edu.duke.js895.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  // inputdata: String input for readPlacement
  private TextPlayer createTextPlayer(String player, int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    return new TextPlayer(player, board, input, output, shipFactory);
  }

    @Test 
    void test_read_placement() throws IOException {
        
        //String positions = "B2V\nC8H\na4v\n";
        
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("A", 10, 20, "B2V\nC8H\na4v\n", bytes);

        ByteArrayOutputStream bytes2 = new ByteArrayOutputStream();
        TextPlayer player2 = createTextPlayer("A", 10, 20, "", bytes2);
        assertThrows(IOException.class, ()->player2.readPlacement(""));
        //App app = new App(player1, player2);

        String prompt = "Please enter a location for a ship:";
        Placement[] expected = new Placement[3];
        expected[0] = new Placement(new Coordinate(1, 2), 'V');
        expected[1] = new Placement(new Coordinate(2, 8), 'H');
        expected[2] = new Placement(new Coordinate(0, 4), 'V');
  
        for (int i = 0; i < expected.length; i++) {
            Placement p = player.readPlacement(prompt);
            assertEquals(p, expected[i]); //did we get the right Placement back
            assertEquals(prompt + "\n", bytes.toString()); //should have printed prompt and newline
            bytes.reset(); //clear out bytes for next time around
          }   

    }

    @Test
    void test_do_one_placement() throws IOException {
        ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
        ByteArrayOutputStream bytes2 = new ByteArrayOutputStream();
        ByteArrayOutputStream bytes3 = new ByteArrayOutputStream();
        //String place1 = "A0V\n";
        //String place3 = "A0Q\n";

        TextPlayer player1 = createTextPlayer("A", 4, 3, "A0V\n", bytes1);
        TextPlayer player2 = createTextPlayer("A", 4, 3, "C0V\nA0V\n", bytes2);  // print prompt String
        TextPlayer player3 = createTextPlayer("A", 4, 3, "A0Q\nA0V\n", bytes3);  // incorrect format placement

        player1.doOnePlacement("Destroyer", player1.shipCreationFns.get("Destroyer"));
        
        String expectedHeader = "  0|1|2|3\n";
        String out =
        "Player A where do you want to place a Destroyer?\n" +
        expectedHeader+
        "A d| | |  A\n"+
        "B d| | |  B\n"+
        "C d| | |  C\n"+
        expectedHeader+
        "\n";

        String ans = bytes1.toString();
        assertEquals(out, ans);

        player2.doOnePlacement("Destroyer", player2.shipCreationFns.get("Destroyer"));
        String out2 =
        "Player A where do you want to place a Destroyer?\n" +
        "That placement is invalid: the ship goes off the bottom of the board.\n" +
        "Player A where do you want to place a Destroyer?\n" +
        expectedHeader+
        "A d| | |  A\n"+
        "B d| | |  B\n"+
        "C d| | |  C\n"+
        expectedHeader+
        "\n";
        String outofBound = bytes2.toString();
        assertEquals(out2, outofBound);
        
        player3.doOnePlacement("Destroyer", player3.shipCreationFns.get("Destroyer"));
      }

    @Test
    void test_do_One_Movement() throws IOException {
        ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
        ByteArrayOutputStream bytes2 = new ByteArrayOutputStream();
        ByteArrayOutputStream bytes3 = new ByteArrayOutputStream();
        ByteArrayOutputStream bytes4 = new ByteArrayOutputStream();
        ByteArrayOutputStream bytes5 = new ByteArrayOutputStream();

        TextPlayer player1 = createTextPlayer("A", 4, 4, "A0U\nA2\nB1\nC0U\n", bytes1);
        TextPlayer player2 = createTextPlayer("A", 4, 4, "A0U\nB1\nB3R\nC0U\n", bytes2);  
        TextPlayer player3 = createTextPlayer("A", 4, 4, "A0U\nB1\nC0U\n", bytes3);
        TextPlayer player4 = createTextPlayer("A", 4, 4, "A0U\n[;\nB1\nC0U\n", bytes4);
        TextPlayer player5 = createTextPlayer("A", 4, 4, "A0U\nB1\nGGG\nC0U\n", bytes5);
        player1.doOnePlacement("Battleship", player1.shipCreationFns.get("Battleship"));
        player2.doOnePlacement("Battleship", player2.shipCreationFns.get("Battleship"));
        player3.doOnePlacement("Battleship", player3.shipCreationFns.get("Battleship"));
        player4.doOnePlacement("Battleship", player4.shipCreationFns.get("Battleship"));
        player5.doOnePlacement("Battleship", player5.shipCreationFns.get("Battleship"));
        

        player4.doOneMovement();
        String expected = 
        "Player A where do you want to place a Battleship?\n"+
        "  0|1|2|3\n"+
        "A  |b| |  A\n"+
        "B b|b|b|  B\n"+
        "C  | | |  C\n"+
        "D  | | |  D\n"+
        "  0|1|2|3\n"+
        "\n"+
        "Select a coordinate of a ship for moving\n"+
        "That target coordinate is invalid: it does not have the correct format.\n"+
        "Select a coordinate of a ship for moving\n"+
        "Select a placement of a ship to move to\n";
        assertEquals(expected, bytes4.toString());

        player5.doOneMovement();
        String expected2 = 
        "Player A where do you want to place a Battleship?\n"+
        "  0|1|2|3\n"+
        "A  |b| |  A\n"+
        "B b|b|b|  B\n"+
        "C  | | |  C\n"+
        "D  | | |  D\n"+
        "  0|1|2|3\n"+
        "\n"+
        "Select a coordinate of a ship for moving\n"+
        "Select a placement of a ship to move to\n"+
        "That placement is invalid: it does not have the correct format.\n"+
        "Select a placement of a ship to move to\n";
        assertEquals(expected2, bytes5.toString());
        
        assertEquals("Invalid ship to move: no ship at this coordinate.\n", player1.doOneMovement());

        String ans = player2.doOneMovement();
        assertEquals("That placement is invalid: the ship goes off the right of the board.", ans);

        assertEquals(null, player3.doOneMovement());
        assertEquals('b', player3.theBoard.whatIsAtForSelf(new Coordinate("D2"))); 

        
    }

    @Test
    public void test_do_one_fire() throws IOException {
        ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
        ByteArrayOutputStream bytes2 = new ByteArrayOutputStream();


        TextPlayer player1 = createTextPlayer("A", 4, 4, "A0U\nGG\nZ8\nA2\nB1\n", bytes1);
        TextPlayer player2 = createTextPlayer("A", 4, 4, "A0U\n", bytes2);  

        player1.doOnePlacement("Battleship", player1.shipCreationFns.get("Battleship"));
        player2.doOnePlacement("Battleship", player2.shipCreationFns.get("Battleship"));

        String result1 = player1.doOneFire(player2.theBoard);
        String result2 = player1.doOneFire(player2.theBoard);
        String result3 = player1.doOneFire(player2.theBoard);
        assertEquals("That coordinate is invalid: the target goes off the bottom of the board.\n", result1);
        assertEquals(null, result2);
        assertEquals(null, result3);

        String expected=
        "Player A where do you want to place a Battleship?\n"+
        "  0|1|2|3\n"+
        "A  |b| |  A\n"+
        "B b|b|b|  B\n"+
        "C  | | |  C\n"+
        "D  | | |  D\n"+
        "  0|1|2|3\n"+
        "\n"+
        "Select a coordinate to fire at\n"+
        "That target coordinate is invalid: it does not have the correct format.\n"+
        "Select a coordinate to fire at\n"+
        "Select a coordinate to fire at\n"+
        "--------------------------------------------------------------------------------\n"+
        "You missed!\n"+
        "--------------------------------------------------------------------------------\n"+
        "Select a coordinate to fire at\n"+
        "--------------------------------------------------------------------------------\n"+
        "You hit a Battleship!\n"+
        "--------------------------------------------------------------------------------\n";
        assertEquals(expected, bytes1.toString());
    }

    @Test
    public void test_do_one_sonar() throws IOException{
        ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
        ByteArrayOutputStream bytes2 = new ByteArrayOutputStream();


        TextPlayer player1 = createTextPlayer("A", 10, 20, "B5U\nGG\nZ9\nD6\n", bytes1);
        TextPlayer player2 = createTextPlayer("A", 10, 20, "C4V\nE5U\nE8V\n", bytes2);  

        player1.doOnePlacement("Battleship", player1.shipCreationFns.get("Battleship"));
        player2.doOnePlacement("Destroyer", player2.shipCreationFns.get("Destroyer"));
        player2.doOnePlacement("Battleship", player2.shipCreationFns.get("Battleship"));
        player2.doOnePlacement("Submarine", player2.shipCreationFns.get("Submarine"));

        String result1 = player1.doOneSonar(player2.theBoard);
        String result2 = player1.doOneSonar(player2.theBoard);

        String expected =
        "Player A where do you want to place a Battleship?\n"+
      "  0|1|2|3|4|5|6|7|8|9\n"+
      "A  | | | | | | | | |  A\n"+
      "B  | | | | | |b| | |  B\n"+
      "C  | | | | |b|b|b| |  C\n"+
      "D  | | | | | | | | |  D\n"+
      "E  | | | | | | | | |  E\n"+
      "F  | | | | | | | | |  F\n"+
      "G  | | | | | | | | |  G\n"+
      "H  | | | | | | | | |  H\n"+
      "I  | | | | | | | | |  I\n"+
      "J  | | | | | | | | |  J\n"+
      "K  | | | | | | | | |  K\n"+
      "L  | | | | | | | | |  L\n"+
      "M  | | | | | | | | |  M\n"+
      "N  | | | | | | | | |  N\n"+
      "O  | | | | | | | | |  O\n"+
      "P  | | | | | | | | |  P\n"+
      "Q  | | | | | | | | |  Q\n"+
      "R  | | | | | | | | |  R\n"+
      "S  | | | | | | | | |  S\n"+
      "T  | | | | | | | | |  T\n"+
      "  0|1|2|3|4|5|6|7|8|9\n"+
      "\n"+
      "Select a center coordinate for sonar scan\n"+
      "That target coordinate is invalid: it does not have the correct format.\n"+
      "Select a center coordinate for sonar scan\n"+
      "Select a center coordinate for sonar scan\n"+
      "--------------------------------------------------------------------------------\n"+
      "Submarines occupy 1 squares\n"+
      "Destroyers occupy 3 squares\n"+
      "Battleships occupy 4 squares\n"+
      "Carriers occupy 0 square\n"+
      "--------------------------------------------------------------------------------\n";
        assertEquals("That coordinate is invalid: the target goes off the bottom of the board.\n", result1);
        assertEquals(null, result2);
        assertEquals(expected, bytes1.toString());
    }

      
    @Test
    public void test_play_One_Turn() throws IOException {
      ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
      ByteArrayOutputStream bytes2 = new ByteArrayOutputStream();
      TextPlayer playerA = createTextPlayer("A", 6, 6, "A0U\nA3H\nG\nM\nA1\nC0U\nF\nA3\nS\nC2\n", bytes1);
      TextPlayer playerB = createTextPlayer("B", 6, 6, "A0U\nC3V\n", bytes2);
      playerA.doOnePlacement("Battleship", playerA.shipCreationFns.get("Battleship"));
      playerA.doOnePlacement("Submarine", playerA.shipCreationFns.get("Submarine"));

      playerB.doOnePlacement("Carrier", playerB.shipCreationFns.get("Carrier"));
      playerB.doOnePlacement("Submarine", playerB.shipCreationFns.get("Submarine"));

      playerA.playOneTurn(playerB.theBoard, playerB.view);
      playerA.playOneTurn(playerB.theBoard, playerB.view);
      playerA.playOneTurn(playerB.theBoard, playerB.view);
      // System.out.print(bytes1.toString());
      //playerB.playOneTurn(playerA.theBoard, playerA.view);
      // InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("testPlayOneTurn.txt");
      // assertNotNull(expectedStream);

      // String expected = new String(expectedStream.readAllBytes());
      // assertEquals(expected, bytes.toString());
    }

    @Test // coordinate, placement, checkresult 
    public void test_do_One_Movement_EOF() throws IOException {
      ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
      ByteArrayOutputStream bytes2 = new ByteArrayOutputStream();
      ByteArrayOutputStream bytes3 = new ByteArrayOutputStream();


      TextPlayer player1 = createTextPlayer("A", 4, 4, "A0U\n", bytes1);
      TextPlayer player2 = createTextPlayer("A", 4, 4, "A0U\nZ9\nA1\nB1U\n", bytes2);  
      TextPlayer player3 = createTextPlayer("A", 4, 4, "A0U\nA1\n", bytes3);
        
      player1.doOnePlacement("Battleship", player1.shipCreationFns.get("Battleship"));
      player2.doOnePlacement("Battleship", player2.shipCreationFns.get("Battleship"));
      player3.doOnePlacement("Battleship", player3.shipCreationFns.get("Battleship"));

      assertThrows(EOFException.class, ()->player1.doOneMovement());
      assertEquals("That coordinate is invalid: the target goes off the bottom of the board.\n", player2.doOneMovement());
      assertThrows(EOFException.class, ()->player3.doOneMovement());    
    }

    @Test
    public void test_do_one_fire_EOF() throws IOException{
      ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
      ByteArrayOutputStream bytes2 = new ByteArrayOutputStream();
      TextPlayer player1 = createTextPlayer("A", 4, 4, "A0U\n", bytes1);  
      TextPlayer player2 = createTextPlayer("A", 4, 4, "A0U\n", bytes2);     
      player1.doOnePlacement("Battleship", player1.shipCreationFns.get("Battleship"));
      player2.doOnePlacement("Battleship", player2.shipCreationFns.get("Battleship"));

      assertThrows(EOFException.class, ()->player1.doOneFire(player2.theBoard));
    }

    @Test
    public void test_do_one_sonar_EOF() throws IOException{
      ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
      ByteArrayOutputStream bytes2 = new ByteArrayOutputStream();
      TextPlayer player1 = createTextPlayer("A", 4, 4, "A0U\n", bytes1);  
      TextPlayer player2 = createTextPlayer("A", 4, 4, "A0U\n", bytes2);     
      player1.doOnePlacement("Battleship", player1.shipCreationFns.get("Battleship"));
      player2.doOnePlacement("Battleship", player2.shipCreationFns.get("Battleship"));

      assertThrows(EOFException.class, ()->player1.doOneSonar(player2.theBoard));
    }

    @Test
    public void test_play_One_Turn_EOF() throws IOException{
      ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
      ByteArrayOutputStream bytes2 = new ByteArrayOutputStream();
      TextPlayer player1 = createTextPlayer("A", 4, 4, "A0U\n", bytes1);  
      TextPlayer player2 = createTextPlayer("B", 4, 4, "A0U\nF\nZ6\nF\nB1\n", bytes2);     
      player1.doOnePlacement("Battleship", player1.shipCreationFns.get("Battleship"));
      player2.doOnePlacement("Battleship", player2.shipCreationFns.get("Battleship"));

      assertThrows(EOFException.class, ()->player1.playOneTurn(player2.theBoard, player2.view));
      player2.playOneTurn(player1.theBoard, player2.view);
      //System.out.print(bytes2.toString());
      String expected = 
      "Player B where do you want to place a Battleship?\n"+
    "  0|1|2|3\n"+
    "A  |b| |  A\n"+
    "B b|b|b|  B\n"+
    "C  | | |  C\n"+
    "D  | | |  D\n"+
    "  0|1|2|3\n"+
    "\n"+
    "Player B's turn:\n"+
    "     Your ocean               Player A's ocean\n"+
    "  0|1|2|3                    0|1|2|3\n"+
    "A  |b| |  A                A  | | |  A\n"+
    "B b|b|b|  B                B  | | |  B\n"+
    "C  | | |  C                C  | | |  C\n"+
    "D  | | |  D                D  | | |  D\n"+
    "  0|1|2|3                    0|1|2|3\n"+
    "--------------------------------------------------------------------------------\n"+
    "Possible actions for Player B:\n"+
    "\n"+
    "F Fire at a square\n"+
    "M Move a ship to another square (3 remaining)\n"+
    "S Sonar scan (3 remaining)\n"+
    "\n"+
    "Player B, what would you like to do?\n"+
    "--------------------------------------------------------------------------------\n"+
    "Select a coordinate to fire at\n"+
    "That coordinate is invalid: the target goes off the bottom of the board.\n"+
    "--------------------------------------------------------------------------------\n"+
    "Possible actions for Player B:\n"+
    "\n"+
    "F Fire at a square\n"+
    "M Move a ship to another square (3 remaining)\n"+
    "S Sonar scan (3 remaining)\n"+
    "\n"+
    "Player B, what would you like to do?\n"+
    "--------------------------------------------------------------------------------\n"+
    "Select a coordinate to fire at\n"+
    "--------------------------------------------------------------------------------\n"+
    "You hit a Battleship!\n"+
    "--------------------------------------------------------------------------------\n";
      assertEquals(expected, bytes2.toString());
  
    }

    @Test
    public void test_check_Placement() {
      ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
      TextPlayer player1 = createTextPlayer("A", 4, 4, "A0U\n", bytes1);
      Placement p = new Placement("A0Z");
      assertEquals("Move Placement: invalid orientation.\n", player1.checkPlacement(p));
    }
}