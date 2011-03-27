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
	
	/**
	 * sets the player thinking sets nextMove to a non-null move
	 */	
	public abstract void ponder(); 
	
	/**
	 * @return the player's next move
	 */
	public Move getMove(){return nextMove;}

	/**
	 * @return the player's check status
	 */
	public boolean isInCheck() {
		return inCheck;
	}

	/**
	 * @param inCheck the new check status of the player
	 */
	public void setInCheck(boolean inCheck) {
		this.inCheck = inCheck;
	}

	/**
	 * @return the mate status of the player
	 */
	public boolean isInCheckMate() {
		return isInCheckMate;
	}

	/**
	 * @param isInCheckMate the checkmate status of the player
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
