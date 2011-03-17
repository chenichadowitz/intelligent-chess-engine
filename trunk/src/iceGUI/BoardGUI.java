package iceGUI;

import gameLogic.HumanPlayer;
import gameLogic.gameBoard;

import javax.swing.*;

import main.Output;

public class BoardGUI{
	
	private static DisplayWindow dw;
	private static BoardPanel bp;

	public static void run(String[] args){
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){}
		
		dw = new DisplayWindow("ICE", 800, 600);
		JMenuBar menuBar = new JMenuBar();
	    dw.setJMenuBar(menuBar);
		bp = new BoardPanel(menuBar);
		Output.setGUI(bp);
		humanVShuman();
		bp.setVisible(true);
		dw.addPanel(bp);
		dw.showFrame();
		bp.repaint();
	}
	
	public static void humanVShuman(){
		Output.setDebugLevel(0);
		HumanPlayer white = new HumanPlayer(true);
		white.setName("Player1");		
		HumanPlayer black = new HumanPlayer(false);
		black.setName("Player2");
		bp.setOpponents(white.toString(), black.toString());
		gameBoard gb = new gameBoard(white, black);
		gb.setUpBoard();
		bp.setupBoard(gb);
	}
	
}
