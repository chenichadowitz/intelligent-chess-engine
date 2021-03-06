package gameLogic;
import java.util.*;
import java.util.regex.*;

import main.Input;

public class HumanPlayer extends Player {

	private Scanner moveReader = new Scanner(System.in);
	private Pattern pat = Pattern.compile("[a-h|A-H][1-8][a-h|A-H][1-8]");
	private Pattern q = Pattern.compile("[q|Q][u|U][i|I][t|T]|[e|E][x|X][i|I][t|T]");
	private int[] lastMove = new int[4];
	
	public HumanPlayer(boolean color){
		this.color = color;
	}
	
	public char getPromotion(){
		return Input.getPromotion();
	}
	
	public Listener getMove() {
		System.out.print("Move (e.g. 1122): ");
		int moveNums;
		while(!moveReader.hasNextInt()){
			if(moveReader.hasNext()){
				String s = moveReader.next();
				Matcher matchQuit = q.matcher(s);
				if(matchQuit.matches())	System.exit(0);
				else {
					Matcher matchPat = pat.matcher(s);
					if(matchPat.matches()){
						return ahMoveToInt(s);
					}
						return null;
				}
			}
		}
		moveNums = moveReader.nextInt();
		if(moveNums < 0){
			System.exit(0);
		}
		int c = 3;
		while(c >= 0 && moveNums != 0){
			lastMove[c] = moveNums % 10;
			c--;
			moveNums /= 10;
		}
		if(c != -1){
			while(c>-1){
				lastMove[c] = 0;
				c--;
			}
		}
		for(int dec = 0; dec < 4; dec++){lastMove[dec]--;}
		return PieceMaker.MakeMove(myBoard,lastMove);
	}
	
	public int[] getLastMove() {
		return lastMove;
	}
	
	private Listener ahMoveToInt(String s){
		s = s.toLowerCase();
		int let1 = s.charAt(0) - 'a' + 1;
		int num1 = s.charAt(1) - '0';
		int let2 = s.charAt(2) - 'a' + 1;
		int num2 = s.charAt(3) - '0';
		int[] toReturn = new int[4];
		if( (0 < let1 && let1 < 9)
			&& (0 < let2 && let2 < 9)
			&& (0 < num1 && num1 < 9)
			&& (0 < num2 && num2 < 9) ){
			//First and third chars are a letter a-h indeed
			//Second and fourth chars are a int 1-8 indeed (now translated to 0-7)
			toReturn[0] = let1-1;
			toReturn[1] = num1-1;
			toReturn[2] = let2-1;
			toReturn[3] = num2-1;
			return PieceMaker.MakeMove(myBoard,toReturn);
		} else {
			// If not, then just return an empty int[] array length 0
			return null;
		}
	}
}