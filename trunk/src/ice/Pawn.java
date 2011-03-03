package ice;

import java.util.ArrayList;

public class Pawn extends Piece {
	public Pawn(boolean player, int xwhere, int ywhere, Board onWhat){
		color = player;
		position[0] = xwhere;
		position[1] = ywhere;
		currentBoard = onWhat;
		value = 1;
		pieceType = "P";
	}
	@SuppressWarnings("unchecked")
	public Piece clone() {
		Knight newPiece = new Knight(color,position[0],position[1],currentBoard);
		newPiece.cover = (ArrayList<Integer[]>) cover.clone();
		newPiece.moves = (ArrayList<Integer[]>) moves.clone();
		newPiece.takes = (ArrayList<Integer[]>) takes.clone();
		return newPiece;
	}
	public void generateMoves(){
		super.generateMoves();
		int delta = 1;
		if(!color){delta = -1;}
		Integer[] squareObj = {position[0], position[1] +delta};
		int[]     square    = {position[0], position[1] +delta};
		boolean[] status = currentBoard.statusOfSquare(square);
		if (!status[0] && status[1]){
			moves.add(squareObj);
			if(position[1] == 1 || position[1] == 6){
				Integer[] squareObj2 = {position[0], position[1] +2*delta};;
				square[1]    += delta;
				status = currentBoard.statusOfSquare(square);
				if (!status[0] && status[1]){
					moves.add(squareObj2);
				}
			}
		}
		Integer[] squareObj3 = {position[0] +1, position[1] +delta};
		square[0]    += 1;
		square[1]     = position[1] +delta;
		status = currentBoard.statusOfSquare(square);
		if (status[0] && (status[1] == !color)) {
			takes.add(squareObj3);
		}
		else if (status[0] && (status[1] == color)){
			cover.add(squareObj3);
		}
		Integer[] squareObj4 = {position[0] -1, position[1] +delta};
		square[0]    -= 2;
		status = currentBoard.statusOfSquare(square);
		if (status[0] && (status[1] == !color)) {
			takes.add(squareObj4);
		}
		else if (status[0] && (status[1] == color)){
			cover.add(squareObj4);
		}
	}

}
