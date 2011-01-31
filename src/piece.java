
import java.util.*;
public abstract class piece {
	boolean color; // true = white; false = black
	int value;
	int[] position; // [x-coord,y-coord]
	ArrayList<Integer[]> moves = new ArrayList<Integer[]>(); //[[x1,y1][x2,y2]...]
	board currentBoard;
	
	boolean getColor()	{return color;} 
	int getValue()		{return value;}
	int[] getPosition()	{return position;}
	ArrayList<Integer[]> getMoves() 	{return moves;}
	void setBoard(board newBoard){ currentBoard = newBoard;}	
	
	boolean move(int[] newsquare){
		generateMoves();
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
	
	abstract void generateMoves();

}
