package iceGUI;

import newerGameLogic.*;

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
	private boolean dragging = false;
	private Piece draggingPiece;
	private int[] mouseDragLocation;
	private Piece clickedPiece;
	private Board gb;
	//private BoardPanel bp;
	private GamePanel panel;
	private ImageIcon boardImage;
	private ImageIcon boardRevImage;
	
	public BoardArea(){ this(null);}
	
	public BoardArea(GamePanel gp){
		super();
		this.panel = gp;
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL imageURL = cldr.getResource("resources/images/board.png");
		boardImage = new ImageIcon(imageURL);
		imageURL = cldr.getResource("resources/images/boardRev.png");
		boardRevImage = new ImageIcon(imageURL);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
		
	public void setupBoard(Board gb2){
		this.gb = gb2;
		PieceGraphic.setupMap(this);
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
			if(!dragging || (draggingPiece != null && !draggingPiece.equalsPiece(p))){
				img = PieceGraphic.getImg(p);
				g.drawImage(img, PieceGraphic.getX(p), PieceGraphic.getY(p), width, height, this);
			}
		}
		if(dragging){
			img = PieceGraphic.getImg(draggingPiece);
			int[] boardXY = Position.fromGuiToPixel(mouseDragLocation, this.getSize());
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
		int[] drawPos = Position.fromGuiToPixel(Position.fromGbToGui(lastClick, flipBoard), size);
		g2.drawRect(drawPos[0] * widthAdj, drawPos[1] * heightAdj, widthAdj, heightAdj);
		g2.setColor(Color.red);
		for(Move m : clickedPiece.getMoves()){
			//TODO: WILL NEED TO WEED OUT LISTENERS AND COVERS EVENTUALLY//
			int[] temp = m.getFinalPos().clone();
			temp = Position.fromGbToGui(temp, flipBoard);
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
	
	private void makeMove(int[] start, int[] end){
		Output.debug(Arrays.toString(start)+","+Arrays.toString(end), 2);
		gb.getPlayerMap().get(gb.getTurn()).setNextMove(new Move(start, end, MoveEnum.Unknown));
		repaint();
	}

	public void mouseClicked(MouseEvent e) {
		Output.debug("Mouse clicked", 2);
		int[] mXY = {e.getX(), e.getY()};
		int[] pt = Position.fromPixelToGb(mXY, flipBoard, this.getSize());
		Output.debug(Arrays.toString(pt), 2);
		if(lastClick == null){
			Piece p = gb.pieceAt(pt);
			if(p != null){
				Output.debug("Piece selected:"+p.toString(), 2);
				lastClick = pt;
				clickedPiece = p;
			}
		} else {
			Output.debug("Piece moved", 2);
			makeMove(lastClick, pt);
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
		Output.debug("MousePressed", 2);
		
		
	}

	public void mouseReleased(MouseEvent e) {
		Output.debug("Mouse released", 2);
		if(dragging){
			
			int[] mXY = {e.getX(), e.getY()};
			int[] mouseLocation = Position.fromPixelToGb(mXY, flipBoard, this.getSize());
			makeMove(draggingPiece.getPosition(), mouseLocation);	
			dragging = false;
			draggingPiece = null;
		}
	}

	public void mouseDragged(MouseEvent e) {
		Output.debug("mouse dragged", 2);
		if(!dragging){
			int[] mXY = {e.getX(), e.getY()};
			Piece p = gb.pieceAt(Position.fromPixelToGb(mXY, flipBoard, this.getSize()));
			if(p!=null){
			//Output.debug(p.toString(), 2);
			} else {
				//Output.debug("Piece is null!", 2);
			}
			if(lastClick == null && p != null && (p.getPieceColor() == gb.getTurn())){
				dragging = true;
				Output.debug("Dragging=true", 2);
				draggingPiece = p;
			} else {
				dragging = false;
				draggingPiece = null;
			}
		
		}
		
		if(dragging){
			int[] mXYDrag = {e.getX(), e.getY()};
			int[] move = Position.fromPixelToGui(mXYDrag, this.getSize());
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