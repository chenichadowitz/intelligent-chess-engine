package ice;
import java.util.*;

@SuppressWarnings("unchecked")
public abstract class Board {
	Player whitePlayer;
	Player blackPlayer;
	protected  boolean playersTurn = true; // whiteturn if true
	protected  ArrayList<Piece> pieces = new ArrayList<Piece>();
	protected LinkedList<Piece>[][] boardState = (LinkedList<Piece>[][]) new LinkedList[8][8];

	public  boolean getTurn(){ return playersTurn;}
	
	public  ArrayList<Piece> getPieces(){ return pieces;}
	
	public LinkedList<Piece>[][] getBoardState(){ return boardState;}

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
                if (Arrays.equals(pieces.get(searcher).getPosition(), square)){
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
		LinkedList<Piece> temp = new LinkedList<Piece>();
		for(Piece p : boardState[square[0]][square[1]]){
			temp.add(p);
		}
		for(Piece currentPiece: temp){
			currentPiece.removeFromBoardState();
			currentPiece.generateMoves();
			currentPiece.addToBoardState();
		}		
	}
	public  void display(){
		Piece current;
		int[] place = new int[2];
		for(int i=7;i>=0;i--){
			System.out.print(i+1 + " ");
			for(int j=0;j<8;j++){
				place[0] = j; place[1] = i;
				current = pieceAt(place);
				if(current == null){
					System.out.print("-- ");
				} else {
					System.out.print(current+" ");
				}
			}
			System.out.println();
		}
		System.out.println("  a  b  c  d  e  f  g  h");
	}
	public  void buildBoardState(){
		LinkedList<Piece> dummy = new LinkedList<Piece>();
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				boardState[i][j] = dummy;
			}
		}
		for(Piece currentPiece: this.getPieces()){
			currentPiece.generateMoves();
			currentPiece.addToBoardState();
		}
	}
	public  Piece pieceAt(int[] square){
		for(Piece currentPiece: pieces){
			if(Arrays.equals(currentPiece.getPosition(), square)){
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
	abstract boolean movePiece(int[] squareAB);
	
	public String toString(){
		return "board"; 
	}
}