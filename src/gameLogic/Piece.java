package gameLogic;

import java.util.*;

import main.Output;
public abstract class Piece {
	protected boolean color; // true = white; false = black
	protected String pieceType;
	protected int value;
	protected boolean castle = false;
	protected int[] position = new int[2]; // [x-coord,y-coord]
	protected ArrayList<Listener> moves = new ArrayList<Listener>();
	public ArrayList<Listener> getMoves(){ return moves;}
	protected Board currentBoard;
	
	public  boolean canCastle() {return castle;}
	public  boolean getColor()	{return color;} 
	public  char type(){return pieceType.charAt(0);}
	public  int getValue()		{return value;}
	public  int[] getPosition()	{return position;}
	public  void setBoard(Board newBoard){ currentBoard = newBoard;}
	public Board getBoard(){ return currentBoard;}
	public  void setPosition(int[] square){ position = square;}
	public void addToBoardState(){
		for(Listener action: moves){			
			action.addMoveToBS();
		}
		Output.debug(this + " added to boardState",3);
	}
	public void removeFromBoardState(){
		for(Listener action: moves){
			action.remMoveFrBS();
		}
		Output.debug(this +" removed from boardState",3);
	}
	public  boolean processSquare(int x, int y){ //returns true if square is empty
		int[] square = {x,y};
		boolean[] status = currentBoard.statusOfSquare(square);
		if(status[0] || status[1]){
			Listener newMove = PieceMaker.MakeMove(currentBoard,this,square);
			if(moves.contains(newMove)){
				Output.debug("WARNING: " +this+ " already has that Move", 1);
			}			
			moves.add(PieceMaker.MakeMove(currentBoard,this,square));
			if(!status[0] && status[1]){return true;}
		}		
		return false;
	}
	public  void generateMoves(){
		Output.debug(this + " generating moves", 3);
		moves = new ArrayList<Listener>();
	}

	public Listener getMoveTo(int[] square){
		for(Listener finder: moves){
			if(Arrays.equals(finder.FinalPos,square)){return finder;}
		}
		return null; // no move to 'square'
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
