
public class staticBoard extends board{
	piece[] pieces;
	board[] subBoards;
	double score;
	
	staticboard(piece[] whiteAndBlackPieces){
		pieces = whiteAndBlackPieces;
		for(int updater = 0; updater < pieces.length; updater++){
			pieces[updater].setBoard(this);
		}
	}
	
}
