package main;

import gameLogic.Listener;
import iceGUI.BoardPanel;

public class Output {
	private static int maxLevel = 5;
	private static BoardPanel gui;
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
	
	public static void setGUI(BoardPanel bp){
		gui = bp;
		Input.setGUI(bp);
	}
	
	public static void printNotation(Listener l, boolean mated){
		halfMoveCounter++;
		if(whiteTurn){
			print(moveCounter + ". " + l.toString());
			if(mated){
				print("+\n  1 - 0\n");
			}
		} else {
			print("  " + l.toString());
			if(mated){
				print("+\n  0 - 1\n");
			} else {
				 print("\n");
			}
			moveCounter++;
		}
		
		whiteTurn = !whiteTurn;
	}
	
	private static void print(String s){
		if(gui != null){
			gui.logGUI(s);
		} else {
			System.out.println(s);
		}
	}
	
	public static void debug(String action, int level){
		if(level <= maxLevel){
			print("\n" + action + "\n");
			//possibly write to file
		}
	}
	
}
