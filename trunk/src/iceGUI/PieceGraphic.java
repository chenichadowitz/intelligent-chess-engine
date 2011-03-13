package iceGUI;

import ice.Piece;

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
	private final Piece pc;
	/** Last Dimension to be received */
	private Dimension lastSize;
	
	/**
	 * Create a PieceGraphic with the provided ImageIcon by default at (0,0)
	 * where (0,0) is equivalent to a8 from white's perspective and (7,7) is h1
	 * @param orig ImageIcon to use for this piece
	 */
	/*
	public PieceGraphic(ImageIcon orig){
		DEFAULT_IMG = orig;
		scaledImg = DEFAULT_IMG;
	}
	*/
	
	/**
	 * Create a PieceGraphic with the provided ImageIcon at the board coordinates
	 * where (0,0) is equivalent to a8 from white's perspective and (7,7) is h1
	 * @param orig ImageIcon to use for this piece
	 * @param x int for the board's x-coordinate
	 * @param y int for the board's y-coordinate
	 * @param p Piece that this is linked to
	 */
	public PieceGraphic(ImageIcon orig, int x, int y, Piece p){
		DEFAULT_IMG = orig;
		scaledImg = DEFAULT_IMG;
		boardCoord[0] = x;
		boardCoord[1] = y;
		pc = p;
	}
	
	public PieceGraphic(ImageIcon orig, int[] xy, Piece p){
		this(orig, xy[0], xy[1], p);
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
	
	public boolean removePiece(){
		if(pc.getPosition() == null && pc.getBoard() == null){
			return true;
		}
		return false;
	}
	
	/**
	 * Resizes the image for the 8x8 board of the given size
	 * @param size
	 */
	public void resize(Dimension size){
		lastSize = size;
		 // Current imgs have weird issue with the bottom
		//scaledImg = new ImageIcon(DEFAULT_IMG.getImage().getScaledInstance(
		//		size.width / 8, size.height / 8, Image.SCALE_DEFAULT));
		scaledImg = new ImageIcon(DEFAULT_IMG.getImage().getScaledInstance(
				(size.width - 8)/ 8, (size.height - 8) / 8, Image.SCALE_DEFAULT));
	}	
	
	/**
	 * @return x-coordinate of the piece on the board
	 */
	public int getX(){
		return boardCoord[0] * lastSize.width / 8 - 1;
	}
	/**
	 * @return y-coordinate of the piece on the board
	 */
	public int getY(){
		return boardCoord[1] * lastSize.height / 8 - 1;
	}
	/**
	 * Returns the current board coordinates of the piece
	 * @return int[] representing the current board coordinates
	 */
	public int[] getBoardPos(){
		return boardCoord;
	}
	/**
	 * Moves this piece to the provided board coordinate
	 * @param move int[] representing the board coordinate to move this piece to
	 */
	public void moveTo(int[] move){
		boardCoord[0] = move[0];
		boardCoord[1] = move[1];
	}
	
	public Piece getPiece(){
		return pc;
	}
}
