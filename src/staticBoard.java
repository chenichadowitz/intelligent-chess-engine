import java.util.*;

public class staticBoard extends Board{
	ArrayList<staticBoard> boards = new ArrayList<staticBoard>();
	int[] moveMade;
	
	public staticBoard(Board oldBoard, int[] move){
		pieces = oldBoard.getPieces();
		boardState = oldBoard.getBoardState();
		playersTurn = oldBoard.getTurn();
		moveMade = move;
		for(int updater = 0; updater < pieces.size(); updater++){
			pieces.get(updater).setBoard(this);
		}
	}
	public boolean movePiece(int[] squareAB){
		int[] square1 = {squareAB[0],squareAB[1]};
		int[] square2 = {squareAB[2],squareAB[3]};
		if(pieceAt(square1) != null && (pieceAt(square1).getMoves().contains(square2) || pieceAt(square1).getTakes().contains(square2))){
			boards.add(new staticBoard(this, squareAB));
			boolean moved = boards.get(boards.size() -1).pieceAt(square1).move(square2);
		if(moved){
			boards.get(boards.size() -1).update(square1);
			boards.get(boards.size() -1).update(square2);
			boards.get(boards.size() -1).switchTurn();
		}
		return moved;
		}
		return false;
	}
	
}
