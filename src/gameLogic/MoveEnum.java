package gameLogic;

import java.util.Arrays;

public enum MoveEnum {

	Listen { 
		private String description = "Listener";
	},
	Cover {
		private String description = "Cover";
	},
	Take {
		private String description = "Take";
	},
	Move {
		private String description = "Move";
	};
	
	private int[] OrigPos 	= new int[2];
	private int[] FinalPos 	= new int[2];
	private Player owner;
	private boolean color;
	private Board currentBoard;
	private Piece movingPiece;
	private boolean oldCastle;
	private String description;
	private boolean putInCheck;
	
	void setOrigPos(int[] square){
		this.OrigPos = square.clone();
	}
	void setOrigPos(int x, int y){
		this.OrigPos[0] = x;
		this.OrigPos[1] = y;
	}
	int[] getOrigPos(){
		return OrigPos;
	}
	void setFinalPos(int[] square){
		this.FinalPos = square.clone();
	}
	void setFinalPos(int x, int y){
		this.FinalPos[0] = x;
		this.FinalPos[1] = y;
	}	
	int[] getFinalPos(){
		return FinalPos;
	}

	/**
	 * @return the owner
	 */
	Player getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	void setOwner(Player owner) {
		this.owner = owner;
	}

	/**
	 * @return the color
	 */
	boolean isColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	void setColor(boolean color) {
		this.color = color;
	}

	/**
	 * @return the currentBoard
	 */
	Board getCurrentBoard() {
		return currentBoard;
	}

	/**
	 * @param currentBoard the currentBoard to set
	 */
	void setCurrentBoard(Board currentBoard) {
		this.currentBoard = currentBoard;
		this.movingPiece = this.currentBoard.pieceAt(OrigPos);
		this.color = this.movingPiece.color;
		this.owner = this.currentBoard.playerMap.get(movingPiece.getColor());
	}

	/**
	 * @return the movingPiece
	 */
	Piece getMovingPiece() {
		return movingPiece;
	}

	/**
	 * @param movingPiece the movingPiece to set
	 */
	void setMovingPiece(Piece movingPiece) {
		this.movingPiece = movingPiece;
	}

	/**
	 * @return the oldCastle
	 */
	boolean isOldCastle() {
		return oldCastle;
	}

	/**
	 * @param oldCastle the oldCastle to set
	 */
	void setOldCastle(boolean oldCastle) {
		this.oldCastle = oldCastle;
	}

	/**
	 * @return the description
	 */
	String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the putInCheck
	 */
	boolean isPutInCheck() {
		return putInCheck;
	}

	/**
	 * @param putInCheck the putInCheck to set
	 */
	void setPutInCheck(boolean putInCheck) {
		this.putInCheck = putInCheck;
	}
	
	public String toString(){
		return description;
	}
	
	boolean equals(MoveEnum test){
		boolean equals = true;
		equals = equals && Arrays.equals(OrigPos, test.getOrigPos());
		equals = equals && Arrays.equals(FinalPos, test.getFinalPos());
		equals = equals && (movingPiece.equals(test.getMovingPiece()));
		equals = equals && (owner == test.getOwner());
		equals = equals && (currentBoard.equals(test.getCurrentBoard()));
		return equals;
	}
	
}
