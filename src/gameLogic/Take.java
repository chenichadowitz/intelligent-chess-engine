package gameLogic;
import main.*;


public class Take extends Listener {

	Piece takenPiece;
	
	public Take(int x1, int y1, int x2, int y2, Board place) {
		super(x1, y1, x2, y2, place);
		takenPiece = currentBoard.pieceAt(FinalPos);
		description = "Take";
	}
	public Take clone(){
		return new Take(OrigPos[0],OrigPos[1],FinalPos[0],FinalPos[1],currentBoard);
	}
	public String toString(){
		String numToLet = "abcdefgh";
		String action = "";
		if(!movingPiece.pieceType.equals("P")){
			action += movingPiece.pieceType;
		}else{
			action += numToLet.substring(OrigPos[0],OrigPos[0]+1);
		}		
		action += "x" + numToLet.substring(FinalPos[0],FinalPos[0]+1);
		action += (FinalPos[1]+1);
		if(putInCheck){action += "+";}
		return action;
	}
	public boolean execute(){
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
		currentBoard.update(OrigPos,FinalPos);
		movingPiece.addToBoardState();
		currentBoard.setKingCheck();
		if(owner.getCheckStatus()){
			Output.debug("that move results in check", 1);
			undo();
			return false;
		}
		if(movingPiece.pieceType.equals("P") && FinalPos[1]%7 == 0){
			char newPieceType = owner.getPromotion();
			toString();
			description += "="+newPieceType;
			currentBoard.takePiece(movingPiece);
			Piece newPiece;
			switch(newPieceType){
				case('R'): 
					newPiece = new Rook(color,FinalPos[0],FinalPos[1],currentBoard);
					break;
				case('N'): 
					newPiece = new Knight(color,FinalPos[0],FinalPos[1],currentBoard);
					break;
				case('B'): 
					newPiece = new Bishop(color,FinalPos[0],FinalPos[1],currentBoard);
					break;
				default  : 
					newPiece = new Queen(color,FinalPos[0],FinalPos[1],currentBoard);
					break;
			}
			currentBoard.pieces.add(newPiece);
			newPiece.generateMoves();
			newPiece.addToBoardState();
			currentBoard.update(FinalPos);
		}
		putInCheck = currentBoard.playerMap.get(!color).getCheckStatus();
		Output.debug(this.toString(),1);
		return true;
	}
	public void undo(){
		if(movingPiece.getClass() == Pawn.class && FinalPos[1]%7 == 0){
			currentBoard.takePiece(currentBoard.pieceAt(FinalPos));
			currentBoard.pieces.add(movingPiece);
		}		
		movingPiece.removeFromBoardState();
		movingPiece.setPosition(OrigPos);
		currentBoard.pieces.add(takenPiece);
		takenPiece.addToBoardState();
		movingPiece.castle = oldCastle;
		movingPiece.generateMoves();
		movingPiece.addToBoardState();
		currentBoard.update(OrigPos,FinalPos);
		currentBoard.setKingCheck();
	}
	
	

}
