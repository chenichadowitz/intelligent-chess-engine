package gameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import main.Output;

public class BoardState {

	private HashMap<int[], LinkedList<Piece>> map;

	public BoardState(){
		 map = new HashMap<int[], LinkedList<Piece>>(64);
	}
	
	public LinkedList<Piece> getPieceList(int[] square){
		return map.get(square);
	}
	
	public LinkedList<Piece> getPieceList(int x, int y){
		int[] square = {x, y};
		return this.getPieceList(square);
	}
		
	public void addPiece(int[] square, Piece p){
		map.get(square).add(p);
	}
	
	public void addPiece(int x, int y, Piece p){
		int[] square = {x,y};
		this.addPiece(square, p);
	}
	
	public void addMove(int[] square, Listener action){
		LinkedList<Piece> pieces = this.getPieceList(square);
		if(pieces.contains(action.movingPiece)){
			Output.debug("WARNING: " +action.movingPiece+ " already exists on boardState", 1);
		}
		map.get(square).add(action.movingPiece);
	}
	
	public void clearBoardState(){
		map = new HashMap<int[], LinkedList<Piece>>();
	}
	
	public void update(int[]... squares){
		ArrayList<Piece> piecesToUpdate = new ArrayList<Piece>();
		for(int[] square : squares){
			for(Piece p : this.getPieceList(square)){
				if(!piecesToUpdate.contains(p)){piecesToUpdate.add(p);}
			}
		}
		for(Piece currentPiece: piecesToUpdate){
			currentPiece.removeFromBoardState();
			currentPiece.generateMoves();
			currentPiece.addToBoardState();
		}
	}	
	
}
