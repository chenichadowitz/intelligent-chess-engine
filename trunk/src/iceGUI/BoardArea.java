package iceGUI;

import ice.Debug;
import ice.Move;
import ice.Piece;
import ice.gameBoard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class BoardArea extends JPanel implements MouseInputListener {
	
	private LinkedList<PieceGraphic> pieceGraphics = new LinkedList<PieceGraphic>();
	private boolean flipBoard = false; // Flip board after each move?
	private int[] lastClick = null;
	private boolean dragging = true;
	private int[] draggingOldLocation;
	private PieceGraphic draggingPiece;
	private PieceGraphic clickedPiece;
	private gameBoard gb;
	
	public BoardArea(){
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	//*****************************************************************************************//
	//TODO: CONSIDER MAKING A "updatePieces" METHOD TO KEEP PieceGraphics and Pieces in sync
	//*****************************************************************************************//
	
	public void setupBoard(gameBoard gb){
		this.gb = gb;
		int[] boardXY = new int[2];
		for(Piece p : gb.getPieces()){
			boardXY = p.getPosition().clone();
			boardXY[1] = 7 - boardXY[1];
			if(p.getColor()){
				switch(p.type()){
					case 'K':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/whiteKing.png"), boardXY, p));
						break;
					case 'Q':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/whiteQueen.png"), boardXY, p));
						break;
					case 'R':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/whiteRook.png"), boardXY, p));
						break;
					case 'B':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/whiteBishop.png"), boardXY, p));
						break;
					case 'N':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/whiteKnight.png"), boardXY, p));
						break;
					case 'P':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/whitePawn.png"), boardXY, p));
						break;
				}
			} else {
					switch(p.type()){
					case 'K':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/blackKing.png"), boardXY, p));
						break;
					case 'Q':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/blackQueen.png"), boardXY, p));
						break;
					case 'R':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/blackRook.png"), boardXY, p));
						break;
					case 'B':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/blackBishop.png"), boardXY, p));
						break;
					case 'N':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/blackKnight.png"), boardXY, p));
						break;
					case 'P':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/blackPawn.png"), boardXY, p));
						break;
				}
			}
		}
		repaint();
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
		//this.setIconSize(size);	
		for(int a=0; a<8; a++){
			for(int b=0; b<8; b++){
				g.setColor((a+b)%2==0 ? Color.white : Color.gray);
				g.fillRect(size.width / 8 * a, size.height / 8 * b, size.width / 8, size.height / 8);
			}
		}
		for(PieceGraphic pg : pieceGraphics){
			if(pg.removePiece()){
				pieceGraphics.remove(pg);
			}
		}
		for(PieceGraphic pg : pieceGraphics){
			Image img = pg.getImg();
			pg.setSize(size);
			 // Current imgs have weird issue with the bottom
			// replace (size.(width/height) - 8) / 8
			// with (size.(width/height) / 8)
			int width = (size.width - 8) / 8;
			int height = (size.height - 8) / 8;
			g.drawImage(img, pg.getX(), pg.getY(), width, height, this);
		}
		if(lastClick != null){
			g.setColor(Color.magenta);
			g.drawRect(lastClick[0] * size.width / 8, lastClick[1] * size.height / 8, size.width / 8, size.height / 8);
		}
	}
	
	private void toggleTurn(){
		gb.switchTurn();
	}
	
	/**
	 * @param pt int[] representing board coord to search for a piece at
	 * @return PieceGraphic obj if found, null if not
	 */
	private PieceGraphic findPiece(int[] pt){
		int[] pgPt;
		boolean turn = gb.getTurn();
		for(PieceGraphic pg : pieceGraphics){
			pgPt = pg.getBoardPos();
			if(pgPt[0] == pt[0] && pgPt[1] == pt[1] && (pg.getPiece().getColor() == turn)){
				return pg;
			}
		}
		return null;
	}
	
	private int[] convertPixelToBoard(int[] pixels){
		Dimension size = this.getSize();
		Debug.debug("pixels:"+pixels[0]+","+pixels[1],1);
		int[] transPt = {pixels[0] / (size.width / 8), pixels[1] / (size.height / 8)};
		Debug.debug("coords:"+transPt[0]+","+transPt[1],1);
		return transPt;
	}
	
	private void movePiece(PieceGraphic pg, int[] moveFrom, int[] moveTo){
		int[] move = {moveFrom[0], 7-moveFrom[1], moveTo[0], 7-moveTo[1]};
		Move moveobj = new Move(gb, move);
		if(gb.movePiece(moveobj)){
			pg.moveTo(moveTo);
			toggleTurn();
		} else {
			pg.moveTo(moveFrom);
		}
	}

	public void mouseClicked(MouseEvent e) {
		int[] mXY = {e.getX(), e.getY()};
		//System.out.println("MouseClickEvent: " + mXY[0] + "," + mXY[1]);
		int[] pt = convertPixelToBoard(mXY);
		if(lastClick == null){
			PieceGraphic pg = findPiece(pt);
			if(pg != null){
				Debug.debug("Piece has been selected", 1);
				Debug.debug(""+pt[0]+","+pt[1], 1);
				lastClick = pt;
				clickedPiece = pg;
			}
		} else {
			//if(findPiece(pt) == null){
			Debug.debug("Piece has been moved", 1);
				movePiece(clickedPiece, lastClick, pt);
			//}
			lastClick = null;
			clickedPiece = null;
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int[] mXY = {e.getX(), e.getY()};
		//System.out.println("MousePressEvent:" + mXY[0] + "," + mXY[1]);
		PieceGraphic pg = findPiece(convertPixelToBoard(mXY));
		if(lastClick == null && pg != null && (pg.getPiece().getColor() == gb.getTurn())){
			Debug.debug("Piece is being dragged....", 1);
			dragging = true;
			draggingOldLocation = pg.getBoardPos().clone();
			draggingPiece = pg;
			// Let's reorder the list of PieceGraphics so that this piece is on the end
			// and is thus drawn on TOP of everything else
			pieceGraphics.remove(pg);
			pieceGraphics.add(pg);
		} else {
			Debug.debug("MousePressed-piece NOT being dragged", 1);
			dragging = false;
			draggingPiece = null;
		}
	}

	public void mouseReleased(MouseEvent e) {
		int[] mXY = {e.getX(), e.getY()};
		PieceGraphic pg = findPiece(convertPixelToBoard(mXY));
		Debug.debug("Mouse released",1);
		if(lastClick == null && pg != null){
			int[] newPos = pg.getBoardPos();
			if(draggingOldLocation != null &&
					(draggingOldLocation[0] != newPos[0] || draggingOldLocation[1] != newPos[1])){
				Debug.debug("Piece released", 1);
				System.out.println("draglocation: "+draggingOldLocation[0]+","+draggingOldLocation[1]);
				movePiece(pg, draggingOldLocation, pg.getBoardPos());
			}
		}
		draggingOldLocation = null;
		dragging = false;
		draggingPiece = null;
	}

	public void mouseDragged(MouseEvent e) {
		Debug.debug("mouse dragged", 1);
		if(dragging){
			int[] mXY = {e.getX(), e.getY()};
			//System.out.println("DragEvent: " + mXY[0] + "," + mXY[1]);
			draggingPiece.moveTo(convertPixelToBoard(mXY));
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}