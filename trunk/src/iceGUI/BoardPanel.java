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
	JMenuItem newgame = new JMenuItem("New Game");
	JMenuItem quit = new JMenuItem("Quit");
	JMenuItem clearLog = new JMenuItem("Clear Log");
	JPanel infoPanel;
	JPanel northInfo;
	JPanel boardDisp;
	JPanel boardRows;
	JPanel boardColumns;
	JLabel infoTitle = new JLabel("Information Pane");
	JLabel turn = new JLabel("White");
	JLabel opponents;
	JToggleButton flip = new JToggleButton("Toggle Board");
	JTextArea logViewer;
	BoardArea ba;
	private gameBoard gb;
	
	public BoardPanel(JMenuBar bar) {
		super();
		menubar = bar;
		menubar.add(menu);
		menu.add(newgame);
		menu.add(clearLog);
		menu.add(quit);
		newgame.addActionListener(this);
		quit.addActionListener(this);
		flip.addActionListener(this);
		clearLog.addActionListener(this);
		opponents = new JLabel();
		setLayout(new BorderLayout());
		infoPanel = new JPanel(new BorderLayout());
		northInfo = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		northInfo.add(opponents, gbc);
		gbc.gridy = 1;
		northInfo.add(turn, gbc);
		gbc.gridy = 2;
		northInfo.add(new JLabel(" "), gbc);
		gbc.gridy = 3;
		northInfo.add(flip, gbc);
		gbc.gridy = 4;
		northInfo.add(new JLabel(" "), gbc);
		JLabel logTitle = new JLabel("Game Log:");
		gbc.gridy = 5;
		northInfo.add(logTitle,gbc);
		gbc.gridy = 6;
		logViewer = new JTextArea(30, 10);
		logViewer.setAutoscrolls(true);
		logViewer.setIgnoreRepaint(true);
		logViewer.setWrapStyleWord(true);
		logViewer.setEditable(false);
		northInfo.add(logViewer, gbc);
		infoPanel.add(northInfo, BorderLayout.NORTH);
		add(infoPanel, BorderLayout.EAST);
		ba = new BoardArea(this);
		add(ba, BorderLayout.CENTER);
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
	
	public void logGUI(String str){
		logViewer.append(str + "\n");
	}
	
	public void setupBoard(gameBoard gb){
		this.gb = gb;
		flip.setSelected(false);
		ba.setupBoard(gb);
	}
	
	
	protected void paintComponent(Graphics g) {
		logViewer.setRows(this.getSize().height / 16 - 10);
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
		else if(src == flip){
			ba.flipBoard();
		}
		else if(src == clearLog){
			logViewer.setText("");
		}
	}

}
