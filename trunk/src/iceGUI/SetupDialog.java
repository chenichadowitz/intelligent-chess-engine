package iceGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;

import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

import newerGameLogic.ComputerPlayer;
import newerGameLogic.HumanPlayer;
import newerGameLogic.Player;
import newerGameLogic.WBColor;

public class SetupDialog extends JDialog  implements ChangeListener, ActionListener{

	private final JPanel contentPanel = new JPanel();
	private JTextField txtPlayerW;
	private JTextField txtPlayerB;
	private JLabel lblDifficultyW;
	private JLabel lblDifficultyB;
	private JLabel lblDiffValW;
	private JLabel lblDiffValB;
	private JRadioButton rbHumanW;
	private JRadioButton rbHumanB;
	private JLabel lblHumanW;
	private JLabel lblHumanB;
	private JRadioButton rbComputerW;
	private JRadioButton rbComputerB;
	private JSlider sliderW;
	private JSlider sliderB;
	private JButton okButton;
	private JButton cancelButton;
	private GamePanel parent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SetupDialog dialog = new SetupDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SetupDialog(GamePanel gp){
		this();
		parent = gp;
	}
	
	/**
	 * Create the dialog.
	 */
	public SetupDialog() {
		setBounds(100, 100, 450, 300);
		setTitle("Setup Game");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblWhite = new JLabel("White:");
			GridBagConstraints gbc_lblWhite = new GridBagConstraints();
			gbc_lblWhite.insets = new Insets(0, 0, 5, 5);
			gbc_lblWhite.gridx = 0;
			gbc_lblWhite.gridy = 0;
			contentPanel.add(lblWhite, gbc_lblWhite);
		}
		{
			rbHumanW = new JRadioButton("Human");
			rbHumanW.setSelected(true);
			rbHumanW.addActionListener(this);
			GridBagConstraints gbc_rbHumanW = new GridBagConstraints();
			gbc_rbHumanW.insets = new Insets(0, 0, 5, 5);
			gbc_rbHumanW.gridx = 1;
			gbc_rbHumanW.gridy = 0;
			contentPanel.add(rbHumanW, gbc_rbHumanW);
		}
		{
			lblHumanW = new JLabel("Name:");
			GridBagConstraints gbc_lblHumanW = new GridBagConstraints();
			gbc_lblHumanW.insets = new Insets(0, 0, 5, 5);
			gbc_lblHumanW.gridx = 2;
			gbc_lblHumanW.gridy = 0;
			contentPanel.add(lblHumanW, gbc_lblHumanW);
		}
		{
			txtPlayerW = new JTextField();
			txtPlayerW.setText("Player1");
			GridBagConstraints gbc_txtPlayerW = new GridBagConstraints();
			gbc_txtPlayerW.insets = new Insets(0, 0, 5, 0);
			gbc_txtPlayerW.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtPlayerW.gridx = 4;
			gbc_txtPlayerW.gridy = 0;
			contentPanel.add(txtPlayerW, gbc_txtPlayerW);
			txtPlayerW.setColumns(10);
		}
		{
			rbComputerW = new JRadioButton("Computer");
			rbComputerW.addActionListener(this);
			GridBagConstraints gbc_rbComputerW = new GridBagConstraints();
			gbc_rbComputerW.insets = new Insets(0, 0, 5, 5);
			gbc_rbComputerW.gridx = 1;
			gbc_rbComputerW.gridy = 1;
			contentPanel.add(rbComputerW, gbc_rbComputerW);
		}
		{
			lblDifficultyW = new JLabel("Difficulty:");
			GridBagConstraints gbc_lblDifficultyW = new GridBagConstraints();
			gbc_lblDifficultyW.insets = new Insets(0, 0, 5, 5);
			gbc_lblDifficultyW.gridx = 2;
			gbc_lblDifficultyW.gridy = 1;
			contentPanel.add(lblDifficultyW, gbc_lblDifficultyW);
		}
		{
			lblDiffValW = new JLabel("5");
			GridBagConstraints gbc_lblDiffValW = new GridBagConstraints();
			gbc_lblDiffValW.insets = new Insets(0, 0, 5, 5);
			gbc_lblDiffValW.gridx = 3;
			gbc_lblDiffValW.gridy = 1;
			contentPanel.add(lblDiffValW, gbc_lblDiffValW);
		}
		{
			sliderW = new JSlider();
			sliderW.setValue(5);
			sliderW.setSnapToTicks(true);
			sliderW.setMaximum(10);
			sliderW.addChangeListener(this);
			GridBagConstraints gbc_sliderW = new GridBagConstraints();
			gbc_sliderW.fill = GridBagConstraints.HORIZONTAL;
			gbc_sliderW.insets = new Insets(0, 0, 5, 0);
			gbc_sliderW.gridx = 4;
			gbc_sliderW.gridy = 1;
			contentPanel.add(sliderW, gbc_sliderW);
		}
		{
			JLabel lblBlack = new JLabel("Black:");
			GridBagConstraints gbc_lblBlack = new GridBagConstraints();
			gbc_lblBlack.insets = new Insets(0, 0, 5, 5);
			gbc_lblBlack.gridx = 0;
			gbc_lblBlack.gridy = 2;
			contentPanel.add(lblBlack, gbc_lblBlack);
		}
		{
			rbHumanB = new JRadioButton("Human");
			rbHumanB.setSelected(true);
			rbHumanB.addActionListener(this);
			GridBagConstraints gbc_rbHumanB = new GridBagConstraints();
			gbc_rbHumanB.insets = new Insets(0, 0, 5, 5);
			gbc_rbHumanB.gridx = 1;
			gbc_rbHumanB.gridy = 2;
			contentPanel.add(rbHumanB, gbc_rbHumanB);
		}
		{
			lblHumanB = new JLabel("Name:");
			GridBagConstraints gbc_lblHumanB = new GridBagConstraints();
			gbc_lblHumanB.insets = new Insets(0, 0, 5, 5);
			gbc_lblHumanB.gridx = 2;
			gbc_lblHumanB.gridy = 2;
			contentPanel.add(lblHumanB, gbc_lblHumanB);
		}
		{
			txtPlayerB = new JTextField();
			txtPlayerB.setText("Player2");
			GridBagConstraints gbc_txtPlayerB = new GridBagConstraints();
			gbc_txtPlayerB.insets = new Insets(0, 0, 5, 0);
			gbc_txtPlayerB.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtPlayerB.gridx = 4;
			gbc_txtPlayerB.gridy = 2;
			contentPanel.add(txtPlayerB, gbc_txtPlayerB);
			txtPlayerB.setColumns(10);
		}
		{
			rbComputerB = new JRadioButton("Computer");
			rbComputerB.addActionListener(this);
			GridBagConstraints gbc_rbComputerB = new GridBagConstraints();
			gbc_rbComputerB.insets = new Insets(0, 0, 0, 5);
			gbc_rbComputerB.gridx = 1;
			gbc_rbComputerB.gridy = 3;
			contentPanel.add(rbComputerB, gbc_rbComputerB);
		}
		{
			lblDifficultyB = new JLabel("Difficulty:");
			GridBagConstraints gbc_lblDifficultyB = new GridBagConstraints();
			gbc_lblDifficultyB.insets = new Insets(0, 0, 0, 5);
			gbc_lblDifficultyB.gridx = 2;
			gbc_lblDifficultyB.gridy = 3;
			contentPanel.add(lblDifficultyB, gbc_lblDifficultyB);
		}
		{
			lblDiffValB = new JLabel("5");
			GridBagConstraints gbc_lblDiffValB = new GridBagConstraints();
			gbc_lblDiffValB.insets = new Insets(0, 0, 0, 5);
			gbc_lblDiffValB.gridx = 3;
			gbc_lblDiffValB.gridy = 3;
			contentPanel.add(lblDiffValB, gbc_lblDiffValB);
		}
		{
			sliderB = new JSlider();
			sliderB.setValue(5);
			sliderB.setSnapToTicks(true);
			sliderB.setMaximum(10);
			sliderB.addChangeListener(this);
			GridBagConstraints gbc_sliderB = new GridBagConstraints();
			gbc_sliderB.gridx = 4;
			gbc_sliderB.gridy = 3;
			contentPanel.add(sliderB, gbc_sliderB);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
		
		toggleWhiteHuman(true);
		toggleBlackHuman(true);
	
	}

