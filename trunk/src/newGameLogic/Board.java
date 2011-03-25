package newGameLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import main.Output;

public abstract class Board {
	private Map<Color, Player> playerMap = new HashMap<Color, Player>();
	private Color turn = Color.White;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	private BoardState boardStatus = new BoardState();
	private ArrayList<Move> moveLog = new ArrayList<Move>();
	private Move prevMove;
	private void resetMoveLog(){ moveLog = new ArrayList<Move>(); }
	
	private Piece takenPiece;
	
	private Piece pieceAt(int[] square){
		for(Piece currentPiece: pieces){
			if(Arrays.equals(currentPiece.getPosition(), square)){
				return currentPiece;
			}
		}
		return null; //no piece at square !!!BOOM!!!
	}
	public  SquareState statusOfSquare(int[] square){
		if(square[0] < 0 || square[1] < 0 || square[0] > 7  || square[1] > 7){
			return SquareState.OffBoard;
		}else{
			if(pieceAt(square) == null){
				return SquareState.Empty;
			}else{
				if(pieceAt(square).getPieceColor() == Color.White){
					return SquareState.OccupiedByWhite;
				} else {
					return SquareState.OccupiedByBlack;
				}
			}
        }
	}
	public  void switchTurn(){
		turn = turn.next();
		Output.debug("switched turn", 3);
	}
	public void update(int[]... squares){
		ArrayList<Piece> piecesToUpdate = new ArrayList<Piece>();
		for(int[] square : squares){
			for(Piece p : boardStatus.getPieceList(square)){
				if(!piecesToUpdate.contains(p)){piecesToUpdate.add(p);}
			}
		}
		for(Piece currentPiece: piecesToUpdate){
			removePieceFromBoardState(currentPiece);
			generateMovesfor(currentPiece);
			addPieceToBoardState(currentPiece);
		}
	}

	private void addPieceToBoardState(Piece currentPiece) {
		for(Move currentMove: currentPiece.getMoves()){
			boardStatus.addPiece(currentMove.getFinalPos(), currentPiece);
		}		
	}

	private void generateMovesfor(Piece currentPiece) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		switch(currentPiece.getType()){
			case Bishop:
				newMoves.addAll(movesForBishopAt(currentPiece.getPosition())); break;
			case King:
				newMoves.addAll(movesForKingAt(currentPiece.getPosition(),currentPiece.canCastle())); break;
			case Knight:
				newMoves.addAll(movesForKnightAt(currentPiece.getPosition())); break;
			case Pawn:
				newMoves.addAll(movesForPawnAt(currentPiece.getPosition(),currentPiece.getPieceColor())); break;
			case Rook:
				newMoves.addAll(movesForRookAt(currentPiece.getPosition())); break;
			case Queen:
				newMoves.addAll(movesForRookAt(currentPiece.getPosition()));
				newMoves.addAll(movesForBishopAt(currentPiece.getPosition()));  break;				
		}
		currentPiece.setMoves(newMoves);
	}
	private boolean processSquare(int[] position, ArrayList<Move> newMoves,
			int i, int j) {
		int[] s = {i,j};
		switch(statusOfSquare(s)){
			case OffBoard: return false;
			case Empty: newMoves.add(new Move(position,s,MoveEnum.Move)); return true;
			case OccupiedByBlack: 
				if(pieceAt(position).getPieceColor() == Color.Black){
					newMoves.add(new Move(position,s,MoveEnum.Cover));
				} else {newMoves.add(new Move(position,s,MoveEnum.Take));}
				return false;
			case OccupiedByWhite:
				if(pieceAt(position).getPieceColor() == Color.White){
					newMoves.add(new Move(position,s,MoveEnum.Cover));
				} else {newMoves.add(new Move(position,s,MoveEnum.Take));}
				return false;
		}
		return false;
	}

	private ArrayList<Move> movesForRookAt(int[] position) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		//backwards
		int length = 1;
		while(processSquare(position,newMoves,position[0],position[1] -length)){
			length++;
		}
		//forwards
		length = 1;
		while(processSquare(position,newMoves,position[0],position[1] + length)){
			length++;
		}
		//left
		length = 1;
		while(processSquare(position,newMoves,position[0] - length,position[1])){
			length++;
		}
		//right
		length = 1;
		while(processSquare(position,newMoves,position[0] + length,position[1])){
			length++;
		}
		return newMoves;
	}

	private ArrayList<Move> movesForPawnAt(int[] position, Color color) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		int delta = 1;
		if(color == Color.Black){delta = -1;}
