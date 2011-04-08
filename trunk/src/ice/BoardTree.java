package ice;

import java.util.ArrayList;

import newGameLogic.Board;
import newGameLogic.Move;
import newGameLogic.StaticBoard;

public class BoardTree {
	private BoardTree parent;
	private ArrayList<BoardTree> children;
	private Move thisMove;
	private Board thisBoard;
	private long score;
	

	public BoardTree(Board b, Move currentMove, int depth, BoardTree p){
		parent = p;
		thisBoard = b;
		thisMove = currentMove;
		if(depth > 1){
			for(Move m: thisBoard.allValidMovesOf(thisBoard.getPlayerMap().get(thisBoard.getTurn()))){
				children.add(new BoardTree(new StaticBoard(thisBoard,m), m,depth-1,this));
			}
		}
	}
	public BoardTree(Board b,int depth){
		this(b,null,depth,null);
	}
	/**
	 * @return the score
	 */
	public long getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(long score) {
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
}
