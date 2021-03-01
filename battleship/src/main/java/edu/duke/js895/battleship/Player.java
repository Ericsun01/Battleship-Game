package edu.duke.js895.battleship;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;

//import java.io.Reader;
import java.util.function.Function;

public abstract class Player {
            /**
   * a board of this player
   * view of this board
   * the input spouce
   * the output
   * the factory of ship
   * name of this player
   * list of ships for placing
   * Map<shipname, factory_function>
   * total rounds of move
   * total rounds of radar
   */
  final Board<Character> theBoard; // per player 
  final BoardTextView view;        // per player
  final BufferedReader inputReader; // share
  final PrintStream out;            // share
  final AbstractShipFactory<Character> shipFactory; // pure functional
  String name;
  final int sonarRadius = 3;
  protected int moveRounds = 3;
  protected int sonarRounds = 3;

   /**
 * Constructs a Textplayer
 * @param name is the name of player 
 * @param theBoard is the the board used by this player
 * @param inputSource is the BufferedReader to initialize
 * @param out is System.out
 * @param factory is the factory of ship
 */
 // @piazza 112
  public Player(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, V2ShipFactory factory) {
      this.name = name;
      this.theBoard = theBoard;
      this.view = new BoardTextView(theBoard);
      this.inputReader = inputSource;
      this.out = out;
      this.shipFactory = factory;
  }


   /**
 * read a Placement from inputReader, return a Placement object
 * @param prompt is the prompt message  
 * @throws EOFException when inputReader can no longer read anything from input
 * @return the placement that read from input
 */
  public abstract Placement readPlacement(String prompt) throws IOException;

  /**
 * do a Placement, print the needed info
 * If the placement input is not valid, this function will infinte loop until a valid one comes
 * @param shipName is the ship's name
 * @param creatFn is a set of creatShip functions that we can choose from
 * @throws IllegalArgumentException when an invalid placement occurs
 */
  public abstract void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException;

  
  /**
 * print the initial message, followed by instruction and doPlacement for all ships
 */
  public abstract void doPlacementPhase() throws IOException; 

//         /**
//  * Check if target is in Bound. since users input coordiante with string input,
//  * we only need to handle the "goes off the bottom" case, other cases will be handled 
//  * by coordinate constructor
//  * @param target the location checking for using
//  * @return the prompt message or null
//  */
//   public abstract String checkCoordinate(Coordinate target);

  /**
 * create a new ship for move action
 * @param shipName the type of ship
 * @param p the placement for new ship
 * @param createFn the correspondiding create ship method
 * @return the ship created
 */
  public abstract Ship<Character> createTestShip(String shipName, Placement p, Function<Placement, Ship<Character>> createFn);
  

        /**
 * move ship that user selects to a placement he/she indicates
 * @return the prompt message or null for moving successfully
 */
  public abstract String doOneMovement() throws IOException;
  /**
 * fire at a location that user input
 * @param enemyBoard indicates enemy's board for attacking
 * @return the prompt message or null for firing successfully
 */
  public abstract String doOneFire(Board<Character> enemyBoard) throws IOException;

     /**
 * scan at a location that user input
 * @param enemyBoard indicates enemy's board for scanning
 * @return the prompt message or null for scanning successfully
 */
  public abstract String doOneSonar(Board<Character> enemyBoard) throws IOException;

         /**
 * scan the board by sonar
 * @param center is the scanning center location
 * @param length is the length of diamond shape's arm
 * @param enemyBoard is the board for scanning
 * @return an array represent num of each types of ships
 */
  public abstract int[] doSonar(Coordinate center, int length, Board<Character> enemyBoard);


  /**
 * print out the name of player of this round, print his board and his enemy's board. Then
 * ask the player to input his target coordinate, also checking if it is legal. At last fire
 * at that coordinate, print the result that if the player hit a ship/ miss  
 * @param enemyBoard the board that used to execute fire function
 * @param enemyBoardTextView the view that used to display the enemy's board
 * @throws EOFException if the inputReader find input file terminate accidentally
 */
  public abstract void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyBoardTextView) throws IOException;
}
