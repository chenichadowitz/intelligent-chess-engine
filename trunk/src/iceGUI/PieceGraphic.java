package iceGUI;

import gameLogic.Piece;

import java.awt.Dimension;
import java.awt.Image;

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
	 * Creates a new PieceGraphic from the provided Piece object and returns it
	 * @param p Piece object to 'wrap' around
	 * @return new PieceGraphic object 'wrapped' around the provided piece
	 */
	public static PieceGraphic makePieceGraphic(BoardArea ba, Piece p){
		int[] xy = new int[2];
		xy = p.getPosition().clone();
		xy[0] += 1;
		xy[1] = 8 - xy[1];
		ClassLoader cldr = ba.getClass().getClassLoader();
		if(p.getColor()){
			switch(p.type()){
				case 'K':
					return new PieceGraphic(new ImageIcon(cldr.getResource("resources/images/whiteKing.png")), xy, p);
				case 'Q':
					return new PieceGraphic(new ImageIcon(cldr.getResource("resources/images/whiteQueen.png")), xy, p);
				case 'R':
					return new PieceGraphic(new ImageIcon(cldr.getResource("resources/images/whiteRook.png")), xy, p);
				case 'B':
					return new PieceGraphic(new ImageIcon(cldr.getResource("resources/images/whiteBishop.png")), xy, p);
				case 'N':
					return new PieceGraphic(new ImageIcon(cldr.getResource("resources/images/whiteKnight.png")), xy, p);
				case 'P':
					return new PieceGraphic(new ImageIcon(cldr.getResource("resources/images/whitePawn.png")), xy, p);
			}
		} else {
			switch(p.type()){
				case 'K':
					return new PieceGraphic(new ImageIcon(cldr.getResource("resources/images/blackKing.png")), xy, p);
				case 'Q':
					return new PieceGraphic(new ImageIcon(cldr.getResource("resources/images/blackQueen.png")), xy, p);
				case 'R':
					return new PieceGraphic(new ImageIcon(cldr.getResource("resources/images/blackRook.png")), xy, p);
				case 'B':
					return new PieceGraphic(new ImageIcon(cldr.getResource("resources/images/blackBishop.png")), xy, p);
				case 'N':
					return new PieceGraphic(new ImageIcon(cldr.getResource("resources/images/blackKnight.png")), xy, p);
				case 'P':
					return new PieceGraphic(new ImageIcon(cldr.getResource("resources/images/blackPawn.png")), xy, p);
			}
		}
		return null;
	}

	/**
	 * Create a PieceGraphic with the provided ImageIcon at the board coordinates
	 * where (0,0) is equivalent to a8 from white's perspective and (7,7) is h1
	 * @param orig ImageIcon to use for this piece
	 * @param x int for the board's x-coordinate
	 * @param y int for the board's y-coordinate
	 * @param p Piece that this is linked to
	 */
	private PieceGraphic(ImageIcon orig, int x, int y, Piece p){
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
	private PieceGraphic(ImageIcon orig, int[] xy, Piece p){
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
	public int getX(boolean flip){
		if(flip){
			return (9 - boardCoord[0]) * lastSize.height / 10;
		}
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
