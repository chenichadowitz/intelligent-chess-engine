package newGameLogic;

import main.Output;

public class GameBoard extends Board {

	public GameBoard(Player player1, Player player2){
		getPlayerMap().put(WBColor.White, player1);
		getPlayerMap().put(WBColor.Black, player2);
		setUpBoard();
	}
	
	/**
	 * sets up the board to initial position
	 */
	public void setUpBoard(){
		//Initialize game variables
		resetBoard();
		//Add pieces
		addPiece(new Piece(WBColor.White, PieceEnum.Rook,0,0));
		addPiece(new Piece(WBColor.White, PieceEnum.Knight,1,0));
		addPiece(new Piece(WBColor.White, PieceEnum.Bishop,2,0));
		addPiece(new Piece(WBColor.White, PieceEnum.Queen,3,0));
		addPiece(new Piece(WBColor.White, PieceEnum.King,4,0));
		addPiece(new Piece(WBColor.White, PieceEnum.Bishop,5,0));
		addPiece(new Piece(WBColor.White, PieceEnum.Knight,6,0));
		addPiece(new Piece(WBColor.White, PieceEnum.Rook,7,0));
		//black pieces
		addPiece(new Piece(WBColor.Black, PieceEnum.Rook,0,7));
		addPiece(new Piece(WBColor.Black, PieceEnum.Knight,1,7));
		addPiece(new Piece(WBColor.Black, PieceEnum.Bishop,2,7));
		addPiece(new Piece(WBColor.Black, PieceEnum.Queen,3,7));
		addPiece(new Piece(WBColor.Black, PieceEnum.King,4,7));
		addPiece(new Piece(WBColor.Black, PieceEnum.Bishop,5,7));
		addPiece(new Piece(WBColor.Black, PieceEnum.Knight,6,7));
		addPiece(new Piece(WBColor.Black, PieceEnum.Rook,7,7));
		//pawns
		for(int pawnAdder = 0; pawnAdder < 8; pawnAdder++){ // Snaaaaaaaaaaakeeee
			addPiece(new Piece(WBColor.White, PieceEnum.Pawn,pawnAdder,1));
			addPiece(new Piece(WBColor.Black, PieceEnum.Pawn,pawnAdder,6));
		}
		buildBoardStatus();
		Output.debug("board set up",2);
	}
	public boolean makeMove(Move action){
		if(pieceAt(action.getOrigPos()) == null){
			Output.debug("no piece there", 1);
			return false;
		}
		Move move = pieceAt(action.getOrigPos()).getMoveTo(action.getFinalPos());	
		if(move.equals(action)){
			if(this.execute(move)){
				getPlayerMap().get(WBColor.White).setInCheckMate((isGameOver((getPlayerMap().get(WBColor.White)))));
				getPlayerMap().get(WBColor.Black).setInCheckMate((isGameOver((getPlayerMap().get(WBColor.Black)))));
				addMovetoLog(move);
				setPrevMove(move);
				switchTurn();
				return true;
			}
		}
		Output.debug("cannot locate the correct move", 2);
		return false;
	}
}
