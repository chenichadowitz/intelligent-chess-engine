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
	private Point lastClick = null;
	
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
			g.drawRect(lastClick.x * size.width / 8, lastClick.y * size.height / 8, size.width / 8, size.height / 8);
		}
	}
	
	private boolean hasPiece(Point pt){
		Point pgPt;
		for(PieceGraphic pg : pieceGraphics){
			pgPt = pg.getBoardPos();
			if(pgPt.x == pt.x && pgPt.y == pt.y){
				return true;
			}
		}
		return false;
	}
	
	private void movePiece(Point moveFrom, Point moveTo){
		Point pgPt;
		for(PieceGraphic pg : pieceGraphics){
			pgPt = pg.getBoardPos();
			if(pgPt.x == moveFrom.x && pgPt.y == moveFrom.y){
				pg.moveTo(moveTo);
				return;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int mX = e.getX();
		int mY = e.getY();
		Dimension size = this.getSize();
		int x = mX / (size.width / 8);
		int y = mY / (size.height / 8);
		//System.out.println("Click made at: (" + x + "," + y + ")");
		Point pt = new Point(x,y);
		if(lastClick == null){
			if(hasPiece(pt)){
				lastClick = pt;
			}
		} else {
			if(!hasPiece(pt)){
				movePiece(lastClick, pt);
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
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}