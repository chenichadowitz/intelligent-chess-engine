package ice;

public class Pawn extends Piece {
	public Pawn(boolean player, int xwhere, int ywhere, Board onWhat){
		color = player;
		position[0] = xwhere;
		position[1] = ywhere;
		currentBoard = onWhat;
		value = 1;
		pieceType = "P";
	}
	public Piece clone() {
		Pawn newPiece = new Pawn(color,position[0],position[1],currentBoard);
		for(Move moveCloner: possibleMoves){
			newPiece.possibleMoves.add(moveCloner.clone());
		}		
		return newPiece;
	}
	public void generateMoves(){
		super.generateMoves();
		int delta = 1;
		if(!color){delta = -1;}
//move up one
		int[]     square    = {position[0], position[1] +delta};
		Move possibleMove = new Move(currentBoard,this,square,true);
		boolean[] status = currentBoard.statusOfSquare(square);
		if(status[0]){
			possibleMoves.add(new Move(currentBoard,this,square,false));
		}
		if (!status[0] && status[1]){
			if(possibleMove.moveType == 1){possibleMoves.add(possibleMove);}		
//move up two
			if(position[1] == 1 || position[1] == 6){
				square[1] += delta;
				possibleMove = new Move(currentBoard,this,square,true);
				status = currentBoard.statusOfSquare(square);
				if(status[0]){
					possibleMoves.add(new Move(currentBoard,this,square,false));
				}
				if (!status[0] && status[1]){
					if(possibleMove.moveType == 1){possibleMoves.add(possibleMove);}
				}
			}
		}
//take/cover right
		square[0] += 1;
		square[1]  = position[1] +delta;
		possibleMove = new Move(currentBoard,this,square);
		status = currentBoard.statusOfSquare(square);
		if(!status[0] && status[1]){
			possibleMoves.add(new Move(currentBoard,this,square,false));
		}
		else if(possibleMove.moveType != 1){
			possibleMoves.add(possibleMove);
		}
//take/cover left
		square[0] -= 2;
		possibleMove = new Move(currentBoard,this,square);
		status = currentBoard.statusOfSquare(square);
		if(!status[0]  && status[1]){
			possibleMoves.add(new Move(currentBoard,this,square,false));
		}
		else if(possibleMove.moveType != 1){
			possibleMoves.add(possibleMove);
		}
	}
}
