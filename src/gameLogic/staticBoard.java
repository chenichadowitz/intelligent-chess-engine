package gameLogic;

import java.util.*;

import main.Output;

public class staticBoard extends Board{
	ArrayList<staticBoard> boards = new ArrayList<staticBoard>();
	int[] moveMade;
	
	public staticBoard(Board oldBoard,Listener action){
		Output.debug("//////staticBoard created",3);
		playerMap.put(true,new ComputerPlayer(true,1));
		playerMap.put(false,new ComputerPlayer(false,1));
		for(Piece newPiece : oldBoard.getPieces()){
			pieces.add(newPiece.clone());
		}		
		playersTurn = oldBoard.getTurn();
		for(Piece updater : pieces){
			updater.setBoard(this);
			for(Listener moveUpdater: updater.moves){
				moveUpdater.setBoard(this);
				moveUpdater.movingPiece = updater;
			}
		}
		buildBoardState();
		Listener staticAction = action.clone();
		staticAction.setBoard(this);
		staticAction.movingPiece = pieceAt(staticAction.OrigPos);
		for(Listener move: staticAction.movingPiece.moves){
			if(move.equals(staticAction)){
				if(move.execute()){
					playerMap.get(true).setMatedStatus(isCheckMate(playerMap.get(true)));
					playerMap.get(false).setMatedStatus(isCheckMate(playerMap.get(false)));
				}
			}
		}
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
