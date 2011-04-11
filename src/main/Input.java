package main;

import java.util.Scanner;

import iceGUI.GamePanel;

public class Input {

	private static GamePanel gui;
	private static Scanner scan = new Scanner(System.in);


	public static void setGUI(GamePanel panel){
		gui = panel;
	}
	
	public static void undoMove(){
		//Attempt to undo a move
	}
	
	public static void loadGame(){
		//Load game from FEN or un-serialize it
	}
	
	
	public static char getPromotion(){
		if(gui != null){
			String s = gui.promotionPrompt();
			if(!s.equals("Knight")){
				return s.charAt(0);
			} else {
				return 'N';
			}
		} else { //GUI not available, use a scanner....
			System.out.println("Promote to...? (1, 2, 3, or 4)");
			System.out.println("1) Queen\n2) Rook\n3) Bishop\n4) Knight");
			int choice = scan.nextInt();
			switch(choice){
				case 1:
					return 'Q';
				case 2:
					return 'R';
				case 3:
					return 'B';
				case 4:
					return 'N';
				default:
					return 'Q';
			}
		}
	}
	
	
}
