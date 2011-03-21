package gameLogic;

public abstract class Player {
	protected boolean color;
	protected boolean checkStatus = false;
	protected boolean mated = false;
	protected String name = "dummy player " + color;
	protected Board myBoard;
	public boolean getCheckStatus(){ return checkStatus; }
	public boolean isMated(){ return mated; }
	public void setCheckStatus(boolean inCheck){ checkStatus = inCheck;}
	public void setMatedStatus(boolean isMated){ mated = isMated; }
	public void setName(String newName){
		name = newName;
	}
	public boolean getColor(){return color;}
	public Board getBoard(){return myBoard;}
	public void setBoard(Board newBoard){
		myBoard = newBoard;
	}
	public String toString(){
		return name;
	}

	public char getPromotion() {
		return 'Q';
	}
	public abstract Listener getMove();
	
}
