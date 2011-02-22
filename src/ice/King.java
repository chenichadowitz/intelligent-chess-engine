package ice;

import java.util.ArrayList;

public class King extends Piece{
	private boolean check = false;
	private boolean castle = true;
	public King(boolean player, int xwhere, int ywhere, Board onWhat){
		color = player;
		position[0] = xwhere;
		position[1] = ywhere;
		currentBoard = onWhat;
		value = 0;
		pieceType = "K";
	}
	boolean move(int[] newsquare){
		if(super.move(newsquare)){
			castle = false;
			return true;
		}
		else{return false;}
	}
	public void generateMoves(){
		moves = new ArrayList<Integer[]>();
		takes = new ArrayList<Integer[]>();
		cover = new ArrayList<Integer[]>();
		processSquare(position[0] -1,position[1] -1);
		processSquare(position[0] -1,position[1]   );
		processSquare(position[0] -1,position[1] +1);
		processSquare(position[0]   ,position[1] -1);
		processSquare(position[0]   ,position[1] +1);
		processSquare(position[0] +1,position[1] -1);
		processSquare(position[0] +1,position[1]   );
		processSquare(position[0] +1,position[1] +1);
		if(castle){//fill in code!!!
		}
		
	}
	public boolean getcheck(){return check;}
	public boolean inCheck(){
		for(Piece p: currentBoard.getPieces()){
			check = p.contains(p.takes, position);
			if(check){return check;}
		}
		return check;
	}
}