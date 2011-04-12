package newerGameLogic;

import java.util.HashMap;
import java.util.LinkedList;

public class PieceManager {
	private HashMap<Position,Piece> pieces;
	private HashMap<Position,LinkedList<Piece>> affectedPieces;
	
	public void resetPieceManager(){
		pieces = new HashMap<Position,Piece>();
		affectedPieces = new HashMap<Position,LinkedList<Piece>>();
	}
	
	public PieceManager(){
		resetPieceManager();
	}
	public void removePiece(Piece oldPiece){
		pieces.remove(oldPiece);
		removeFromAffectedPieces(oldPiece);			
	}	
	public void addPiece(Piece newPiece){
		pieces.put(newPiece.getPosition(),newPiece);
		NewMovesFor(newPiece);
		addToAffectedPieces(newPiece);			
	}

	private void NewMovesFor(Piece currentPiece) {
		moveFactory.generateMovesfor(currentPiece, this);		
	}

	private void removeFromAffectedPieces(Piece currentPiece){
		for(Move currentMove: currentPiece.getMoves()){
			affectedPieces.get(currentMove.getFinalPos()).remove(currentPiece);
		}
	}
	
	
	private void addToAffectedPieces(Piece currentPiece) {
		for(Move currentMove: currentPiece.getMoves()){
			affectedPieces.get(currentMove.getFinalPos()).add(currentPiece);
		}
	}

	public SquareState statusOfSquare(Position square) {
		if(square.isOffBoard()){return SquareState.OffBoard;}
		if(pieceAt(square) == null){return SquareState.Empty;}
		if(pieceAt(square).getPieceColor() == WBColor.White){
			return SquareState.OccupiedByWhite;
		} else {
			return SquareState.OccupiedByBlack;
		}
	}

	public Piece pieceAt(Position position) {
		return pieces.get(position);
	}
	
	public void makeMove(Move move){
		Piece movingPiece = pieces.get(move.getOrigPos());
		removePiece(movingPiece);
		movingPiece.setPosition(move.getFinalPos());
		addPiece(movingPiece);
	}
}
