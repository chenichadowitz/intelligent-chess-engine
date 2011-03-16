package iceGUI;

import gameLogic.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import main.Debug;

public class BoardArea extends JPanel implements MouseInputListener {
	
	private LinkedList<PieceGraphic> pieceGraphics;
	private boolean flipBoard = false;
	private int[] lastClick = null;
	private boolean dragging = true;
	private int[] draggingOldLocation;
	private PieceGraphic draggingPiece;
	private PieceGraphic clickedPiece;
	private gameBoard gb;
	private BoardPanel bp;
	private ImageIcon boardImage = new ImageIcon("resources/board.png");
	private ImageIcon boardRevImage = new ImageIcon("resources/boardRev.png");
	
	public BoardArea(BoardPanel bp){
		super();
		this.bp = bp;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	private PieceGraphic contains(Piece p){
		for(PieceGraphic pg : pieceGraphics){
			if(pg.getPiece().equals(p)){
				return pg;
			}
		}
		return null;
	}
	
	private void updateBoard(){
		PieceGraphic pg;
		int[] pieceXY = new int[2];
		for(Piece p : gb.getPieces()){
			pg = contains(p);
			if(pg == null){
				pieceGraphics.add(PieceGraphic.makePieceGraphic(p));
			} else {
				if(pg.getPiece().getPosition() != null){
					pieceXY = pg.getPiece().getPosition().clone();
					pieceXY[0] += 1;
					pieceXY[1] = 8 - pieceXY[1];
					if(!Arrays.equals(pieceXY, pg.getBoardPos())){
						pg.moveTo(pieceXY);
					}
				}
			}
		}
		repaint();
	}
		
	public void setupBoard(gameBoard gb){
		this.gb = gb;
		flipBoard = false;
		pieceGraphics = new LinkedList<PieceGraphic>();
		for(Piece p : gb.getPieces()){
			pieceGraphics.add(PieceGraphic.makePieceGraphic(p));
		}
		repaint();
	}
	
	public void flipBoard(){
		flipBoard = !flipBoard;
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
	
	private void cleanPieceGraphics(){
		Iterator<PieceGraphic> pgIter = pieceGraphics.iterator();
		PieceGraphic pgTemp;
		ArrayList<Piece> pieces = gb.getPieces();
		while(pgIter.hasNext()){
			pgTemp = pgIter.next();
			if(!pieces.contains(pgTemp.getPiece())){
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
			int width = (size.width - 8) / 10;
			int height = (size.height - 8) / 10;
			g.drawImage(img, pg.getX(flipBoard), pg.getY(flipBoard), width, height, this);
		}
	}
	
	private void drawPieceBorder(Graphics g, Dimension size){
		if(lastClick != null){
			g.setColor(Color.magenta);
			g.drawRect(lastClick[0] * size.width / 10, lastClick[1] * size.height / 10, size.width / 10, size.height / 10);
		}
	}
	
	private void drawBoardGraphic(Graphics g, Dimension size){
		if(flipBoard){
			Image imgRev = boardRevImage.getImage();
			g.drawImage(imgRev, 0, 0, size.width, size.height, this);
		} else {
			Image img = boardImage.getImage();
			g.drawImage(img, 0, 0, size.width, size.height, this);
		}
	}
	
	protected void paintComponent(Graphics g) {
		makeSquare();
		Dimension size = this.getSize();
		drawBoardGraphic(g, size);
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
		int[] transPt = {pixels[0] / (size.width / 10), pixels[1] / (size.height / 10)};
		if(flipBoard){
			transPt[1] = 9 - transPt[1];
		}
		return transPt;
	}
	
	private void movePiece(PieceGraphic pg, int[] moveFrom, int[] moveTo){
		if(moveTo == null){
			pg.moveTo(moveFrom);
		} else {
			int[] move = new int[4];
			move[0] = moveFrom[0] - 1;
			move[1] = 8 - moveFrom[1];
			move[2] = moveTo[0] - 1;
			move[3] = 8 - moveTo[1];
			Debug.debug(Arrays.toString(move), 2);
			Listener moveobj = PieceMaker.MakeMove(gb, move);
			if(gb.movePiece(moveobj)){
				pg.moveTo(moveTo);
				toggleTurn();
			} else {
				pg.moveTo(moveFrom);
			}
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

	public void mouseExited(MouseEvent e) {
		//Debug.debug("MouseExit", 2);
		mouseReleased(e);
	}

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
		} else if(pg == null && draggingPiece != null && draggingOldLocation != null){
			Debug.debug("Offscreen release", 2);
			movePiece(draggingPiece, draggingOldLocation, null);
		}
		draggingOldLocation = null;
		dragging = false;
		draggingPiece = null;
	}

	public void mouseDragged(MouseEvent e) {
		//Debug.debug("mouse dragged", 3);
		if(dragging){
			int[] mXY = {e.getX(), e.getY()};
			int[] move = convertPixelToBoard(mXY);
			if(move[0] < 1) move[0] = 1;
			if(move[0] > 8) move[0] = 8;
			if(move[1] < 1) move[1] = 1;
			if(move[1] > 8) move[1] = 8;
			draggingPiece.moveTo(move);
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}