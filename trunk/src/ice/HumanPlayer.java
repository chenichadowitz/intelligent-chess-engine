package ice;
import java.util.*;
import java.util.regex.*;

public class HumanPlayer extends Player {

	private boolean color;
	private Scanner moveReader = new Scanner(System.in);
	private Pattern p = Pattern.compile("[0-7],[0-7] [0-7],[0-7]");
	private Pattern q = Pattern.compile("[q|Q][u|U][i|I][t|T]|[e|E][x|X][i|I][t|T]");
	private int[] lastMove = new int[4];
	
	public HumanPlayer(boolean color){
		this.color = color;
	}
	
	public String toString(){
		if(color) return "White";
		else return "Black";
	}
	
	public int[] getMove() {
		/*System.out.println("Please enter a move in the following form where you are moving a piece from a,b to c,d:");
		System.out.println("a,b c,d");
		String move = "";
		*/
		int moveNums;
		/*boolean passedCheck = false;
		while( !passedCheck ){
			move = moveReader.next();
			//move = "1,2 3,4";
			passedCheck = checkMove(move);
			if (!passedCheck ) System.out.println("Please try again....");
		}*/
		while(!moveReader.hasNextInt()){
			if(moveReader.hasNext()){
				String s = moveReader.next();
				Matcher matchQuit = q.matcher(s);
				if(matchQuit.matches())	System.exit(0);
				else return new int[0];
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
		return lastMove;
	}
	
	public int[] getLastMove() {
		return lastMove;
	}
	
	private boolean checkMove( String move ){
		Matcher m = p.matcher(move);
		if( m.matches() ) {
			lastMove[0] = move.charAt(0) - '0';
			lastMove[1] = move.charAt(2) - '0';
			lastMove[2] = move.charAt(4) - '0';
			lastMove[3] = move.charAt(6) - '0';
			return true;
		}
		return false;
	}
}