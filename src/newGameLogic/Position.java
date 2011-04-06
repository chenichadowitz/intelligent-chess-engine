package newGameLogic;

import java.util.Arrays;

import main.Output;

public class Position {
	
	private final Integer x;
	private final Integer y;
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Position(int[] xy){
		this.x = xy[0];
		this.y = xy[1];
	}
	
	public int getX(){ return x; }
	public int getY(){ return y; }
	
	/**
	 * Returns the coordinate representation of this position
	 */
	public String toString(){
		// (char)(97+x) will convert from int to chars a-h, where 0 is a, 1 is b....
		return "" + (char)(97 + x) + y;
	}
	
	public boolean equals(Object obj){
		if(obj == null) return false;
		if(!(obj instanceof Position)) return false;
		Position testing = (Position) obj;
		return (this.getX() == testing.getX() && this.getY() == testing.getY());
	}
	
	public int hashCode(){
		return ( x.hashCode() + 31 * y.hashCode() );
	}
	
	public int[] toGui(boolean flipped){
		int[] result = new int[2];
		if(flipped){
			result[0] = 7 - x + 1;
			result[1] = y + 1;
		} else {
			result[0] = x + 1;
			result[1] = 7 - y + 1;
		}
		return result;
	}
	
	public static int[] fromGbToGui(int[] from, boolean flipped){
		return (new Position(from).toGui(flipped));
	}
	
	public static int[] fromPixelToGui(int[] from, java.awt.Dimension size){
		int[] result = new int[2];
		//if(flipped){
		//	result[0] = (7 - from[0]) * 10 / size.width;
		//	result[1] = from[1] * 10 / size.height;
		//} else {
			result[0] = from[0] * 10 / size.width;
			result[1] = from[1] * 10 / size.height;
		//}
		return result;
	}
	
	public static int[] fromGuiToGb(int[] from, boolean flipped){
		//Output.debug("From GUI:" + Arrays.toString(from), 2);
		int[] result = new int[2];
		if(flipped){
			result[0] = 7 - from[0] - 1;
			result[1] = from[0] - 1;
		} else {
			result[0] = from[0] - 1;
			result[1] = 7 - from[1] + 1;
		}
		//Output.debug("To GB:" + Arrays.toString(result), 2);
		return result;
	}
	
	public static int[] fromPixelToGb(int[] from, boolean flipped, java.awt.Dimension size){
		//Output.debug("From pixel:" + Arrays.toString(from), 2);
		return ( fromGuiToGb ( fromPixelToGui (from, size) , flipped) );
	}
	
	public static int[] fromGuiToPixel(int[] from, java.awt.Dimension size){
		int[] result = new int[2];
		result[0] = size.width * from[0] / 10;
		result[1] = size.height * from[1] / 10;
		return result;
	}

}
