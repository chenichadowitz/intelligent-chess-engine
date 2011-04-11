package newerGameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;



public class BoardState {

	private HashMap<Position, LinkedList<Piece>> map;
	private HashMap<Position, Piece> pieceMap;
	
	/**
	 * Creates a new BoardState object
	 */
	public BoardState(){
		initializeBoard();
	}
	/**
	 * add's a piece to the BoardState
	 * @param newPiece piece to add
	 */
	public void addPiece(Piece newPiece){
		pieceMap.put(newPiece.getPosition(), newPiece);
		
	}
	/**
	 * returns the piece at a given location
	 * @param square the position of a possible piece
	 * @return returns the piece at that square
	 */
	public Piece getPieceAt(Position square){
		return pieceMap.get(square);
	}
		
	/**
	 * Initializes the map so that each value is an initialized LinkedList<Piece>
	 */
	private void initializeBoard(){
		map = new HashMap<Position, LinkedList<Piece>>(64);
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				map.put(new Position(i,j),new LinkedList<Piece>());
			}
		}
	}
	
	/**
	 * Returns a LinkedList<Piece> for a given square
	 * @param square the square to look for
	 * @return LinkedList<Piece> for the given square
	 */
	public LinkedList<Piece> getPieceList(Position square){
		return map.get(square);
	}
	
	/**
	 * Returns a LinkedList<Piece> for a given square
	 * @param x x-coordinate of the square to look for
	 * @param y y-coordinate of the square to look for
	 * @return LinkedList<Piece> for the given square
	 */
	public LinkedList<Piece> getPieceList(int x, int y){
		return this.getPieceList(new Position(x,y));
	}
		
	/**
	 * Add a Piece to the given square's list
	 * @param square square to add a Piece to
	 * @param p Piece to add to the given square
	 */
	public void addPiece(Position square, Piece p){
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
		initializeBoard();
	}
	
	/**
	 * updates the given squares' boardStatus pieces
	 * @param squares the square to update
	 */
	private void update(Position... squares){
		ArrayList<Piece> piecesToUpdate = new ArrayList<Piece>();
		for(Position square : squares){
			for(Piece p : boardStatus.getPieceList(square)){
				if(!piecesToUpdate.contains(p)){piecesToUpdate.add(p);}
			}
		}
		for(Piece currentPiece: piecesToUpdate){
			removePieceFromBoardState(currentPiece);
			moveFactory.generateMovesfor(currentPiece,this);
			addPieceToBoardState(currentPiece);
		}
	}
	
	
	
}
