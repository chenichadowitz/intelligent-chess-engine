
public class King extends piece{
	King(boolean player, int[] where, board onWhat){
		color = player;
		position = where;
		currentBoard = onWhat;
		value = 0;
	}
	void generateMoves(){
		boolean[] status;
		int[] square = new int[2];
		for(int column = -1; column <=1; column++){
			for(int row = -1; row <= 1; row++){
				square[0] = position[0] + column;
				square[1] = position[1] + row;
				status = currentBoard.statusOfSquare(square);
				if ((status[0] && status[1] == !color) || (!status[0] && status[1])) {
					moves.add(square);
				}
			}
		}
		
	}
}
