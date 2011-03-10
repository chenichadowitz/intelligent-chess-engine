package ice;

public class Queen extends Piece {
	public Queen(boolean player, int xwhere, int ywhere, Board onWhat){
		color = player;
		position[0] = xwhere;
		position[1] = ywhere;
		currentBoard = onWhat;
		value = 9;
		pieceType = "Q";
	}
	public Piece clone() {
		Queen newPiece = new Queen(color,position[0],position[1],currentBoard);
		for(Move moveCloner: possibleMoves){
			newPiece.possibleMoves.add(moveCloner.clone());
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
