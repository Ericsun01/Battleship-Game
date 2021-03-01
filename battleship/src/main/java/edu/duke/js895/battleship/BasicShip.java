package edu.duke.js895.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class BasicShip<T> implements Ship<T>{
	// This field represent all area of a ship and if this location has been hit/miss
	// For Boolean, there are True, False, null
	protected HashMap<Coordinate, Boolean> myPieces;
	protected ShipDisplayInfo<T> myDisplayInfo;  // display info to owner
	protected ShipDisplayInfo<T> enemyDisplayInfo;  // display info to enemy
	ArrayList<Coordinate> piecesIndex;  // record index with Coordination (by list index) 

/**
   * Constructs a Coordinate, given the coordinate player choose.
   * @param where is location of this ship
   * @param myDisplayInfo display info from my own perspective
   * @param enemyDisplayInfo display info from my enemy's perspective
   */
	
	public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
		this.myPieces = new HashMap<Coordinate, Boolean>();
		this.piecesIndex = new ArrayList<Coordinate>();
		for (Coordinate c: where) {
			this.myPieces.put(c, false);
			this.piecesIndex.add(c);
		}
		this.myDisplayInfo = myDisplayInfo;
		this.enemyDisplayInfo = enemyDisplayInfo;
	}


	   /**
 * 
	/**
   * check whether a ship occupy this location
   * @param c is location for checking
   * @throws IllegalArgumentException if The coordinate is not a part of this ship.
   */

	protected void checkCoordinateInThisShip(Coordinate c) {
		if (myPieces.get(c) == null) {
			throw new IllegalArgumentException("The coordinate is not a part of this ship");
		}
	}

	/**
   * check whether a ship occupy this location
   * @param where is location for checking
   * @return if there is a ship on this location
   */
	@Override
	public boolean occupiesCoordinates(Coordinate where) {
		// Check if this ship occupies the given coordinate.
		return myPieces.get(where) != null;
	}

	/**
   * check whether a ship get hit on all pieces
   * @return if there is a ship sink because of geting hit on all pieces
   */
	@Override
	public boolean isSunk() {
		// Check if this ship has been hit in all of its locations meaning it has been
   		// sunk.
		boolean ans = true;
		for (Map.Entry<Coordinate, Boolean> entry: myPieces.entrySet()) {
			ans = ans && entry.getValue();  
		}
		return ans;
	}

	/**
   * Make this ship record that it has been hit at the given coordinate. The
   * specified coordinate must be part of the ship.
   */
	@Override
	public void recordHitAt(Coordinate where) {
		checkCoordinateInThisShip(where);
		myPieces.put(where, true);		
	}

	/**
   * Check if this ship was hit at the specified coordinates. The coordinates must
   * be part of this Ship.
   */
	@Override
	public boolean wasHitAt(Coordinate where) {
		checkCoordinateInThisShip(where);
		return myPieces.get(where);
	}

	   /**
 * Return whether a ship occupy this location
 * Return the view-specific information at the given coordinate. This coordinate
 * must be part of the ship.
 * @param where indicate the coordiinate we want to get its display content
 * @param myShip indicate if this is my ship or enemy's
 * @return the display info
 */
	@Override
	public T getDisplayInfoAt(Coordinate where, boolean myship) {
		checkCoordinateInThisShip(where);
		if (myship) {
			return myDisplayInfo.getInfo(where, myPieces.get(where)); // "if" sentence is in getInfo function
		}
		return enemyDisplayInfo.getInfo(where, myPieces.get(where));
	} 

	  /**
   * Get all of the Coordinates that this Ship occupies.
   * @return An Iterable with the coordinates that this Ship occupies
   */
	@Override
	public Iterable<Coordinate> getCoordinates() {
		return myPieces.keySet();
	}
    
	 /**
   * move the current ship by changing its myPieces and piecesIndex
   */
	@Override
	public void shipMoveTo(Ship<T> testShip) {
		ArrayList<Coordinate> moveTo = testShip.getPiecesIndex();
		HashMap<Coordinate, Boolean> template = new HashMap<>();
		int len = this.piecesIndex.size();
		for (int i=0; i<len; i++) {
			Coordinate key = piecesIndex.get(i);			
			Boolean value = myPieces.get(key);
			template.put(moveTo.get(i), value);			
		}
		this.myPieces = template;
		this.piecesIndex = moveTo;
	}

	 /**
   * Get the index list with Coordinates that this Ship occupies.
   * @return An ArrayList with the coordinates that this Ship occupies
   */
	@Override
	public ArrayList<Coordinate> getPiecesIndex() {
		return this.piecesIndex;
	}
}
