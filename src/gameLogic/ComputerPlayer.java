package gameLogic;

import ice.IceEngine;

public class ComputerPlayer extends Player {

	IceEngine engine;
	
	ComputerPlayer(boolean color, int difficulty){
		this.color = color;
		engine = new IceEngine(1,this);
	}

	public Listener getMove() {
		return engine.getNextMove();
	}
	
	
}
