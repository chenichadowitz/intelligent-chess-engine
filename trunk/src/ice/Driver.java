package ice;

import java.util.Scanner;

public class Driver {
	private static Scanner genericScanner = new Scanner(System.in);

	private static int maxLevel = 5;
	private static gameBoard gb;
	
	public static void debug(String action, int level){
		if(level < maxLevel){
			System.out.println(action);
			//possibly write to file
		}
	}
	
	public static void initializeGame(){
		HumanPlayer white = new HumanPlayer(true);
		System.out.print("White player name: ");
		if(genericScanner.hasNext()){
			white.setName(genericScanner.next());
		}
		HumanPlayer black = new HumanPlayer(false);
		System.out.print("Black player name: ");
		if(genericScanner.hasNext()){
			black.setName(genericScanner.next());
		}
	
		gb = new gameBoard(white, black);
		gb.setUpBoard();
		/*
		BoardFrame frame = new BoardFrame("TEst title");
		frame.setSize(800,600);
		frame.setText(gb.display());
		*/
	}

	public static void main(String[] args) {
		System.out.print("Max debug level: ");
		if(genericScanner.hasNextInt()){
			maxLevel = genericScanner.nextInt();
		}	
		initializeGame();
		if(args.length > 0 && args[0].equals("xboard")){
			Xboard.run(args);
		} else {
			gb.playGame();
		}
	}

}
