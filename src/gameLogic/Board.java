package gameLogic;
import java.util.*;

import main.Debug;

@SuppressWarnings("unchecked")
public abstract class Board {
	Map<Boolean, Player> playerMap = new HashMap<Boolean, Player>();
	protected  boolean playersTurn = true; // whiteturn if true
	protected  ArrayList<Piece> pieces = new ArrayList<Piece>();
	protected LinkedList<Piece>[][] boardState = (LinkedList<Piece>[][]) new LinkedList[8][8];
	protected ArrayList<Listener> moveLog = new ArrayList<Listener>();
	protected Listener curMove;
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
	public  void switchTurn(){
		playersTurn = !playersTurn;
		Debug.debug("switched turn", 3);
	}
	public  void update(int[]... squares){
		ArrayList<Piece> piecesToUpdate = new ArrayList<Piece>();
		for(int[] square : squares){
			for(Piece p : boardState[square[0]][square[1]]){
				if(!piecesToUpdate.contains(p)){piecesToUpdate.add(p);}
			}
		}
		for(Piece currentPiece: piecesToUpdate){
			currentPiece.removeFromBoardState();
			currentPiece.generateMoves();
			currentPiece.addToBoardState();
		}
	}
	public String buildDisplay(){
		Debug.debug("building board display...", 3);
		StringBuilder sb = new StringBuilder();
		Piece current;
		int[] place = new int[2];
		for(int i=7;i>=0;i--){
			sb.append(i+1 + "| ");
			for(int j=0;j<8;j++){
				place[0] = j; place[1] = i;
				current = pieceAt(place);
				if(current == null){
					sb.append("-- ");
				} else {
					sb.append(current+" ");
				}
			}
			sb.append("\n");
		}
		sb.append("--------------------------\n");
		sb.append("    a  b  c  d  e  f  g  h\n");
		return sb.toString();
	}
	public  void buildBoardState(){
		Debug.debug("building boardState",3);
		LinkedList<Piece> dummy = new LinkedList<Piece>();
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				boardState[i][j] = dummy;
				dummy = new LinkedList<Piece>();
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
		Debug.debug("took " + taken, 3);
		taken.removeFromBoardState();
		pieces.remove(taken);
	}	
	abstract boolean movePiece(Listener action);
	
	public String toString(){
		return "board"; 
	}
	public String moveLogtoString(){
		String log = "";
		boolean logTurn = true;
		for(Listener move: moveLog){
			if(logTurn){log +=("\n" + (moveLog.indexOf(move)/2 +1) + ". " + move.toString());}
			else	   {log +=( "  " + move.toString());}
			logTurn = !logTurn;
		}
		if(playerMap.get(true).isMated() || playerMap.get(false).isMated()){
			log += "+ \n";
			if(playerMap.get(true).isMated()){log += "  0-1";}
			else{log += "  1-0";}
		}
		return log;
	}
	public void setKingCheck(){
		for(Piece kingFinder: pieces){
			boolean inCheck = false;
			if(kingFinder.pieceType.equals("K")){
				for(Piece affectingPiece: boardState[kingFinder.position[0]][kingFinder.position[1]]){
					inCheck = inCheck || (affectingPiece.color != kingFinder.color);
				}
				Player curPlayer = playerMap.get(kingFinder.color);
				curPlayer.setCheckStatus(inCheck);
				if(curPlayer.getCheckStatus()){
					Debug.debug(curPlayer + " is in check", 2);
				}
			}
		}
	}
	public boolean isCheckMate(Player whosInCheck){
		if(whosInCheck.checkStatus != true){return false;} //add check for stalemate
		ArrayList<Listener> allMoves = new ArrayList<Listener>();
		for(Piece curPiece: pieces){
			if(curPiece.color == whosInCheck.color){
				for(Listener moveAdder: curPiece.moves){
					allMoves.add(moveAdder);
				}
			}
		}
		ArrayList<Listener> allValidMoves = new ArrayList<Listener>();
		for(Listener moveChecker: allMoves){
			if(!moveChecker.resultsInCheck()){allValidMoves.add(moveChecker);}
		}
		for(Listener moves : allValidMoves){
			System.out.println(moves);			
		}
		if(allValidMoves.size() > 0){return false;}
		Debug.debug(whosInCheck + " is mated. good game", 1);
		return true;
	}

}