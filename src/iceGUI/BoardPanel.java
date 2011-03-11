package iceGUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class BoardPanel extends JPanel implements ActionListener{
	
	JMenuBar menubar;
	JMenu menu = new JMenu("Menu");
	JMenuItem newgame = new JMenuItem("New Game (x)");
	JMenuItem quit = new JMenuItem("Quit");
	JPanel infoPanel = new JPanel();
	JLabel infoTitle = new JLabel("Information Pane");
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
		add(infoPanel, BorderLayout.EAST);
		add(ba, BorderLayout.CENTER);
		
		//northPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
		
		//setOpaque(false); // we don't paint all our bits
		//setBorder(BorderFactory.createLineBorder(Color.black));
	}

	
	public Dimension getPreferredSize() {
		// Figure out what the layout manager needs and
		// then add 100 to the largest of the dimensions
		// in order to enforce a 'round' bullseye 
		Dimension layoutSize = super.getPreferredSize();
		int max = Math.max(layoutSize.width,layoutSize.height);
		return new Dimension(max+100,max+100);
	}
	
	/*
	protected void paintComponent(Graphics g) {
		
	}
	*/

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
