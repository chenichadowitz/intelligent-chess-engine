package gameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import main.Output;

public class BoardState {

	private HashMap<int[], LinkedList<Piece>> map;

	/**
	 * Creates a new BoardState object
	 */
	public BoardState(){
		 map = new HashMap<int[], LinkedList<Piece>>(64);
	}
	
	/**
	 * Returns a LinkedList<Piece> for a given square
	 * @param square the square to look for
	 * @return LinkedList<Piece> for the given square
	 */
	public LinkedList<Piece> getPieceList(int[] square){
		return map.get(square);
	}
	
	/**
	 * Returns a LinkedList<Piece> for a given square
	 * @param x x-coordinate of the square to look for
	 * @param y y-coordinate of the square to look for
	 * @return LinkedList<Piece> for the given square
	 */
	public LinkedList<Piece> getPieceList(int x, int y){
		int[] square = {x, y};
		return this.getPieceList(square);
	}
		
	/**
	 * Add a Piece to the given square's list
	 * @param square square to add a Piece to
	 * @param p Piece to add to the given square
	 */
	public void addPiece(int[] square, Piece p){
		map.get(square).add(p);
	}
	
	/**
	 * Add a Piece to the given square's list
	 * @param x x-coordinate of the square to add to
	 * @param y y-coordinate of the square to add to
	 * @param p Piece to add to the given square
	 */
	public void addPiece(int x, int y, Piece p){
		int[] square = {x,y};
		this.addPiece(square, p);
	}
	
	/**
	 * Add the given move's Piece to the given square
	 * @param square square to add the move's Piece to
	 * @param action the move whose Piece to add
	 */
	public void addMove(int[] square, Listener action){
		LinkedList<Piece> pieces = this.getPieceList(square);
		if(pieces.contains(action.movingPiece)){
			Output.debug("WARNING: " +action.movingPiece+ " already exists on boardState", 1);
		}
		map.get(square).add(action.movingPiece);
	}
	
	/**
	 * Add the given move's Piece to the given square
	 * @param x x-coordinate of the square to add the move's Piece to
	 * @param y y-coordinate of the square to add the move's Piece to
	 * @param action the move whose Piece to add
	 */
	public void addMove(int x, int y, Listener action){
		int[] square = {x,y};
		this.addMove(square, action);
	}
	
	/**
	 * Clear the BoardState and re-initialize it to empty
	 */
	public void clearBoardState(){
		map = new HashMap<int[], LinkedList<Piece>>();
	}
	
	/**
	 * Update the given squares to reflect any changes that have been made
	 * @param squares the squares to update (any number of 2-element integer arrays)
	 */
	public void update(int[]... squares){
		ArrayList<Piece> piecesToUpdate = new ArrayList<Piece>();
		for(int[] square : squares){
			for(Piece p : this.getPieceList(square)){
				if(!piecesToUpdate.contains(p)){piecesToUpdate.add(p);}
			}
		}
		for(Piece currentPiece: piecesToUpdate){
			currentPiece.removeFromBoardState();
			currentPiece.generateMoves();
			currentPiece.addToBoardState();
		}
	}	
	
}
