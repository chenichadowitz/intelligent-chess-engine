package gameLogic;
import main.*;

public class Castle extends Listener {

	Piece rook;
	int[] rookMove = new int[2];
	int[] rookLocation = new int[2];
	public Castle(int x1, int y1, int x2, int y2, Board place) {
		super(x1, y1, x2, y2, place);
		rookMove[0] = FinalPos[0]-(FinalPos[0] - OrigPos[0])/2;
		rookMove[1] = FinalPos[1];
		rookLocation[0] = 7*(FinalPos[0]-2)/4;
		rookLocation[1] = OrigPos[1];
		rook = currentBoard.pieceAt(rookLocation);
		description = "Castle";
		oldCastle = movingPiece.castle;
	}
	public Castle clone(){
		return new Castle(OrigPos[0],OrigPos[1],FinalPos[0],FinalPos[1],currentBoard);
	}
	public String toString(){
		String action = "O-O";
		if(FinalPos[0] < OrigPos[0]){action = action.concat("-O");}
		if(putInCheck){action += "+";}	
		return action;
	}
	public boolean execute(){
		currentBoard.curMove = this;
		movingPiece.removeFromBoardState();
		rook.removeFromBoardState();
		movingPiece.setPosition(FinalPos);
		movingPiece.castle = false;
		rook.setPosition(rookMove);	
		movingPiece.generateMoves();
		rook.generateMoves();
		currentBoard.update(FinalPos,OrigPos,rookMove,rookLocation);
		movingPiece.addToBoardState();
		rook.addToBoardState();
		currentBoard.setKingCheck();
		if(owner.getCheckStatus()){
			Output.debug("that move results in check", 1);
			undo();
			return false;
		}
		putInCheck = currentBoard.playerMap.get(!color).getCheckStatus();
		Output.debug(this.toString(),1);
		return true;
	}
	public void undo(){
		movingPiece.removeFromBoardState();
		rook.removeFromBoardState();
		movingPiece.setPosition(OrigPos);
		rook.setPosition(rookLocation);	
		movingPiece.generateMoves();
		rook.generateMoves();
		currentBoard.update(FinalPos,OrigPos,rookMove,rookLocation);
		movingPiece.addToBoardState();
		rook.addToBoardState();
		movingPiece.castle = oldCastle;
		currentBoard.setKingCheck();
	}

}
