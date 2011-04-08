package newGameLogic;

public abstract class Player {
	private WBColor playerColor;
	private boolean inCheck = false;
	private boolean isInCheckMate = false;
	private String name;
	protected Move nextMove;

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
	 * sets the move the player is to make
	 * @param nextMove the nextMove to set
	 */
	public void setNextMove(Move nextMove){
		//this.nextMove = nextMove;
	}

	/**
	 * @return the player's color
	 */
	public WBColor getPlayerColor() {
		return playerColor;
	}

	/**
	 * @return the player's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * sets the players name
	 * @param name name to set
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * sets the player's color
	 */
	public void setColor(WBColor c){
		playerColor = c;
	}

}
