package ice;

import gameLogic.*;
import java.util.*;

public class IceEngine {
	int difficulty;
	ComputerPlayer me;
	
	public IceEngine(int level, ComputerPlayer thisPlayer){
		difficulty = level;
		me = thisPlayer;
	}
	
	public Listener getNextMove(){
		ArrayList<staticBoard> staticBoards = new ArrayList<staticBoard>();
		ArrayList<Listener> possibleMoves = me.getBoard().allValidMovesOf(me);
		int[] scores = new int[possibleMoves.size()];
		for(Listener curMove: possibleMoves){
			staticBoards.add(new staticBoard(me.getBoard(),curMove));
		}
		for(Board curBoard: staticBoards){
			scores[staticBoards.indexOf(curBoard)] = scoreBoard(curBoard);
		}
		int movenum = 0;
		for(int a = 0; a < scores.length; a++){
			if(scores[a] > scores[movenum]){movenum = a;}
		}
		return possibleMoves.get(movenum);
	}
	public int scoreBoard(Board curBoard){
		int score = 0;
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 8; col++){
				for(Piece pieceCounter: curBoard.getBoardState()[row][col]){
					if(pieceCounter.getColor() == me.getColor()){score++;}
					else{score--;}
				}
			}
		}
		return score;
	}
	
	
}
