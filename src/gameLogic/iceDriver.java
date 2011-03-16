package gameLogic;

import java.util.Scanner;

import main.Debug;

public class iceDriver {

	private static Scanner genericScanner = new Scanner(System.in);
	
	public static void humanVShuman(){
		System.out.print("Max debug level: ");
		if(genericScanner.hasNextInt()){
			Debug.setDebugLevel(genericScanner.nextInt());
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
		System.out.println(gb.buildDisplay());
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
					Listener moveObj = PieceMaker.MakeMove(gb,move);
					moveResult = gb.movePiece(moveObj);
				} else { 
					System.out.println(gb.buildDisplay());
				}
			}
			System.out.println(gb.buildDisplay());
			gb.switchTurn();
		}
	}

	public static void run(String[] args) {
			humanVShuman();
	}

}
