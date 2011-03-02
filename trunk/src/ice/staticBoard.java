package ice;
import java.util.*;

public class staticBoard extends Board{
	ArrayList<staticBoard> boards = new ArrayList<staticBoard>();
	int[] moveMade;
	
	public staticBoard(Board oldBoard, int[] move){
		Driver.debug("//////staticBoard created",3);
		playerMap.put(true, new Player());
		playerMap.put(false, new Player());
		for(Piece newPiece : oldBoard.getPieces()){
			pieces.add(newPiece.clone());
		}
		boardState = oldBoard.getBoardState();
		playersTurn = oldBoard.getTurn();
		moveMade = move;
		for(int updater = 0; updater < pieces.size(); updater++){
			pieces.get(updater).setBoard(this);
		}
		int[] square1 = {move[0],move[1]};
		int[] square2 = {move[2],move[3]};
		pieceAt(square1).setPosition(square2);
		update(square1);
		update(square2);
		setKingCheck();
		switchTurn();
	}
	public boolean movePiece(int[] squareAB){
		int[] square1 = {squareAB[0],squareAB[1]};
		int[] square2 = {squareAB[2],squareAB[3]};
		if(pieceAt(square1) != null && (pieceAt(square1).getMoves().contains(square2) || pieceAt(square1).getTakes().contains(square2))){
			boards.add(new staticBoard(this, squareAB));
			boolean moved = boards.get(boards.size() -1).pieceAt(square1).move(square2);
			return moved;
		}
		return false;
	}
	
}
