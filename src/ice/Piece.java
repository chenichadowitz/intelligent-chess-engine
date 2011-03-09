package ice;

import java.util.*;
public abstract class Piece {
	protected boolean color; // true = white; false = black
	protected String pieceType;
	protected int value;
	protected boolean castle = false;
	protected int[] position = new int[2]; // [x-coord,y-coord]
	protected ArrayList<Move> possibleMoves = new ArrayList<Move>();
/*	
	protected ArrayList<Integer[]> moves = new ArrayList<Integer[]>(); //[[x1,y1][x2,y2]...]
	protected ArrayList<Integer[]> takes = new ArrayList<Integer[]>(); //[[x1,y1][x2,y2]...]
	protected ArrayList<Integer[]> cover = new ArrayList<Integer[]>();
*/	
	protected Board currentBoard;
	
	public  boolean canCastle() {return castle;}
	public  boolean getColor()	{return color;} 
	public  int getValue()		{return value;}
	public  int[] getPosition()	{return position;}
/*	public  ArrayList<Integer[]> getMoves() 	{return moves;}
	public  ArrayList<Integer[]> getTakes()		{return takes;}
	public  ArrayList<Integer[]> getCover()		{return cover;}
*/	public  void setBoard(Board newBoard){ currentBoard = newBoard;}
	public  void setPosition(int[] square){ position = square;}
	
	/*public boolean contains(ArrayList<Integer[]> haystack, int[] needle){
		Integer[] sqr = new Integer[2];
		sqr[0] = needle[0]; sqr[1] = needle[1];
		for(Integer[] move : haystack){
			if(Arrays.equals(sqr, move)){
				return true;
			}
		}
		return false;
	}
	*/
	/*public boolean move(Move action){
		int[] sqaureAB = {position[0], position[1], newSquare[0], newSquare[1]};
		if((contains(moves, newSquare) || contains(takes, newSquare)) && currentBoard.getTurn() == color){
			if(!willBeInCheck(color, sqaureAB)){
				removeFromBoardState();
				if(contains(takes, newSquare)){currentBoard.takePiece(currentBoard.pieceAt(newSquare));}
				position = newSquare;
				Driver.debug(this + " moved to " + newSquare[0] + " " + newSquare[1],2);
				addToBoardState();
				castle = false;
				return true;
			}
			Driver.debug("cannot move into check", 2);
		}
		Driver.debug("move failed",2);	
		return false;
		
		if(possibleMoves.contains(action)){
			return action.execute();
		} else {return false;}
	}*/
	public void addToBoardState(){
	/*	for(Integer[] square: moves){
			currentBoard.boardState[square[0]][square[1]].add(this);
		}
		for(Integer[] square: takes){
			currentBoard.boardState[square[0]][square[1]].add(this);
		}
		for(Integer[] square: cover){
			currentBoard.boardState[square[0]][square[1]].add(this);
		}
		*/
		for(Move action: possibleMoves){
			currentBoard.boardState[action.FinalPos[0]][action.FinalPos[1]].add(this);
		}
		Driver.debug(this + " added to boardState",3);
	}
	public void removeFromBoardState(){
	/*	for(Integer[] square: moves){
			currentBoard.boardState[square[0]][square[1]].remove(this);
		}
		for(Integer[] square: takes){
			currentBoard.boardState[square[0]][square[1]].remove(this);
		}
		for(Integer[] square: cover){
			currentBoard.boardState[square[0]][square[1]].remove(this);
		}
		*/
		for(Move action: possibleMoves){
			currentBoard.boardState[action.FinalPos[0]][action.FinalPos[1]].remove(this);
		}
		Driver.debug(this +" removed from boardState",3);
	}
	public  boolean processSquare(int x, int y){ //returns true if square is empty
		int[] square = {x,y};
		boolean[] status = currentBoard.statusOfSquare(square);
		
	/*	if (status[0] && (status[1] == !color)) {
			takes.add(new Integer[] {square[0],square[1]});
		}
		else if (status[0] && (status[1] == color)){
			cover.add(new Integer[] {square[0],square[1]});
		}
		else if (!status[0] && status[1]){
			moves.add(new Integer[] {square[0],square[1]});
			return true;
		}
	*/
		if(status[0] || status[1]){
			possibleMoves.add(new Move(currentBoard,this,square));
			if(!status[0] && status[1]){return true;}
		}		
		return false;
	}
/*	protected boolean willBeInCheck(boolean colorOfPlayer, int[] possibleMove){
		Driver.debug("checking if move " + possibleMove[0] + " " 
				+ possibleMove[1] + " " + possibleMove[2] + " " 
				+ possibleMove[3] + " " + "results in check", 3);
		staticBoard checkChecker = new staticBoard(currentBoard, possibleMove);
		Driver.debug("/////staticBoard exited",3);
		Driver.debug("checkStatus is = " + checkChecker.isPlayerInCheck(colorOfPlayer),3);
		return checkChecker.isPlayerInCheck(colorOfPlayer);
	}
	*/
	public  void generateMoves(){
		Driver.debug(this + " generating moves", 3);
	/*
		moves = new ArrayList<Integer[]>();
		takes = new ArrayList<Integer[]>();
		cover = new ArrayList<Integer[]>();
	*/
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
