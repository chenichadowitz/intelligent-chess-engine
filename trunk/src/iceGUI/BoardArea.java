package iceGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class BoardArea extends JPanel implements MouseInputListener {
	
	private ArrayList<PieceGraphic> pieceGraphics = new ArrayList<PieceGraphic>();
	private boolean flipBoard = false; // Flip board after each move?
	private int[] lastClick = null;
	private boolean dragging = true;
	private PieceGraphic draggingPiece;
	private PieceGraphic clickedPiece;
	
	public BoardArea(){
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		PieceGraphic king = new PieceGraphic(new ImageIcon("resources/blackKing.png"));
		PieceGraphic queen = new PieceGraphic(new ImageIcon("resources/blackQueen.png"), 2,2);
		pieceGraphics.add(king); pieceGraphics.add(queen);
	}

	private void setIconSize(Dimension d){
		for(PieceGraphic pg : pieceGraphics){
			pg.resize(d);
		}
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
		this.setIconSize(size);	
		for(int a=0; a<8; a++){
			for(int b=0; b<8; b++){
				g.setColor((a+b)%2==0 ? Color.white : Color.black);
				g.fillRect(size.width / 8 * a, size.height / 8 * b, size.width / 8, size.height / 8);
			}
		}
		for(PieceGraphic pg : pieceGraphics){
			g.drawImage(pg.getScaledImg(), pg.getX(), pg.getY(), this);
		}
		if(lastClick != null){
			g.setColor(Color.magenta);
			g.drawRect(lastClick[0] * size.width / 8, lastClick[1] * size.height / 8, size.width / 8, size.height / 8);
		}
	}
	
	/**
	 * @param pt int[] representing board coord to search for a piece at
	 * @return PieceGraphic obj if found, null if not
	 */
	private PieceGraphic findPiece(int[] pt){
		int[] pgPt;
		for(PieceGraphic pg : pieceGraphics){
			pgPt = pg.getBoardPos();
			if(pgPt[0] == pt[0] && pgPt[0] == pt[0]){
				return pg;
			}
		}
		return null;
	}
	
	private int[] convertPixToBoard(int[] pix){
		Dimension size = this.getSize();
		int[] transPt = {pix[0] / (size.width / 8), pix[1] / (size.height / 8)};
		return transPt;
	}
	
	private void movePiece(PieceGraphic pg, int[] moveFrom, int[] moveTo){
		pg.moveTo(moveTo);
	}

	public void mouseClicked(MouseEvent e) {
		int[] mXY = {e.getX(), e.getY()};
		int[] pt = convertPixToBoard(mXY);
		if(lastClick == null){
			PieceGraphic pg = findPiece(pt);
			if(pg != null){
				lastClick = pt;
				clickedPiece = pg;
			}
		} else {
			if(findPiece(pt) == null){
				movePiece(clickedPiece, lastClick, pt);
			}
			lastClick = null;
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
		PieceGraphic pg = findPiece(convertPixToBoard(mXY));
		if(lastClick == null && pg != null){
			dragging = true;
			draggingPiece = pg;
		} else {
			dragging = false;
			draggingPiece = null;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(dragging){
			int[] mXY = {e.getX(), e.getY()};
			draggingPiece.moveTo(convertPixToBoard(mXY));
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}