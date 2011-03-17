package gameLogic;

import main.Output;


public class King extends Piece{
	public King(boolean player, int xwhere, int ywhere, Board onWhat){
		color = player;
		position[0] = xwhere;
		position[1] = ywhere;
		currentBoard = onWhat;
		value = 0;
		pieceType = "K";
		castle = true;
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
		if(castle){
			Output.debug("checking castling", 3);
			//castle king side
			int delta = 1;
			int[] edge = {position[0],(int)(3.5+3.5*delta)};
			if(currentBoard.pieceAt(edge) != null && currentBoard.pieceAt(edge).canCastle()){
				int[] possibleMove = {position[0]+delta,position[1]};
				Listener moveChecker = this.getMoveTo(possibleMove);
				if(moveChecker.description.equals("Move")){
					int[]     square = {position[0] +2*delta, position[1]};
					boolean[] status = currentBoard.statusOfSquare(square);
					if (!status[0] && status[1]){
						moves.add(PieceMaker.MakeMove(currentBoard,this,square));
					} else {
						moves.add(new Listener(position[0],position[1],square[0],square[1],currentBoard));
					}
				}
			}
			//castle queen side
			delta = -1;
			edge[1] = (int)(3.5+3.5*delta);
			if(currentBoard.pieceAt(edge) != null && currentBoard.pieceAt(edge).canCastle()){
				int[] possibleMove = {position[0]+delta,position[1]};
				Listener moveChecker = this.getMoveTo(possibleMove);
				if(moveChecker.description.equals("Move")){
					int[]     square = {position[0] +2*delta, position[1]};
					boolean[] status = currentBoard.statusOfSquare(square);
					if (!status[0] && status[1]){
						moves.add(PieceMaker.MakeMove(currentBoard,this,square));
					} else {
						moves.add(new Listener(position[0],position[1],square[0],square[1],currentBoard));
					}
				}
			}
		}
	}
	public Piece clone(){
		King newPiece = new King(color,position[0],position[1],currentBoard);
		for(Listener moveCloner: moves){
			newPiece.moves.add(moveCloner.clone());
		}
		newPiece.castle = castle;
		return newPiece;
	}
}
