package ice;

import java.util.ArrayList;

public class Knight extends Piece {

	public Knight(boolean player, int xwhere, int ywhere, Board onWhat){
		color = player;
		position[0] = xwhere;
		position[1] = ywhere;
		currentBoard = onWhat;
		value = 3;
		pieceType = "N";
	}
	public void generateMoves() {
		Driver.debug(this + " generating moves", 4);
		moves = new ArrayList<Integer[]>();
		takes = new ArrayList<Integer[]>();
		cover = new ArrayList<Integer[]>();
		processSquare(position[0] -2,position[1] -1);
		processSquare(position[0] -2,position[1] +1);
		processSquare(position[0] +2,position[1] -1);
		processSquare(position[0] +2,position[1] +1);
		processSquare(position[0] -1,position[1] +2);
		processSquare(position[0] +1,position[1] +2);
		processSquare(position[0] -1,position[1] -2);
		processSquare(position[0] +1,position[1] +2);
	}
	public Piece clone(){
		Knight newPiece = new Knight(color,position[0],position[1],currentBoard);
		newPiece.cover = (ArrayList<Integer[]>) cover.clone();
		newPiece.moves = (ArrayList<Integer[]>) moves.clone();
		newPiece.takes = (ArrayList<Integer[]>) takes.clone();
		return newPiece;
	}

}
