package ice;

import newGameLogic.*;
import java.util.*;

public class IceEngine {
	int depth;
	WBColor myColor;
	
	public IceEngine(int level, WBColor color){
		depth = level;
		myColor = color;
	}
	
	public Move getNextMove(){
		BoardTree myTree = new BoardTree(depth,myColor);
		scoreTree(myTree);
		BoardTree bestChild = myTree.getChildren().get(0);
		for(BoardTree child: myTree.getChildren()){
			if(myColor == WBColor.White){
				if(bestChild.getScore() < child.getScore()){bestChild = child;}
			} else {
				if(bestChild.getScore() > child.getScore()){bestChild = child;}
			}
		}
		return bestChild.getThisMove();
	}
	private void scoreTree(BoardTree bt){
		if(bt.getChildren() != null){
			for(BoardTree thisBt: bt.getChildren()){
				scoreTree(thisBt);
			}
			bt.setScore(bt.getAverageOfChildren());
		} else {
			bt.setScore(scoreBoard(bt.getThisBoard()));
		}
	}
	
	public float scoreBoard(Board curBoard){
		float totalScore = 0;
		totalScore += BoardStateSubScore(curBoard);
		return totalScore;
	}
	public float BoardStateSubScore(Board curBoard){
		int whiteBoardCount = 0;
		int BlackBoardCount = 0;
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 8; col++){
				for(Piece pieceCounter: curBoard.getBoardStatus().getPieceList(row,col)){
					if(pieceCounter.getPieceColor() == WBColor.White){whiteBoardCount++;}
					else{BlackBoardCount--;}
				}
			}
		}
		return whiteBoardCount/BlackBoardCount;
	}
	
	
}
