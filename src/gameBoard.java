
public class gameBoard extends Board{
	
	
	
	
	
	boolean movePiece(Piece movingPiece, int[] square){
		int[] squareInit = movingPiece.getPosition();
		boolean moved = movingPiece.move(square);
		if(moved){
			update(squareInit);
			update(square);
		}		
		return moved;	
	}

	
	

}
