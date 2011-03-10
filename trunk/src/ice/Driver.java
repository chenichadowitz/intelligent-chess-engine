package ice;

import java.util.Scanner;

public class Driver {

	private static Scanner genericScanner = new Scanner(System.in);
	private static int maxLevel = 5;
	
	public static void debug(String action, int level){
		if(level <= maxLevel){
			System.out.println(action);
			//possibly write to file
		}
	}
	
	public static void humanVShuman(){
		System.out.print("Max debug level: ");
		if(genericScanner.hasNextInt()){
			maxLevel = genericScanner.nextInt();
		}
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
				if(move.length != 0){
					for(int i=0;i<4;i++) move[i]--;
					Move moveObj = new Move(gb,move);
					moveResult = gb.movePiece(moveObj);
				} else { 
					System.out.println(gb.display());
				}
			}
			System.out.println(gb.display());
			gb.switchTurn();
		}
	}

	public static void main(String[] args) {
		if(args.length > 0 && args[0].equals("xboard")){
			Xboard.run(args);
		} else {
			humanVShuman();
		}
	}

}
