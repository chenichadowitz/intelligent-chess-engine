package iceGUI;

import ice.Debug;
import ice.Move;
import ice.Piece;
import ice.gameBoard;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class BoardArea extends JPanel implements MouseInputListener {
	
	private LinkedList<PieceGraphic> pieceGraphics;
	private boolean flipBoard = false; // Flip board after each move?
	private int[] lastClick = null;
	private boolean dragging = true;
	private int[] draggingOldLocation;
	private PieceGraphic draggingPiece;
	private PieceGraphic clickedPiece;
	private gameBoard gb;
	private BoardPanel bp;
	
	public BoardArea(BoardPanel bp){
		super();
		this.bp = bp;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	private void updateBoard(){
		int[] pieceXY = new int[2];
		for(PieceGraphic pg : pieceGraphics){
			if(pg.getPiece().getPosition() != null){
				pieceXY = pg.getPiece().getPosition().clone();
			//if(pieceXY != null){ //In case this is ever called without cleaning up pieces that should have been removed.... 
				pieceXY[1] = 7 - pieceXY[1];
				if(!Arrays.equals(pieceXY, pg.getBoardPos())){
					pg.moveTo(pieceXY);
				}
			}
		}
		repaint();
	}
	
	public void setupBoard(gameBoard gb){
		this.gb = gb;
		int[] pieceXY = new int[2];
		pieceGraphics = new LinkedList<PieceGraphic>();
		for(Piece p : gb.getPieces()){
			pieceXY = p.getPosition().clone();
			pieceXY[1] = 7 - pieceXY[1];
			if(p.getColor()){
				switch(p.type()){
					case 'K':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/whiteKing.png"), pieceXY, p));
						break;
					case 'Q':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/whiteQueen.png"), pieceXY, p));
						break;
					case 'R':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/whiteRook.png"), pieceXY, p));
						break;
					case 'B':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/whiteBishop.png"), pieceXY, p));
						break;
					case 'N':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/whiteKnight.png"), pieceXY, p));
						break;
					case 'P':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/whitePawn.png"), pieceXY, p));
						break;
				}
			} else {
					switch(p.type()){
					case 'K':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/blackKing.png"), pieceXY, p));
						break;
					case 'Q':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/blackQueen.png"), pieceXY, p));
						break;
					case 'R':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/blackRook.png"), pieceXY, p));
						break;
					case 'B':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/blackBishop.png"), pieceXY, p));
						break;
					case 'N':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/blackKnight.png"), pieceXY, p));
						break;
					case 'P':
						pieceGraphics.add(new PieceGraphic(new ImageIcon("resources/orig/blackPawn.png"), pieceXY, p));
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
	
	private void makeSquare(){
		Dimension size = this.getSize();
		if(!isSquare(size)){
			if(size.width < size.height){
				this.setSize(size.width, size.width);
			} else {
				this.setSize(size.height, size.height);
			}
		}
	}
	
	private void drawBoardSquares(Graphics g, Dimension size){
		for(int a=0; a<8; a++){
			for(int b=0; b<8; b++){
				g.setColor((a+b)%2==0 ? Color.white : Color.gray);
				g.fillRect(size.width / 8 * a, size.height / 8 * b, size.width / 8, size.height / 8);
			}
		}
	}
	
	private void cleanPieceGraphics(){
		Iterator<PieceGraphic> pgIter = pieceGraphics.iterator();
		PieceGraphic pgTemp;
		while(pgIter.hasNext()){
			pgTemp = pgIter.next();
			if(pgTemp.mustRemovePiece()){
				pgIter.remove();
			}
		}
	}
	
	private void drawPieceGraphics(Graphics g, Dimension size){
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
	}
	
	private void drawPieceBorder(Graphics g, Dimension size){
		if(lastClick != null){
			g.setColor(Color.magenta);
			g.drawRect(lastClick[0] * size.width / 8, lastClick[1] * size.height / 8, size.width / 8, size.height / 8);
		}
	}
	
	protected void paintComponent(Graphics g) {
		//makeSquare();
		Dimension size = this.getSize();
		drawBoardSquares(g, size);
		cleanPieceGraphics();
		drawPieceGraphics(g, size);
		drawPieceBorder(g, size);		
	}
	
	private void toggleTurn(){
		bp.switchTurn();
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
		//Debug.debug("pixels:"+pixels[0]+","+pixels[1],3);
		int[] transPt = {pixels[0] / (size.width / 8), pixels[1] / (size.height / 8)};
		//Debug.debug("coords:"+transPt[0]+","+transPt[1],3);
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
		updateBoard();
	}

	public void mouseClicked(MouseEvent e) {
		int[] mXY = {e.getX(), e.getY()};
		int[] pt = convertPixelToBoard(mXY);
		if(lastClick == null){
			PieceGraphic pg = findPiece(pt);
			if(pg != null){
				//Debug.debug("Piece has been selected", 2);
				//Debug.debug(""+pt[0]+","+pt[1], 2);
				lastClick = pt;
				clickedPiece = pg;
			}
		} else {
			//if(findPiece(pt) == null){
			//Debug.debug("Piece has been moved", 2);
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
		PieceGraphic pg = findPiece(convertPixelToBoard(mXY));
		if(lastClick == null && pg != null && (pg.getPiece().getColor() == gb.getTurn())){
			//Debug.debug("Piece is being dragged....", 2);
			dragging = true;
			draggingOldLocation = pg.getBoardPos().clone();
			draggingPiece = pg;
			// Let's reorder the list of PieceGraphics so that this piece is on the end
			// and is thus drawn on TOP of everything else
			pieceGraphics.remove(pg);
			pieceGraphics.add(pg);
		} else {
			//Debug.debug("MousePressed-piece NOT being dragged", 2);
			dragging = false;
			draggingPiece = null;
		}
	}

	public void mouseReleased(MouseEvent e) {
		int[] mXY = {e.getX(), e.getY()};
		PieceGraphic pg = findPiece(convertPixelToBoard(mXY));
		//Debug.debug("Mouse released",2);
		if(lastClick == null && pg != null){
			int[] newPos = pg.getBoardPos();
			if(draggingOldLocation != null &&
					(draggingOldLocation[0] != newPos[0] || draggingOldLocation[1] != newPos[1])){
				//Debug.debug("Piece released", 2);
				movePiece(pg, draggingOldLocation, pg.getBoardPos());
			}
		}
		draggingOldLocation = null;
		dragging = false;
		draggingPiece = null;
	}

	public void mouseDragged(MouseEvent e) {
		//Debug.debug("mouse dragged", 3);
		if(dragging){
			int[] mXY = {e.getX(), e.getY()};
			draggingPiece.moveTo(convertPixelToBoard(mXY));
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}