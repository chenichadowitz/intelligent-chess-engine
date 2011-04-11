package newerGameLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


import main.Output;

public class Board implements Cloneable{
	private WBColor turn = WBColor.White;
	private BoardState boardStatus = new BoardState();
	private MoveLog log = new MoveLog();
		
	/**
	 * returns the state of the square in question
	 * @param square square to get the state of
	 * @return the state of the square
	 */
	public  SquareState statusOfSquare(Position square){
		if(boardStatus.getPieceAt(square) == null){
			return SquareState.Empty;
		}else{
			if(boardStatus.getPieceAt(square).getPieceColor() == WBColor.White){
				return SquareState.OccupiedByWhite;
			} else {
				return SquareState.OccupiedByBlack;
			}
		}
    }
	/**
	 * switches the player's turn
	 */
	public void switchTurn(){
		turn = turn.next();
		Output.debug("switched turn", 3);
	}
	/**
	 * removes the given piece from the boardState and piece array the piece is now dead
	 * @param taken piece to take
	 */
	private void takePiece(Piece taken){
		Output.debug("took " + taken, 3);
		removePieceFromBoardState(taken);
		pieces.remove(taken);
	}	
	
	/**
	 * adds the given move to the game log
	 * @param m move to add
	 */
	public void addMovetoLog(Move m){
		log.addToLog(m);
		Output.printNotation(m);
	}
	
	
	/**
	 * checks for game ending scenarios
	 * @param whosTurn the player who's turn it is
	 * @return returns true if the game is over
	 */
	public boolean isGameOver(Player whosTurn){
		ArrayList<Move> allValidMoves = allValidMovesOf(whosTurn);		
		if(!whosTurn.isInCheck()){
			//more tests for staleMate
			if(pieces.size() == 2){return true;}
			if(allValidMoves.size() == 0){
				return true;
			}
			else{return false;}
		}
		if(allValidMoves.size() > 0){return false;}
		return true;
	}
	/**
	 * @return the turn
	 */
	public WBColor getTurn() {
		return turn;
	}
	
	/**
	 * @return the boardStatus
	 */
	public BoardState getBoardStatus() {
		return boardStatus;
	}
	/**
	 * resets the board's variables
	 */
	public void resetBoard(){
		turn = WBColor.White;
		boardStatus.resetBoardState();
		log.resetMoveLog();
		Output.resetOutput();
	}
}
