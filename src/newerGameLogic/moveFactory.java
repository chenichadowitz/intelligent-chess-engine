package newerGameLogic;

import java.util.ArrayList;
import java.util.Arrays;


import main.Output;

public class moveFactory {
	static Board currentBoard;
	
	/**
	 * generates moves for the given piece
	 * @param currentPiece piece to generate moves for
	 */
	public static void generateMovesfor(Piece currentPiece, Board board) {
		currentBoard = board;
		ArrayList<Move> newMoves = new ArrayList<Move>();
		switch(currentPiece.getType()){
			case Bishop:
				newMoves.addAll(movesForBishopAt(currentPiece.getPosition())); break;
			case King:
				newMoves.addAll(movesForKingAt(currentPiece.getPosition(),currentPiece.canCastle())); break;
			case Knight:
				newMoves.addAll(movesForKnightAt(currentPiece.getPosition())); break;
			case Pawn:
				newMoves.addAll(movesForPawnAt(currentPiece.getPosition(),currentPiece.getPieceColor())); break;
			case Rook:
				newMoves.addAll(movesForRookAt(currentPiece.getPosition())); break;
			case Queen:
				newMoves.addAll(movesForRookAt(currentPiece.getPosition()));
				newMoves.addAll(movesForBishopAt(currentPiece.getPosition()));  break;				
		}
		currentPiece.setMoves(newMoves);
	}
	/**
	 * processes the given square correctly for most pieces
	 * @param position the position of the piece generating the moves
	 * @param newMoves the move array to add the new move to
	 * @param i the x coordinate of the move
	 * @param j the y coordinate of the move
	 * @return returns whether that square was empty or not
	 */
	private static boolean processSquare(int[] position, ArrayList<Move> newMoves,
			int i, int j) {
		int[] s = {i,j};
		switch(currentBoard.statusOfSquare(s)){
			case OffBoard: return false;
			case Empty: newMoves.add(new Move(position,s,MoveEnum.Move)); return true;
			case OccupiedByBlack: 
				if(currentBoard.pieceAt(position).getPieceColor() == WBColor.Black){
					newMoves.add(new Move(position,s,MoveEnum.Cover));
				} else {newMoves.add(new Move(position,s,MoveEnum.Take));}
				return false;
			case OccupiedByWhite:
				if(currentBoard.pieceAt(position).getPieceColor() == WBColor.White){
					newMoves.add(new Move(position,s,MoveEnum.Cover));
				} else {newMoves.add(new Move(position,s,MoveEnum.Take));}
				return false;
		}
		return false;
	}
	/**
	 * generates moves for a rook at the given square
	 * @param position rook location
	 * @return the new moves array
	 */
	private static ArrayList<Move> movesForRookAt(int[] position) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		//backwards
		int length = 1;
		while(processSquare(position,newMoves,position[0],position[1] -length)){
			length++;
		}
		//forwards
		length = 1;
		while(processSquare(position,newMoves,position[0],position[1] + length)){
			length++;
		}
		//left
		length = 1;
		while(processSquare(position,newMoves,position[0] - length,position[1])){
			length++;
		}
		//right
		length = 1;
		while(processSquare(position,newMoves,position[0] + length,position[1])){
			length++;
		}
		return newMoves;
	}
	/**
	 * generates moves for a pawn at the given location
	 * @param position the position of the pawn
	 * @param color the color of the pawn
	 * @return the array of new moves
	 */
	private static ArrayList<Move> movesForPawnAt(int[] position, WBColor color) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		int delta = 1;
		if(color == WBColor.Black){delta = -1;}
//move up one
		int[]     square    = {position[0], position[1] +delta};
		SquareState status = currentBoard.statusOfSquare(square);
		if(status == SquareState.OccupiedByBlack || status == SquareState.OccupiedByWhite){
			newMoves.add(new Move(position,square,MoveEnum.Listen));
		}
		if (status == SquareState.Empty){
			newMoves.add(new Move(position,square,MoveEnum.Move));
//move up two
			if(position[1] == 1 || position[1] == 6){
				square[1] += delta;
				status = currentBoard.statusOfSquare(square);
				if(status == SquareState.OccupiedByBlack || status == SquareState.OccupiedByWhite){
					newMoves.add(new Move(position,square,MoveEnum.Listen));
				}
				if (status == SquareState.Empty){
					newMoves.add(new Move(position,square,MoveEnum.Move));	
				}
			}
		}
		int[] row = {3,4};
//take/cover right
		if(position[0] != 7){
			square[0]  = position[0] +1;
			square[1]  = position[1] +delta;
			status = currentBoard.statusOfSquare(square);
			if(status == SquareState.Empty){
				newMoves.add(new Move(position,square,MoveEnum.Listen));
				if(position[1] == row[(delta+1)/2]){
					int[] toTheRight = {position[0]+1,position[1]};
					if(Arrays.equals(currentBoard.getprevMove().getFinalPos(),toTheRight) && currentBoard.pieceAt(toTheRight).getType() == PieceEnum.Pawn){
						newMoves.add(new Move(position,square,MoveEnum.EnPassant));
					} else {
						newMoves.add(new Move(position,square[0],position[1],MoveEnum.Listen));
					}
				}
			}
			else if(status != SquareState.OffBoard){
				newMoves.add(new Move(position,square,MoveEnum.Take));
			}
		}
