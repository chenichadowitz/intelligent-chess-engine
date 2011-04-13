package newerGameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class PieceManager {
	private HashMap<Position,Piece> pieces;
	private HashMap<Position,LinkedList<Piece>> affectedPieces;
	
	public PieceManager(){
		resetPieceManager();
	}
	
	public void resetPieceManager(){
		pieces = new HashMap<Position,Piece>();
		affectedPieces = new HashMap<Position,LinkedList<Piece>>();
	}	
	
	public LinkedList<Piece> getAffectedPieces(Position p){
		return affectedPieces.get(p);
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
		MoveFactory.generateMovesfor(currentPiece, this);		
	}

	public void removeFromAffectedPieces(Piece currentPiece){
		for(Move currentMove: currentPiece.getMoves()){
			affectedPieces.get(currentMove.getFinalPos()).remove(currentPiece);
		}
	}
	
	public void addToAffectedPieces(Piece currentPiece) {
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
		movingPiece.moveTo(move.getFinalPos());
		addPiece(movingPiece);
	}

	public void notifyPieces(ArrayList<Position> positions) {
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		for(Position pos : positions){
			for(Piece p : affectedPieces.get(pos)){
				if(!pieces.contains(p)) { 
					pieces.add(p);
					p.update(this);
				}
			}
		}
	}
	
}
