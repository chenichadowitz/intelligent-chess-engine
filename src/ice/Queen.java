package ice;

import java.util.ArrayList;

public class Queen extends Piece {
	public Queen(boolean player, int xwhere, int ywhere, Board onWhat){
		color = player;
		position[0] = xwhere;
		position[1] = ywhere;
		currentBoard = onWhat;
		value = 0;
		pieceType = "Q";
	}
	@SuppressWarnings("unchecked")
	public Piece clone() {
		Queen newPiece = new Queen(color,position[0],position[1],currentBoard);
		newPiece.cover = (ArrayList<Integer[]>) cover.clone();
		newPiece.moves = (ArrayList<Integer[]>) moves.clone();
		newPiece.takes = (ArrayList<Integer[]>) takes.clone();
		return newPiece;
	}


	public void generateMoves() {
		super.generateMoves();
		//backwards
		int length = 1;
		while(processSquare(position[0],position[1] -length)){
			length++;
		}
		//forwards
		length = 1;
		while(processSquare(position[0],position[1] + length)){
			length++;
		}
		//left
		length = 1;
		while(processSquare(position[0] - length,position[1])){
			length++;
		}
		//right
		length = 1;
		while(processSquare(position[0] + length,position[1])){
			length++;
		}
		//down-left
		length = 1;
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
