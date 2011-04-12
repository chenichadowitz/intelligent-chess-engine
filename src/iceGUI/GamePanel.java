package iceGUI;

import main.Output;
import newGameLogic.GameBoard;
import newerGameLogic.Player;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridBagLayout;

public class GamePanel extends JPanel implements ActionListener{

	JMenuBar menuBar;
	JButton flip;
	JLabel opponentsLabel;
	JLabel turn;
	SetupDialog setupDialog;
	ArrayList<JMenuItem> debugLevels;
	ArrayList<JMenuItem> mainMenuItems;
	SidePanel eastPanel;
	SidePanel southPanel;
	TextView notation;
	TextView console;
	GameBoard gb;
	BoardArea ba;
	
	public GamePanel(JMenuBar menuBar){
		super();
		initialize();
		setupMenus(menuBar);
	}
	
	private void setupMenus(JMenuBar bar){
		this.menuBar = bar;
		debugLevels = new ArrayList<JMenuItem>(6);
		for(int i=0; i<6; i++){
			debugLevels.add(new JMenuItem(""+i));
		}
		JMenu mainMenu = new JMenu("Menu");
		menuBar.add(mainMenu);
		mainMenuItems = new ArrayList<JMenuItem>(3);
		mainMenuItems.add(new JMenuItem("New Game"));
		mainMenuItems.add(new JMenuItem("Clear Console"));
		mainMenuItems.add(new JMenuItem("Quit"));
		for(JMenuItem item : mainMenuItems){
			mainMenu.add(item);
			item.addActionListener(this);
		}
		JMenu debugMenu = new JMenu("Debug");
		menuBar.add(debugMenu);
		for(JMenuItem menuitem : debugLevels){
			debugMenu.add(menuitem);
			menuitem.addActionListener(this);
		}
	}
	
	private void initialize(){
		setLayout(new BorderLayout());		

		//Build the east panel
		eastPanel = new SidePanel();
		opponentsLabel = new JLabel();
		eastPanel.addItem(opponentsLabel, 0, 0);
		turn = new JLabel("White");
		eastPanel.addItem(turn, 0, 1);
		notation = new TextView(30, 10);
		eastPanel.addItem(notation, 0, 2);
		flip = new JButton("Flip Board");
		flip.addActionListener(this);
		eastPanel.addItem(flip, 0, 3);
		add(eastPanel, BorderLayout.EAST);
		//Build the south panel
		southPanel = new SidePanel();
		int top = southPanel.constraints.insets.top;
		southPanel.constraints.insets.top = 10;
		southPanel.addItem(new JLabel("Console"), 0, 0);
		console = new TextView(5, 40);
		southPanel.constraints.insets.top = top;
		southPanel.addItem(console, 0, 1);
		add(southPanel, BorderLayout.SOUTH);
		
		ba = new BoardArea();
		add(ba, BorderLayout.CENTER);
	}
	
	public void switchTurn(){
		if(turn.getText().equals("Black"))
			turn.setText("White");
		else turn.setText("Black");
	}
	
	public String promotionPrompt(){
		Object[] choices = {"Queen", "Rook", "Bishop", "Knight"};
		String s = (String) JOptionPane.showInputDialog(this,
				"Choose promotion type:",
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
	
	public void setOpponents(String white, String black){
		opponentsLabel.setText(white + " vs. " + black);
	}
	
	public void setupGame(GameBoard gb){
		this.gb = gb;
		notation.clear();
		turn.setText("White");
		flip.setSelected(false);
		ba.setupBoard(gb);
	}
	
	protected void paintComponent(Graphics g){
		notation.setRows(this.getSize().height / 16 - 15);
		console.setColumns(this.getSize().width / 16 );//- 15);
	}
	
	public void printConsole(String str){
		console.append(str);
	}
	
	public void printNotation(String str){
		notation.append(str);
	}
	
	public void newGame(Player w, Player b){
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(mainMenuItems.contains(src)){
			JMenuItem selected = (JMenuItem) src;
			if(selected.getText().equals("New Game")){
				setupDialog = new SetupDialog(this);
				setupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				setupDialog.setVisible(true);
				gb.resetBoard();
				setupGame(gb);
			} else if(selected.getText().equals("Clear Console")){
				console.clear();
			} else if(selected.getText().equals("Quit")){
				System.exit(0);
			}
		} else if(debugLevels.contains(src)){
			JMenuItem selected = (JMenuItem) src;
			int level = Integer.parseInt(selected.getText());
			Output.debug("Setting debug level to: " + level, 3);
			Output.setDebugLevel(level);
		}
		else if(src == flip){
			ba.flipBoard();
		}
	}
}
