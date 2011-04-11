package newerGameLogic;

import ice.IceEngine;

public class ComputerPlayer extends Player {

	IceEngine engine;
	
	public ComputerPlayer(WBColor color,int level){
		setColor(color);
		engine = new IceEngine(level, getPlayerColor());
	}
	
	@Override
	public PieceEnum getPromotion() {
		// TODO Auto-generated method stub
		return null;
	}

	public void findMove(){
		setNextMove(engine.getNextMove());
	}
}
