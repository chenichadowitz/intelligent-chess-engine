package main;

import newGameLogic.Move;
import iceGUI.BoardPanel;
import iceGUI.GamePanel;

public class Output {
	private static int maxLevel = 5;
	private static GamePanel gui;
	private static boolean whiteTurn = true;
	private static int moveCounter = 1;
	private static int halfMoveCounter = 0;
	
	public static void resetOutput(){
		whiteTurn = true;
		moveCounter = 1;
		halfMoveCounter = 0;
	}
	
	public static void setDebugLevel(int lvl){
		maxLevel = lvl;
	}
	
	public static void setGUI(GamePanel panel){
		gui = panel;
		Input.setGUI(panel);
	}
	
	public static void printNotation(Move l, boolean mated){
		halfMoveCounter++;
		if(whiteTurn){
			printToNotation(moveCounter + ". " + l.toString());
			if(mated){
				printToNotation("+\n  1 - 0\n");
			}
		} else {
			printToNotation("  " + l.toString());
			if(mated){
				printToNotation("+\n  0 - 1\n");
			} else {
				 printToNotation("\n");
			}
			moveCounter++;
		}
		
		whiteTurn = !whiteTurn;
	}
	
	private static void printToNotation(String s){
		if(gui != null){
			gui.printNotation(s);
		} else {
			System.out.println(s);
		}
	}
	
	private static void printToConsole(String s){
		if(gui != null){
			gui.printConsole(s);
		} else {
			System.out.println(s);
		}
	}
	
	public static void debug(String action, int level){
		if(level <= maxLevel){
			printToConsole("\n" + action + "\n");
			//possibly write to file
		}
	}
	
}
