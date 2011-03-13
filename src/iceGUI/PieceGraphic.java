package iceGUI;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

public class PieceGraphic {
	
	/** final variable to hold the original (un-scaled) ImageIcon to scale from */
	private final ImageIcon DEFAULT_IMG;
	/** current scaled ImageIcon (to draw) */
	private ImageIcon scaledImg;
	/** current pixel coordinate location */
	private int[] boardCoord = {0,0};
	/** Piece object to be linked with */
	//private Piece pc;
	private Dimension lastSize;
	
	
	public PieceGraphic(ImageIcon orig){
		DEFAULT_IMG = orig;
		scaledImg = DEFAULT_IMG;
	}
	
	public PieceGraphic(ImageIcon orig, int x, int y){
		this(orig);
		boardCoord[0] = x;
		boardCoord[1] = y;
	}
	
	/**
	 * @return current scaled ImageIcon
	 */	
	public ImageIcon getScaledImgIcon(){
		return scaledImg;
	}
	
	/**
	 * @return Image of the current scaled ImageIcon
	 */
	public Image getScaledImg(){
		return scaledImg.getImage();
	}
	
	/**
	 * Resizes the image for the 8x8 board of the given size
	 * @param size
	 */
	public void resize(Dimension size){
		lastSize = size;
		scaledImg = new ImageIcon(DEFAULT_IMG.getImage().getScaledInstance(
				size.width / 8, size.height / 8, Image.SCALE_DEFAULT));
	}	
	
	/**
	 * @return x-coordinate of the piece on the board
	 */
	public int getX(){
		return boardCoord[0] * lastSize.width / 8;
	}
	/**
	 * @return y-coordinate of the piece on the board
	 */
	public int getY(){
		return boardCoord[1] * lastSize.height / 8;
	}
	
	public Point getBoardPos(){
		return new Point(boardCoord[0], boardCoord[1]);
	}
	
	public void moveTo(Point move){
		boardCoord[0] = move.x;
		boardCoord[1] = move.y;
	}
}
