package newerGameLogic;

import java.util.ArrayList;
import java.util.Arrays;

public class Piece implements Cloneable{
	private WBColor pieceColor;
	private PieceEnum type;
	private int value;
	private boolean castle = false;
	private Position position; // [x-coord,y-coord]
	private ArrayList<Move> moves = new ArrayList<Move>();
	
	/**
	 *  creates a piece
	 * @param ownerColor the color of the piece's owner
	 * @param pieceType the type of piece it is
	 * @param where the position of the piece
	 */
	public Piece(WBColor ownerColor, PieceEnum pieceType,Position where){
		pieceColor = ownerColor;
		type = pieceType;
		position = where;
		if(type == PieceEnum.King || type == PieceEnum.Rook){castle = true;}
		else{castle = false;}
	}
	
	/**
	 * returns move of the piece to the given square
	 * @param square where the move is to
	 * @return the move to that square
	 */
	public Move getMoveTo(int[] square){
		for(Move thisMove: moves){
			if(Arrays.equals(thisMove.getFinalPos(), square)){return thisMove;}
		}
		return null; //no move to that sqaure BOOM!!!
	}
	
	/**
	 * @return returns w or b, then Piece type
	 */
	public String toString(){
		return pieceColor.toString() + type.toString();
	}
	/**
	 * @return the type
	 */
	public PieceEnum getType() {
		return type;
	}
	/**
	 * @param type the type to set (only works if is pawn)
	 */
	public void setType(PieceEnum type) {
		if(this.type == PieceEnum.Pawn){
			this.type = type;
		}
	}
	/**
	 * @return the castle
	 */
	public boolean canCastle() {
		return castle;
	}
	/**
	 * @param castle the castle to set
	 */
	public void setCastle(boolean castle) {
		this.castle = castle;
	}
	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}
	/**
	 * @param position the position to set (only works for {0-7,0-7})
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * @return the moves
	 */
	public ArrayList<Move> getMoves() {
		return moves;
	}
	/**
	 * @param moves the moves to set
	 */
	public void setMoves(ArrayList<Move> moves) {
		this.moves = moves;
	}
	/**
	 * @return the pieceColor
	 */
	public WBColor getPieceColor() {
		return pieceColor;
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	public boolean equals(Object obj){
		if(obj == null) return false;
		Piece p = (Piece) obj;
		return (p.pieceColor == this.pieceColor && 
				p.type == this.type);
	}
	
	public boolean equalsPiece(Piece p){
		if(p == null) return false;
		return (p.pieceColor == this.pieceColor && 
				p.type == this.type && 
				p.position == this.position);
	}
	
	public int hashCode(){
		return ( pieceColor.hashCode() + 31 * type.hashCode() );
	}
}
