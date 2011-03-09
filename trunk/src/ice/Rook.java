package ice;

import java.util.ArrayList;

public class Rook extends Piece {
	public Rook(boolean player, int xwhere, int ywhere, Board onWhat){
		color = player;
		position[0] = xwhere;
		position[1] = ywhere;
		currentBoard = onWhat;
		value = 5;
		pieceType = "R";
		castle = true;
	}
	@SuppressWarnings("unchecked")
	public Piece clone() {
		Rook newPiece = new Rook(color,position[0],position[1],currentBoard);
/*		newPiece.cover = (ArrayList<Integer[]>) cover.clone();
		newPiece.moves = (ArrayList<Integer[]>) moves.clone();
		newPiece.takes = (ArrayList<Integer[]>) takes.clone();
		*/
		newPiece.possibleMoves = (ArrayList<Move>) possibleMoves.clone();
		return newPiece;
	}

	@Override
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
	}

}
