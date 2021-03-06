/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.js895.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * This class works as top level class, make two players start game
 */
public class App {
     /**
   * The players of this game
   */
    // TextPlayer player1;
    // TextPlayer player2;
    private Player player1;
    private Player player2;
    static int seed = 5;
    /**
   * Construct a new App with some players
   * 
   * @param player1 is the first TextPlayer to display
   * @param player2 is the second TextPlayer to display
   */
   public App(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
   }

   /**
 * check if the type number player choose is valid
 */
   public String initialCheck(String typeID) {
      if (typeID.equals("1") || typeID.equals("2")) {
        return null;
      }
      return "Invalid type of player, please type again.";
   }

   /**
 * let the player to choose the identification of players, human or computer
 * @throws IOException if get invalid input.
 */
   public String initialState (int playerID, BufferedReader input) throws IOException{
        while (true) {
          String prompt = 
          "Would you like player "+playerID+" to be :\n"+
          "1. human player\n"+
          "2. computer player\n";
          System.out.println(prompt);
          String type = input.readLine();
          String result = initialCheck(type);
          if (result == null) {
            return type;
          }
          System.out.println(result);
      }
   }

    /**
   * Construct a new App with some players
   * do placement behavior for two players
   */
   public void doPlacementPhase() throws IOException {
       player1.doPlacementPhase();
       player2.doPlacementPhase();
   }

   /**
 * This method carries out the attacking phase of the game.  Players
 * alternate turns starting with player 1.  On a turn a player chooses and
 * carries out an action.  The phase ends when one player has lost
 * at which point this method declares the winner by printing a message.
 *
 * @throws IOException if get invalid input.
 */
   public String doPlayingPhase() throws IOException{
       while(true) {
         player1.playOneTurn(player2.theBoard, player2.view);
         if (player2.theBoard.loseGame()) {
            return "A";
         }

         player2.playOneTurn(player1.theBoard, player1.view);
         if (player1.theBoard.loseGame()) {
           return "B"; 
         } 
      }
   }

   /**
 * This method print out the final meesage: anounce the winner and game is over
 */
   public void gameOver(String winner) {
      System.out.println("The winner is "+winner+"! game over");
   }

    /**
   * Play the game
   * !!!
   * If you want play completely randomly, just don't pass in the parameter seed 
   * into ComputerPlayer's constructor, just use the 5 parameters' constructor
   * !!!
   */
    public static void main(String[] args) throws IOException{
        Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
        Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        V2ShipFactory factory = new V2ShipFactory();
        Player player1 = new TextPlayer("A", b1, input, System.out, factory);
        Player player2 = new TextPlayer("B", b2, input, System.out, factory);

        App app = new App(player1, player2);
        String player1Identity = app.initialState(1, input);
        String player2Identity = app.initialState(2, input);
        if (player1Identity.equals("1")) {
           app.player1 = new TextPlayer("A", b1, input, System.out, factory);
        } 
        
        if (player1Identity.equals("2")) {
          app.player1 = new ComputerPlayer("A", b1, input, System.out, factory, seed);
        }

        if (player2Identity.equals("1")) {
          app.player2 = new TextPlayer("B", b2, input, System.out, factory);
        }

        if (player2Identity.equals("2")) {
          app.player2 = new ComputerPlayer("B", b2, input, System.out, factory, seed);
        }

        app.doPlacementPhase();
        String winner = app.doPlayingPhase();
        app.gameOver(winner);
    }
}
