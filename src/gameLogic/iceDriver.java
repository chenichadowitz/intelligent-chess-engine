package gameLogic;

import java.util.Scanner;

import main.Output;

public class iceDriver {

	private static Scanner genericScanner = new Scanner(System.in);
	private static gameBoard gb;
	
	public static void getPlayersSetUpBoard(){
		Player white = null;
		Player black = null;
		System.out.println("0 = human; 1 = computer");
		System.out.print("white player's type: ");
		if(genericScanner.hasNextInt()){
			int playerType = genericScanner.nextInt();
			if(playerType == 0){
				white = new HumanPlayer(true);
			}
			else{
				white = new ComputerPlayer(true, 1);
			}
			System.out.print("White player name: ");
			if(genericScanner.hasNext()){
				white.setName(genericScanner.next());
			}
			System.out.println();
		}
		System.out.print("black player's type: ");
		if(genericScanner.hasNextInt()){
			int playerType = genericScanner.nextInt();
			if(playerType == 0){
				black = new HumanPlayer(false);
			}
			else{
				black = new ComputerPlayer(false, 1);
			}
			System.out.print("White player name: ");
			if(genericScanner.hasNext()){
				black.setName(genericScanner.next());
			}
			System.out.println();
		}
		gb = new gameBoard(white, black);
		gb.setUpBoard();
	}
	public static void play(){
		Player current;
		boolean moveResult;
		System.out.println(gb.buildDisplay());
		while(!gb.playerMap.get(true).isMated() && !gb.playerMap.get(false).isMated()){
			current = gb.playerMap.get(gb.getTurn());
			moveResult = false;
			while(!moveResult){
				System.out.println("Current player: " + current);
				Listener move = current.getMove();
				if(move == null){System.out.println(gb.buildDisplay());}
				else{
					moveResult = gb.movePiece(move);
				}
			}
			System.out.println(gb.buildDisplay());
			gb.switchTurn();
		}
	}

	public static void run(String[] args) {
		System.out.print("Max debug level: ");
		if(genericScanner.hasNextInt()){
			Output.setDebugLevel(genericScanner.nextInt());
		}
		getPlayersSetUpBoard();
		play();
	}

}
