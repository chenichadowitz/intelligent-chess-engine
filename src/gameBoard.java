
public class gameBoard extends board{
	
	
	
	
	
	boolean movePiece(piece movingPiece, int[] square){
		int[] squareInit = movingPiece.getPosition();
		boolean moved = movingPiece.move(square);
		if(moved){
			update(squareInit);
			update(square);
		}		
		return moved;	
	}

	
	

}
