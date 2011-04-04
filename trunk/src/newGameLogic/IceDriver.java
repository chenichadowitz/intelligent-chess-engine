package newGameLogic;

import java.util.Scanner;
import main.Output;

public class IceDriver {

	private static Scanner genericScanner = new Scanner(System.in);
	private static GameBoard gb;
	
	public static void getPlayersSetUpBoard(){
		Player white = null;
		Player black = null;
		System.out.println("0 = human; 1 = computer");
		System.out.print("white player's type: ");
		if(genericScanner.hasNextInt()){
			int playerType = genericScanner.nextInt();
			if(playerType == 0){
				white = new HumanPlayer(WBColor.White);
			}
			else{
				white = new ComputerPlayer();
			}
			System.out.print("White player name: ");
			if(genericScanner.hasNext()){
				white.setName(genericScanner.next());
			}
		}
		System.out.print("black player's type: ");
		if(genericScanner.hasNextInt()){
			int playerType = genericScanner.nextInt();
			if(playerType == 0){
				black = new HumanPlayer(WBColor.White);
			}
			else{
				black = new ComputerPlayer();
			}
			System.out.print("White player name: ");
			if(genericScanner.hasNext()){
				black.setName(genericScanner.next());
			}
		}
		gb = new GameBoard(white, black);
	}
	public static void play(){
		Player currentPlayer;
		System.out.println(gb.toString());
		while(!gb.getPlayerMap().get(WBColor.White).isInCheckMate() && !gb.getPlayerMap().get(WBColor.Black).isInCheckMate()){
			currentPlayer = gb.getPlayerMap().get(gb.getTurn());
			while(currentPlayer.getMove() == null){
				System.out.println("Current player: " + currentPlayer);
				currentPlayer.getNextMove();
			}
			gb.makeMove(currentPlayer.getMove());
			System.out.println(gb.toString());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.print("Max debug level: ");
		if(genericScanner.hasNextInt()){
			Output.setDebugLevel(genericScanner.nextInt());
		}
		getPlayersSetUpBoard();
		play();
	}

}
