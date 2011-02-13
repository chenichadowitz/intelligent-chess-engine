import java.util.*;

public abstract class Board {
	protected  boolean playersTurn = true; // whiteturn if true
	public  boolean getTurn(){ return playersTurn;}
	
	protected  ArrayList<Piece> pieces = new ArrayList<Piece>();
	public  ArrayList<Piece> getPieces(){ return pieces;}
	protected LinkedList<Piece>[][] boardState = (LinkedList<Piece>[][])new LinkedList[8][8];
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
		for(Piece currentPiece: boardState[square[0]][square[1]]){
			currentPiece.removeFromBoardState();
			currentPiece.generateMoves();
			currentPiece.addToBoardState();
		}		
	}
	public  void display(){
		//cheni's code
	}
	public  void biuldBoardState(){
		for(Piece currentPiece: this.getPieces()){
			currentPiece.generateMoves();
			currentPiece.addToBoardState();
		}
	}
	public  Piece pieceAt(int[] square){
		for(Piece currentPiece: pieces){
			if(currentPiece.getPosition() == square){
				return currentPiece;
			}
		}
		return null; //no piece at square !!!BOOM!!!
	}
	public  void takePiece(Piece taken){
		taken.removeFromBoardState();
		taken.setPosition(null);
		taken.setBoard(null);
		pieces.remove(taken);
	}	
	abstract boolean movePiece(Piece movingPiece, int[] square);
}
