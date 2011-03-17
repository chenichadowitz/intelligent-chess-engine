package main;

import gameLogic.Listener;
import iceGUI.BoardPanel;

public class Output {
	private static int maxLevel = 5;
	private static BoardPanel gui;
	private static boolean whiteTurn = true;
	private static int moveCounter = 1;
	private static int halfMoveCounter = 0;
	
	public static void setDebugLevel(int lvl){
		maxLevel = lvl;
	}
	
	public static void setGui(BoardPanel bp){
		gui = bp;
	}
	
	public static void printNotation(Listener l){
		halfMoveCounter++;
		if(whiteTurn){
			print(moveCounter + ". " + l.toString());
		} else {
			print("  " + l.toString() + "\n");
			moveCounter++;
		}
		whiteTurn = !whiteTurn;
	}
	
	private static void print(String s){
		if(gui != null){
			gui.logGUI(s);
		} else {
			System.out.print(s);
		}
	}
	
	public static void debug(String action, int level){
		if(level <= maxLevel){
			print("\n" + action + "\n");
			//possibly write to file
		}
	}
	
}
