package newGameLogic;

import java.util.ArrayList;
import java.util.Arrays;

import main.Output;

public class Piece {
	private Color pieceColor;
	private PieceEnum type;
	private int value;
	private boolean castle = false;
	private int[] position = new int[2]; // [x-coord,y-coord]
	private ArrayList<Move> moves = new ArrayList<Move>();
	
	/**
	 *  creates a piece
	 * @param ownerColor the color of the piece's owner
	 * @param pieceType the type of piece it is
	 * @param xwhere the x-coordinate of the piece
	 * @param ywhere the y-coordinate of the piece
	 */
	public Piece(Color ownerColor, PieceEnum pieceType, int xwhere, int ywhere){
		pieceColor = ownerColor;
		type = pieceType;
		position[0] = xwhere;
		position[1] = ywhere;
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
	public int[] getPosition() {
		return position;
	}
	/**
	 * @param position the position to set (only works for {0-7,0-7})
	 */
	public void setPosition(int[] position) {
		if(position[0] < 0 || position[0] >7 || position[1] < 0 || position[1] > 7){
			Output.debug("Warning: set position off board... FAILED", 1);
		} else {this.position = position;}
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
	public Color getPieceColor() {
		return pieceColor;
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
}
