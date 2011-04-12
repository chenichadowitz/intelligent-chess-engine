package iceGUI;

import newGameLogic.PieceEnum;
import newGameLogic.WBColor;

public class ColorPieceKey {
	private WBColor color;
	private PieceEnum pieceType;
	
	public ColorPieceKey(WBColor col, PieceEnum type){
		color = col;
		pieceType = type;
	}
	
	public WBColor getColor(){ return color; }
	public PieceEnum getPieceType(){ return pieceType; }
	
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass().getName().equals("ColorPieceKey")) return false;
		ColorPieceKey test = (ColorPieceKey) obj;
		return (color == test.getColor() && pieceType == test.getPieceType());
	}
	
	public int hashCode() {
		return ( color.hashCode() + 31 * pieceType.hashCode() );
	}
}
