package iceGUI;

import javax.swing.*;

import main.Output;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import gameLogic.Piece;
import gameLogic.gameBoard;

public class BoardPanel extends JPanel implements ActionListener{
	
	JMenuBar menubar;
	JMenu menu = new JMenu("Menu");
	JMenuItem newgame = new JMenuItem("New Game");
	JMenuItem quit = new JMenuItem("Quit");
	JMenuItem clearLog = new JMenuItem("Clear Log");
	JMenu debugMenu = new JMenu("Debug Lvls");
	JMenuItem lvl0 = new JMenuItem("0");
	JMenuItem lvl1 = new JMenuItem("1");
	JMenuItem lvl2 = new JMenuItem("2");
	JMenuItem lvl3 = new JMenuItem("3");
	JMenuItem lvl4 = new JMenuItem("4");
	JMenuItem lvl5 = new JMenuItem("5");
	JPanel infoPanel;
	JPanel northInfo;
	JPanel boardDisp;
	JPanel boardRows;
	JPanel boardColumns;
	JLabel infoTitle = new JLabel("Information Pane");
	JLabel turn = new JLabel("White");
	JLabel opponents;
	JToggleButton flip = new JToggleButton("Flip Board");
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
		menubar.add(debugMenu);
		debugMenu.add(lvl0); debugMenu.add(lvl1); debugMenu.add(lvl2);
		debugMenu.add(lvl3); debugMenu.add(lvl4); debugMenu.add(lvl5);
		lvl0.addActionListener(this); lvl1.addActionListener(this); lvl2.addActionListener(this);
		lvl3.addActionListener(this); lvl4.addActionListener(this); lvl5.addActionListener(this); 
		newgame.addActionListener(this);
		quit.addActionListener(this);
		flip.addActionListener(this);
		clearLog.addActionListener(this);
		opponents = new JLabel();
		setLayout(new BorderLayout());
		infoPanel = new JPanel(new BorderLayout());
		northInfo = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 10, 2, 10);
		gbc.weighty = 0.0; gbc.gridy = 0; gbc.gridx = 0;		
		northInfo.add(opponents, gbc);
		gbc.gridy++; northInfo.add(turn, gbc);
		gbc.gridy++; gbc.weighty = 1.0;
		JLabel logTitle = new JLabel("Game Log:");
		gbc.gridy++; northInfo.add(logTitle,gbc);
		gbc.gridy++;
		logViewer = new JTextArea(0, 10);
		JScrollPane scrollPane = new JScrollPane(logViewer,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		logViewer.setEditable(false);
		logViewer.setCaretPosition(0);
		northInfo.add(scrollPane, gbc);
		gbc.gridy++; northInfo.add(flip, gbc);
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
	
	public String promotionPrompt(){
		Object[] choices = {"Queen", "Rook", "Bishop", "Knight"};
		String s = (String) JOptionPane.showInputDialog(this, "Choose promotion type:", 
				"Promote to...",
				JOptionPane.PLAIN_MESSAGE,
				null,
				choices,
				"Queen");
		if(s != null && s.length() > 0){
			return s;
		}
		return "Queen";
	}
	
	public void setOpponents(String a, String b){
		opponents.setText(a + " vs. " + b);
	}
	
	public void logGUI(String str){
		//str += "\n";
		logViewer.append(str);
		logViewer.setCaretPosition(logViewer.getText().length());
	}
	
	public void setupBoard(gameBoard gb){
		this.gb = gb;
		flip.setSelected(false);
		ba.setupBoard(gb);
	}
	
	
	protected void paintComponent(Graphics g) {
		logViewer.setRows(this.getSize().height / 16 - 15);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == newgame){
			gb.setUpBoard();
			this.setupBoard(gb);
			logViewer.setText("");
			turn.setText("White");
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
		else if(src == lvl0) Output.setDebugLevel(0);
		else if(src == lvl1) Output.setDebugLevel(1);
		else if(src == lvl2) Output.setDebugLevel(2);
		else if(src == lvl3) Output.setDebugLevel(3);
		else if(src == lvl4) Output.setDebugLevel(4);
		else if(src == lvl5) Output.setDebugLevel(5);
	}

}
