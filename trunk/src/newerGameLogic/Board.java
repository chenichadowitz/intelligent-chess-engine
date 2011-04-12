package newerGameLogic;

public class Board {
	PieceManager pieces;
	MoveLog log;
	PlayerStatus color;
	WBColor turn;
	
	Board(){
		log = new MoveLog();
		color = new PlayerStatus();
		turn = WBColor.White;
		pieces = new PieceManager();
	}
	private void initializeBoard() {
		pieces.addPiece(new Piece(WBColor.White, PieceEnum.Rook,0,0));
		pieces.addPiece(new Piece(WBColor.White, PieceEnum.Knight,1,0));
		pieces.addPiece(new Piece(WBColor.White, PieceEnum.Bishop,2,0));
		pieces.addPiece(new Piece(WBColor.White, PieceEnum.Queen,3,0));
		pieces.addPiece(new Piece(WBColor.White, PieceEnum.King,4,0));
		pieces.addPiece(new Piece(WBColor.White, PieceEnum.Bishop,5,0));
		pieces.addPiece(new Piece(WBColor.White, PieceEnum.Knight,6,0));
		pieces.addPiece(new Piece(WBColor.White, PieceEnum.Rook,7,0));
		//black pieces
		pieces.addPiece(new Piece(WBColor.Black, PieceEnum.Rook,0,7));
		pieces.addPiece(new Piece(WBColor.Black, PieceEnum.Knight,1,7));
		pieces.addPiece(new Piece(WBColor.Black, PieceEnum.Bishop,2,7));
		pieces.addPiece(new Piece(WBColor.Black, PieceEnum.Queen,3,7));
		pieces.addPiece(new Piece(WBColor.Black, PieceEnum.King,4,7));
		pieces.addPiece(new Piece(WBColor.Black, PieceEnum.Bishop,5,7));
		pieces.addPiece(new Piece(WBColor.Black, PieceEnum.Knight,6,7));
		pieces.addPiece(new Piece(WBColor.Black, PieceEnum.Rook,7,7));
		//pawns
		for(int pawnAdder = 0; pawnAdder < 8; pawnAdder++){ // Snaaaaaaaaaaakeeee
			pieces.addPiece(new Piece(WBColor.White, PieceEnum.Pawn,pawnAdder,1));
			pieces.addPiece(new Piece(WBColor.Black, PieceEnum.Pawn,pawnAdder,6));
		}
	}
	public Piece pieceAt(Position Pos) {
		return pieces.pieceAt(Pos);
	}
	public WBColor getTurn() {
		return turn;
	}
	public Move getPrevMove() {
		return log.LastMove();
	}
	
	
	
	
	
	
}
