import java.util.*;
import java.util.regex.*;

public class HumanPlayer extends Player {

	private boolean color;
	private Scanner moveReader = new Scanner(System.in);
	private Pattern p = Pattern.compile("[0-7],[0-7] [0-7][0-7]");
	private int[] lastMove = new int[4];
	
	public HumanPlayer(boolean color){
		this.color = color;
	}
	
	public int[] getMove() {
		System.out.println("Please enter a move in the following form where you are moving a piece from a,b to c,d:");
		System.out.println("a,b c,d");
		String move = "";
		boolean passedCheck = false;
		while( !passedCheck ){
			move = moveReader.next();
			passedCheck = checkMove(move);
		}
		return lastMove;
	}
	
	public int[] getLastMove() {
		return lastMove;
	}
	
	private boolean checkMove( String move ){
		Matcher m = p.matcher(move);
		if( m.matches() ) {
			lastMove[0] = move.charAt(0);
			lastMove[1] = move.charAt(2);
			lastMove[2] = move.charAt(4);
			lastMove[3] = move.charAt(6);
			return true;
		}
		return false;
	}
}