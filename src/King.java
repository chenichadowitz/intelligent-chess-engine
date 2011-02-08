
public class King extends piece{
	boolean check = false;
	King(boolean player, int[] where, board onWhat){
		color = player;
		position = where;
		currentBoard = onWhat;
		value = 0;
	}
	void generateMoves(){
		processSquare(position[0] -1,position[1] -1);
		processSquare(position[0] -1,position[1]   );
		processSquare(position[0] -1,position[1] +1);
		processSquare(position[0]   ,position[1] -1);
		processSquare(position[0]   ,position[1] +1);
		processSquare(position[0] +1,position[1] -1);
		processSquare(position[0] +1,position[1]   );
		processSquare(position[0] +1,position[1] +1);		
	}
	boolean getcheck(){return check;}
	void setCheck(boolean inCheck){check = inCheck;}
}
