package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import newGameLogic.Move;
import iceGUI.GamePanel;

public class Output {
	private static int maxLevel = 5;
	private static GamePanel gui;
	private static boolean whiteTurn = true;
	private static int moveCounter = 1;
	private static int halfMoveCounter = 0;
	private static String debugFileName = "debug.log";
	private static FileWriter debugWriter;
	
	public static void resetOutput(){
		whiteTurn = true;
		moveCounter = 1;
		halfMoveCounter = 0;
	}
	
	public static void setDebugLevel(int lvl){
		maxLevel = lvl;
		if(gui != null){
		/*	try {
				gui.clearConsole();
				Scanner debugReader = new Scanner(new File(debugFileName));
				while(debugReader.hasNextLine()){
					String debugLine = debugReader.nextLine();
					if((debugLine.charAt(0)) <= maxLevel){ ///////////////////
						
					}
					gui.printConsole(debugLine);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}
	
	public static void setGUI(GamePanel panel){
		gui = panel;
		Input.setGUI(panel);
	}
	
	public static void saveGame(){
		//Write game to FEN or serialize it
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
		try{
			if(debugWriter == null)
				debugWriter = new FileWriter(debugFileName);
			debugWriter.write(s);
			debugWriter.flush();
		} catch (IOException e){
			if(gui != null) gui.printConsole("Cannot write to debug log.");
			System.out.println("Cannot write to debug log.");
		}
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
