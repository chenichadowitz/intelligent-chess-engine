package newerGameLogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Piece implements Cloneable{
	private WBColor pieceColor;
	private PieceEnum type;
	private int value;
	private boolean castle = false;
	private Position position;
	private HashMap<Position,Move> moves = new HashMap<Position, Move>();
	
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
	
	public Piece(WBColor ownerColor, PieceEnum pieceType, int x, int y){
		this(ownerColor,pieceType,new Position(x,y));
	}
	
	/**
	 * returns move of the piece to the given square
	 * @param square where the move is to
	 * @return the move to that square
	 */
	public Move getMoveTo(Position square){
		return moves.get(square);
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
	public Collection<Move> getMoves() {
		return moves.values();
	}
	/**
	 * @param moves the moves to set
	 */
	public void setMoves(ArrayList<Move> moves) {
		for(Move currentMove: moves){
			this.moves.put(currentMove.getFinalPos(), currentMove);
		}
	}
	/**
	 * removes all moves
	 */
	public void resetMoves(){
		moves = new HashMap<Position, Move>();
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
