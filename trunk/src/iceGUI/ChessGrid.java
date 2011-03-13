package iceGUI;

import ice.Piece;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChessGrid extends JPanel {
	
	private ArrayList<ImageIcon> pieceImgs = new ArrayList<ImageIcon>();
	
	public ChessGrid(){
		super();
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		// Will eventually (?) call ChessGrid(Piece[] pieces) from here with a standard layout?
		// For now let's just experiment with a king
		
		ImageIcon king = new ImageIcon("/home/cheni/Desktop/chessking.jpg");
		ImageIcon queen = new ImageIcon("/home/cheni/Desktop/chessqueen.jpg");
		pieceImgs.add(king); pieceImgs.add(queen);
		JLabel kingLabel = new JLabel(king);
		JLabel queenLabel = new JLabel(queen);
		//JLabel test = new JLabel(Integer.toString(super.getHeight()));
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(kingLabel,gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(queenLabel, gbc);
		//gbc.gridx = 1;
		//gbc.gridy = 1;
		//add(test, gbc);
	}
	
	public void setIconSize(Dimension d){
		for(ImageIcon img : pieceImgs){
			img.setImage(img.getImage().getScaledInstance(d.width/ 8, d.height/ 8, Image.SCALE_DEFAULT));
		}
	}
	
	public ChessGrid(Piece[] pieces){
		/** Will implement this later....
		 * this will take an array of Piece objects to 
		 * populate the board with so that we can start off
		 * from a pre-set layout
		 */
	}

}
