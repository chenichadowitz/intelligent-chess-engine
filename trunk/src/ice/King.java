package ice;

import java.util.ArrayList;

public class King extends Piece{
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
		super.generateMoves();
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
	@SuppressWarnings("unchecked")
	public Piece clone(){
		King newPiece = new King(color,position[0],position[1],currentBoard);
		newPiece.cover = (ArrayList<Integer[]>) cover.clone();
		newPiece.moves = (ArrayList<Integer[]>) moves.clone();
		newPiece.takes = (ArrayList<Integer[]>) takes.clone();
		return newPiece;
	}
}
