package iceGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class BoardArea extends JPanel {

	private ImageIcon img;
	
	public BoardArea(){
		super();
		img = new ImageIcon("/home/cheni/Desktop/test.svg");
		//setLayout(new GridLayout(8,8));
		
	}
	/*
	public Dimension getPreferredSize() {
		// Figure out what the layout manager needs and
		// then add 100 to the largest of the dimensions
		// in order to enforce a 'round' bullseye 
		Dimension layoutSize = super.getPreferredSize();
		int max = Math.max(layoutSize.width,layoutSize.height);
		return new Dimension(max+100,max+100);
	}*/

	protected void paintComponent(Graphics g) {
		//Dimension size = getSize();
		Dimension size = this.getSize();
		/**
	        int x = 0;
	        int y = 0;
	        int i = 0;
	        while(x < size.width && y < size.height) {
	            g.setColor(i%2==0? Color.red : Color.white);
	            g.fillOval(x,y,size.width-(2*x),size.height-(2*y));
	            x+=10; y+=10; i++;
	        }
		 **/
		for(int a=0; a<8; a++){
			for(int b=0; b<8; b++){
				g.setColor((a+b)%2==0 ? Color.white : Color.black);
				g.fillRect(size.width / 8 * a, size.height / 8 * b, size.width / 8, size.height / 8);
			}
		}
		g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	
	
	
	
}