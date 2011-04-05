package iceGUI;

import newGameLogic.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import main.Output;

@SuppressWarnings("serial")
public class BoardArea extends JPanel implements MouseInputListener {
	
	//private LinkedList<PieceGraphicOld> pieceGraphics;
	private boolean flipBoard = false;
	private int[] lastClick = null;
	private boolean dragging = true;
	private int[] draggingOldLocation;
	private Piece draggingPiece;
	private Piece clickedPiece;
	private GameBoard gb;
	private BoardPanel bp;
	private ImageIcon boardImage;
	private ImageIcon boardRevImage;
	
	public BoardArea(BoardPanel bp){
		super();
		this.bp = bp;
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL imageURL = cldr.getResource("resources/images/board.png");
		boardImage = new ImageIcon(imageURL);
		imageURL = cldr.getResource("resources/images/boardRev.png");
		boardRevImage = new ImageIcon(imageURL);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
		
	public void setupBoard(GameBoard gb){
		this.gb = gb;
		flipBoard = false;
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
	
	private void drawPieces(Graphics g, Dimension size){
		PieceGraphic.setSize(size);
		 // Current imgs have weird issue with the bottom
		// replace (size.(width/height) - 8) / 8
		// with (size.(width/height) / 8)
		int width = (size.width - 8) / 10;
		int height = (size.height - 8) / 10;
		PieceGraphic.setFlip(flipBoard);
		for(Piece p : gb.getPieces()){
			Image img = PieceGraphic.getImg(p);
			g.drawImage(img, PieceGraphic.getX(p), PieceGraphic.getY(p), width, height, this);
		}
	}
	
	private void drawPieceBorder(Graphics g, Dimension size){
		Graphics2D g2 = (Graphics2D) g;
		Stroke oldstroke = g2.getStroke();
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.magenta);
		int widthAdj = size.width / 10;
		int heightAdj = size.height / 10;
		g2.drawRect(lastClick[0] * widthAdj, lastClick[1] * heightAdj, widthAdj, heightAdj);
		g2.setColor(Color.red);
		for(Move m : clickedPiece.getMoves()){
			//TODO: WILL NEED TO WEED OUT LISTENERS AND COVERS EVENTUALLY//
			int[] temp = m.getFinalPos().clone();
			temp = PieceGraphic.convertCoord(temp);
			g2.drawRect(temp[0] * widthAdj, temp[1] * heightAdj, widthAdj, heightAdj); 
		}
		g2.setStroke(oldstroke);
	}
	
	private void drawBoard(Graphics g, Dimension size){
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
		drawBoard(g, size);
		drawPieces(g, size);
		if(lastClick != null){
			drawPieceBorder(g, size);	
		}
	}
	
	private void toggleTurn(){
		bp.switchTurn();
		gb.switchTurn();
	}
	
	/**
	 * @param pt int[] representing board coord to search for a piece at
	 * @return PieceGraphic obj if found, null if not
	 */
	private PieceGraphicOld findPiece(int[] pt){
		int[] pgPt;
		WBColor turn = gb.getTurn();
		for(PieceGraphicOld pg : pieceGraphics){
			pgPt = pg.getBoardPos();
			if(Arrays.equals(pgPt, pt) && (pg.getPiece().getColor() == turn)){
				return pg;
			}
		}
		return null;
	}
	
	private int[] convertPixelToBoard(int[] pixels){
		Dimension size = this.getSize();
		int[] transPt = {pixels[0] / (size.width / 10), pixels[1] / (size.height / 10)};
		if(flipBoard){
			transPt[0] = 9 - transPt[0];
			transPt[1] = 9 - transPt[1];
		}
		return transPt;
	}
	
	private void movePiece(PieceGraphicOld pg, int[] moveFrom, int[] moveTo){
		// If it's not moving to anywhere, move it back to moveFrom
		if(moveTo == null){
			pg.moveTo(moveFrom);
		} else { // Otherwise, go through the motion to make the move
			int[] move = new int[4];
			move[0] = moveFrom[0] - 1;
			move[1] = 8 - moveFrom[1];
			move[2] = moveTo[0] - 1;
			move[3] = 8 - moveTo[1];
			Output.debug(Arrays.toString(move), 2);
			Listener moveobj = PieceMaker.MakeMove(gb, move);
			// If the gameBoard can make the specified move, make it and toggle turn
			if(gb.movePiece(moveobj)){
				pg.moveTo(moveTo);
				toggleTurn();
			} else { // Otherwise, move it back to moveFrom (on the gui)
				pg.moveTo(moveFrom);
			}
		}
		updateBoard();
	}

	public void mouseClicked(MouseEvent e) {
		int[] mXY = {e.getX(), e.getY()};
		int[] pt = convertPixelToBoard(mXY);
		if(lastClick == null){
			PieceGraphicOld pg = findPiece(pt);
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
		PieceGraphicOld pg = findPiece(convertPixelToBoard(mXY));
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
		PieceGraphicOld pg = findPiece(convertPixelToBoard(mXY));
		//Debug.debug("Mouse released",2);
		if(lastClick == null && pg != null){
			int[] newPos = pg.getBoardPos();
			if(draggingOldLocation != null &&
					(draggingOldLocation[0] != newPos[0] || draggingOldLocation[1] != newPos[1])){
				//Debug.debug("Piece released", 2);
				movePiece(pg, draggingOldLocation, pg.getBoardPos());
			}
		} else if(pg == null && draggingPiece != null && draggingOldLocation != null){
			Output.debug("Offscreen release", 2);
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