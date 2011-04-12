package newerGameLogic;

import java.util.Arrays;

import main.Output;

public class moveExecuter {
	private static Board board;
	
	/**
	 * executes the given move
	 * @param m move to execute
	 * @return returns if the moves succeeded or not
	 */
	public boolean execute(Move m,Board here) {
		board = here;
		String notation = "";
		Piece p = board.pieceAt(m.getOrigPos());
		int[] rookLocation = new int[2];
		int[] rookMove = new int[2];
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
			notation += numToLet.charAt(p.getPosition()[0]) + "x";
		} else {
			notation += p.getType().toString();
			for(Piece pieceFinder : boardStatus.getPieceList(new Position(m.getFinalPos()))){
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
		moveFactory.generateMovesfor(p,this);
		if(p.canCastle()){m.setOldCastle(true);}
		if(m.getType() == MoveEnum.Take || m.getType() == MoveEnum.EnPassant){
			takePiece(m.getAffectedPiece());
		}
		if(m.getType() == MoveEnum.Castle){
			removePieceFromBoardState(m.getAffectedPiece());
			m.getAffectedPiece().setPosition(rookMove);
			moveFactory.generateMovesfor(m.getAffectedPiece(),this);
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
			Output.debug("that move results in check", 2);
			undo(m);
			return false;
		}
		if(p.getType() == PieceEnum.Pawn && m.getFinalPos()[1]%7 == 0){
			p.setType(playerMap.get(p.getPieceColor()).getPromotion());
			notation += "=" + p.getType().toString();
			moveFactory.generateMovesfor(p,this);
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
			moveFactory.generateMovesfor(m.getAffectedPiece(),this);
			addPieceToBoardState(m.getAffectedPiece());
		}
		moveFactory.generateMovesfor(p,this);
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
}