//take/cover left
		if(position[0] != 0){
			square[0]  = position[0] -1;
			square[1]  = position[1] +delta;
			status = currentBoard.statusOfSquare(square);
			if(status == SquareState.Empty){
				newMoves.add(new Move(position,square,MoveEnum.Listen));
				if(position[1] == row[(delta+1)/2]){
					int[] toTheLeft = {position[0]+1,position[1]};
					if(Arrays.equals(currentBoard.getprevMove().getFinalPos(),toTheLeft) && currentBoard.pieceAt(toTheLeft).getType() == PieceEnum.Pawn){
						newMoves.add(new Move(position,square,MoveEnum.EnPassant));
					} else {
						newMoves.add(new Move(position,square[0],position[1],MoveEnum.Listen));
					}
				}
			}
			else if(status != SquareState.OffBoard){
				newMoves.add(new Move(position,square,MoveEnum.Take));
			}
		}
		return newMoves;
	}
	/**
	 * generates moves for a knight at the given square
	 * @param position the position of the knight
	 * @return the array of new moves
	 */
	private static ArrayList<Move> movesForKnightAt(int[] position) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		processSquare(position,newMoves,position[0] -2,position[1] -1);
		processSquare(position,newMoves,position[0] -2,position[1] +1);
		processSquare(position,newMoves,position[0] +2,position[1] -1);
		processSquare(position,newMoves,position[0] +2,position[1] +1);
		processSquare(position,newMoves,position[0] -1,position[1] +2);
		processSquare(position,newMoves,position[0] +1,position[1] +2);
		processSquare(position,newMoves,position[0] -1,position[1] -2);
		processSquare(position,newMoves,position[0] +1,position[1] -2);
		return newMoves;
	}
	/**
	 * generates new moves for a king at the given location
	 * @param position the position of the king
	 * @param castle whether that king can castle
	 * @return returns the new moves array
	 */
	private static ArrayList<Move> movesForKingAt(int[] position, boolean castle) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		processSquare(position,newMoves,position[0] -1,position[1] -1);//down left
		processSquare(position,newMoves,position[0] -1,position[1]   );//down
		processSquare(position,newMoves,position[0] -1,position[1] +1);//down right
		processSquare(position,newMoves,position[0] +1,position[1] -1);//up right
		processSquare(position,newMoves,position[0] +1,position[1]   );//up
		processSquare(position,newMoves,position[0] +1,position[1] +1);//up left
		if(processSquare(position,newMoves,position[0]   ,position[1] -1)){//left
			if(castle){
				Output.debug("checking queenside castling", 3);
				//castle Queen side
				int delta = 1;
				int[] edge = {(int)(3.5+3.5*delta),position[1]};
				if(currentBoard.pieceAt(edge) != null && currentBoard.pieceAt(edge).canCastle()){
					Move moveChecker = newMoves.get(newMoves.size()-1);
					if(moveChecker.getType() == MoveEnum.Move){
						int[]     square = {position[0] +2*delta, position[1]};
						SquareState status = currentBoard.statusOfSquare(square);
						if (status == SquareState.Empty){
							newMoves.add(new Move(position,square,MoveEnum.Castle));
						} else {
							newMoves.add(new Move(position,square,MoveEnum.Listen));
						}
					}
				}
			}
		}
		if(processSquare(position,newMoves,position[0]   ,position[1] +1)){//right
			if(castle){
				Output.debug("checking kingside castling", 3);
				//castle king side
				int delta = 1;
				int[] edge = {(int)(3.5+3.5*delta),position[1]};
				if(currentBoard.pieceAt(edge) != null && currentBoard.pieceAt(edge).canCastle()){
					Move moveChecker = newMoves.get(newMoves.size()-1);
					if(moveChecker.getType() == MoveEnum.Move){
						int[]     square = {position[0] +2*delta, position[1]};
						SquareState status = currentBoard.statusOfSquare(square);
						if (status == SquareState.Empty){
							newMoves.add(new Move(position,square,MoveEnum.Castle));
						} else {
							newMoves.add(new Move(position,square,MoveEnum.Listen));
						}
					}
				}
			}
		}
		return newMoves;
	}
	/**
	 * generates moves for a bishop at the given location
	 * @param position the position of the bishop
	 * @return returns the new array of moves
	 */
	private static ArrayList<Move> movesForBishopAt(int[] position) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		//down-left
		int length = 1;
		while(processSquare(position,newMoves,position[0] -length,position[1] -length)){
			length++;
		}
		//down-right
		length = 1;
		while(processSquare(position,newMoves,position[0] +length,position[1] -length)){
			length++;
		}
		//up-left
		length = 1;
		while(processSquare(position,newMoves,position[0] - length,position[1] +length)){
			length++;
		}
		//up-right
		length = 1;
		while(processSquare(position,newMoves,position[0] + length,position[1] +length)){
			length++;
		}
		return newMoves;
	}
	
}
