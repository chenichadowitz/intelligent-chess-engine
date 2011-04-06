package iceGUI;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import newGameLogic.WBColor;
import newGameLogic.Piece;
import newGameLogic.PieceEnum;

public class PieceGraphic {
	
	private static String imagePath = "resources/images/";
	private static String imageExt = ".png";
	private static Dimension lastSize;
	private static Map<Piece, ImageIcon> map = new HashMap<Piece, ImageIcon>();
	private static ClassLoader cldr;
	private static boolean boardFlipped = false;

	
	public static void setupMap(BoardArea ba){
		cldr = ba.getClass().getClassLoader();
		for(WBColor col : WBColor.values()){
			for(PieceEnum type : PieceEnum.values()){
				map.put(new Piece(col, type, -1, -1), //Use a new Piece with the correct color and type (offscreen) for key
						new ImageIcon(cldr.getResource( //Create a new ImageIcon for the value, getting the image
								imagePath + col.toString() + type.toString() + imageExt))); //from the path+color+type+extension
			}
		}
	}
	
	public static int[] guiCoord(Piece p){
		int[] oldXY = p.getPosition().clone();
		int[] xy = convertCoordToGUI(oldXY);
		return xy;
	}

	public static int[] convertCoordToGUI(int[] xy){
		xy[0] += 1;
		xy[1] = 8 - xy[1];
		return xy;
	}
	
	public static int getX(Piece p){
		int[] boardCoord = guiCoord(p); 
		if(boardFlipped){
			return (9 - boardCoord[0]) * lastSize.height / 10;
		}
		return boardCoord[0] * lastSize.width / 10;
	}
	
	public static int getY(Piece p){
		int[] boardCoord = guiCoord(p);
		if(boardFlipped){
			return (9 - boardCoord[1]) * lastSize.height / 10;
		}
		return boardCoord[1] * lastSize.height / 10;
	}
	
	public static Image getImg(Piece p){
		return map.get(p).getImage();
	}
	
	public static void setSize(Dimension d){
		lastSize = d;
	}
	
	public static void moveTo(Piece p, int[] move){
		
	}
	
	public static void setFlip(boolean flip){
		boardFlipped = flip;
	}
}
