package newerGameLogic;

import java.util.Arrays;

import main.Output;

public class Move implements Cloneable{
	private MoveEnum type;
	private Position OrigPos;
	private Position FinalPos;
	private boolean oldCastle;
	private String notation;
	private boolean putInCheck;
	private Piece affectedPiece;
	
	public Move(int x1, int y1, int x2, int y2, MoveEnum moveType){
		if(between0And7(x1,x2,y1,y2)){
			OrigPos = new Position(x1,y1);
			FinalPos = new Position(x2,y2);
			type = moveType;
			putInCheck = false;
		}
		else {
			Output.debug("Warning: move created off board", 1);
			type = MoveEnum.Rubbish;
		}
	}
	
	/**
	 * checks if a list of numbers is between 0 and 7 i.e. on the board
	 * @param numbers the numbers to check
	 * @return if all numbers satisfy the requirements
	 */
	private boolean between0And7(int... numbers) {
		for(int number: numbers){
			if(number > 7 || number < 0){return false;}
		}
		return true;
	}


	public Move(int[] square1, int[] square2, MoveEnum moveType){
		this(square1[0],square1[1],square2[0], square2[1],moveType);
	}
	public Move(int[] square, int x, int y, MoveEnum moveType){
		this(square[0],square[1],x,y,moveType);
	}
	public Move(int[] squareAB, MoveEnum moveType){
		this(squareAB[0],squareAB[1],squareAB[2], squareAB[3],moveType);
	}
	/*
	public Move(Piece mover, int[] newSquare, MoveEnum moveType){
		this(mover.getPosition()[0],mover.getPosition()[1],newSquare[0],newSquare[1],moveType);
	}
	public Move(Piece mover, int x, int y, MoveEnum moveType){
		this(mover.getPosition()[0],mover.getPosition()[1],x,y,moveType);
	}
	*/
	/**
	 * returns equal if moves are to and from the same squares
	 * NOTE: not if the move is to the same square that it is from
	 * @param m move to test equality to
	 * @return returns equality
	 */
	public boolean equals(Move m) {
		if (this == m)
			return true;
		if (m == null)
			return false;
		return OrigPos == m.OrigPos && FinalPos == m.FinalPos;
	}


	/**
	 * @return the type
	 */
	public MoveEnum getType() {
		return type;
	}


	/**
	 * @return the origPos
	 */
	public Position getOrigPos() {
		return OrigPos;
	}


	/**
	 * @return the finalPos
	 */
	public Position getFinalPos() {
		return FinalPos;
	}


	/**
	 * @return the notation
	 */
	public String getNotation() {
		return notation;
	}
	
	/**
	 * set the notation for the move
	 * @param notation notation for the move
	 */
	public void setNotation(String notation){
		this.notation = notation;
	}

	/**
	 * @param oldCastle the oldCastle to set
	 */
	public void setOldCastle(boolean oldCastle) {
		this.oldCastle = oldCastle;
	}


	/**
	 * @return the oldCastle
	 */
	public boolean isOldCastle() {
		return oldCastle;
	}


	/**
	 * @param putInCheck the putInCheck to set
	 */
	public void setPutInCheck(boolean putInCheck) {
		this.putInCheck = putInCheck;
	}


	/**
	 * @return the putInCheck
	 */
	public boolean isPutInCheck() {
		return putInCheck;
	}


	/**
	 * @param affectedPiece the affectedPiece to set
	 */
	public void setAffectedPiece(Piece affectedPiece) {
		this.affectedPiece = affectedPiece;
	}


	/**
	 * @return the affectedPiece
	 */
	public Piece getAffectedPiece() {
		return affectedPiece;
	}
	/**
	 * clones a move
	 * @return returns a clone of this piece
	 */
	public Move clone(){
		return new Move(OrigPos,FinalPos,type);
	}
}
