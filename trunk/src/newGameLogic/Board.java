package newGameLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import main.Output;

public abstract class Board implements Cloneable{
	private Color turn = Color.White;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	private BoardState boardStatus = new BoardState();
	private ArrayList<Move> moveLog = new ArrayList<Move>();
	private Move prevMove;
	private Map<Color, Player> playerMap = new HashMap<Color, Player>();
	/**
	 * resets the move log	
	 */
	private void resetMoveLog(){ moveLog = new ArrayList<Move>(); }
	/**
	 * returns the piece at a given square on the board	
	 * @param square square to search
	 * @return piece at the given square
	 */
	public Piece pieceAt(int[] square){
		for(Piece currentPiece: pieces){
			if(Arrays.equals(currentPiece.getPosition(), square)){
				return currentPiece;
			}
		}
		return null; //no piece at square !!!BOOM!!!
	}
	
	/**
	 * returns the piece at a given square on the board
	 * @param column the column of the theoretical piece
	 * @param rank rank of the theoretical piece
	 * @return the piece at that position
	 */
	public Piece pieceAt(int column, int rank){
		int[] square = {column,rank};
		return pieceAt(square);
	}
	
	
	/**
	 * returns the state of the square in question
	 * @param square square to get the state of
	 * @return the state of the square
	 */
	private  SquareState statusOfSquare(int[] square){
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
	/**
	 * switches the player's turn
	 */
	public void switchTurn(){
		turn = turn.next();
		Output.debug("switched turn", 3);
	}
	/**
	 * updates the given squares' boardStatus pieces
	 * @param squares the square to update
	 */
	private void update(int[]... squares){
		ArrayList<Piece> piecesToUpdate = new ArrayList<Piece>();
		for(int[] square : squares){
			System.out.println(square[0] + " " + square[1]);
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
	/**
	 * adds the given piece to the boardstatus
	 * @param currentPiece piece to add
	 */
	public void addPieceToBoardState(Piece currentPiece) {
		for(Move currentMove: currentPiece.getMoves()){
			boardStatus.addPiece(currentMove.getFinalPos(), currentPiece);
		}		
	}
	/**
	 * generates moves for the given piece
	 * @param currentPiece piece to generate moves for
	 */
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
	/**
	 * processes the given square correctly for most pieces
	 * @param position the position of the piece generating the moves
	 * @param newMoves the move array to add the new move to
	 * @param i the x coordinate of the move
	 * @param j the y coordinate of the move
	 * @return returns whether that square was empty or not
	 */
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
	/**
	 * generates moves for a rook at the given square
	 * @param position rook location
	 * @return the new moves array
	 */
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
	/**
	 * generates moves for a pawn at the given location
	 * @param position the position of the pawn
	 * @param color the color of the pawn
	 * @return the array of new moves
	 */
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
	/**
	 * generates moves for a knight at the given square
	 * @param position the position of the knight
	 * @return the array of new moves
	 */
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
	/**
	 * generates new moves for a king at the given location
	 * @param position the position of the king
	 * @param castle whether that king can castle
	 * @return returns the new moves array
	 */
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
	/**
	 * generates moves for a bishop at the given location
	 * @param position the position of the bishop
	 * @return returns the new array of moves
	 */
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
	
	/**
	 * removes the given piece from the boardstate
	 * @param currentPiece the piece to remove
	 */
	private void removePieceFromBoardState(Piece currentPiece) {
		for(Move currentMove: currentPiece.getMoves()){
			boardStatus.removePiece(currentMove.getFinalPos(), currentPiece);
		}		
	}
	
	/**
	 * @return String representation of the current gameboard state
	 */
	private String buildDisplay(){
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
	/**
	 * generates all the values for the boardState from a blank slate
	 */
	public  void buildBoardStatus(){
		Output.debug("building boardState",3);
		boardStatus.clearBoardState();
		for(Piece currentPiece: pieces){
			generateMovesfor(currentPiece);
			addPieceToBoardState(currentPiece);
		}
	}
	/**
	 * removes the given piece from the boardState and piece array the piece is now dead
	 * @param taken piece to take
	 */
	private void takePiece(Piece taken){
		Output.debug("took " + taken, 3);
		removePieceFromBoardState(taken);
		pieces.remove(taken);
	}	
	/**
	 * performs the action necessary to make a move
	 * @param action the move to make
	 * @return returns true if the move succeeded
	 */
	abstract boolean makeMove(Move action);
	
	/**
	 * displays the board using buildDisplay()
	 */
	public String toString(){
		return buildDisplay(); 
	}
	/**
	 * adds the given move to the game log
	 * @param m move to add
	 */
	public void addMovetoLog(Move m){
		moveLog.add(m);
		boolean mated = (playerMap.get(Color.White).isInCheckMate() || playerMap.get(Color.Black).isInCheck());
		Output.printNotation(m, mated);
	}
	/**
	 * determines whether each player is in check by finding all kings and setting the player in check if any of those kings are being threatened
	 */
	private void setKingCheck(){
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
	/**
	 * tests whether a move results in check by executing the move then undoing it
	 * @param m move to test
	 * @return returns true if move can be made
	 */
	private boolean MoveResultsInCheck(Move m){
		if(execute(m)){undo(m); return false;}
		return true;
	}
	/**
	 * executes the given move
	 * @param m move to execute
	 * @return returns if the moves succeeded or not
	 */
	protected boolean execute(Move m) {
		String notation = "";
		Piece p = pieceAt(m.getOrigPos());
		int[] rookLocation = new int[2];
		int[] rookMove = new int[2];
		if(p.getPieceColor() != turn){
			Output.debug("wrong turn",1);
			return false;
		}
		switch(m.getType()){
		case Take:
			if(pieceAt(m.getFinalPos()) == null){
				Output.debug("no piece to take", 1);
			}
			break;
		case Castle:
			if(pieceAt(m.getOrigPos()) == null || pieceAt(m.getFinalPos()).getType() != PieceEnum.King){
				Output.debug("no king there", 1);
				return false;
			}
			if(m.getAffectedPiece().getType() != PieceEnum.Rook){
				Output.debug("can't castle...", 1);
				return false;
			}
			if(m.getAffectedPiece().getPosition()[1] == p.getPosition()[1]){
				Output.debug("rook in the wrong place...", 1);
			}
			break;
		case EnPassant:
			if(Arrays.equals(prevMove.getFinalPos(),m.getAffectedPiece().getPosition())){
				Output.debug("you lost your chance, sorry", 1);
				return false;
			}
			break;
		case Cover: return false;
		case Listen: return false;
		case Rubbish: return false;
		case Unknown: Output.debug("something has gone horribly wrong", 1); return false;
		}
//string builder
		String numToLet = "abcdefgh";
		if(p.getType() == PieceEnum.Pawn && (m.getType() == MoveEnum.Take || m.getType() == MoveEnum.EnPassant)){
			notation += numToLet.charAt(p.getPosition()[0]) + "x";
		} else {
			notation += p.getType().toString();
			for(Piece pieceFinder : boardStatus.getPieceList(m.getFinalPos())){
				if(pieceFinder.getPieceColor() == p.getPieceColor() && pieceFinder != p){
					if(p.getPosition()[0] != pieceFinder.getPosition()[0]){
						notation += numToLet.charAt(p.getPosition()[0]);
					} else if(p.getPosition()[1] != pieceFinder.getPosition()[1]){
						notation += p.getPosition()[1];
					} else { notation += numToLet.charAt(p.getPosition()[0]) + p.getPosition()[1];}
				}
			}
		}
		notation += numToLet.charAt(m.getFinalPos()[0]) + m.getFinalPos()[1];
//end of string builder
		switch(m.getType()){
		case Take: 
			m.setAffectedPiece(pieceAt(m.getFinalPos()));
			break;
		case EnPassant:
			int[] takenPawnLoc = new int[2];
			takenPawnLoc[0] = m.getFinalPos()[0];
			takenPawnLoc[1] = m.getOrigPos()[1];
			m.setAffectedPiece(pieceAt(takenPawnLoc));
			break;
		case Castle:
			rookMove[0] = m.getFinalPos()[0]-(m.getFinalPos()[0] - m.getOrigPos()[0])/2;
			rookMove[1] = m.getFinalPos()[1];
			rookLocation[0] = 7*(m.getFinalPos()[0]-2)/4;
			rookLocation[1] = m.getOrigPos()[1];
			m.setAffectedPiece(pieceAt(rookLocation));
			if(m.getFinalPos()[0] > m.getOrigPos()[0]){notation = "O-O";}
			else {notation = "O-O-O";}
		}
		removePieceFromBoardState(p);
		p.setPosition(m.getFinalPos());
		generateMovesfor(p);
		if(p.canCastle()){m.setOldCastle(true);}
		if(m.getType() == MoveEnum.Take || m.getType() == MoveEnum.EnPassant){
			takePiece(m.getAffectedPiece());
		}
		if(m.getType() == MoveEnum.Castle){
			removePieceFromBoardState(m.getAffectedPiece());
			m.getAffectedPiece().setPosition(rookMove);
			generateMovesfor(m.getAffectedPiece());
		}
		switch(m.getType()){
		case Move:
			update(m.getOrigPos(),m.getFinalPos());
			break;
		case Take:
			update(m.getOrigPos(),m.getFinalPos());
			break;
		case Castle:
			update(m.getOrigPos(),m.getFinalPos(),rookLocation,rookMove);
			break;
		case EnPassant:
			update(m.getOrigPos(),m.getFinalPos(),m.getAffectedPiece().getPosition());
			break;
		}
		setKingCheck();
		if(playerMap.get(p.getPieceColor()).isInCheck()){
			Output.debug("that move results in check", 1);
			undo(m);
			return false;
		}
		if(p.getType() == PieceEnum.Pawn && m.getFinalPos()[1]%7 == 0){
			p.setType(playerMap.get(p.getPieceColor()).getPromotion());
			notation += "=" + p.getType().toString();
			generateMovesfor(p);
			addPieceToBoardState(p);
			setKingCheck();
		}
		if(playerMap.get(p.getPieceColor().next()).isInCheck()){notation += "+";}
		m.setNotation(notation);
		return true;		
	}
	/**
	 * undoes the given move
	 * @param m move to undo
	 */
	private void undo(Move m) {
		Piece p = pieceAt(m.getFinalPos());
		removePieceFromBoardState(p);
		p.setPosition(m.getOrigPos());
		p.setCastle(m.isOldCastle());
		if(m.getType() == MoveEnum.Take || m.getType() == MoveEnum.EnPassant){
			pieces.add(m.getAffectedPiece());
			addPieceToBoardState(m.getAffectedPiece());
		}
		if(m.getType() == MoveEnum.Castle){
			removePieceFromBoardState(m.getAffectedPiece());
			int[] rookunMove = new int[2];
			rookunMove[0] = (m.getAffectedPiece().getPosition()[0]-3)*7;
			rookunMove[1] = m.getAffectedPiece().getPosition()[1];
			m.getAffectedPiece().setPosition(rookunMove);
			generateMovesfor(m.getAffectedPiece());
			addPieceToBoardState(m.getAffectedPiece());
		}
		generateMovesfor(p);
		addPieceToBoardState(p);
		setKingCheck();
		switch(m.getType()){
		case Move:
			update(m.getOrigPos(),m.getFinalPos());
			break;
		case Take:
			update(m.getOrigPos(),m.getFinalPos());
			break;
		case Castle:
			int[] rookLocation = new int[2];
			int[] rookMove = new int[2];
			rookMove[0] = m.getFinalPos()[0]-(m.getFinalPos()[0] - m.getOrigPos()[0])/2;
			rookMove[1] = m.getFinalPos()[1];
			rookLocation[0] = 7*(m.getFinalPos()[0]-2)/4;
			rookLocation[1] = m.getOrigPos()[1];
			update(m.getOrigPos(),m.getFinalPos(),rookLocation,rookMove);
			break;
		case EnPassant:
			update(m.getOrigPos(),m.getFinalPos(),m.getAffectedPiece().getPosition());
			break;
		}
		
		
	}
	/**
	 * finds and returns all the valid moves a player has
	 * @param currentPlayer player to find moves for
	 * @return returns the arraylist of moves that can be made
	 */
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
	/**
	 * checks for game ending scenarios
	 * @param whosTurn the player who's turn it is
	 * @return returns true if the game is over
	 */
	public boolean isGameOver(Player whosTurn){
		ArrayList<Move> allValidMoves = allValidMovesOf(whosTurn);		
		if(!whosTurn.isInCheck()){
			//more tests for staleMate
			if(moveLog.get(moveLog.size()-1).equals(moveLog.get(moveLog.size()-3)) &&
				moveLog.get(moveLog.size()-1).equals(moveLog.get(moveLog.size()-5)) &&
				moveLog.get(moveLog.size()-2).equals(moveLog.get(moveLog.size()-4)) &&
				moveLog.get(moveLog.size()-2).equals(moveLog.get(moveLog.size()-6))){
				return true;
			}
			if(pieces.size() == 2){return true;}
			if(allValidMoves.size() == 0){
				return true;
			}
			else{return false;}
		}
		if(allValidMoves.size() > 0){return false;}
		Output.debug(whosTurn + " is mated. good game", 1);
		return true;
	}
	/**
	 * @return the turn
	 */
	public Color getTurn() {
		return turn;
	}
	/**
	 * @param prevMove the prevMove to set
	 */
	public void setPrevMove(Move prevMove) {
		this.prevMove = prevMove;
	}
	/**
	 * @return the playerMap
	 */
	public Map<Color, Player> getPlayerMap() {
		return playerMap;
	}
	/**
	 * @return the pieces
	 */
	public ArrayList<Piece> getPieces() {
		return pieces;
	}
	/**
	 * @return the boardStatus
	 */
	public BoardState getBoardStatus() {
		return boardStatus;
	}
	/**
	 * @return the moveLog
	 */
	public ArrayList<Move> getMoveLog() {
		return moveLog;
	}
	/**
	 * resets the board's variables
	 */
	public void resetBoard(){
		pieces = new ArrayList<Piece>();
		turn = Color.White;
		boardStatus.clearBoardState();
		resetMoveLog();
		Output.resetOutput();
	}
	/**
	 * adds a piece to the pieces array
	 * @param p piece to add
	 */
	public void addPiece(Piece p){
		pieces.add(p);
	}
	/**
	 * returns the FEN of the board
	 * @return returns the FEN
	 */
	public String getFEN(){
		String FEN = "";
		for(int rank = 7; rank >=0; rank--){
			int blankSquares = 0;
			for(int column = 0; column < 8; column++){
				if(pieceAt(column,rank) == null){blankSquares++;}
				else{
					if(blankSquares > 0){FEN += blankSquares;}
					blankSquares = 0;
					if(pieceAt(column,rank).getPieceColor() == Color.White){
						FEN += pieceAt(column,rank).getType().toString();
					} else {
						FEN += pieceAt(column,rank).getType().toString().toLowerCase();
					}
				}
			}
			FEN += "/";
		}
		FEN += " " + turn + " ";
		if(pieceAt(4,0).canCastle() && pieceAt(7,0).canCastle()){FEN += "K";}
		if(pieceAt(4,0).canCastle() && pieceAt(0,0).canCastle()){FEN += "Q";}
		if(pieceAt(4,7).canCastle() && pieceAt(7,7).canCastle()){FEN += "k";}
		if(pieceAt(4,7).canCastle() && pieceAt(0,7).canCastle()){FEN += "q";}
		if(FEN.endsWith("")){FEN += "-";}
		FEN += " ";
		if(pieceAt(prevMove.getFinalPos()).getType() == PieceEnum.Pawn && Math.abs(prevMove.getFinalPos()[1]-prevMove.getOrigPos()[1]) == 2){
				String numToLet = "abcdefgh";
				FEN += numToLet.charAt(prevMove.getFinalPos()[0]) + (prevMove.getFinalPos()[1]-prevMove.getOrigPos()[1])/2 + prevMove.getOrigPos()[1];
		} else {FEN += "-";}
		return FEN;
	}
	
	
}
