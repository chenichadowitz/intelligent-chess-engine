package ice;

import java.util.ArrayList;

public class King extends Piece{
	protected ArrayList<Integer[]> listeningSquares = new ArrayList<Integer[]>(); //[[x1,y1][x2,y2]...]
	public King(boolean player, int xwhere, int ywhere, Board onWhat){
		color = player;
		position[0] = xwhere;
		position[1] = ywhere;
		currentBoard = onWhat;
		value = 0;
		pieceType = "K";
		castle = true;
	}
	boolean move(int[] newsquare){
		int origPos = position[0];
		if(super.move(newsquare)){
			castle = false;
			if(Math.abs(position[0]- origPos) == 2){
				int[] rookLocation ={7*(newsquare[0]-2)/4,position[1]};
				int[] rookMove     ={newsquare[0]-(newsquare[0] - origPos)/2,newsquare[1]};
				currentBoard.pieceAt(rookLocation).move(rookMove);
				currentBoard.update(rookLocation);
				currentBoard.update(rookMove);
			}	
			System.out.println(castle);
			return true;
		}
		else{return false;}
	}
	public void generateMoves(){
		super.generateMoves();
		listeningSquares =  new ArrayList<Integer[]>();
		processSquare(position[0] -1,position[1] -1);
		processSquare(position[0] -1,position[1]   );
		processSquare(position[0] -1,position[1] +1);
		processSquare(position[0]   ,position[1] -1);
		processSquare(position[0]   ,position[1] +1);
		processSquare(position[0] +1,position[1] -1);
		processSquare(position[0] +1,position[1]   );
		processSquare(position[0] +1,position[1] +1);
		if(castle){
			Driver.debug("checking castling", 3);
			//castle king side
			int delta = 1;
			int[] edge = {position[0],(int)(3.5+3.5*delta)};
			if(currentBoard.pieceAt(edge) != null && currentBoard.pieceAt(edge).canCastle()){
				int[] possibleMove = {position[0], position[1], position[0]+delta,position[1]};
				if(!willBeInCheck(color, possibleMove)){
					int[]     square = {position[0] +2*delta, position[1]};
					boolean[] status = currentBoard.statusOfSquare(square);
					if (!status[0] && status[1]){
						moves.add(new Integer[] {square[0],square[1]});
					}
					else{
						listeningSquares.add(new Integer[] {square[0],square[1]});
					}
				}
			}
			//castle queen side
			delta = -1;
			edge[1] = (int)(3.5+3.5*delta);
			if(currentBoard.pieceAt(edge) != null && currentBoard.pieceAt(edge).canCastle()){
				int[] possibleMove = {position[0], position[1], position[0]+delta,position[1]};
				if(!willBeInCheck(color, possibleMove)){
					int[]     square = {position[0] +2*delta, position[1]};
					boolean[] status = currentBoard.statusOfSquare(square);
					if (!status[0] && status[1]){
						moves.add(new Integer[] {square[0],square[1]});
					}
					else{
						listeningSquares.add(new Integer[] {square[0],square[1]});
					}
				}
			}
		}
	}
	@SuppressWarnings("unchecked")
	public Piece clone(){
		King newPiece = new King(color,position[0],position[1],currentBoard);
		newPiece.cover = (ArrayList<Integer[]>) cover.clone();
		newPiece.moves = (ArrayList<Integer[]>) moves.clone();
		newPiece.takes = (ArrayList<Integer[]>) takes.clone();
		newPiece.listeningSquares = (ArrayList<Integer[]>) listeningSquares.clone();
		return newPiece;
	}
	public void addToBoardState(){
		super.addToBoardState();
		if(castle){
			for(Integer[] square: listeningSquares){
				currentBoard.boardState[square[0]][square[1]].add(this);
			}
		}
	}
	public void removeFromBoardState(){
		super.removeFromBoardState();
		if(castle){
			for(Integer[] square: listeningSquares){
				currentBoard.boardState[square[0]][square[1]].remove(this);
			}
		}
	}
}
