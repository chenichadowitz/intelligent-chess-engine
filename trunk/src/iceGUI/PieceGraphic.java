package iceGUI;

import ice.Piece;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

public class PieceGraphic {
	
	/** final variable to hold the original (un-scaled) ImageIcon to scale from */
	private final ImageIcon DEFAULT_IMG;
	/** current pixel coordinate location */
	private int[] boardCoord = {0,0};
	/** Piece object to be linked with */
	private Piece pc = null;
	/** Last Dimension to be received */
	private Dimension lastSize;
	
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
		boardCoord[0] = x;
		boardCoord[1] = y;
		pc = p;
	}
	/**
	 * Create a PieceGraphic with the provided ImageIcon at the board coordinates
	 * where (0,0) is equivalent to a8 from white's perspective and (7,7) is h1
	 * @param orig ImageIcon to use for this piece
	 * @param xy (x,y) board coordinates
	 * @param p Piece that this is linked to
	 */
	public PieceGraphic(ImageIcon orig, int[] xy, Piece p){
		this(orig, xy[0], xy[1], p);
	}
	/**
	 * Get the Image of the PieceGraphic for drawing
	 * @return the respective Image object 
	 */
	public Image getImg(){
		return DEFAULT_IMG.getImage();
	}
	
	/**
	 * Set the dimensions that the board is currently drawn at
	 * @param d Dimension object representing the size
	 */
	public void setSize(Dimension d){
		lastSize = d;
	}
	
	/**
	 * @return x-coordinate of the piece on the board
	 */
	public int getX(){
		return boardCoord[0] * lastSize.width / 10;
	}
	/**
	 * @return y-coordinate of the piece on the board
	 */
	public int getY(boolean flip){
		if(flip){
			return (9 - boardCoord[1]) * lastSize.height / 10;
		}
		return boardCoord[1] * lastSize.height / 10;
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
