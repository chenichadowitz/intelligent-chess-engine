package ice;
import java.util.*;

public class staticBoard extends Board{
	ArrayList<staticBoard> boards = new ArrayList<staticBoard>();
	int[] moveMade;
	
	public staticBoard(Board oldBoard, /*int[] move*/ Move action){
		Driver.debug("//////staticBoard created",3);
		playerMap.put(true, new Player());
		playerMap.put(false, new Player());
		for(Piece newPiece : oldBoard.getPieces()){
			pieces.add(newPiece.clone());
		}
		boardState = oldBoard.getBoardState();
		playersTurn = oldBoard.getTurn();
//		moveMade = move;
		for(Piece updater : pieces){
			updater.setBoard(this);
			for(Move moveUpdater: updater.possibleMoves){
				moveUpdater.setCurrentBoard(this);
			}
		}
//		int[] square1 = {move[0],move[1]};
//		int[] square2 = {move[2],move[3]};
//		pieceAt(square1).setPosition(square2);
//		pieceAt(square2).castle = false;
//		update(square1);
//		update(square2);
		action.setCurrentBoard(this);
		action.execute(true);//forcing move
		update(action.OrigPos);
		update(action.FinalPos);
		setKingCheck();
		switchTurn();
	}
	public boolean movePiece(/*int[] squareAB*/ Move action){
/*
		int[] square1 = {squareAB[0],squareAB[1]};
		int[] square2 = {squareAB[2],squareAB[3]};
		if(pieceAt(square1) != null && (pieceAt(square1).getMoves().contains(square2) || pieceAt(square1).getTakes().contains(square2))){
			boards.add(new staticBoard(this, squareAB));
			boolean moved = boards.get(boards.size() -1).pieceAt(square1).move(square2);
			return moved;
		}
		return false;
	}
*/
		if(action.validMove){
			boards.add(new staticBoard(this, action));
			return true;
		}
		return false;
	}
}
