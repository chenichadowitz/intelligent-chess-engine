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
	
	private boolean flipBoard = false;
	private int[] lastClick = null;
	private boolean dragging = true;
	private Piece draggingPiece;
	private int[] mouseDragLocation;
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
		Image img;
		for(Piece p : gb.getPieces()){
			if(!dragging || !draggingPiece.equalsPiece(p)){
				img = PieceGraphic.getImg(p);
				g.drawImage(img, PieceGraphic.getX(p), PieceGraphic.getY(p), width, height, this);
			}
		}
		if(dragging){
			img = PieceGraphic.getImg(draggingPiece);
			int[] boardXY = convertPixelToBoard(mouseDragLocation);
			g.drawImage(img, boardXY[0], boardXY[1], width, height, this);
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
			temp = PieceGraphic.convertCoordToGUI(temp);
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
	
	private int[] convertPixelToBoard(int[] pixels){
		Dimension size = this.getSize();
		int[] transPt = {pixels[0] / (size.width / 10), pixels[1] / (size.height / 10)};
		if(flipBoard){
			transPt[0] = 9 - transPt[0];
			transPt[1] = 9 - transPt[1];
		}
		return transPt;
	}
	
	private void makeMove(int[] start, int[] end){
		gb.setNextMove(new Move(start, end, MoveEnum.Unknown));
		repaint();
	}

	public void mouseClicked(MouseEvent e) {
		int[] mXY = {e.getX(), e.getY()};
		int[] pt = convertPixelToBoard(mXY);
		if(lastClick == null){
			Piece p = gb.pieceAt(pt);
			if(p != null){
				lastClick = pt;
				clickedPiece = p;
				repaint();
			}
		} else {
			makeMove(lastClick, pt);
			lastClick = null;
			clickedPiece = null;
		}
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
		Piece p = gb.pieceAt(convertPixelToBoard(mXY));
		if(lastClick == null && p != null && (p.getPieceColor() == gb.getTurn())){
			dragging = true;
			draggingPiece = p;
		} else {
			dragging = false;
			draggingPiece = null;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(dragging){
			int[] mXY = {e.getX(), e.getY()};
			int[] mouseLocation = convertPixelToBoard(mXY);
			makeMove(draggingPiece.getPosition(), mouseLocation);	
			dragging = false;
			draggingPiece = null;
		}
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
			mouseDragLocation = move;
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}