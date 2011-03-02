package ice;

import java.util.Scanner;

public class Driver {

	private static Scanner genericScanner = new Scanner(System.in);
	
	public static void debug(String action){
		System.out.println(action);
		//possibly write to file
	}
	
	
	
	public static void main(String[] args) {
		HumanPlayer white = new HumanPlayer(true);
		System.out.print("White player name: ");
		if(genericScanner.hasNext()){
			white.setName(genericScanner.next());
		}
		System.out.println();
		HumanPlayer black = new HumanPlayer(false);
		System.out.print("Black player name: ");
		if(genericScanner.hasNext()){
			black.setName(genericScanner.next());
		}
		System.out.println();
		HumanPlayer current;
		gameBoard gb = new gameBoard(white, black);
		gb.setUpBoard();
		boolean moveResult;
		/*
		BoardFrame frame = new BoardFrame("TEst title");
		frame.setSize(800,600);
		frame.setText(gb.display());
		*/
		System.out.println(gb.display());
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
				} else { 
					//frame.setText(gb.display());
					System.out.println(gb.display());
				}
			}
			//frame.setText(gb.display());
			System.out.println(gb.display());
			gb.switchTurn();
		}
		
	}

}
