package gameLogic;
import java.util.*;

import main.Debug;
public class Listener{
	protected int[] OrigPos = new int[2];
	protected int[] FinalPos =  new int[2];
	protected Player owner;
	protected boolean color;
	protected Board currentBoard;
	protected Piece movingPiece;
	protected boolean oldCastle;
	protected String description;
	protected boolean putInCheck;
	
	public Listener(int x1, int y1, int x2, int y2, Board place){
		currentBoard = place;
		OrigPos[0] = x1;
		OrigPos[1] = y1;
		FinalPos[0] = x2;
		FinalPos[1] = y2;
		movingPiece = currentBoard.pieceAt(OrigPos);
		color = movingPiece.color;
		owner = currentBoard.playerMap.get(movingPiece.getColor());
		description = "Listener";
	}
	
	public Listener clone(){
		return new Listener(OrigPos[0],OrigPos[1],FinalPos[0],FinalPos[1],currentBoard);
	}
	public String toString(){return description;}
	
	public boolean execute() {
		return false;
	}
	public void undo() {}

	public void setBoard(Board newBoard){
		currentBoard = newBoard;
	}
	
	public boolean equals(Listener test){
		boolean equals = true;
		equals = equals && Arrays.equals(OrigPos, test.OrigPos);
		equals = equals && Arrays.equals(FinalPos, test.FinalPos);
		equals = equals && (movingPiece.equals(test.movingPiece));
		equals = equals && (owner == test.owner);
		equals = equals && (currentBoard.equals(test.currentBoard));
		return equals;
	}
	public static boolean contains(ArrayList<Listener> haystack, Listener needle){
		for(Listener move : haystack){
			if(needle.equals(move)){
				return true;
			}
		}
		return false;
	}
	public boolean resultsInCheck(){
		if(execute()){undo(); return false;}
		return true;
	}
	
	
	
	public void addMoveToBS(){
		if(currentBoard.boardState[FinalPos[0]][FinalPos[1]].contains(movingPiece)){
			Debug.debug("WARNING: " +movingPiece+ " already exists on boardState", 1);
		}
		currentBoard.boardState[FinalPos[0]][FinalPos[1]].add(movingPiece);
	}
	public void remMoveFrBS(){
		currentBoard.boardState[FinalPos[0]][FinalPos[1]].remove(movingPiece);
	}

}
