
public class King extends piece{
	private boolean check = false;
	private boolean castle = true;
	public King(boolean player, int[] where, board onWhat){
		color = player;
		position = where;
		currentBoard = onWhat;
		value = 0;
	}
	boolean move(int[] newsquare){
		if(super.move(newsquare)){
			castle = false;
			return true;
		}
		else{return false;}
	}
	public void generateMoves(){
		processSquare(position[0] -1,position[1] -1);
		processSquare(position[0] -1,position[1]   );
		processSquare(position[0] -1,position[1] +1);
		processSquare(position[0]   ,position[1] -1);
		processSquare(position[0]   ,position[1] +1);
		processSquare(position[0] +1,position[1] -1);
		processSquare(position[0] +1,position[1]   );
		processSquare(position[0] +1,position[1] +1);
		if(castle){//fill in code!!!
		}
		
	}
	public boolean getcheck(){return check;}
	public void setCheck(boolean inCheck){check = inCheck;}
	public boolean inCheck(){
		for(piece p: currentBoard.getPieces()){
			check = p.takes.contains(position);
			if(check){return check;}
		}
		return check;
	}
}
