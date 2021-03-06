package gameLogic;

import java.util.ArrayList;
import java.util.LinkedList;

import main.Output;

public class gameBoard extends Board{
	
	public gameBoard(Player player1, Player player2){
		playerMap.put(true, player1);
		playerMap.put(false, player2);
		playerMap.get(true).setBoard(this);
		playerMap.get(false).setBoard(this);
	}
	
	@SuppressWarnings("unchecked")
	public void setUpBoard(){
		//Initialize game variables
		pieces = new ArrayList<Piece>();
		playersTurn = true;
		boardState = (LinkedList<Piece>[][]) new LinkedList[8][8];
		resetMoveLog();
		Output.resetOutput();
		//Add pieces
		pieces.add(new Rook  (true, 0,0 ,this));
		pieces.add(new Knight(true, 1,0 ,this));
		pieces.add(new Bishop(true, 2,0 ,this));
		pieces.add(new Queen (true, 3,0 ,this));
		pieces.add(new King  (true, 4,0 ,this));
		pieces.add(new Bishop(true, 5,0 ,this));
		pieces.add(new Knight(true, 6,0 ,this));
		pieces.add(new Rook  (true, 7,0 ,this));
		//black pieces
		pieces.add(new Rook  (false, 0,7 ,this));
		pieces.add(new Knight(false, 1,7 ,this));
		pieces.add(new Bishop(false, 2,7 ,this));
		pieces.add(new Queen (false, 3,7 ,this));
		pieces.add(new King  (false, 4,7 ,this));
		pieces.add(new Bishop(false, 5,7 ,this));
		pieces.add(new Knight(false, 6,7 ,this));
		pieces.add(new Rook  (false, 7,7 ,this));
		//pawns
		for(int pawnAdder = 0; pawnAdder < 8; pawnAdder++){ // Snaaaaaaaaaaakeeee
			pieces.add(new Pawn(true, pawnAdder,1, this));
			pieces.add(new Pawn(false,pawnAdder,6, this));
		}
		
		buildBoardState();
		Output.debug("board set up",1);
	}
		
	public boolean movePiece(Listener action){
		if(action.color != getTurn()){
			Output.debug("wrong piece color", 1);
			return false;
		}
		for(Listener move: action.movingPiece.moves){
			if(move.equals(action)){
				if(move.execute()){
					playerMap.get(true).setMatedStatus(isCheckMate(playerMap.get(true)));
					playerMap.get(false).setMatedStatus(isCheckMate(playerMap.get(false)));
					addMovetoLog(move);
					return true;
				}
			}
		}
		Output.debug(action.toString() + " is not in the available moves...", 4);
		return false;
	}
}
