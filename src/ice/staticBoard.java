package ice;
import java.util.*;

public class staticBoard extends Board{
	ArrayList<staticBoard> boards = new ArrayList<staticBoard>();
	int[] moveMade;
	
	public staticBoard(Board oldBoard,Move action){
		Debug.debug("//////staticBoard created",3);
		playerMap.put(true, new Player());
		playerMap.put(false, new Player());
		for(Piece newPiece : oldBoard.getPieces()){
			pieces.add(newPiece.clone());
		}
		boardState = oldBoard.getBoardState().clone();
		playersTurn = oldBoard.getTurn();
		for(Piece updater : pieces){
			updater.setBoard(this);
			for(Move moveUpdater: updater.possibleMoves){
				moveUpdater.setCurrentBoard(this);
				moveUpdater.movingPiece = updater;
			}
		}
		Move staticAction = action.clone();
		staticAction.setCurrentBoard(this);
		staticAction.movingPiece = pieceAt(staticAction.OrigPos);
		staticAction.execute();//forcing move
		update(staticAction.OrigPos);
		update(staticAction.FinalPos);
		setKingCheck();
		switchTurn();
	}
	public boolean movePiece(Move action){
		if(action.validMove){
			boards.add(new staticBoard(this, action));
			return true;
		}
		return false;
	}
}
