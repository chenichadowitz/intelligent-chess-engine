package newGameLogic;

import java.util.Arrays;
import main.Output;

public class Move {
	MoveEnum type;
	private int[] OrigPos = new int[2];
	private int[] FinalPos =  new int[2];
	private boolean oldCastle;
	private String notation;
	private boolean putInCheck;
	private Piece affectedPiece;
	
	public Move(int x1, int y1, int x2, int y2, MoveEnum moveType){
		if(x1>=0 && x1<=7 && y1>=0 && y1<=7 && x2>=0 && x2<=7 && y2>=0 && y2<=7){
			OrigPos[0] = x1;
			OrigPos[1] = y1;
			FinalPos[0] = x2;
			FinalPos[1] = y2;
			type = moveType;
			putInCheck = false;
		}
		else {
			Output.debug("Warning: move created off board", 1);
			type = MoveEnum.Rubbish;
		}
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
	public Move(Piece mover, int[] newSquare, MoveEnum moveType){
		this(mover.getPosition()[0],mover.getPosition()[1],newSquare[0],newSquare[1],moveType);
	}
	public Move(Piece mover, int x, int y, MoveEnum moveType){
		this(mover.getPosition()[0],mover.getPosition()[1],x,y,moveType);
	}
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
		return Arrays.equals(OrigPos, m.OrigPos) && Arrays.equals(FinalPos, m.FinalPos);
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
	public int[] getOrigPos() {
		return OrigPos;
	}


	/**
	 * @return the finalPos
	 */
	public int[] getFinalPos() {
		return FinalPos;
	}


	/**
	 * @return the notation
	 */
	public String getNotation() {
		return notation;
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
	
	
	
	
}
