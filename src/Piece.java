
import java.util.*;
public abstract class Piece {
	protected boolean color; // true = white; false = black
	protected int value;
	protected int[] position; // [x-coord,y-coord]
	protected ArrayList<Integer[]> moves = new ArrayList<Integer[]>(); //[[x1,y1][x2,y2]...]
	protected ArrayList<Integer[]> takes = new ArrayList<Integer[]>(); //[[x1,y1][x2,y2]...]
	protected ArrayList<Integer[]> cover = new ArrayList<Integer[]>();
	protected Board currentBoard;
	
	public  boolean getColor()	{return color;} 
	public  int getValue()		{return value;}
	public  int[] getPosition()	{return position;}
	public  ArrayList<Integer[]> getMoves() 	{return moves;}
	public  ArrayList<Integer[]> getTakes()		{return takes;}
	public  ArrayList<Integer[]> getCover()		{return cover;}
	public  void setBoard(Board newBoard){ currentBoard = newBoard;}	
	
	boolean move(int[] newsquare){
		if (isValidMove(newsquare)){
			position = newsquare;
			return true;
		}
		else return false;
	}
	
	public  boolean isValidMove(int[] move){
		for (int searcher = 0; searcher < moves.size(); searcher++){
			if(moves.contains(move)){return true;}
		}
		return false;
	}
	public  boolean processSquare(int x, int y){ //returns true if square is empty
		int[] square = {x,y};
		boolean[] status = currentBoard.statusOfSquare(square);
		Integer[] squareObj = {square[0],square[1]};
		if (status[0] && status[1] == !color) {
			takes.add(squareObj);
		}
		else if (status[0] && status[1] == color){
			cover.add(squareObj);
		}
		else if (!status[0] && status[1]){
			moves.add(squareObj);
			return true;
		}
		return false;
	}
		
	
	public  abstract void generateMoves();

}
