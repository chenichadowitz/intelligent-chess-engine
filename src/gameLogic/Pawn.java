package gameLogic;

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
		for(Listener moveCloner: moves){
			newPiece.moves.add(moveCloner.clone());
		}	
		return newPiece;
	}
	public void generateMoves(){
		super.generateMoves();
		int delta = 1;
		if(!color){delta = -1;}
//move up one
		int[]     square    = {position[0], position[1] +delta};
		boolean[] status = currentBoard.statusOfSquare(square);
		if(status[0]){
			moves.add(new Listener(position[0],position[1],square[0],square[1],currentBoard));
		}
		Listener possibleMove = PieceMaker.MakeMove(currentBoard,this,square);
		if (!status[0] && status[1]){
			if(possibleMove.description.equals("Move")){moves.add(possibleMove);}		
//move up two
			if(position[1] == 1 || position[1] == 6){
				square[1] += delta;
				status = currentBoard.statusOfSquare(square);
				if(status[0]){
					moves.add(new Listener(position[0],position[1],square[0],square[1],currentBoard));
				}
				possibleMove = PieceMaker.MakeMove(currentBoard,this,square);
				if (!status[0] && status[1]){
					if(possibleMove.description.equals("Move")){moves.add(possibleMove);}		
				}
			}
		}
//take/cover right
		if(position[0] != 7){
			square[0]  = position[0] +1;
			square[1]  = position[1] +delta;
			status = currentBoard.statusOfSquare(square);
			possibleMove = PieceMaker.MakeMove(currentBoard,this,square);
			if(!status[0] && status[1]){
				moves.add(possibleMove);
			}
			else if(!possibleMove.description.equals("Move")){
				moves.add(possibleMove);
			}
		}
//take/cover left
		if(position[0] != 0){
			square[0]  = position[0] -1;
			square[1]  = position[1] +delta;
			status = currentBoard.statusOfSquare(square);
			possibleMove = PieceMaker.MakeMove(currentBoard,this,square);
			if(!status[0] && status[1]){
				moves.add(possibleMove);
			}
			else if(!possibleMove.description.equals("Move")){
				moves.add(possibleMove);
			}
		}
	}
}
