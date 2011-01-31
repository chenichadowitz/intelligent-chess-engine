
public abstract class board {
	piece[] pieces;
	
	piece[] getPieces() {return pieces;}
	
	int getStatusOfSquare(int[] square){
		//returns -1 if black; 0 if empty; 1 if white
		for(int searcher = 0; searcher < pieces.length; searcher ++){
			if (pieces[searcher].getPosition() == square){
				if(pieces[searcher].getColor() == true ){
					return 1;
				}
				else return -1;
			}
		}
		return 0;
	}
	
	piece[] getpieces(){
		return pieces;
	}	
	
	abstract boolean movePiece();
}
