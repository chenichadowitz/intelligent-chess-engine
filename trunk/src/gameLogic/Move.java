package gameLogic;

import main.*;

public class Move extends Listener{
	public Move(int x1, int y1, int x2, int y2, Board place) {
		super(x1, y1, x2, y2, place);
		description = "Move";
	}
	public Move clone(){
		return new Move(OrigPos[0],OrigPos[1],FinalPos[0],FinalPos[1],currentBoard);
	}

	public String toString(){
		String numToLet = "abcdefgh";
		String action = "";
		if(!movingPiece.pieceType.equals("P")){
			action += movingPiece.pieceType;
		}
		action += numToLet.substring(FinalPos[0],FinalPos[0]+1);
		action += (FinalPos[1]+1);
		return action;
	}
	public boolean execute(){
		if(!contains(movingPiece.moves,this)){
			Debug.debug(movingPiece + " does not have that move", 1);
			return false;
		}
		movingPiece.removeFromBoardState();
		movingPiece.setPosition(FinalPos);
		oldCastle = movingPiece.castle;
		movingPiece.castle = false;
		movingPiece.generateMoves();
		currentBoard.update(OrigPos,FinalPos);
		movingPiece.addToBoardState();
		currentBoard.setKingCheck();
		if(owner.getCheckStatus()){
			Debug.debug("that move results in check", 1);
			undo();
			return false;
		}
		currentBoard.moveLog.add(this);
		if(movingPiece.pieceType.equals("P") && FinalPos[1]%7 == 0){
			char newPieceType = owner.getPromotion();
			currentBoard.takePiece(movingPiece);
			Piece newPiece;
			switch(newPieceType){
			case('R'): newPiece = new Rook(color,FinalPos[0],FinalPos[1],currentBoard);
			break;
			case('N'): newPiece = new Knight(color,FinalPos[0],FinalPos[1],currentBoard);
			break;
			case('B'): newPiece = new Bishop(color,FinalPos[0],FinalPos[1],currentBoard);
			break;
			default  : newPiece = new Queen(color,FinalPos[0],FinalPos[1],currentBoard);
			break;
			}
			currentBoard.pieces.add(newPiece);
			newPiece.generateMoves();
			newPiece.addToBoardState();
		}
		Debug.debug(this.toString(),1);
		return true;
	}
	public void undo(){
		movingPiece.removeFromBoardState();
		movingPiece.setPosition(OrigPos);
		movingPiece.castle = oldCastle;
		movingPiece.generateMoves();
		movingPiece.addToBoardState();
		currentBoard.update(OrigPos,FinalPos);
	}

}