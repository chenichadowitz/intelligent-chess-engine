
public class staticBoard extends Board{
	Piece[] pieces;
	Board[] subBoards;
	double score;
	
	staticboard(Piece[] whiteAndBlackPieces){
		pieces = whiteAndBlackPieces;
		for(int updater = 0; updater < pieces.length; updater++){
			pieces[updater].setBoard(this);
		}
	}
	
}
