package newerGameLogic;

import java.util.HashMap;
import java.util.Map;

import newGameLogic.Board;
import newGameLogic.Player;
import newGameLogic.WBColor;


public class Game {
	Board gameBoard;
	private Map<WBColor, Player> playerMap = new HashMap<WBColor, Player>();
	gameState state;
	
	private Game(){
		state = gameState.Setup;
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
	
	
	
}
