
import java.util.*;
public abstract class piece {
	boolean color; // true = white; false = black
	int value;
	int[] position; // [x-coord,y-coord]
	ArrayList<Integer[]> moves = new ArrayList<Integer[]>(); //[[x1,y1][x2,y2]...]
	ArrayList<Integer[]> takes = new ArrayList<Integer[]>(); //[[x1,y1][x2,y2]...]
	board currentBoard;
	
	boolean getColor()	{return color;} 
	int getValue()		{return value;}
	int[] getPosition()	{return position;}
	ArrayList<Integer[]> getMoves() 	{return moves;}
	ArrayList<Integer[]> getTakes()		{return takes;}
	void setBoard(board newBoard){ currentBoard = newBoard;}	
	
	boolean move(int[] newsquare){
		if (isValidMove(newsquare)){
			position = newsquare;
			return true;
		}
		else return false;
	}
	
	boolean isValidMove(int[] move){
		for (int searcher = 0; searcher < moves.size(); searcher++){
			if(moves.contains(move)){return true;}
		}
		return false;
	}
	boolean processSquare(int x, int y){ //returns true if square is empty
		int[] square = {x,y};
		boolean[] status = currentBoard.statusOfSquare(square);
		Integer[] squareObj = {square[0],square[1]};
		if (status[0] && status[1] == !color) {
			takes.add(squareObj);
		}
		else if (!status[0] && status[1]){
			moves.add(squareObj);
			return true;
		}
		return false;
	}
		
	
	abstract void generateMoves();

}
