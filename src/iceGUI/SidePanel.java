package iceGUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class SidePanel extends JPanel {
	
	GridBagConstraints constraints;
	GridBagLayout layout;
	
	public SidePanel(){
		layout = new GridBagLayout();
		setLayout(layout);
		constraints = new GridBagConstraints();
	}
	
	public SidePanel(LayoutManager lm){
		setLayout(lm);
	}
	
	public void resetGBC(){
		constraints = new GridBagConstraints();
	}
	
	public void addItem(JComponent component, int gridx, int gridy){
		constraints.gridx = gridx; constraints.gridy = gridy;
		addItem(component);
	}
	
	public void addItem(JComponent component, GridBagConstraints constraints){
		this.constraints = constraints;
		addItem(component);
	}
	
	public void addItem(JComponent component, int gridx, int gridy, int gridwidth, int gridheight){
		constraints.gridx = gridx; constraints.gridy = gridy; constraints.gridwidth = gridwidth; constraints.gridheight = gridheight;
		addItem(component);
	}
	
	public void addItem(JComponent component, int gridx, int gridy, int fill){
		constraints.gridx = gridx; constraints.gridy = gridy; constraints.fill = fill;
		addItem(component);
	}
	
	public void addItem(JComponent component){
		if(constraints != null)
			layout.setConstraints(component, constraints);
		add(component);
	}
	
	public void addItem(JComponent component, String constraint){
		add(component, constraint);
	}

}
