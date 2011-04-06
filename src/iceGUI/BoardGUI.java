package iceGUI;

import newGameLogic.GameBoard;
import newGameLogic.HumanPlayer;
import newGameLogic.Player;
import newGameLogic.WBColor;

import javax.swing.*;

import main.Output;

public class BoardGUI{
	
	private static DisplayWindow dw;
	private static BoardPanel bp;
	private static GameBoard gb;

	public static void run(String[] args){
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){}
		
		dw = new DisplayWindow("ICE", 800, 600);
		JMenuBar menuBar = new JMenuBar();
	    dw.setJMenuBar(menuBar);
		bp = new BoardPanel(menuBar);
		Output.setGUI(bp);
		Output.setDebugLevel(0);
		Player white = new HumanPlayer(WBColor.White);
		Player black = new HumanPlayer(WBColor.Black);
		white.setName("Player1");
		black.setName("Player2");
		bp.setOpponents(white.getName(), black.getName());
		gb = new GameBoard(white, black);
		bp.setupBoard(gb);
		bp.setVisible(true);
		dw.addPanel(bp);
		dw.showFrame();
		bp.repaint();
	}
}
