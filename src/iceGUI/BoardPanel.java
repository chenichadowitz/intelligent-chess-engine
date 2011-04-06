package iceGUI;

import javax.swing.*;

import main.Output;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import newGameLogic.GameBoard;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel implements ActionListener{
	
	private JMenuBar menubar;
	private JMenu menu = new JMenu("Menu");
	private ArrayList<JMenuItem> mainMenu = new ArrayList<JMenuItem>();
	private ArrayList<String> mainMenuItems = new ArrayList<String>();
	private JMenuItem newgame = new JMenuItem("New Game");
	private JMenuItem quit = new JMenuItem("Quit");
	private JMenuItem clearLog = new JMenuItem("Clear Log");
	private JMenu debugMenu = new JMenu("Debug Lvls");
	private ArrayList<JMenuItem> debugLevels = new ArrayList<JMenuItem>();
	private JPanel infoPanel;
	private JPanel northInfo;
	private JLabel turn = new JLabel("White");
	private JLabel opponents;
	private JToggleButton flip = new JToggleButton("Flip Board");
	private JTextArea logViewer;
	private BoardArea ba;
	private GameBoard gb;
	
	private void setupMenus(JMenuBar bar){
		
		for(int i=0; i<6; i++){
			debugLevels.add(new JMenuItem(""+i));
		}
		menubar = bar;
		menubar.add(menu);
		menu.add(newgame);
		menu.add(clearLog);
		menu.add(quit);
		menubar.add(debugMenu);
		for(JMenuItem menuitem : debugLevels){
			debugMenu.add(menuitem);
			menuitem.addActionListener(this);
		}
		newgame.addActionListener(this);
		quit.addActionListener(this);
	}
	
	public BoardPanel(JMenuBar bar) {
		super();
		setupMenus(bar);
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
	
	public void setupBoard(GameBoard gb){
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
		else if(debugLevels.contains(src)){
			JMenuItem selected = (JMenuItem) src;
			int level = Integer.parseInt(selected.getText());
			Output.debug("Setting debug level to: " + level, 3);
			Output.setDebugLevel(level);
		}
	}

}
