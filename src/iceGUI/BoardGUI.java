package iceGUI;

import ice.Debug;
import ice.HumanPlayer;
import ice.gameBoard;

import javax.swing.*;

public class BoardGUI{
	
	private static DisplayWindow dw;
	private static BoardPanel bp;

	public static void main(String[] args){
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){}
		
		dw = new DisplayWindow("ICE", 800, 600);
		JMenuBar menuBar = new JMenuBar();
	    dw.setJMenuBar(menuBar);
		bp = new BoardPanel(menuBar);
		Debug.setGui(bp);
		humanVShuman();
		bp.setVisible(true);
		dw.addPanel(bp);
		dw.showFrame();
		bp.repaint();
	}
	
	public static void humanVShuman(){
		Debug.setDebugLevel(1);
		HumanPlayer white = new HumanPlayer(true);
		white.setName("c");		
		HumanPlayer black = new HumanPlayer(false);
		black.setName("j");
		bp.setOpponents(white.toString(), black.toString());
		gameBoard gb = new gameBoard(white, black);
		gb.setUpBoard();
		bp.setupBoard(gb);
	}
	
}
