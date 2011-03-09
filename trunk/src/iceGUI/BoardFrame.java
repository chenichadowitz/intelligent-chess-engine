package iceGUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;

public class BoardFrame extends JFrame{
	
	JLabel jt;
	private int[] xy = new int[2];
	
	public BoardFrame(String title){
		setBounds(60,60,100,100);
		JPanel mainPanel = new JPanel(new BorderLayout());
		jt = new JLabel();
		JPanel info = new JPanel();
		JPanel board = new JPanel(new GridLayout(8,8));
		
		info.add(new JLabel("INFORMATION"));
		mainPanel.add(info, BorderLayout.EAST);
		mainPanel.add(board, BorderLayout.CENTER);
		this.add(mainPanel);
		setVisible(true);
	}
	
	public void setText(String text){
		//jt.setText(text);
	}
	
	
}
