package newGameLogic;

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

}
