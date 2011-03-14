package iceGUI;

import java.util.Scanner;

import ice.Debug;
import ice.Driver;
import ice.HumanPlayer;
import ice.Move;
import ice.gameBoard;

import javax.swing.*;

public class BoardGUI{
	
	private static DisplayWindow dw;
	private static BoardPanel bp;

	public static void main(String[] args){
		dw = new DisplayWindow("ICE", 800, 600);
		JMenuBar menuBar = new JMenuBar();
	    dw.setJMenuBar(menuBar);
		bp = new BoardPanel(menuBar);
		humanVShuman();
		bp.setVisible(true);
		dw.addPanel(bp);
		dw.showFrame();
	}
	
	public static void humanVShuman(){
		//Scanner genericScanner = new Scanner(System.in);
		Debug.setDebugLevel(3);
		HumanPlayer white = new HumanPlayer(true);
		System.out.print("White player name: c");
		white.setName("c");
		/*if(genericScanner.hasNext()){
			white.setName(genericScanner.next());
		}
		System.out.println();
		*/
		HumanPlayer black = new HumanPlayer(false);
		System.out.print("Black player name: j");
		black.setName("j");
		/*if(genericScanner.hasNext()){
			black.setName(genericScanner.next());
		}
		System.out.println();
		*/
		bp.setOpponents(white.toString(), black.toString());
		HumanPlayer current;
		gameBoard gb = new gameBoard(white, black);
		gb.setUpBoard();
		bp.setupBoard(gb);
	}
	
}
