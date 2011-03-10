package ice;

import java.util.ArrayList;

public class Pawn extends Piece {
	protected ArrayList<Integer[]> listeningSquares = new ArrayList<Integer[]>(); //[[x1,y1][x2,y2]...]
	public Pawn(boolean player, int xwhere, int ywhere, Board onWhat){
		color = player;
		position[0] = xwhere;
		position[1] = ywhere;
		currentBoard = onWhat;
		value = 1;
		pieceType = "P";
	}
	@SuppressWarnings("unchecked")
	public Piece clone() {
		Pawn newPiece = new Pawn(color,position[0],position[1],currentBoard);
		for(Move moveCloner: possibleMoves){
			newPiece.possibleMoves.add(moveCloner.clone());
		}		newPiece.listeningSquares = (ArrayList<Integer[]>) listeningSquares.clone();
		return newPiece;
	}
	public void generateMoves(){
		super.generateMoves();
		listeningSquares =  new ArrayList<Integer[]>();
		int delta = 1;
		if(!color){delta = -1;}
//move up one
		int[]     square    = {position[0], position[1] +delta};
		Move possibleMove = new Move(currentBoard,this,square);
		boolean[] status = currentBoard.statusOfSquare(square);
		if(status[0]){
			listeningSquares.add(new Integer[] {position[0], position[1] +delta} );
		}
		if (!status[0] && status[1]){
			if(possibleMove.moveType == 1){possibleMoves.add(possibleMove);}		
//move up two
			if(position[1] == 1 || position[1] == 6){
				square[1] += delta;
				possibleMove = new Move(currentBoard,this,square);
				status = currentBoard.statusOfSquare(square);
				if(status[0]){
					listeningSquares.add(new Integer[] {position[0], position[1] + 2*delta});
				}
				if (!status[0] && status[1]){
					if(possibleMove.moveType == 1){possibleMoves.add(possibleMove);}
				}
			}
		}
//take/cover right
		square[0] += 1;
		square[1]  = position[1] +delta;
		possibleMove = new Move(currentBoard,this,square);
		status = currentBoard.statusOfSquare(square);
		if(!status[0] && status[1]){
			listeningSquares.add(new Integer[] {position[0] +1, position[1] +delta});
		}
		else if(possibleMove.moveType != 1){
			possibleMoves.add(possibleMove);
		}
//take/cover left
		square[0] -= 2;
		possibleMove = new Move(currentBoard,this,square);
		status = currentBoard.statusOfSquare(square);
		if(!status[0]  && status[1]){
			listeningSquares.add(new Integer[] {position[0] -1, position[1] +delta});
		}
		else if(possibleMove.moveType != 1){
			possibleMoves.add(possibleMove);
		}
	}
	public void addToBoardState(){
		super.addToBoardState();
		for(Integer[] square: listeningSquares){
			currentBoard.boardState[square[0]][square[1]].add(this);
		}
	}
	public void removeFromBoardState(){
		super.removeFromBoardState();
		for(Integer[] square: listeningSquares){
			currentBoard.boardState[square[0]][square[1]].remove(this);
		}
	}

}
