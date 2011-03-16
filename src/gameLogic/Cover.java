package gameLogic;

public class Cover extends Listener {
	
	Piece coveredPiece;
	public Cover(int x1, int y1, int x2, int y2, Board place) {
		super(x1, y1, x2, y2, place);
		coveredPiece = currentBoard.pieceAt(FinalPos);
		description = "cover";
	}
	public Cover clone(){
		return new Cover(OrigPos[0],OrigPos[1],FinalPos[0],FinalPos[1],currentBoard);
	}

}
