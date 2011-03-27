package newGameLogic;

public abstract class Player {
	private Color playerColor;
	private boolean inCheck = false;
	private boolean isInCheckMate = false;
	private String name;
	private Move nextMove = null;

	/**
	 * @return returns the char of the piece to promote the pawn to
	 */
	
	public abstract PieceEnum getPromotion();
	public abstract void ponder(); 
	
	public Move getMove(){return nextMove;}

	/**
	 * @return the inCheck
	 */
	public boolean isInCheck() {
		return inCheck;
	}

	/**
	 * @param inCheck the inCheck to set
	 */
	public void setInCheck(boolean inCheck) {
		this.inCheck = inCheck;
	}

	/**
	 * @return the isInCheckMate
	 */
	public boolean isInCheckMate() {
		return isInCheckMate;
	}

	/**
	 * @param isInCheckMate the isInCheckMate to set
	 */
	public void setInCheckMate(boolean isInCheckMate) {
		this.isInCheckMate = isInCheckMate;
	}

	/**
	 * for use with CLI
	 * @return the nextMove
	 */
	public abstract Move getNextMove();

	/**
	 * for use with GUI
	 * @param nextMove the nextMove to set
	 */
	public void setNextMove(Move nextMove){
		this.nextMove = nextMove;
	}

	/**
	 * @return the player's color
	 */
	public Color getPlayerColor() {
		return playerColor;
	}

	/**
	 * @return the player's name
	 */
	public String getName() {
		return name;
	};

}
