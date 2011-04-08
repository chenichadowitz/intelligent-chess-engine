package ice;

import java.util.ArrayList;

import newGameLogic.Board;
import newGameLogic.GameBoard;
import newGameLogic.Move;
import newGameLogic.StaticBoard;
import newGameLogic.WBColor;

public class BoardTree {
	private BoardTree parent;
	private ArrayList<BoardTree> children;
	private Move thisMove;
	private Board thisBoard;
	private float score;
	private WBColor turnColor;
	
	public BoardTree(Board b, Move currentMove, int depth, BoardTree p, WBColor thisColor){
		parent = p;
		thisBoard = b;
		thisMove = currentMove;
		turnColor = thisColor;
		if(depth > 1){
			for(Move m: thisBoard.allValidMovesOf(thisBoard.getPlayerMap().get(thisBoard.getTurn()))){
				children.add(new BoardTree(new StaticBoard(thisBoard,m), m,depth-1,this, turnColor.next()));
			}
		}
	}
	public BoardTree(int depth, WBColor thisColor){
		this(GameBoard.getCurrentGB(),null,depth,null, thisColor);
	}
	
	/**
	 * @return the turnColor
	 */
	public WBColor getTurnColor() {
		return turnColor;
	}	
	/**
	 * @return the score
	 */
	public float getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(float score) {
		this.score = score;
	}
	/**
	 * @return the parent
	 */
	public BoardTree getParent() {
		return parent;
	}
	/**
	 * @return the children
	 */
	public ArrayList<BoardTree> getChildren() {
		return children;
	}
	/**
	 * @return the thisBoard
	 */
	public Board getThisBoard() {
		return thisBoard;
	}
	/**
	 * @return the thisMove
	 */
	public Move getThisMove() {
		return thisMove;
	}
	public float getAverageOfChildren() {
		float average = 0;
		for(BoardTree child: children){
			average += child.getScore();
		}
		return average/children.size();
	}
}