	private void toggleWhiteHuman(boolean visible){
		rbHumanW.setSelected(visible);
		txtPlayerW.setVisible(visible);
		lblHumanW.setVisible(visible);
		toggleWhiteComp(!visible);
	}
	
	private void toggleWhiteComp(boolean visible){
		rbComputerW.setSelected(visible);
		lblDifficultyW.setVisible(visible);
		lblDiffValW.setVisible(visible);
		sliderW.setVisible(visible);
	}
	
	private void toggleBlackHuman(boolean visible){
		rbHumanB.setSelected(visible);
		txtPlayerB.setVisible(visible);
		lblHumanB.setVisible(visible);
		toggleBlackComp(!visible);
	}
	
	private void toggleBlackComp(boolean visible){
		rbComputerB.setSelected(visible);
		lblDifficultyB.setVisible(visible);
		lblDiffValB.setVisible(visible);
		sliderB.setVisible(visible);
	}
	
	private void gatherAndReturnChoices(){
		Player white = collectPlayerInfo(WBColor.White);
		Player black = collectPlayerInfo(WBColor.Black);
		
		if(parent != null){
			//parent.newGame(white, black);
		} else {
			System.out.println(white.getClass().getName() + " " + white.getName());
			System.out.println(black.getClass().getName() + " " + black.getName());
		}
	}
	
	private Player collectPlayerInfo(WBColor color){
		switch(color){
		case White:
			if(rbHumanW.isSelected()){
				Player white = new HumanPlayer(WBColor.White);
				white.setName(txtPlayerW.getText());
				return white;
			} else 
				return new ComputerPlayer(WBColor.White, sliderW.getValue());
		case Black:
			if(rbHumanB.isSelected()){
				Player black = new HumanPlayer(WBColor.Black);
				black.setName(txtPlayerB.getText());
				return black;
			} else
				return new ComputerPlayer(WBColor.Black, sliderB.getValue());
		default:
			throw new InvalidParameterException("Color enum unrecognized....");
		}
	}
	
	
	public void stateChanged(ChangeEvent arg0) {
		if(sliderW.getValue() != Integer.parseInt(lblDiffValW.getText()))
			lblDiffValW.setText(Integer.toString(sliderW.getValue()));
		if(sliderB.getValue() != Integer.parseInt(lblDiffValW.getText()))
			lblDiffValB.setText(Integer.toString(sliderB.getValue()));				
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(rbHumanW)){
			toggleWhiteHuman(true);
		} else if(e.getSource().equals(rbComputerW)){
			toggleWhiteHuman(false);
		} else if(e.getSource().equals(rbHumanB)){
			toggleBlackHuman(true);
		} else if(e.getSource().equals(rbComputerB)){
			toggleBlackHuman(false);
		} else if(e.getSource().equals(okButton)){
			gatherAndReturnChoices();
			this.dispose();
		} else if(e.getSource().equals(cancelButton)){
			this.dispose();
		}
	}

}
