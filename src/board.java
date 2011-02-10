import java.util.*;

public abstract class board {
	protected  boolean playersTurn = true; // whiteturn if true
	public  boolean getTurn(){ return playersTurn;}
	
	protected  ArrayList<piece> pieces = new ArrayList<piece>();
	public  ArrayList<piece> getPieces(){ return pieces;}
	protected LinkedList<piece>[][] boardState = (LinkedList<piece>[][])new LinkedList[8][8];
	public  boolean[] statusOfSquare(int[] square){
		boolean[] squareStatus = {false,true};
		//possible returns are:
		//true,true   = occupied by white piece
		//true,false  = occupied by black piece 
		//false,true  = unoccupied on board
		//false,false = unoccupied off board
		
		if(square[0] < 0 || square[1] < 0 || square[0] > 7  || square[1] > 7){
			squareStatus[1] = false;
			return squareStatus;
		}
		else{
			for(int searcher = 0; searcher < pieces.size(); searcher ++){
                if (pieces.get(searcher).getPosition() == square){
                	squareStatus[0] = true;
                    squareStatus[1] = pieces.get(searcher).getColor();
                    return squareStatus;
                }
			}
        }
		return squareStatus;
	}
	public  void switchTurn(){playersTurn = !playersTurn;}
	public  void update(int[] square){
		//fill in code
	}
	public  void display(){
		//cheni's code
	}
	
	abstract boolean movePiece(piece movingPiece, int[] square);
}