//move up one
		int[]     square    = {position[0], position[1] +delta};
		SquareState status = statusOfSquare(square);
		if(status == SquareState.OccupiedByBlack || status == SquareState.OccupiedByWhite){
			newMoves.add(new Move(position,square,MoveEnum.Listen));
		}
		if (status == SquareState.Empty){
			newMoves.add(new Move(position,square,MoveEnum.Move));
//move up two
			if(position[1] == 1 || position[1] == 6){
				square[1] += delta;
				status = statusOfSquare(square);
				if(status == SquareState.OccupiedByBlack || status == SquareState.OccupiedByWhite){
					newMoves.add(new Move(position,square,MoveEnum.Listen));
				}
				if (status == SquareState.Empty){
					newMoves.add(new Move(position,square,MoveEnum.Move));	
				}
			}
		}
		int[] row = {3,4};
//take/cover right
		if(position[0] != 7){
			square[0]  = position[0] +1;
			square[1]  = position[1] +delta;
			status = statusOfSquare(square);
			if(status == SquareState.Empty){
				newMoves.add(new Move(position,square,MoveEnum.Listen));
				if(position[1] == row[(delta+1)/2]){
					int[] toTheRight = {position[0]+1,position[1]};
					if(Arrays.equals(prevMove.getFinalPos(),toTheRight) && pieceAt(toTheRight).getType() == PieceEnum.Pawn){
						newMoves.add(new Move(position,square,MoveEnum.EnPassant));
					} else {
						newMoves.add(new Move(position,square[0],position[1],MoveEnum.Listen));
					}
				}
			}
			else if(status != SquareState.OffBoard){
				newMoves.add(new Move(position,square,MoveEnum.Take));
			}
		}
