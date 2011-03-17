package iceGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PromotionPrompt extends JPanel implements ActionListener{
	
	public PromotionPrompt(){
		//Let's make the promotion popup window...
		//Make each button and add a listener for each....
		//Queen:
		JRadioButton queenRadio = new JRadioButton("Queen");
	    queenRadio.setActionCommand("Q"); queenRadio.addActionListener(this);
	    queenRadio.setSelected(true); // Preselected
	    //Rook:
	    JRadioButton rookRadio = new JRadioButton("Rook");
	    rookRadio.setActionCommand("R"); rookRadio.addActionListener(this);
	    //Bishop:
	    JRadioButton bishopRadio = new JRadioButton("Bishop");
	    bishopRadio.setActionCommand("B"); bishopRadio.addActionListener(this);
	    //Knight:
	    JRadioButton knightRadio = new JRadioButton("Knight");
	    knightRadio.setActionCommand("N"); knightRadio.addActionListener(this);
	    //Let's group them into a group....
	    ButtonGroup promotionGroup = new ButtonGroup();
	    promotionGroup.add(queenRadio);
	    promotionGroup.add(rookRadio);
	    promotionGroup.add(bishopRadio);
	    promotionGroup.add(knightRadio);
	    //Add each button to the prompt window....
	    add(queenRadio);
	    add(rookRadio);
	    add(bishopRadio);
	    add(knightRadio);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
