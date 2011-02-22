package ice;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HumanPlayer white = new HumanPlayer(true);
		HumanPlayer black = new HumanPlayer(false);
		HumanPlayer current;
		gameBoard gb = new gameBoard(white, black);
		gb.setUpBoard();
		boolean moveResult;
		gb.display();
		while(true){
			if(gb.getTurn()){
				current = white;
			} else {
				current = black;
			}
			moveResult = false;
			while(!moveResult){
				System.out.println("Current player: " + current);
				System.out.print("Move (e.g. 1122): ");
				int[] move = current.getMove();
				System.out.println();
				//System.out.println(move.length);
				if(move.length != 0){
					for(int i : move) System.out.print(i + " ");
					System.out.println();
					for(int i=0;i<4;i++) move[i]--;
					moveResult = gb.movePiece(move);
					//System.out.println("MoveResult: "+moveResult);
				} else { gb.display(); }
			}
			gb.display();
			gb.switchTurn();
		}
		
	}

}
