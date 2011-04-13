package newerGameLogic;

import java.util.HashMap;
import java.util.Map;

import newerGameLogic.Board;
import newGameLogic.Player;
import newerGameLogic.WBColor;


public class Game {
	Board gameBoard;
	private Map<WBColor, Player> playerMap = new HashMap<WBColor, Player>();
	GameState state;
	
	private Game(){
		state = GameState.Setup;
		gameBoard = new Board();
	}
	
	public void gotCommand(Command todo){
		switch(todo){
		case Move: gotMove(); break;
		case EndGame: gotEndGame(); break;
		case Load: gotLoad(); break;
		case NewGame: gotNewGame(); break;
		case Quit: gotQuit(); break;
		case Save: gotSave(); break;
		case Undo: gotUndo(); break;
		}
	}
	
	public Player getPlayer(newerGameLogic.WBColor wbColor){
		return playerMap.get(wbColor);
	}
	
	
}
