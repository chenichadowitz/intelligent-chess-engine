package ice;

import java.util.*;
public abstract class Piece {
	protected boolean color; // true = white; false = black
	protected String pieceType;
	protected int value;
	protected boolean castle = false;
	protected int[] position = new int[2]; // [x-coord,y-coord]
	protected ArrayList<Move> possibleMoves = new ArrayList<Move>();
	protected Board currentBoard;
	
	public  boolean canCastle() {return castle;}
	public  boolean getColor()	{return color;} 
	public  int getValue()		{return value;}
	public  int[] getPosition()	{return position;}
	public  void setBoard(Board newBoard){ currentBoard = newBoard;}
	public  void setPosition(int[] square){ position = square;}
	public void addToBoardState(){
		for(Move action: possibleMoves){
			currentBoard.boardState[action.FinalPos[0]][action.FinalPos[1]].add(this);
		}
		Driver.debug(this + " added to boardState",3);
	}
	public void removeFromBoardState(){
		for(Move action: possibleMoves){
			currentBoard.boardState[action.FinalPos[0]][action.FinalPos[1]].remove(this);
		}
		Driver.debug(this +" removed from boardState",3);
	}
	public  boolean processSquare(int x, int y){ //returns true if square is empty
		int[] square = {x,y};
		boolean[] status = currentBoard.statusOfSquare(square);
		if(status[0] || status[1]){
			possibleMoves.add(new Move(currentBoard,this,square));
			if(!status[0] && status[1]){return true;}
		}		
		return false;
	}
	public  void generateMoves(){
		Driver.debug(this + " generating moves", 3);
		possibleMoves = new ArrayList<Move>();
	}

	public String toString(){
		if(color){
			return "w"+pieceType;
		} else {
			return "b"+pieceType;
		}
	}
	public abstract Piece clone(); 
}
