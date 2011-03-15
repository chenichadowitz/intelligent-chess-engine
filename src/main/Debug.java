package main;

import iceGUI.BoardPanel;

public class Debug {
	private static int maxLevel = 5;
	private static BoardPanel gui;
	
	public static void setDebugLevel(int lvl){
		maxLevel = lvl;
	}
	
	public static void setGui(BoardPanel bp){
		gui = bp;
	}
	
	public static void debug(String action, int level){
		if(level <= maxLevel){
			if(gui != null){
				gui.logGUI(action);
			} else {
				System.out.println(action);
			}
			//possibly write to file
		}
	}
	
}
