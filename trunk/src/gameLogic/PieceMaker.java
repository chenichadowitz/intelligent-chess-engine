package gameLogic;

import main.Debug;

public class PieceMaker {
	public static Listener MakeMove(Board place, int x1, int y1, int x2, int y2){
		int[] FinalPos = {x2,y2};
		int[] OrigPos  = {x1,y1};

		if(place.pieceAt(FinalPos) != null){
			if(place.pieceAt(FinalPos).color == place.pieceAt(OrigPos).color){
				return new Cover(x1,y1,x2,y2,place);
			}
			else{
				return new Take(x1,y1,x2,y2,place);
			}
		}else{
			if(place.pieceAt(OrigPos).type() == 'K' && Math.abs(FinalPos[0] - OrigPos[0]) == 2){
				return new Castle(x1,y1,x2,y2,place);
			}	
			return new Move(x1,y1,x2,y2,place);
		}
	}
	public static Listener MakeMove(Board place, int[] square1, int[] square2){
			return MakeMove(place, square1[0],square1[1],square2[0], square2[1]);
	}
	public static Listener MakeMove(Board place, int[] squareAB){
		return MakeMove(place, squareAB[0],squareAB[1],squareAB[2], squareAB[3]);
	}
	public static Listener MakeMove(Board place, Piece mover, int[] newSquare){
		return MakeMove(place,mover.position[0],mover.position[1],newSquare[0],newSquare[1]);
	}
	public static Listener MakeMove(Board place, Piece mover, int x, int y){
		return MakeMove(place,mover.position[0], mover.position[1],x,y);
	}
}
