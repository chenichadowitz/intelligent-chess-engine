package gameLogic;

import main.Output;

public class EnPassant extends Listener {

	int[] taken = new int[2];
	Piece takenPiece;
	public EnPassant(int x1, int y1, int x2, int y2, Board place) {
		super(x1, y1, x2, y2, place);
		description = "En Passant";
		int delta = -1;
		if(color){delta = 1;}
		taken[0] = FinalPos[0];
		taken[1] = FinalPos[1] - delta;
		takenPiece = currentBoard.pieceAt(taken);
		if(takenPiece == null){
			Output.debug("WARNING: no piece there", 1);
		}
	}
	public String toString(){
		if(!description.equals("En Passant")){return description;}
		String numToLet = "abcdefgh";
		String action = numToLet.substring(OrigPos[0],OrigPos[0]+1);
		action += "x" + numToLet.substring(FinalPos[0],FinalPos[0]+1);
		action += FinalPos[1]+1;
		if(putInCheck){action += "+";}
		description = action;
		return action;
	}
	public boolean execute(){
		int[] posOfLastMove = currentBoard.curMove.movingPiece.position;
		if(!(posOfLastMove[1] == movingPiece.position[1] && posOfLastMove[0] == FinalPos[0])){
			Output.debug("you lost your chance", 1);
			return false;
		}
		currentBoard.curMove = this;
		if(!contains(movingPiece.moves,this)){
			Output.debug(movingPiece + " does not have that move", 1);
			return false;
		}
		movingPiece.removeFromBoardState();
		currentBoard.takePiece(takenPiece);
		movingPiece.setPosition(FinalPos);
		oldCastle = movingPiece.castle;
		movingPiece.castle = false;
		movingPiece.generateMoves();
		currentBoard.update(OrigPos,FinalPos,taken);
		movingPiece.addToBoardState();
		currentBoard.setKingCheck();
		if(owner.getCheckStatus()){
			Output.debug("that move results in check", 1);
			undo();
			return false;
		}
		putInCheck = currentBoard.playerMap.get(!color).getCheckStatus();
		currentBoard.addMovetoLog(this);
		Output.debug(this.toString(),1);
		return true;
	}
	public void undo(){
		movingPiece.removeFromBoardState();
		movingPiece.setPosition(OrigPos);
		currentBoard.pieces.add(takenPiece);
		takenPiece.addToBoardState();
		movingPiece.castle = oldCastle;
		movingPiece.generateMoves();
		movingPiece.addToBoardState();
		currentBoard.update(OrigPos,FinalPos,taken);
		currentBoard.setKingCheck();
	}

}