//take/cover left
		if(position[0] != 0){
			square[0]  = position[0] -1;
			square[1]  = position[1] +delta;
			status = statusOfSquare(square);
			if(status == SquareState.Empty){
				newMoves.add(new Move(position,square,MoveEnum.Listen));
				if(position[1] == row[(delta+1)/2]){
					int[] toTheLeft = {position[0]+1,position[1]};
					if(Arrays.equals(prevMove.getFinalPos(),toTheLeft) && pieceAt(toTheLeft).getType() == PieceEnum.Pawn){
						newMoves.add(new Move(position,square,MoveEnum.EnPassant));
					} else {
						newMoves.add(new Move(position,square[0],position[1],MoveEnum.Listen));
					}
				}
			}
			else if(status != SquareState.OffBoard){
				newMoves.add(new Move(position,square,MoveEnum.Take));
			}
		}
		return newMoves;
	}

	private ArrayList<Move> movesForKnightAt(int[] position) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		processSquare(position,newMoves,position[0] -2,position[1] -1);
		processSquare(position,newMoves,position[0] -2,position[1] +1);
		processSquare(position,newMoves,position[0] +2,position[1] -1);
		processSquare(position,newMoves,position[0] +2,position[1] +1);
		processSquare(position,newMoves,position[0] -1,position[1] +2);
		processSquare(position,newMoves,position[0] +1,position[1] +2);
		processSquare(position,newMoves,position[0] -1,position[1] -2);
		processSquare(position,newMoves,position[0] +1,position[1] -2);
		return newMoves;
	}

	private ArrayList<Move> movesForKingAt(int[] position, boolean castle) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		processSquare(position,newMoves,position[0] -1,position[1] -1);//down left
		processSquare(position,newMoves,position[0] -1,position[1]   );//down
		processSquare(position,newMoves,position[0] -1,position[1] +1);//down right
		processSquare(position,newMoves,position[0] +1,position[1] -1);//up right
		processSquare(position,newMoves,position[0] +1,position[1]   );//up
		processSquare(position,newMoves,position[0] +1,position[1] +1);//up left
		if(processSquare(position,newMoves,position[0]   ,position[1] -1)){//left
			if(castle){
				Output.debug("checking queenside castling", 3);
				//castle Queen side
				int delta = 1;
				int[] edge = {(int)(3.5+3.5*delta),position[1]};
				if(pieceAt(edge) != null && pieceAt(edge).canCastle()){
					Move moveChecker = newMoves.get(newMoves.size()-1);
					if(moveChecker.getType() == MoveEnum.Move){
						int[]     square = {position[0] +2*delta, position[1]};
						SquareState status = statusOfSquare(square);
						if (status == SquareState.Empty){
							newMoves.add(new Move(position,square,MoveEnum.Castle));
						} else {
							newMoves.add(new Move(position,square,MoveEnum.Listen));
						}
					}
				}
			}
		}
		if(processSquare(position,newMoves,position[0]   ,position[1] +1)){//right
			if(castle){
				Output.debug("checking kingside castling", 3);
				//castle king side
				int delta = 1;
				int[] edge = {(int)(3.5+3.5*delta),position[1]};
				if(pieceAt(edge) != null && pieceAt(edge).canCastle()){
					Move moveChecker = newMoves.get(newMoves.size()-1);
					if(moveChecker.getType() == MoveEnum.Move){
						int[]     square = {position[0] +2*delta, position[1]};
						SquareState status = statusOfSquare(square);
						if (status == SquareState.Empty){
							newMoves.add(new Move(position,square,MoveEnum.Castle));
						} else {
							newMoves.add(new Move(position,square,MoveEnum.Listen));
						}
					}
				}
			}
		}
		return newMoves;
	}

	private ArrayList<Move> movesForBishopAt(int[] position) {
		ArrayList<Move> newMoves = new ArrayList<Move>();
		//down-left
		int length = 1;
		while(processSquare(position,newMoves,position[0] -length,position[1] -length)){
			length++;
		}
		//down-right
		length = 1;
		while(processSquare(position,newMoves,position[0] +length,position[1] -length)){
			length++;
		}
		//up-left
		length = 1;
		while(processSquare(position,newMoves,position[0] - length,position[1] +length)){
			length++;
		}
		//up-right
		length = 1;
		while(processSquare(position,newMoves,position[0] + length,position[1] +length)){
			length++;
		}
		return newMoves;
	}
	

	private void removePieceFromBoardState(Piece currentPiece) {
		for(Move currentMove: currentPiece.getMoves()){
			boardStatus.removePiece(currentMove.getFinalPos(), currentPiece);
		}		
	}
	
	/**
	 * @return String representation of the current gameboard state
	 */
	public String buildDisplay(){
		Output.debug("building board display...", 3);
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
	public  void buildBoardStatus(){
		Output.debug("building boardState",3);
		boardStatus.clearBoardState();
		for(Piece currentPiece: pieces){
			generateMovesfor(currentPiece);
			addPieceToBoardState(currentPiece);
		}
	}
	public void takePiece(Piece taken){
		Output.debug("took " + taken, 3);
		removePieceFromBoardState(taken);
		pieces.remove(taken);
	}	
	
	abstract boolean movePiece(Move action);
	
	public String toString(){
		return buildDisplay(); 
	}
	
	public void addMovetoLog(Move m){
		moveLog.add(m);
		boolean mated = (playerMap.get(Color.White).isInCheckMate() || playerMap.get(Color.Black).isInCheck());
		Output.printNotation(m, mated);
	}
	public void setKingCheck(){
		for(Piece kingFinder: pieces){
			boolean inCheck = false;
			if(kingFinder.getType() == PieceEnum.King){
				for(Piece affectingPiece: boardStatus.getPieceList(kingFinder.getPosition())){
					inCheck = inCheck || (affectingPiece.getPieceColor() != kingFinder.getPieceColor() && affectingPiece.getMoveTo(kingFinder.getPosition()).getType() == MoveEnum.Take);
				}
				Player curPlayer = playerMap.get(kingFinder.getPieceColor());
				curPlayer.setInCheck(inCheck);
				if(curPlayer.isInCheck()){
					Output.debug(curPlayer + " is in check", 2);
				}
			}
		}
	}
	public boolean MoveResultsInCheck(Move m){
		if(execute(m)){undo(m); return false;}
		return true;
	}
	
	private boolean execute(Move m) {
		if(m.getType() == MoveEnum.Listen || m.getType() == MoveEnum.Cover || m.getType() == MoveEnum.Rubbish){
			return false;
		}
		if(pieceAt(m.getOrigPos()) == null){return false;}
		Piece movingPiece = pieceAt(m.getOrigPos());
		removePieceFromBoardState(movingPiece);
		if(m.getType() == MoveEnum.Take){
			takenPiece = pieceAt(m.getFinalPos());
			takePiece(takenPiece);
		}
		movingPiece.setPosition(m.getFinalPos());
		if(movingPiece.getType() == PieceEnum.King || movingPiece.getType() == PieceEnum.Rook){
			m.setOldCastle(movingPiece.canCastle());
			movingPiece.setCastle(false);
		}
		generateMovesfor(movingPiece);
		update(m.getOrigPos(),m.getFinalPos());
		addPieceToBoardState(movingPiece);
		setKingCheck();
		Player owner = playerMap.get(movingPiece.getPieceColor());
		if(owner.isInCheck()){
			Output.debug("that move results in check", 1);
			undo(m);
			return false;
		}
		if(movingPiece.getType() == PieceEnum.Pawn && m.getFinalPos()[1]%7 == 0){
			char newPieceType = owner.getPromotion();
			switch(newPieceType){
				case('R'): 
					movingPiece.setType(PieceEnum.Rook);
					break;
				case('N'): 
					movingPiece.setType(PieceEnum.Knight);
					break;
				case('B'): 
					movingPiece.setType(PieceEnum.Bishop);
				break;
				default :
					movingPiece.setType(PieceEnum.Queen);
					break;
			}
			generateMovesfor(movingPiece);
			addPieceToBoardState(movingPiece);
			setKingCheck();
		}
		m.setPutInCheck(playerMap.get(movingPiece.getPieceColor().next()).isInCheck());
		Output.debug(m.toString(),1);
		prevMove = m;
		return true;
	
	
	
	}

	private void undo(Move m) {
		pieces.add(takenPiece);
		addPieceToBoardState(takenPiece);
		Piece movingPiece = pieceAt(m.getFinalPos());
		removePieceFromBoardState(movingPiece);
		movingPiece.setPosition(m.getOrigPos());
		movingPiece.setCastle(m.isOldCastle());
		generateMovesfor(movingPiece);
		addPieceToBoardState(movingPiece);
		update(m.getOrigPos(),m.getFinalPos());
		setKingCheck();		
	}

	public ArrayList<Move> allValidMovesOf(Player currentPlayer){
		ArrayList<Move> allMoves = new ArrayList<Move>();
		for(Piece curPiece: pieces){
			if(curPiece.getPieceColor() == currentPlayer.getPlayerColor()){
				for(Move moveAdder: curPiece.getMoves()){
					allMoves.add(moveAdder);
				}
			}
		}
		ArrayList<Move> allValidMoves = new ArrayList<Move>();
		for(Move moveChecker: allMoves){
			if(!MoveResultsInCheck(moveChecker)){allValidMoves.add(moveChecker);}
		}
		return allValidMoves;
	}	
	public boolean isGameOver(Player whosTurn){
		ArrayList<Move> allValidMoves = allValidMovesOf(whosTurn);		
		if(!whosTurn.isInCheck()){
			//more tests for staleMate
			if(allValidMoves.size() == 0){
				return true;
			}
			else{return false;}
		}
		if(allValidMoves.size() > 0){return false;}
		Output.debug(whosTurn + " is mated. good game", 1);
		return true;
	}


	

}
