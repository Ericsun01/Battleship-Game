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
 * This class represent a player, has methods relate to doPlacement
 */
public class TextPlayer extends Player{
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
    // final Board<Character> theBoard; // per player 
    // final BoardTextView view;        // per player
    // final BufferedReader inputReader; // share
    // final PrintStream out;            // share
    // final AbstractShipFactory<Character> shipFactory; // pure functional
    // String name;
    // final ArrayList<String> shipsToPlace;
    // final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
    // final int sonarRadius = 3;
  
    final ArrayList<String> shipsToPlace;
    final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
   
     /**
   * Constructs a Textplayer
   * @param name is the name of player 
   * @param theBoard is the the board used by this player
   * @param inputSource is the BufferedReader to initialize
   * @param out is System.out
   * @param factory is the factory of ship
   */
   // @piazza 112
   public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, V2ShipFactory factory) {
        super(name, theBoard, inputSource, out, factory);
        this.shipsToPlace = new ArrayList<String>();
        this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
        setupShipCreationMap();
        setupShipCreationList();
    }
    // public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, V2ShipFactory factory) {
    //     this.name = name;
    //     this.theBoard = theBoard;
    //     this.view = new BoardTextView(theBoard);
    //     this.inputReader = inputSource;
    //     this.out = out;
    //     this.shipFactory = factory;
    //     this.shipsToPlace = new ArrayList<String>();
    //     this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
    //     setupShipCreationMap();
    //     setupShipCreationList();
    // }

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
        out.println(prompt);
        String result = inputReader.readLine();
        if (result == null) {
            throw new EOFException();
        }
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
            try {
                // read a Placement (prompt: "Where would you like to put your ship?")
                Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?"); 
                // Create a basic ship based on the location in that Placement (orientation doesn't matter yet) 
                Ship<Character> b = createFn.apply(p);     
                // Add that ship to the board
                String checkResult = theBoard.tryAddShip(b);
                if (checkResult != null) {
                    out.println(checkResult);
                } else {
                    // Print out the board (to out, not to System.out)
                    // !!! println will add an additional \n at the end !!!
                    out.println(view.displayMyOwnBoard());
                    break;
                }            
            } catch (IllegalArgumentException e) {
                out.println("That placement is invalid: it does not have the correct format.");
            }
        }
    }

    
    /**
   * print the initial message, followed by instruction and doPlacement for all ships
   */
    public void doPlacementPhase() throws IOException {
        // display the starting (empty) board
        out.print(view.displayMyOwnBoard());
    
        // print the instructions message 
        String instruction = 
        "--------------------------------------------------------------------------------\n"+
        "Player "+ name +": you are going to place the following ships (which are all\n"+ 
        "rectangular). For each ship, type the coordinate of the upper left\n" + 
        "side of the ship, followed by either H (for horizontal) or V (for\n"+
        "vertical). For example M4H would place a ship horizontally starting\n"+
        "at M4 and going to the right. You have\n"+ 
        "\n"+
        "2 \"Submarines\" ships that are 1x2\n"+ 
        "3 \"Destroyers\" that are 1x3\n"+
        "3 \"Battleships\" that are 1x4\n"+
        "2 \"Carriers\" that are 1x6\n"+
        "--------------------------------------------------------------------------------\n";
        out.print(instruction);

        // traverse the Arraylist of Ship, call the corresponding make function to build ship
        for (String ship: shipsToPlace) {
            doOnePlacement(ship, shipCreationFns.get(ship));
        }       
    }

          /**
   * Check if target is in Bound. since users input coordiante with string input,
   * we only need to handle the "goes off the bottom" case, other cases will be handled 
   * by coordinate constructor
   * @param target the location checking for using
   * @return the prompt message or null
   */
  public String checkCoordinate(Coordinate target) {
        int row = target.getRow();

        if (row >= theBoard.getHeight()) {
            return "That coordinate is invalid: the target goes off the bottom of the board.\n";
        }

        return null;
    }

    /**
   * Check if placement is valid. 
   * we will check the coordinate part to previous handler, and we check if orientation is valid 
   * @param p the placement for checking
   * @return the prompt message or null
   */
    public String checkPlacement(Placement p) {
        String coordinateCheck = checkCoordinate(p.getWhere()); 
        if (coordinateCheck != null) {
            return "Move Placement: "+ coordinateCheck;
        }

        char ori = p.getOrientation();
        if (ori != 'H' && ori != 'V' && ori != 'U' && ori != 'R' && ori != 'D' && ori != 'L') {
            return "Move Placement: invalid orientation.\n";
        }

        return null;
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
        while(true) {
            try {
                out.print("Select a coordinate of a ship for moving\n");
                String location = inputReader.readLine();
                if (location == null) {
                    throw new EOFException();
                }
                where = new Coordinate(location);
                String checkResult = checkCoordinate(where);
                if (checkResult!=null) {
                    return checkResult;                   
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                out.print("That target coordinate is invalid: it does not have the correct format.\n");
            }
        }

        while(true) {
            try {
                out.print("Select a placement of a ship to move to\n");
                String location = inputReader.readLine();
                if (location == null) {
                    throw new EOFException();
                }
                towhere = new Placement(location);
                String checkResult = checkPlacement(towhere);
                if (checkResult!=null) {
                    return checkResult;                   
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                out.print("That placement is invalid: it does not have the correct format.\n");
            }
        }

        Ship<Character> toMove = theBoard.selectShip(where);
        if (toMove == null) {
            return "Invalid ship to move: no ship at this coordinate.\n";
        }
        String name = toMove.getName();
        Ship<Character> testShip = createTestShip(name, towhere, shipCreationFns.get(name));
        String result = theBoard.moveShip(toMove, testShip);
        if (result == null) {
            return null;
        }  
        return result;         
    }
        
    /**
   * fire at a location that user input
   * @param enemyBoard indicates enemy's board for attacking
   * @return the prompt message or null for firing successfully
   */
    public String doOneFire(Board<Character> enemyBoard) throws IOException{
        Coordinate target = new Coordinate(0, 0); // default reference, will be changed later
        while(true) {
            try {
                out.print("Select a coordinate to fire at\n");
                String location = inputReader.readLine();
                if (location == null) {
                    throw new EOFException();
                }
                target = new Coordinate(location);
                String checkResult = checkCoordinate(target);
                if (checkResult!=null) {
                    //out.println(checkResult);   
                    return checkResult;                
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                out.print("That target coordinate is invalid: it does not have the correct format.\n");
            }
        }
        
        // step3: fire at the target and print result
        Ship<Character> s = enemyBoard.fireAt(target);
        String report = new String();
        if (s != null) {
             report =  
             "--------------------------------------------------------------------------------\n"+
             "You hit a "+
             s.getName()+
             "!\n"+
             "--------------------------------------------------------------------------------\n";
        } else {
             report =  
             "--------------------------------------------------------------------------------\n"+
             "You missed!\n"+
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
        Coordinate center = new Coordinate("A0");
        while(true) {
            try {
                out.print("Select a center coordinate for sonar scan\n");
                String location = inputReader.readLine();
                if (location == null) {
                    throw new EOFException();
                }
                center = new Coordinate(location);
                String checkResult = checkCoordinate(center);
                if (checkResult!=null) {
                    //out.println(checkResult);     
                    return checkResult;              
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                out.print("That target coordinate is invalid: it does not have the correct format.\n");
            }
        }
        int[] ans = doSonar(center, sonarRadius, enemyBoard);
        String detectResult =
        "--------------------------------------------------------------------------------\n"+
        "Submarines occupy "+ans[0]+" squares\n"+
        "Destroyers occupy "+ans[1]+" squares\n"+
        "Battleships occupy "+ans[2]+" squares\n"+
        "Carriers occupy "+ans[3]+" square\n"+
        "--------------------------------------------------------------------------------\n";
        out.print(detectResult);
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
   * print out the name of player of this round, print his board and his enemy's board. Then
   * ask the player to input his target coordinate, also checking if it is legal. At last fire
   * at that coordinate, print the result that if the player hit a ship/ miss  
   * @param enemyBoard the board that used to execute fire function
   * @param enemyBoardTextView the view that used to display the enemy's board
   * @throws EOFException if the inputReader find input file terminate accidentally
   */
    public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyBoardTextView) throws IOException {
        // step1: show two boards based on last turn's result
        out.print("Player "+name+"'s turn:\n");
        String enemyHeader = new String();
        if (name == "A") {
            enemyHeader = "Player B's ocean";
        } else {
            enemyHeader = "Player A's ocean";
        }
        out.print(view.displayMyBoardWithEnemyNextToIt(enemyBoardTextView, "Your ocean", enemyHeader));

        
        // step2: acquire input of action type
        while(true) {
            String action = 
                "--------------------------------------------------------------------------------\n"+
                "Possible actions for Player "+name+":\n"+
                "\n"+
                "F Fire at a square\n"+
                "M Move a ship to another square (" + moveRounds + " remaining)\n"+
                "S Sonar scan (" + sonarRounds + " remaining)\n"+
                "\n"+
                "Player "+name+", what would you like to do?\n"+
                "--------------------------------------------------------------------------------\n";
            out.print(action);

            String result = inputReader.readLine();
            if (result == null) {
                throw new EOFException();
            }
            result = result.toUpperCase();
            
            //System.out.println("pass EOF");

            if (result.equals("F")) {      // choose fire
                String fireResult = doOneFire(enemyBoard);
                if (fireResult == null) {
                    break;
                }
                out.print(fireResult);
                continue;
            }

            //System.out.println("miss F");

            if (result.equals("M")) {      // choose move
                if (moveRounds == 0) {
                    out.print("No available move action left, please select again\n");
                    continue;
                }
                String moveResult = doOneMovement();
                if (moveResult == null) {
                    moveRounds--;
                    break;
                }
                out.print(moveResult);
                continue;
            }

            //System.out.println("miss M");

            if (result.equals("S")) {       // choose sonar 
                if (sonarRounds == 0) {
                    out.print("No available sonar action left, please select again\n");
                    continue;
                }
                String sonarResult = doOneSonar(enemyBoard);
                if (sonarResult == null) {
                    sonarRounds--;
                    break;
                }
                out.print(sonarResult);
                continue;
            }

            out.print("Invalid action type, please select again\n");
        }
    }
}
