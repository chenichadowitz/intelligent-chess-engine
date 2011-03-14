package iceGUI;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardLabel extends JPanel {
	
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	
	public BoardLabel(String type){
		if(type.equals("rows")){
			setLayout(new GridLayout(8,0));
			for(int i=1; i<=8; i++){
				labels.add(new JLabel("  "+i+"  "));
			}
		} else {
			setLayout(new GridLayout(0,8));
			labels.add(new JLabel("    A"));
			for(char c='B'; c<='G'; c++){
				labels.add(new JLabel(""+c));
			}
		}
		for(JLabel l : labels){
			this.add(l);
		}
	}
	
	

}
