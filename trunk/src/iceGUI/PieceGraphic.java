package iceGUI;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import newGameLogic.Position;
import newGameLogic.WBColor;
import newGameLogic.Piece;
import newGameLogic.PieceEnum;

public class PieceGraphic {
	
	private static String imagePath = "resources/images/";
	private static String imageExt = ".png";
	private static Dimension lastSize;
	private static Map<ColorPieceKey, ImageIcon> map = new HashMap<ColorPieceKey, ImageIcon>();
	private static boolean boardFlipped = false;

	
	public static void setupMap(BoardArea ba){
		ClassLoader cldr = ba.getClass().getClassLoader();
		ColorPieceKey key;
		for(WBColor col : WBColor.values()){
			for(PieceEnum type : PieceEnum.values()){
				key = new ColorPieceKey(col, type);
				ImageIcon img = new ImageIcon(cldr.getResource(
						imagePath + col.name() + type.name() + imageExt));
				map.put(key, //Use a new Piece with the correct color and type (offscreen) for key
						
						img //Create a new ImageIcon for the value, getting the image
								); //from the path+color+type+extension
			}
		}
	}
	
	public static int getX(Piece p){
		return Position.fromGuiToPixel(
				Position.fromGbToGui( p.getPosition().clone(),
						boardFlipped), lastSize)[0];		
	}
	
	public static int getY(Piece p){
		return Position.fromGuiToPixel(
				Position.fromGbToGui( p.getPosition().clone(),
						boardFlipped), lastSize)[1];
	}
	
	public static Image getImg(Piece p){
		ColorPieceKey key = new ColorPieceKey(p.getPieceColor(), p.getType());
		return map.get(key).getImage();
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
