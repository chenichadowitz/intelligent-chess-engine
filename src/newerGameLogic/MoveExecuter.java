package newerGameLogic;

import java.util.Arrays;

import main.Output;

public class MoveExecuter {
	
	private static Board board;
	private static Game game;
	
	public static void setGame(Game g){
		game = g;
	}
	
	/**
	 * executes the given move
	 * @param m move to execute
	 * @return returns if the moves succeeded or not
	 */
	public static boolean execute(Move m, Board b) {
		board = b;
		String notation = "";
		Piece p = board.pieceAt(m.getOrigPos());
		Position rookNew;
		Position rookOld;
		if(p.getPieceColor() != board.getTurn()){
			Output.debug("wrong turn",1);
			return false;
		}
		switch(m.getType()){
		case Take:
			if(board.pieceAt(m.getFinalPos()) == null){
				Output.debug("no piece to take", 2);
			}
			break;
		case Castle:
			if(board.pieceAt(m.getOrigPos()) == null || board.pieceAt(m.getFinalPos()).getType() != PieceEnum.King){
				Output.debug("no king there", 2);
				return false;
			}
			if(m.getAffectedPiece().getType() != PieceEnum.Rook){
				Output.debug("can't castle...", 2);
				return false;
			}
			if(m.getAffectedPiece().getPosition().getY() == p.getPosition().getY()){
				Output.debug("rook in the wrong place...", 2);
			}
			break;
		case EnPassant:
			if(board.getPrevMove().getFinalPos().equals(m.getAffectedPiece().getPosition())){
				Output.debug("you lost your chance, sorry", 2);
				return false;
			}
			break;
		case Cover: Output.debug("that move cannot be executed", 2);return false;
		case Listen:Output.debug("that move cannot be executed", 2);return false;
		case Rubbish: Output.debug("that move cannot be executed", 2);return false;
		case Unknown: Output.debug("something has gone horribly wrong", 1); return false;
		}
//string builder
		String numToLet = "abcdefgh";
		if(p.getType() == PieceEnum.Pawn && (m.getType() == MoveEnum.Take || m.getType() == MoveEnum.EnPassant)){
			notation += numToLet.charAt(p.getPosition().getX()) + "x";
		} else {
			notation += p.getType().toString();
			for(Piece pieceFinder : board.pieces.getAffectedPieces(m.getFinalPos())){
				if(pieceFinder.getPieceColor() == p.getPieceColor() && pieceFinder != p){
					if(p.getPosition().getX() != pieceFinder.getPosition().getX()){
						notation += numToLet.charAt(p.getPosition().getX());
					} else if(p.getPosition().getY() != pieceFinder.getPosition().getY()){
						notation += p.getPosition().getY();
					} else { notation += numToLet.charAt(p.getPosition().getX()) + p.getPosition().getY();}
				}
			}
		}
		notation += numToLet.charAt(m.getFinalPos().getX()) + m.getFinalPos().getY();
//end of string builder
		
		
		switch(m.getType()){
			case Take: 
				m.setAffectedPiece(board.pieceAt(m.getFinalPos()));
				break;
			case EnPassant:
				int[] takenPawnLoc = new int[2];
				Position takenPawnPos = new Position(m.getFinalPos().getX(), m.getOrigPos().getY());
				m.setAffectedPiece(board.pieceAt(takenPawnPos));
				break;
			case Castle:
				rookNew = new Position(m.getFinalPos().getX()-(m.getFinalPos().getX() - m.getOrigPos().getX())/2, m.getFinalPos().getY());
				rookOld= new Position(7*(m.getFinalPos().getX()-2)/4,m.getOrigPos().getY());
				m.setAffectedPiece(board.pieceAt(rookOld));
				if(m.getFinalPos().getX() > m.getOrigPos().getX()){notation = "O-O";}
				else {notation = "O-O-O";}
		}		
		if(p.canCastle()){m.setOldCastle(true);}
		
		if(m.getType() == MoveEnum.Take || m.getType() == MoveEnum.EnPassant){
			board.pieces.removePiece(m.getAffectedPiece());
		}
		board.pieces.makeMove(m);

		if(m.getType() == MoveEnum.Castle){
			board.pieces.makeMove(new Move(rookOld, rookNew, MoveEnum.Move));
		}
		
		board.pieces.notifyPieces(m.getAffectedPositions());
		
		board.setKingCheck();
		
		if(board.playerStatus.isInCheck(p.getPieceColor())){
			Output.debug("that move results in check", 2);
			undo(m);
			return false;
		}
		if(p.getType() == PieceEnum.Pawn && m.getFinalPos().getY()%7 == 0){
			p.setType(game.getPlayer(p.getPieceColor()).getPromotion());//ASK BOARD FOR PROMOTION
			notation += "=" + p.getType().toString();
			MoveFactory.generateMovesfor(p,this);
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
	private static void undo(Move m) {
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
			MoveFactory.generateMovesfor(m.getAffectedPiece(),this);
			addPieceToBoardState(m.getAffectedPiece());
		}
		MoveFactory.generateMovesfor(p,this);
		addPieceToBoardState(p);
		setKingCheck();
		switch(m.getType()){
		case Move:
			board.update(m.getOrigPos(),m.getFinalPos());
			break;
		case Take:
			board.update(m.getOrigPos(),m.getFinalPos());
			break;
		case Castle:
			int[] rookLocation = new int[2];
			int[] rookMove = new int[2];
			rookMove[0] = m.getFinalPos()[0]-(m.getFinalPos()[0] - m.getOrigPos()[0])/2;
			rookMove[1] = m.getFinalPos()[1];
			rookLocation[0] = 7*(m.getFinalPos()[0]-2)/4;
			rookLocation[1] = m.getOrigPos()[1];
			board.update(m.getOrigPos(),m.getFinalPos(),rookLocation,rookMove);
			break;
		case EnPassant:
			board.update(m.getOrigPos(),m.getFinalPos(),m.getAffectedPiece().getPosition());
			break;
		}
		
		
	}
	
	public static boolean ifValidMove(Move m, Board board){
		if(execute(m, board)){
			undo(m);
			return true;
		}
		return false;
	}
}
