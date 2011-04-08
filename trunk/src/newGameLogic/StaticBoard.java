package newGameLogic;

import main.Output;

public class StaticBoard extends Board {
	
	public StaticBoard(Board b, Move m){
		Output.debug("staticBoard created",5);
		for(Piece newPiece : b.getPieces()){
			addPiece(newPiece.clone());
			addPieceToBoardState(newPiece);
		}		
		if(getTurn() != b.getTurn()){switchTurn();}
		for(Move move: pieceAt(m.getOrigPos()).getMoves()){
			if(move.equals(m)){
				execute(m);
			}
		}
		switchTurn();
	}
	/**
	 * does nothing can't move the staticBoard
	 */
	boolean makeMove(Move action) {
		return false;
	}

}
