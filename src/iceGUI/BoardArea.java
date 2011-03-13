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
	
	private ArrayList<PieceGraphic> pieceGraphics = new ArrayList<PieceGraphic>();
	
	public BoardArea(){
		super();	
		PieceGraphic king = new PieceGraphic(new ImageIcon("resources/blackKing.jpg"));
		PieceGraphic queen = new PieceGraphic(new ImageIcon("resources/blackQueen.jpg"), 2,2);
		pieceGraphics.add(king); pieceGraphics.add(queen);
	}

	private void setIconSize(Dimension d){
		for(PieceGraphic pg : pieceGraphics){
			pg.resize(d);
		}
	}
	
	private boolean isSquare(Dimension size){
		if(Math.abs(size.height - size.width) > 5){
			return false;
		}
		return true;
	}
	
	protected void paintComponent(Graphics g) {
		Dimension size = this.getSize();
		if(!isSquare(size)){
			if(size.width < size.height){
				this.setSize(size.width, size.width);
			} else {
				this.setSize(size.height, size.height);
			}
			size = this.getSize();
		}
		
		this.setIconSize(size);

			
		for(int a=0; a<8; a++){
			for(int b=0; b<8; b++){
				g.setColor((a+b)%2==0 ? Color.white : Color.black);
				g.fillRect(size.width / 8 * a, size.height / 8 * b, size.width / 8, size.height / 8);
			}
		}
		
		for(PieceGraphic pg : pieceGraphics){
			g.drawImage(pg.getScaledImg(), pg.getX(), pg.getY(), this);
		}
	}
	
}