package iceGUI;

import newGameLogic.GameBoard;
import newGameLogic.HumanPlayer;
import newGameLogic.Player;
import newGameLogic.WBColor;

import javax.swing.*;

import main.Output;

public class BoardGUI{
	
	private static DisplayWindow dw;
	private static GamePanel panel;
	private static GameBoard gb;

	public static void run(String[] args){
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){}
		
		dw = new DisplayWindow("ICE", 630, 630);
		JMenuBar menuBar = new JMenuBar();
	    dw.setJMenuBar(menuBar);
		//bp = new BoardPanel(menuBar);
	    panel = new GamePanel(menuBar);
		//Output.setGUI(bp);
	    Output.setGUI(panel);
		Output.setDebugLevel(2);
		Player white = new HumanPlayer(WBColor.White);
		Player black = new HumanPlayer(WBColor.Black);
		white.setName("Player1");
		black.setName("Player2");
		//bp.setOpponents(white.getName(), black.getName());
		panel.setOpponents(white.getName(), black.getName());
		gb = GameBoard.createGameBoard(white, black);
		//bp.setupBoard(gb);
		panel.setupGame(gb);
		//bp.setVisible(true);
		//dw.addPanel(bp);
		dw.addPanel(panel);
		dw.showFrame();
		//bp.repaint();
		panel.repaint();
		play();
	}
	public static void play(){
		Player currentPlayer;
		while(!gb.getPlayerMap().get(WBColor.White).isInCheckMate() && !gb.getPlayerMap().get(WBColor.Black).isInCheckMate()){
			currentPlayer = gb.getPlayerMap().get(gb.getTurn());
			while(currentPlayer.getMove() == null){
//				Output.debug("no move", 3);
			}
			Output.debug("Loop got move!", 0);
			gb.makeMove(currentPlayer.getMove());
		}
	}
}
