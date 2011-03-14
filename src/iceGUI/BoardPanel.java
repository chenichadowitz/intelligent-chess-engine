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
	JPanel infoPanel;
	JPanel northInfo;
	JPanel boardDisp;
	JPanel boardRows;
	JPanel boardColumns;
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
		boardRows = new BoardLabel("rows");
		boardDisp.add(boardRows, BorderLayout.WEST);
		boardColumns = new BoardLabel("columns");
		boardDisp.add(boardColumns, BorderLayout.SOUTH);
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
	
	private void makeSquare(){
		Dimension size = ba.getSize();
		if(!isSquare(size)){
			if(size.width < size.height){
				ba.setSize(size.width, size.width);
			} else {
				ba.setSize(size.height, size.height);
			}
		}
	}
	
	private boolean isSquare(Dimension size){
		if(Math.abs(size.height - size.width) > 5){
			return false;
		}
		return true;
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
		makeSquare();

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
