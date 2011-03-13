package iceGUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import ice.Piece;
import ice.gameBoard;

public class BoardPanel extends JPanel implements ActionListener{
	
	JMenuBar menubar;
	JMenu menu = new JMenu("Menu");
	JMenuItem newgame = new JMenuItem("New Game (x)");
	JMenuItem quit = new JMenuItem("Quit");
	JPanel infoPanel = new JPanel();
	JLabel infoTitle = new JLabel("Information Pane");
	JLabel opponents = new JLabel();
	BoardArea ba = new BoardArea();
	
	public BoardPanel(JMenuBar bar) {
		super();
		menubar = bar;
		menubar.add(menu);
		menu.add(newgame);
		menu.add(quit);
		
		newgame.addActionListener(this);
		quit.addActionListener(this);
		    
		setLayout(new BorderLayout());
		infoPanel.add(infoTitle);
		infoPanel.add(opponents);
		add(infoPanel, BorderLayout.EAST);
		add(ba, BorderLayout.CENTER);
	}
	
	public void setOpponents(String a, String b){
		opponents.setText(a + " vs. " + b);
	}
	
	public void setupBoard(gameBoard gb){
		ba.setupBoard(gb);
	}
	
	
	/*
	protected void paintComponent(Graphics g) {

	}
	*/

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == newgame){
			//Not implemented yet
		}
		else if(src == quit){
			System.exit(0);
		}
	}
}
