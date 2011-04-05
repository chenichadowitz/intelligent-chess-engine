package newGameLogic;

import java.util.HashMap;
import java.util.LinkedList;


public class BoardState {

	private HashMap<Position, LinkedList<Piece>> map;

	/**
	 * Creates a new BoardState object
	 */
	public BoardState(){
		 map = new HashMap<Position, LinkedList<Piece>>(64);
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
	public void addPiece(Position square, Piece p){
		if(map.get(square) == null){map.put(square,new LinkedList<Piece>());}
		map.get(square).add(p);
	}
	
	/**
	 * Add a Piece to the given square's list
	 * @param x x-coordinate of the square to add to
	 * @param y y-coordinate of the square to add to
	 * @param p Piece to add to the given square
	 */
	public void addPiece(int x, int y, Piece p){
		this.addPiece(new Position(x,y), p);
	}
	/**
	 * Remove a Piece from the given square's list
	 * @param square square to remove the Piece from
	 * @param p Piece to remove from the given square
	 */
	public void removePiece(Position square, Piece p){
		map.get(square).remove(p);
	}
	
	/**
	 * Remove a Piece from the given square's list
	 * @param x x-coordinate of the square to remove from
	 * @param y y-coordinate of the square to remove from
	 * @param p Piece to remove from the given square
	 */
	public void removePiece(int x, int y, Piece p){
		this.removePiece(new Position(x,y), p);
	}
	
	/**
	 * Clear the BoardState and re-initialize it to empty
	 */
	public void clearBoardState(){
		map = new HashMap<Position, LinkedList<Piece>>();
	}
}
