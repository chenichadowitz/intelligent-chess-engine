package newerGameLogic;

import java.util.ArrayList;

public class MoveFactory {
	private static PieceManager currentBoard;
	
	/**
	 * generates moves for the given piece
	 * @param currentPiece piece to generate moves for
	 */
	public static void generateMovesfor(Piece currentPiece, PieceManager board) {
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
	private static boolean processSquare(Position position, ArrayList<Move> newMoves,
			Position newPosition) {
		switch(currentBoard.statusOfSquare(newPosition)){
			case OffBoard: return false;
			case Empty: newMoves.add(new Move(position,newPosition,MoveEnum.Move)); return true;
			case OccupiedByBlack: 
				if(currentBoard.pieceAt(position).getPieceColor() == WBColor.Black){
					newMoves.add(new Move(position,newPosition,MoveEnum.Cover));
				} else {newMoves.add(new Move(position,newPosition,MoveEnum.Take));}
				return false;
			case OccupiedByWhite:
				if(currentBoard.pieceAt(position).getPieceColor() == WBColor.White){
					newMoves.add(new Move(position,newPosition,MoveEnum.Cover));
				} else {newMoves.add(new Move(position,newPosition,MoveEnum.Take));}
				return false;
		}
		return false;
	}
	/**
	 * generates moves for a rook at the given square
	 * @param position rook location
	 * @return the new moves array
	 */
	private static ArrayList<Move> movesForRookAt(Position position) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		//backwards
		int length = 1;
		while(processSquare(position,newMoves,position.shift(0,-length))){
			length++;
		}
		//forwards
		length = 1;
		while(processSquare(position,newMoves,position.shift(0, length))){
			length++;
		}
		//left
		length = 1;
		while(processSquare(position,newMoves,position.shift(-length, 0))){
			length++;
		}
		//right
		length = 1;
		while(processSquare(position,newMoves,position.shift(length, 0))){
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
	private static ArrayList<Move> movesForPawnAt(Position position, WBColor color) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		int delta = 1;
		if(color == WBColor.Black){delta = -1;}
//move up one
		Position square = position.shift(0,delta);
		SquareState status = currentBoard.statusOfSquare(square);
		if(status == SquareState.OccupiedByBlack || status == SquareState.OccupiedByWhite){
			newMoves.add(new Move(position,square,MoveEnum.Listen));
		}
		if (status == SquareState.Empty){
			newMoves.add(new Move(position,square,MoveEnum.Move));
//move up two
			if(position.getY() == 1 || position.getY() == 6){
				square = square.shift(0, delta);
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
		if(position.getX() != 7){
			square = square.shift(1, delta);
			status = currentBoard.statusOfSquare(square);
			if(status == SquareState.Empty){
				newMoves.add(new Move(position,square,MoveEnum.Listen));
				if(position.getY() == row[(delta+1)/2]){
					Position toTheRight = position.shift(1, 0);
						newMoves.add(new Move(position,toTheRight,MoveEnum.EnPassant));
				}
			}
			else if(status != SquareState.OffBoard){
				newMoves.add(new Move(position,square,MoveEnum.Take));
			}
		}
//take/cover left
		if(position.getX() != 0){
			square = square.shift(-1, delta);
			status = currentBoard.statusOfSquare(square);
			if(status == SquareState.Empty){
				newMoves.add(new Move(position,square,MoveEnum.Listen));
				if(position.getY() == row[(delta+1)/2]){
					Position toTheLeft = position.shift(-1, 0);
					newMoves.add(new Move(position,toTheLeft,MoveEnum.EnPassant));
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
	private static ArrayList<Move> movesForKnightAt(Position position) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		processSquare(position,newMoves,position.shift(-2,-1));
		processSquare(position,newMoves,position.shift(-2,+1));
		processSquare(position,newMoves,position.shift(+2,-1));
		processSquare(position,newMoves,position.shift(+2,+1));
		processSquare(position,newMoves,position.shift(-1,+2));
		processSquare(position,newMoves,position.shift(+1,+2));
		processSquare(position,newMoves,position.shift(-1,-2));
		processSquare(position,newMoves,position.shift(-1,-2));
		return newMoves;
	}
	/**
	 * generates new moves for a king at the given location
	 * @param position the position of the king
	 * @param castle whether that king can castle
	 * @return returns the new moves array
	 */
	private static ArrayList<Move> movesForKingAt(Position position, boolean castle) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		processSquare(position,newMoves,position.shift(-1,-1));
		processSquare(position,newMoves,position.shift(-1, 0));
		processSquare(position,newMoves,position.shift(-1,+1));
		processSquare(position,newMoves,position.shift(+1,-1));
		processSquare(position,newMoves,position.shift(+1, 0));
		processSquare(position,newMoves,position.shift(+1,+1));
		if(processSquare(position,newMoves,position.shift( 0,-1))){//left
			if(castle){
				//castle Queen side
				int delta = 1;
				Position edge = new Position((int)(3.5+3.5*delta),position.getY());
				if(currentBoard.pieceAt(edge) != null && currentBoard.pieceAt(edge).canCastle()){
					Move moveChecker = newMoves.get(newMoves.size()-1);
					if(moveChecker.getType() == MoveEnum.Move){
						Position square = position.shift(2*delta, 0);
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
		if(processSquare(position,newMoves,position.shift( 0,+1))){//right
			if(castle){
				//castle king side
				int delta = 1;
				Position edge = new Position((int)(3.5+3.5*delta),position.getY());
				if(currentBoard.pieceAt(edge) != null && currentBoard.pieceAt(edge).canCastle()){
					Move moveChecker = newMoves.get(newMoves.size()-1);
					if(moveChecker.getType() == MoveEnum.Move){
						Position square = position.shift(2*delta, 0);
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
	private static ArrayList<Move> movesForBishopAt(Position position) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		//down-left
		int length = 1;
		while(processSquare(position,newMoves,position.shift(-length, -length))){
			length++;
		}
		//down-right
		length = 1;
		while(processSquare(position,newMoves,position.shift(length, -length))){
			length++;
		}
		//up-left
		length = 1;
		while(processSquare(position,newMoves,position.shift(-length, length))){
			length++;
		}
		//up-right
		length = 1;
		while(processSquare(position,newMoves,position.shift(length, length))){
			length++;
		}
		return newMoves;
	}
	
}
