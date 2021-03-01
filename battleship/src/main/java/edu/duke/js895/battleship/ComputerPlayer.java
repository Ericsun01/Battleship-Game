package edu.duke.js895.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
//import java.util.Collections;
//import java.io.Reader;
import java.util.function.Function;

/**
 * This class represent the computer player
 */
public class ComputerPlayer extends Player {

    private ComputerAction generator; 
    final ArrayList<String> shipsToPlace;
    final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
         /**
   * Constructs a Textplayer
   * @param generator is the random signal generator
   * @param shipToPlace is the total ship to add
   * @param shipCreationFns is the mapping that store ship's name with its constructor 

   */
   // @piazza 112
   public ComputerPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, V2ShipFactory factory) {
        super(name, theBoard, inputSource, out, factory);
        this.generator = new ComputerAction(theBoard.getWidth(), theBoard.getHeight());
        this.shipsToPlace = new ArrayList<String>();
        this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
        setupShipCreationMap();
        setupShipCreationList();
    }

    public ComputerPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, V2ShipFactory factory, int seed) {
        super(name, theBoard, inputSource, out, factory);
        this.generator = new ComputerAction(seed, theBoard.getWidth(), theBoard.getHeight());
        this.shipsToPlace = new ArrayList<String>();
        this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
        setupShipCreationMap();
        setupShipCreationList();
    }

     /**
 * Fill up the Map<shipname, factory_function>
 */
  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    }

 /**
* Fill up the list of ships
*/
protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
    }
    
        /**
   * read a Placement from inputReader, return a Placement object
   * @param prompt is the prompt message  
   * @throws EOFException when inputReader can no longer read anything from input
   * @return the placement that read from input
   */
  public Placement readPlacement(String prompt) throws IOException {
    StringBuilder ans = new StringBuilder();
    if (prompt.equals("Submarine")) {
        generator.submarinePlacement(ans);
    } 
    if (prompt.equals("Destroyer")) {
        generator.destroyerPlacement(ans);
    } 
    if (prompt.equals("Battleship")) {
        generator.battleshipPlacement(ans);
    } 
    if (prompt.equals("Carrier")) {
        generator.carrierPlacement(ans);
    }
    
    String result = ans.toString();
    return new Placement(result);
} 

/**
* do a Placement, print the needed info
* If the placement input is not valid, this function will infinte loop until a valid one comes
* @param shipName is the ship's name
* @param creatFn is a set of creatShip functions that we can choose from
* @throws IllegalArgumentException when an invalid placement occurs
*/
public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    while (true) {   
    // read a Placement (prompt: "Where would you like to put your ship?")
        Placement p = readPlacement(shipName); 
        // Create a basic ship based on the location in that Placement (orientation doesn't matter yet) 
        Ship<Character> b = createFn.apply(p);     
        // Add that ship to the board
        String checkResult = theBoard.tryAddShip(b);
        if (checkResult == null) {  // result may not null if placement is invalid
            break;
        } 
    }
}


/**
* print the initial message, followed by instruction and doPlacement for all ships
*/
public void doPlacementPhase() throws IOException {
    for (String ship: shipsToPlace) {
        doOnePlacement(ship, shipCreationFns.get(ship));
    }       
}

/**
* Check if placement is valid. 
* we will check the coordinate part to previous handler, and we check if orientation is valid 
* @param p the placement for checking
* @return the prompt message or null
*/

public String checkPlacement(Placement p, String shipName) {
    // System.out.println("placement for check is "+p.toString());
    // System.out.println("ship for move placement check is "+shipName);
    // Ship<Character> test = createTestShip(shipName, p, shipCreationFns.get(name));
    Ship<Character> test = new ComplexShip<Character>(name, p.getWhere(), p.getOrientation(), 't', '*');
    if (shipName == "Submarine") {
        test = shipFactory.makeSubmarine(p);
    }
    if (shipName == "Destroyer") {
        test = shipFactory.makeDestroyer(p);
    }
    if (shipName == "Battleship") {
        test = shipFactory.makeBattleship(p);
    }
    if (shipName == "Carrier") {
        test = shipFactory.makeCarrier(p);
    }

    String checkResult = theBoard.validatePlacement(test);
    return checkResult;
}

