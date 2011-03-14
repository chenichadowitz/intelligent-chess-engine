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
	JPanel infoPanel;
	JPanel northInfo;
	JPanel boardDisp;
	JLabel infoTitle = new JLabel("Information Pane");
	JLabel turn = new JLabel("White");
	JLabel opponents;
	BoardArea ba;
	private gameBoard gb;
	
	public BoardPanel(JMenuBar bar) {
		super();
		menubar = bar;
		menubar.add(menu);
		menu.add(newgame);
		menu.add(quit);
		newgame.addActionListener(this);
		quit.addActionListener(this);
		
		boardDisp = new JPanel(new BorderLayout());
		ba = new BoardArea(this);
		boardDisp.add(ba, BorderLayout.CENTER);
		
		opponents = new JLabel();
		//JLabel spacer = new JLabel("            ");
		setLayout(new BorderLayout());
		infoPanel = new JPanel(new BorderLayout());
		northInfo = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		northInfo.add(opponents, gbc);
		gbc.gridx++;
		northInfo.add(turn, gbc);
		//gbc.gridx++;
		//northInfo.add(spacer, gbc);
		infoPanel.add(northInfo, BorderLayout.NORTH);
		add(infoPanel, BorderLayout.EAST);
		add(boardDisp, BorderLayout.CENTER);
	}
	
	public void switchTurn(){
		if(turn.getText().equals("Black")){
			turn.setText("White");
		} else {
			turn.setText("Black");
		}
	}
	
	public void setOpponents(String a, String b){
		opponents.setText(a + " vs. " + b + " - ");
	}
	
	public void setupBoard(gameBoard gb){
		this.gb = gb;
		ba.setupBoard(gb);
	}	
	
	
	protected void paintComponent(Graphics g) {
		

	}
	

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == newgame){
			gb.setUpBoard();
			this.setupBoard(gb);
		}
		else if(src == quit){
			System.exit(0);
		}
	}
}
