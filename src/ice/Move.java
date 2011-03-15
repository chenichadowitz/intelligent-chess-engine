package ice;

import java.util.*;

import main.Debug;

public class Move {
	protected int moveType;//1 = move, 2 = take, 3 = cover
	protected int[] OrigPos = new int[2];
	protected int[] FinalPos =  new int[2];
	protected boolean validMove;
	protected boolean owner;
	protected Board currentBoard;
	protected Piece movingPiece;
	protected boolean oldCastle;
	
	public Move(Board place, int x1, int y1, int x2, int y2, boolean valid){
		currentBoard = place;
		OrigPos[0] = x1;
		OrigPos[1] = y1;
		FinalPos[0] = x2;
		FinalPos[1] = y2;
		movingPiece = currentBoard.pieceAt(OrigPos);
		owner = movingPiece.getColor();
		if(valid){	
			if(currentBoard.pieceAt(FinalPos) != null){
				if(currentBoard.pieceAt(FinalPos).color == movingPiece.color){
					validMove = false;
					moveType = 3; // cover
				}
				else{
					validMove = true;
					moveType = 2; // take
				}
			}else{
				validMove = true;
				moveType = 1; // move
			}
			if(movingPiece.type() == 'K' && Math.abs(FinalPos[0] - OrigPos[0]) == 2){
				moveType = 4; // castle
			}
		}else{
			validMove = false;
			moveType = 0; // listen
		}
		Debug.debug(this.toString(), 5);
	}
	public Move(Board place, int[] square1, int[] square2){
		this(place, square1[0],square1[1],square2[0], square2[1], true);
	}
	public Move(Board place, int[] squareAB){
		this(place, squareAB[0],squareAB[1],squareAB[2], squareAB[3], true);
	}
	public Move(Board place, Piece mover, int[] newSquare, boolean valid){
		this(place,mover.position[0],mover.position[1],newSquare[0],newSquare[1], valid);
	}
	public Move(Board place, Piece mover, int[] newSquare){
		this(place,mover.position[0],mover.position[1],newSquare[0],newSquare[1], true);
	}
	public Move(Board place, Piece mover, int x, int y){
		this(place,mover.position[0], mover.position[1],x,y, true);
	}
	
	public void setCurrentBoard(Board newBoard){
		currentBoard = newBoard;
	}
	public boolean equals(Move test){
		boolean equals = true;
		equals = equals && Arrays.equals(OrigPos, test.OrigPos);
		equals = equals && Arrays.equals(FinalPos, test.FinalPos);
		equals = equals && (movingPiece.equals(test.movingPiece));
		equals = equals && (validMove == test.validMove);
		equals = equals && (owner == test.owner);
		equals = equals && (currentBoard.equals(test.currentBoard));
		return equals;
	}
	public boolean contains(ArrayList<Move> haystack, Move needle){
		for(Move move : haystack){
			if(needle.equals(move)){
				return true;
			}
		}
		return false;
	}
	public String toString(){
		String numToLet = "abcdefgh";
		String action = "";
		if(moveType == 4){
			action = "O-O";
			if(FinalPos[0] < OrigPos[0]){action = action.concat("-O");}
		} else {
			if(!movingPiece.pieceType.equals("P")){
				action = action.concat(movingPiece.pieceType);
			} else {
				if(moveType == 2){action = action.concat(numToLet.substring(OrigPos[0],OrigPos[0]+1));}
			}
			if(moveType == 2){
				action = action.concat("x");
			}
			action = action.concat(numToLet.substring(FinalPos[0],FinalPos[0]+1) + (FinalPos[1]+1));
		}
		if(currentBoard.playerMap.get(!owner).checkStatus){action = action.concat("+");}
		return action;
	}
	public Move clone(){
		return new Move(currentBoard,OrigPos,FinalPos);
	}

	public boolean execute(){
		if(!contains(movingPiece.possibleMoves,this)){
			Debug.debug(movingPiece + " does not have that move", 1);
			return false;
		}
		if(moveType == 4){
			int delta = (FinalPos[0] - OrigPos[0])/2;
			for(Piece affectingPiece: currentBoard.boardState[movingPiece.position[0]+delta][movingPiece.position[1]]){
				if(affectingPiece.color != movingPiece.color){validMove = false;}
			}
		}		
		Piece takenPiece = currentBoard.pieceAt(FinalPos);
		if(validMove){
			movingPiece.removeFromBoardState();
			switch(moveType){
			case(1):
				movingPiece.setPosition(FinalPos);				
				break;
			case(2):
				currentBoard.takePiece(currentBoard.pieceAt(FinalPos));
				movingPiece.setPosition(FinalPos);
				break;
			case(4): 
				movingPiece.setPosition(FinalPos);
				int[] rookLocation ={7*(FinalPos[0]-2)/4,OrigPos[1]};
				int[] rookMove     ={FinalPos[0]-(FinalPos[0] - OrigPos[0])/2,FinalPos[1]};
				currentBoard.pieceAt(rookLocation).setPosition(rookMove);
				currentBoard.update(rookLocation);
				currentBoard.update(rookMove);
				break;
			}
			oldCastle = movingPiece.castle;
			movingPiece.castle = false;
			movingPiece.generateMoves();
			movingPiece.addToBoardState();
			currentBoard.update(OrigPos);
			currentBoard.update(FinalPos);
		}		
		currentBoard.setKingCheck();
		if(currentBoard.playerMap.get(owner).getCheckStatus()){
			movingPiece.removeFromBoardState();
			switch(moveType){
			case(1):
				movingPiece.setPosition(OrigPos);				
				break;
			case(2):
				currentBoard.pieces.add(takenPiece);
				movingPiece.setPosition(OrigPos);
				break;
			case(4): 
				movingPiece.setPosition(OrigPos);
				int[] rookMove ={7*(FinalPos[0]-2)/4,OrigPos[1]};
				int[] rookLocation ={FinalPos[0]-(FinalPos[0] - OrigPos[0])/2,FinalPos[1]};
				currentBoard.pieceAt(rookLocation).setPosition(rookMove);
				currentBoard.update(rookLocation);
				currentBoard.update(rookMove);
				break;
			}
			movingPiece.generateMoves();
			movingPiece.addToBoardState();
			currentBoard.setKingCheck();
			movingPiece.castle = oldCastle;
			currentBoard.update(OrigPos);
			currentBoard.update(FinalPos);
			return false;
		}
		if(validMove){
			Debug.debug(this.toString(), 1);
			currentBoard.moveLog.add(this);
			if(movingPiece.pieceType.equals("P") && FinalPos[1]%7 == 0){
				char newPieceType = currentBoard.playerMap.get(owner).getPromotion();
				currentBoard.takePiece(movingPiece);
				Piece newPiece;
				switch(newPieceType){
				case('R'): newPiece = new Rook(owner,FinalPos[0],FinalPos[1],currentBoard);
				break;
				case('N'): newPiece = new Knight(owner,FinalPos[0],FinalPos[1],currentBoard);
				break;
				case('B'): newPiece = new Bishop(owner,FinalPos[0],FinalPos[1],currentBoard);
				break;
				default  : newPiece = new Queen(owner,FinalPos[0],FinalPos[1],currentBoard);
				break;
				}
				currentBoard.pieces.add(newPiece);
				newPiece.generateMoves();
				newPiece.addToBoardState();
			}
		}
		return validMove;
	}
}