/**
* create a new ship for move action
* @param shipName the type of ship
* @param p the placement for new ship
* @param createFn the correspondiding create ship method
* @return the ship created
*/
public Ship<Character> createTestShip(String shipName, Placement p, Function<Placement, Ship<Character>> createFn) {
    return createFn.apply(p);
}

      /**
* move ship that user selects to a placement he/she indicates
* @return the prompt message or null for moving successfully
*/
public String doOneMovement() throws IOException{
    Coordinate where = new Coordinate(0, 0);
    Placement towhere = new Placement("A0H");
    
    String location = generator.moveCoordinate(theBoard);
    where = new Coordinate(location);
    Ship<Character> toMove = theBoard.selectShip(where);
    String name = toMove.getName();

    while(true) {        
            StringBuilder sb = new StringBuilder();

            if (name.equals("Submarine")) {
                generator.submarinePlacement(sb);
                towhere = new Placement(sb.toString());
            }
            if (name.equals("Destroyer")) {
                generator.destroyerPlacement(sb);
                towhere = new Placement(sb.toString());
            } 
            if (name.equals("Battleship")) {
                generator.battleshipPlacement(sb);
                towhere = new Placement(sb.toString());
            } 
            if (name.equals("Carrier")) {
                generator.carrierPlacement(sb);
                towhere = new Placement(sb.toString());
            }  

            String checkResult = checkPlacement(towhere, name);

            if (checkResult == null) {
                break;
            }
        }

    Ship<Character> testShip = createTestShip(name, towhere, shipCreationFns.get(name));
    theBoard.moveShip(toMove, testShip);   
    return null;   
}
    
/**
* fire at a location that user input
* @param enemyBoard indicates enemy's board for attacking
* @return the prompt message or null for firing successfully
*/
public String doOneFire(Board<Character> enemyBoard) throws IOException{
    String location = generator.randomCoordinate();
    Coordinate target = new Coordinate(location);
    
    // step3: fire at the target and print result
    Ship<Character> s = enemyBoard.fireAt(target);
    String report = new String();
    if (s != null) {
         report =  
         "--------------------------------------------------------------------------------\n"+
         "Player "+name+" hit your "+
         s.getName()+
         " at "+
         target.toString()+
         "!\n"+
         "--------------------------------------------------------------------------------\n";
    } else {
         report =  
         "--------------------------------------------------------------------------------\n"+
         "Player "+name+" missed!\n"+
         "--------------------------------------------------------------------------------\n";   
    }
    // return null indicate we finished
    out.print(report);
    return null;
}        

   /**
* scan at a location that user input
* @param enemyBoard indicates enemy's board for scanning
* @return the prompt message or null for scanning successfully
*/
public String doOneSonar(Board<Character> enemyBoard) throws IOException{
    String location = generator.randomCoordinate();
    Coordinate center = new Coordinate(location);
    doSonar(center, sonarRadius, enemyBoard);
    return null;
}

       /**
* scan the board by sonar
* @param center is the scanning center location
* @param length is the length of diamond shape's arm
* @param enemyBoard is the board for scanning
* @return an array represent num of each types of ships
*/
public int[] doSonar(Coordinate center, int length, Board<Character> enemyBoard) {
    int[] ans = new int[4];
    int row = center.getRow();
    int col = center.getColumn();   
    int startCol = col;
    int endCol = col;
    boolean flag = true;
    for (int i = row - length; i < row+length; i++) {
        for (int j = startCol; j <= endCol; j++) {
            Coordinate c = new Coordinate(i,j);
            Character val = enemyBoard.whatIsAtForSelf(c);
            if (val == null) {
                continue;
            }

            if (val == 's') {
                ans[0]++;
            }

            if (val == 'd') {
                ans[1]++;
            }

            if (val == 'b') {
                ans[2]++;
            }

            if (val == 'c') {
                ans[3]++;
            }
        }

        if ((endCol - startCol) >= 2*length) {
            flag = false;
        }
        if (flag) {
            startCol--;
            endCol++;
        } else {
            startCol++;
            endCol--;
        }
    }
    return ans;
}


/**
* For computer, once it choose a type of action, it must be done! cannot change it in the middle 
* print out the name of player of this round, print his board and his enemy's board. Then
* ask the player to input his target coordinate, also checking if it is legal. At last fire
* at that coordinate, print the result that if the player hit a ship/ miss  
* @param enemyBoard the board that used to execute fire function
* @param enemyBoardTextView the view that used to display the enemy's board
* @throws EOFException if the inputReader find input file terminate accidentally
*/
public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyBoardTextView) throws IOException {

    // step2: acquire input of action type
    while(true) {
        String result = generator.createRandomActions(moveRounds, sonarRounds);
        //result = result.toUpperCase();
        //System.out.println("B have action"+result);    

        if (result.equals("F")) {      // choose fire
            doOneFire(enemyBoard);
            break;
        }

        if (result.equals("M")) {      // choose move
            doOneMovement();
            out.print("Player " + name + " used a special action\n");
            moveRounds--;
            break;
        }

        if (result.equals("S")) {       // choose sonar 
            doOneSonar(enemyBoard);
            out.print("Player " + name + " used a special action\n");
            sonarRounds--;
            break;
            }
        }
    }
}
