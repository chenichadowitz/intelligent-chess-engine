package newerGameLogic;

import java.util.ArrayList;

public class Move implements Cloneable{
	private MoveEnum type;
	private Position OrigPos;
	private Position FinalPos;
	private boolean oldCastle;
	private String notation;
	private boolean putInCheck;
	private Piece affectedPiece;
	
	public Move(Position start, Position end, MoveEnum moveType){
		OrigPos = start;
		FinalPos = end;
		type = moveType;
		putInCheck = false;
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
	public ArrayList<Position> getAffectedPositions() {
		ArrayList<Position> positions = new ArrayList<Position>();
		positions.add(OrigPos); 
		positions.add(FinalPos);
		if(type == MoveEnum.EnPassant){
			positions.add(affectedPiece.getPosition());
		} else if(type == MoveEnum.Castle){
			positions.add(affectedPiece.getPosition());
			//TODO: Rooks final position
			//positions.add(e)
		}
		return positions;
	}
}
