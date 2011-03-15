package ice;

import main.Debug;


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
			Debug.debug("checking castling", 3);
			//castle king side
			int delta = 1;
			int[] edge = {position[0],(int)(3.5+3.5*delta)};
			if(currentBoard.pieceAt(edge) != null && currentBoard.pieceAt(edge).canCastle()){
				int[] possibleMove = {position[0]+delta,position[1]};
				Move moveChecker = this.getMoveTo(possibleMove);
				if(moveChecker.moveType == 1){
					int[]     square = {position[0] +2*delta, position[1]};
					boolean[] status = currentBoard.statusOfSquare(square);
					if (!status[0] && status[1]){
						possibleMoves.add(new Move(currentBoard,this,square,true));
					} else {
						possibleMoves.add(new Move(currentBoard,this,square,false));
					}
				}
			}
			//castle queen side
			delta = -1;
			edge[1] = (int)(3.5+3.5*delta);
			if(currentBoard.pieceAt(edge) != null && currentBoard.pieceAt(edge).canCastle()){
				int[] possibleMove = {position[0]+delta,position[1]};
				Move moveChecker = this.getMoveTo(possibleMove);
				if(moveChecker.moveType == 1){
					int[]     square = {position[0] +2*delta, position[1]};
					boolean[] status = currentBoard.statusOfSquare(square);
					if (!status[0] && status[1]){
						possibleMoves.add(new Move(currentBoard,this,square,true));
					} else {
						possibleMoves.add(new Move(currentBoard,this,square,false));
					}
				}
			}
		}
	}
	public Piece clone(){
		King newPiece = new King(color,position[0],position[1],currentBoard);
		for(Move moveCloner: possibleMoves){
			newPiece.possibleMoves.add(moveCloner.clone());
		}
		newPiece.castle = castle;
		return newPiece;
	}
}
