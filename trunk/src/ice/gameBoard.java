package ice;

public class gameBoard extends Board{
	
	public gameBoard(Player player1, Player player2){
		whitePlayer = player1;
		blackPlayer = player2;
	}
	
	public void setUpBoard(){
		//current implementation is for testing
		pieces.add(new King(true,  0,0,this));
		pieces.add(new King(false, 7,7,this));
		buildBoardState();
		Driver.debug("board set up");
		//only for TESTING
	}
	
	boolean movePiece(int[] squareAB){
		int[] square1 = {squareAB[0],squareAB[1]};
		if(pieceAt(square1) != null){
			int[] square2 = {squareAB[2],squareAB[3]};
			Driver.debug("moving piece " + pieceAt(square1) + " to " + square2[0] + " " + square2[1]);
			boolean moved = pieceAt(square1).move(square2);
			if(moved){
				update(square1);
				update(square2);
			}
			return moved;
		}
		Driver.debug("no piece there");
		return false;
	}

}
