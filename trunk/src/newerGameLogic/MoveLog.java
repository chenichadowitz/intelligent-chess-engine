package newerGameLogic;

import java.util.LinkedList;

public class MoveLog {
	LinkedList<Move> log = new LinkedList<Move>();
	
	public void addToLog(Move latestMove){
		log.add(latestMove);
	}
	public void takeBackMove(){
		log.removeLast();
	}
	public Move LastMove(){
		return log.getLast();
	}
	public void resetMoveLog(){
		log = new LinkedList<Move>();
	}
	
}
