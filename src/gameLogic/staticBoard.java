package gameLogic;
import gameLogic.Board;
import gameLogic.Piece;
import gameLogic.Player;

import java.util.*;

import main.Debug;

public class staticBoard extends Board{
	ArrayList<staticBoard> boards = new ArrayList<staticBoard>();
	int[] moveMade;
	
	public staticBoard(Board oldBoard,Listener action){
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
			for(Listener moveUpdater: updater.moves){
				moveUpdater.setBoard(this);
				moveUpdater.movingPiece = updater;
			}
		}
		Listener staticAction = action.clone();
		staticAction.setBoard(this);
		staticAction.movingPiece = pieceAt(staticAction.OrigPos);
		staticAction.execute();//forcing move
		update(staticAction.OrigPos);
		update(staticAction.FinalPos);
		setKingCheck();
		switchTurn();
	}
	public boolean movePiece(Listener action){
		boards.add(new staticBoard(this, action));
		return true;
	}
}
