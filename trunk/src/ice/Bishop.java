package ice;

import java.util.ArrayList;

public class Bishop extends Piece {
	public Bishop(boolean player, int xwhere, int ywhere, Board onWhat){
		color = player;
		position[0] = xwhere;
		position[1] = ywhere;
		currentBoard = onWhat;
		value = 0;
		pieceType = "B";
	}
	public Piece clone() {
		Bishop newPiece = new Bishop(color,position[0],position[1],currentBoard);
		newPiece.cover = (ArrayList<Integer[]>) cover.clone();
		newPiece.moves = (ArrayList<Integer[]>) moves.clone();
		newPiece.takes = (ArrayList<Integer[]>) takes.clone();
		return newPiece;
	}
	public void generateMoves() {
		Driver.debug(this + " generating moves");
		moves = new ArrayList<Integer[]>();
		takes = new ArrayList<Integer[]>();
		cover = new ArrayList<Integer[]>();
		//down-left
		int length = 1;
		while(processSquare(position[0] -length,position[1] -length)){
			length++;
		}
		//down-right
		length = 1;
		while(processSquare(position[0] +length,position[1] -length)){
			length++;
		}
		//up-left
		length = 1;
		while(processSquare(position[0] - length,position[1] +length)){
			length++;
		}
		//up-right
		length = 1;
		while(processSquare(position[0] + length,position[1] +length)){
			length++;
		}
	}

}
