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
		gb = GameBoard.createGameBoard(white, black);
		bp.setupBoard(gb);
		bp.setVisible(true);
		dw.addPanel(bp);
		dw.showFrame();
		bp.repaint();
		play();
	}
	public static void play(){
		Player currentPlayer;
		while(!gb.getPlayerMap().get(WBColor.White).isInCheckMate() && !gb.getPlayerMap().get(WBColor.Black).isInCheckMate()){
			currentPlayer = gb.getPlayerMap().get(gb.getTurn());
			while(currentPlayer.getMove() == null){
			}
			Output.debug("Got move!", 0);
			gb.makeMove(currentPlayer.getMove());
		}
	}
}
