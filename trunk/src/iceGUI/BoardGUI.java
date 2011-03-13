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
		bp.setVisible(true);
		dw.addPanel(bp);
		dw.showFrame();
	}
	
	public static void humanVShuman(){
		Scanner genericScanner = new Scanner(System.in);
		System.out.print("Max debug level: ");
		if(genericScanner.hasNextInt()){
			Debug.setDebugLevel(genericScanner.nextInt());
		}
		HumanPlayer white = new HumanPlayer(true);
		System.out.print("White player name: ");
		if(genericScanner.hasNext()){
			white.setName(genericScanner.next());
		}
		System.out.println();
		HumanPlayer black = new HumanPlayer(false);
		System.out.print("Black player name: ");
		if(genericScanner.hasNext()){
			black.setName(genericScanner.next());
		}
		
		System.out.println();
		bp.setOpponents(white.toString(), black.toString());
		HumanPlayer current;
		gameBoard gb = new gameBoard(white, black);
		gb.setUpBoard();
		bp.setupBoard(gb.getPieces());
		boolean moveResult;
		while(true){
			if(gb.getTurn()){
				current = white;
			} else {
				current = black;
			}
			moveResult = false;
			while(!moveResult){
				System.out.println("Current player: " + current);
				System.out.print("Move (e.g. 1122): ");
				int[] move = current.getMove();
				System.out.println();
				if(move.length != 0){
					for(int i=0;i<4;i++) move[i]--;
					Move moveObj = new Move(gb,move);
					moveResult = gb.movePiece(moveObj);
				} else { 
					System.out.println(gb.display());
				}
			}
			System.out.println(gb.display());
			gb.switchTurn();
		}
	}
	
}