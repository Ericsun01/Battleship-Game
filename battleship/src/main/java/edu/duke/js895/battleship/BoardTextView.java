package edu.duke.js895.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of a Board (i.e., converting it to a
 * string to show to the user). It supports two ways to display the Board: one
 * for the player's own board, and one for the enemy's board.
 */

public class BoardTextView {
    /**
   * The Board to display
   */
    private final Board<Character> toDisplay;
    /**
   * Constructs a BoardView, given the board it will display.
   * @param toDisplay is the Board to display
   * @throws IllegalArgumentException if the board is larger than 10X26
   */
    public BoardTextView (Board<Character> toDisplay) {
        this.toDisplay = toDisplay;
        if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
            throw new IllegalArgumentException(
                "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
          }
    }

   /**
   * This makes the header line, e.g. 0|1|2|3|4\n
   * 
   * @return the String that is the header line for the given board
   */
    String makeHeader() {
        StringBuilder ans = new StringBuilder("  "); // README shows two spaces at 
        String sep = ""; //start with nothing to separate, then switch to | to separate
        for (int i=0; i<toDisplay.getWidth(); i++) {
            ans.append(sep);
            ans.append(i);
            sep="|";
        }
        ans.append("\n");
        return ans.toString();
    }

     /**
   * Given a set of functions to choose from, return the board display content
   * @param getSquareFn is the displayBoard functions we can choose from.
   * @return the board display content String
   */
    protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
        StringBuilder ans = new StringBuilder("");
        ans.append(makeHeader());
        String letter;  // the letter presents at begin/end of each row
        for (int i=0; i<toDisplay.getHeight(); i++) {
            letter = String.valueOf((char)(65+i)); // To acquire letter in each row
            ans.append(letter);
            ans.append(" ");
            String sep = "";
            for (int j=0; j<toDisplay.getWidth(); j++) {  // To make enough space for each row
                ans.append(sep);
                Coordinate loc = new Coordinate(i,j);
                if (getSquareFn.apply(loc) != null) {     // To judge if a ship is here
                    ans.append(getSquareFn.apply(loc).toString()); // If it is, print it out
                } else {
                    ans.append(" ");
                }         
                sep="|";
            }
            ans.append(" ");
            ans.append(letter);     
            ans.append("\n");
        }
        // add the bottom
        ans.append(makeHeader());
        return ans.toString();
    }

    /**
   * display the board content that display for my own perspective
   * @return my own board display content String
   */
    public String displayMyOwnBoard() {
        return displayAnyBoard((c)->toDisplay.whatIsAtForSelf(c));
    }

     /**
   * display the board content that shows enemy
   * @return enemy's board display content String
   */
    public String displayEnemyBoard() {
        return displayAnyBoard((c)->toDisplay.whatIsAtForEnemy(c));
    }

    public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
        String myOwnBoardView = this.displayMyOwnBoard();  // all ships, hit
        String enemyBoardView = enemyView.displayEnemyBoard(); // only hit/miss
        StringBuilder ans =  new StringBuilder();
        String[] ownView = myOwnBoardView.split("\n");
        String[] eneView = enemyBoardView.split("\n");
        int length = ownView.length;
        int W = this.toDisplay.getWidth();

        ans.append("     "); // 5 spaces
        ans.append(myHeader);
        int inter = 2*W+17-myHeader.length();
        for (int i=0; i<inter; i++) {
            ans.append(" ");
        }
        ans.append(enemyHeader+"\n");

        ans.append(ownView[0]);  // special treating the first line, actually 20 spaces apart
        for (int k=0; k<18; k++) {
            ans.append(" ");
        }
        ans.append(eneView[0]);
        ans.append("\n");

        for (int i=1; i<length-1; i++) {
            ans.append(ownView[i]);
            for (int j=0; j<16; j++) {
                ans.append(" ");
            }
            ans.append(eneView[i]);
            ans.append("\n");
        }

        ans.append(ownView[length-1]);  // special treating the last line, actually 20 spaces apart
        for (int k=0; k<18; k++) {
            ans.append(" ");
        }
        ans.append(eneView[length-1]);
        ans.append("\n");

        return ans.toString();
    }
    
}
