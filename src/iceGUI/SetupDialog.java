package iceGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SetupDialog extends JDialog implements ChangeListener, ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtWhiteName;
	private JTextField txtBlackName;
	private JSlider sliderB;
	private JSlider sliderW;
	private JTextField blackCompLvl;
	private JTextField whiteCompLvl;
	private JLabel lblWhiteName;
	private JLabel lblWhiteDifficulty;
	private JRadioButton rbWhiteComputer;
	private JRadioButton rbWhiteHuman;
	private JLabel lblBlackName;
	private JLabel lblBlackDifficulty;
	private JRadioButton rbBlackComputer;
	private JRadioButton rbBlackHuman;

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

	/**
	 * Create the dialog.
	 */
	public SetupDialog() {
		setAlwaysOnTop(true);
		setModal(true);
		setTitle("Setup Game");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][][][grow]", "[][][][]"));
		{
			JLabel lblWhite = new JLabel("White:");
			contentPanel.add(lblWhite, "cell 0 0");
		}
		{
			rbWhiteHuman = new JRadioButton("Human");
			rbWhiteHuman.setSelected(true);
			contentPanel.add(rbWhiteHuman, "cell 1 0");
		}
		{
			lblWhiteName = new JLabel("Name:");
			contentPanel.add(lblWhiteName, "cell 2 0,alignx trailing");
		}
		{
			txtWhiteName = new JTextField();
			txtWhiteName.setText("Player1");
			contentPanel.add(txtWhiteName, "cell 3 0,growx");
			txtWhiteName.setColumns(10);
		}
		{
			rbWhiteComputer = new JRadioButton("Computer");
			contentPanel.add(rbWhiteComputer, "cell 1 1");
		}
		{
			lblWhiteDifficulty = new JLabel("Difficulty:");
			contentPanel.add(lblWhiteDifficulty, "cell 2 1");
		}
		{
			sliderW = new JSlider();
			sliderW.setMaximum(10);
			sliderW.setValue(5);
			sliderW.setSnapToTicks(true);
			sliderW.addChangeListener(this);
			contentPanel.add(sliderW, "flowx,cell 3 1");
		}
		{
			JLabel lblBlack = new JLabel("Black:");
			contentPanel.add(lblBlack, "cell 0 2");
		}
		{
			rbBlackHuman = new JRadioButton("Human");
			rbBlackHuman.setSelected(true);
			contentPanel.add(rbBlackHuman, "cell 1 2");
		}
		{
			lblBlackName = new JLabel("Name:");
			contentPanel.add(lblBlackName, "cell 2 2,alignx trailing");
		}
		{
			txtBlackName = new JTextField();
			txtBlackName.setText("Player2");
			contentPanel.add(txtBlackName, "cell 3 2,growx");
			txtBlackName.setColumns(10);
		}
		{
			rbBlackComputer = new JRadioButton("Computer");
			contentPanel.add(rbBlackComputer, "cell 1 3");
		}
		{
			lblBlackDifficulty = new JLabel("Difficulty:");
			contentPanel.add(lblBlackDifficulty, "cell 2 3");
		}
		{
			sliderB = new JSlider();
			sliderB.setValue(5);
			sliderB.setSnapToTicks(true);
			sliderB.setMaximum(10);
			sliderB.addChangeListener(this);
			contentPanel.add(sliderB, "flowx,cell 3 3");
		}
		{
			blackCompLvl = new JTextField("5");
			blackCompLvl.setEditable(false);
			contentPanel.add(blackCompLvl, "cell 3 3");
			blackCompLvl.setColumns(10);
		}
		{
			whiteCompLvl = new JTextField("5");
			whiteCompLvl.setEditable(false);
			contentPanel.add(whiteCompLvl, "cell 3 1");
			whiteCompLvl.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public void stateChanged(ChangeEvent arg0) {
		if(sliderW.getValue() != Integer.parseInt(whiteCompLvl.getText()))
			whiteCompLvl.setText(Integer.toString(sliderW.getValue()));
		if(sliderB.getValue() != Integer.parseInt(blackCompLvl.getText()))
			blackCompLvl.setText(Integer.toString(sliderB.getValue()));		
	}

	private void toggleWhiteHuman(boolean b){
		lblWhiteName.setVisible(b);
	}
	
	private void toggleWhiteComp(){
		
	}
	
	private void toggleBlackHuman(){
		
	}
	
	private void toggleBlackComp(){
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(rbWhiteHuman)){
			lblWhiteName.setVisible(true);
			sliderW.setVisible(false);
			
		}
		
	}
}
