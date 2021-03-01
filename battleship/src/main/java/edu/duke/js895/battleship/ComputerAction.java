package edu.duke.js895.battleship;

import java.util.Random;
/**
 * This class define how random signal is generated for different actions
 */
public class ComputerAction {
     /**  The width, height of this Board
   *  left, right boundary of number from 0 - 9
   * left, right boundary of letter from A - Z
   * and a random object
    */
    private Random random;
    final int boardWidth;
    final int boardHeight;
    final int numLeft;
    final int numRight;
    final int uppercaseLeft;
    final int uppercaseRight;

    /**
   * Constructs a ComputerAction object with the specified width
   * and height of a board
   * @param boardWidth is the width of board.
   * @param boardHeight is the height of board.
   */
    public ComputerAction(int boardWidth, int boardHeight) {
        this.random = new Random();
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.numLeft = 0;
        this.numRight = boardWidth - 1;
        this.uppercaseLeft = 65;
        this.uppercaseRight = 65 + boardHeight - 1;
    }

    public ComputerAction(int seed, int boardWidth, int boardHeight) {
        this.random = new Random(seed);
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.numLeft = 0;
        this.numRight = boardWidth - 1;
        this.uppercaseLeft = 65;
        this.uppercaseRight = 65 + boardHeight - 1;
    }

      /**
   * add a placement String for ship to place
   * @param src is the random letters we can choose from
   * @param range is the range that random number generates
   * @param ans is the string builder we use
   */
    public void addShipPlacement(String src, int range, StringBuilder ans) { 
            int rowIndex = uppercaseLeft + (int)(random.nextFloat()*(uppercaseRight - uppercaseLeft + 1));
            ans.append((char)rowIndex);

            int colIndex = numLeft + (int)(random.nextFloat()*(numRight - numLeft + 1));
            ans.append(String.valueOf(colIndex));

            int index = random.nextInt(range);
            ans.append(src.substring(index, index+1));
    }

    public void submarinePlacement(StringBuilder ans) {
        addShipPlacement("VH", 2, ans);
    }

    public void destroyerPlacement(StringBuilder ans) {
        addShipPlacement("VH", 2, ans);
    }

    public void battleshipPlacement(StringBuilder ans) {
        addShipPlacement("URDL", 4, ans);
    }

    public void carrierPlacement(StringBuilder ans) {
        addShipPlacement("URDL", 4, ans);
    }

      /**
   * Constructs a ComputerAction object with the specified width
   * and height of a board
   * @param moves is the left number of Move action usage
   * @param scans is the left number of Sonar action usage 
   * @return the action string we choose
   */
    public String createRandomActions(int moves, int scans) {
        int index = 0;
        String src = new String();
        if (moves > 0 && scans > 0) {
            src = "FMS";
            index = random.nextInt(3);
        } 
        
        if (moves > 0 && scans <= 0) {
            src = "FM";
            index = random.nextInt(2);
        }

        if (moves <= 0 && scans > 0) {
            src = "FS";
            index = random.nextInt(2);
        }

        if (moves == 0 && scans == 0) {
            src = "F";
            index = random.nextInt(1);
        }
        return src.substring(index,index+1);
    }

      /**
   * Constructs a coordinate that we can choose for ship move
   * @param theBoard is the board we usue.
   * @return the coordinate we choose for the ship that moves
   */
    public String moveCoordinate(Board<Character> theBoard) {  // coordinate for move action
        Coordinate ans = new Coordinate(0,0);
        StringBuilder stb = new StringBuilder();
        while (true) {
            ans = new Coordinate(randomCoordinate());
            Ship<Character> s = theBoard.selectShip(ans);
            if (s != null) {
                break;
            }
        }
 
        char row = (char)(uppercaseLeft + ans.getRow());
        int col = ans.getColumn();
        stb.append(row);
        stb.append(col);
        return stb.toString();
    }

      /**
   * Constructs a random coordinate for actions 
   * @return the random coordinate that we can fire at or scan
   */
    public String randomCoordinate() {            // coordinate for other action
        StringBuilder ans = new StringBuilder();
        int rowIndex = uppercaseLeft + (int)(random.nextFloat()*(uppercaseRight - uppercaseLeft + 1));
        ans.append((char)rowIndex);

        int colIndex = numLeft + (int)(random.nextFloat()*(numRight - numLeft + 1));
        ans.append(String.valueOf(colIndex));

        return ans.toString();
    }

}
