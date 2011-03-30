package newGameLogic;

import main.Output;

public class StaticBoardTree extends Board {

	public StaticBoardTree(Board b, Move m){
		Output.debug("//////staticBoard created",3);
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
	
	
	
	
	
	
	@Override
	boolean makeMove(Move action) {
		// TODO Auto-generated method stub
		return false;
	}

}
