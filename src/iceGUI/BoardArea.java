package iceGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardArea extends JPanel {

//	private ImageIcon img;
//	private ImageIcon img2;
	private ChessGrid grid;
	private ArrayList<ImageIcon> pieceImgs = new ArrayList<ImageIcon>();

	
	public BoardArea(){
		super();
		//img = new ImageIcon("/home/cheni/Desktop/tuxLinux.jpg");
		//img2 = new ImageIcon("/home/cheni/Desktop/tuxLinux2.jpg");
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		// Will eventually (?) call ChessGrid(Piece[] pieces) from here with a standard layout?
		// For now let's just experiment with a king
		
		ImageIcon king = new ImageIcon("resources/blackKing.jpg");
		ImageIcon queen = new ImageIcon("resources/blackQueen.jpg");
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
		//grid = new ChessGrid();
		//add(grid);
	}

	private void setIconSize(Dimension d){
		for(ImageIcon img : pieceImgs){
			img.setImage(img.getImage().getScaledInstance(d.width/ 8, d.height/ 8, Image.SCALE_DEFAULT));
		}
	}
	
	protected void paintComponent(Graphics g) {
		Dimension size = this.getSize();
		this.setIconSize(size);

			
		for(int a=0; a<8; a++){
			for(int b=0; b<8; b++){
				g.setColor((a+b)%2==0 ? Color.white : Color.black);
				g.fillRect(size.width / 8 * a, size.height / 8 * b, size.width / 8, size.height / 8);
			}
		}
		
		
		//g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
		//g.drawImage(img2.getImage(), 30, 30, null);
	}
}