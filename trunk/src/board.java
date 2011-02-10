import java.util.*;

public abstract class board {
	boolean playersTurn = true; // whiteturn if true
	boolean getTurn(){ return playersTurn;}
	
	ArrayList<piece> pieces = new ArrayList<piece>();
	ArrayList<piece> getPieces(){ return pieces;}
	boolean[] statusOfSquare(int[] square){
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
	void switchTurn(){playersTurn = !playersTurn;}
	
	void display(){
		//cheni's code
	}
	
	abstract boolean movePiece();
}
