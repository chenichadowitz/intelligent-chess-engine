package gameLogic;

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
	public Piece clone() {
		Rook newPiece = new Rook(color,position[0],position[1],currentBoard);
		for(Listener moveCloner: moves){
			newPiece.moves.add(moveCloner.clone());
		}
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
	}

}
