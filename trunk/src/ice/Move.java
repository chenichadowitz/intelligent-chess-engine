package ice;

import java.util.*;

public class Move {
	protected int moveType;//1 = move, 2 = take, 3 = cover
	protected int[] OrigPos = new int[2];
	protected int[] FinalPos =  new int[2];
	protected boolean validMove;
	protected boolean owner;
	protected Board currentBoard;
	protected Piece movingPiece;
	
	public Move(Board place, int x1, int y1, int x2, int y2, boolean valid){
		currentBoard = place;
		OrigPos[0] = x1;
		OrigPos[1] = y1;
		FinalPos[0] = x2;
		FinalPos[1] = y2;
		
		if(x2 > 7 || x2< 0 || y2> 7 || y2 < 0){
			Driver.debug("WARNING: move created off the board",1);
		}
		
		
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
			moveType = 0;
		}
		Driver.debug(this.toString(), 5);
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
		String action = " ";
		switch(moveType){
			case(1):action = " moves to "; break;
			case(2):action = " takes " + currentBoard.pieceAt(FinalPos) + " "; break;
			case(3):action = " covers " + currentBoard.pieceAt(FinalPos) + " "; break;
			case(4):action = " castles to "; break;
		}
		return movingPiece + " at " 
			+ OrigPos[0] + " " + OrigPos[1] + action
			+  FinalPos[0] + " " + FinalPos[1];
	}
	public Move clone(){
		return new Move(currentBoard,OrigPos,FinalPos);
	}

	public boolean execute(boolean Force){
		if(!contains(movingPiece.possibleMoves,this)){
			Driver.debug(movingPiece + " does not have that move", 1);
			return false;
		}		
		if(!Force){
			if(isMoveIntoCheck()){Driver.debug("that moves into check", 1);}
		}
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
			movingPiece.generateMoves();
			movingPiece.addToBoardState();
			movingPiece.castle = false;
		}
		return validMove;
	}
	public boolean execute(){
		return execute(false);
	}
	public boolean isMoveIntoCheck(){
		Driver.debug("checking if move " + this + " results in check", 3);
		staticBoard checkChecker = new staticBoard(currentBoard, this);
		Driver.debug("/////staticBoard exited",3);
		Driver.debug("checkStatus is = " + checkChecker.isPlayerInCheck(owner),3);
		validMove = !checkChecker.isPlayerInCheck(owner);
		return !validMove;
	}
	

	
	
	

}
